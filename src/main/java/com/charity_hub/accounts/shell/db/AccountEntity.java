package com.charity_hub.accounts.shell.db;

import com.charity_hub.accounts.core.models.account.*;
import com.charity_hub.accounts.core.models.account.Permission;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record AccountEntity(@Id String accountId, String mobileNumber, String fullName, String photoUrl,
                            boolean blocked,
                            long joinedDate, long lastUpdated, List<String> permissions, List<DeviceEntity> devices) {

    public static AccountEntity from(Account domain) {
        return new AccountEntity(
                domain.getId().value().toString(),
                domain.getMobileNumber().value(),
                domain.getFullName() != null ? domain.getFullName().value() : null,
                domain.getPhotoUrl() != null ? domain.getPhotoUrl().value() : null,
                domain.isBlocked(),
                domain.getJoinedDate().getTime(),
                new Date().getTime(),
                domain.getPermissions().stream()
                        .map(Permission::name)
                        .collect(Collectors.toList()),
                domain.getDevices().stream()
                        .map(DeviceEntity::from)
                        .collect(Collectors.toList())
        );
    }

    public Account toDomain() {
        try {
            return new Account(
                    new AccountId(UUID.fromString(accountId())),
                    MobileNumber.of(mobileNumber()),
                    devices().stream()
                            .map(DeviceEntity::toDomain)
                            .collect(Collectors.toList()),
                    permissions().stream()
                            .map(Permission::fromString)
                            .collect(Collectors.toList()),
                    fullName() != null ?
                            FullName.create(fullName()) : null,
                    photoUrl() != null ?
                            PhotoUrl.create(photoUrl()) : null,
                    blocked(),
                    new Date(joinedDate())
            );
        } catch (Exception exc) {
            throw new RuntimeException("Could not map identity from the database - " + exc.getMessage());
        }
    }
}