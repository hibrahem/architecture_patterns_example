package com.charity_hub.accounts.internal.shell.mappers;

import com.charity_hub.accounts.internal.core.models.account.Account;
import com.charity_hub.accounts.internal.core.models.device.Device;
import com.charity_hub.accounts.internal.shell.controllers.common.AccessTokenPayload;
import com.charity_hub.accounts.internal.shell.controllers.authconfig.JWTPayload;
import com.charity_hub.accounts.internal.shell.controllers.common.RefreshTokenPayload;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class TokenMapper {

    public static JWTPayload toAccessToken(Account account, Device device, Date expireAt) {
        Logger.getAnonymousLogger().info("jwt id " + UUID.randomUUID());

        return new AccessTokenPayload(
                device.getDeviceType().value(),           // audience
                UUID.randomUUID().toString(),             // jwtId
                expireAt,                                 // expireAt
                new Date(),                               // issuedAt
                account.getId().value().toString(),       // uuid
                account.getFullName() != null ?           // fullName
                        account.getFullName().value() : null,
                account.getPhotoUrl() != null ?           // photoUrl
                        account.getPhotoUrl().value() : null,
                account.isBlocked(),                      // blocked
                account.getMobileNumber().value(),        // mobileNumber
                device.getDeviceId().value(),             // deviceId
                account.getPermissions().stream()         // permissions
                        .map(Enum::name)
                        .collect(Collectors.toList())
        );
    }

    public static JWTPayload toRefreshToken(Account account, Device device, Date expireAt) {
        return new RefreshTokenPayload(
                device.getDeviceType().value(),           // audience
                UUID.randomUUID().toString(),             // jwtId
                expireAt,                                 // expireAt
                new Date(),                               // issuedAt
                account.getId().value().toString(),       // uuid
                account.getMobileNumber().value(),        // mobileNumber
                device.getDeviceId().value()             // deviceId
        );
    }
}