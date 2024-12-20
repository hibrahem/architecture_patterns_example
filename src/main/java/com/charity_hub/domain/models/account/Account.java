package com.charity_hub.domain.models.account;

import com.charity_hub.domain.contracts.IJWTGenerator;
import com.charity_hub.domain.models.device.Device;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
public class Account {
    // Private fields with getters
    private final MobileNumber mobileNumber;
    private final List<Permission> permissions;
    private final AccountId id;
    private FullName fullName;
    private PhotoUrl photoUrl;
    private final Date joinedDate;
    public final List<Device> devices;
    private boolean blocked;

    public Account(
            AccountId id,
            MobileNumber mobileNumber,
            List<Device> devices,
            List<Permission> permissions,
            FullName fullName,
            PhotoUrl photoUrl,
            boolean blocked,
            Date joinedDate
    ) {
        this.id = id;
        this.mobileNumber = mobileNumber;
        this.devices = new ArrayList<>(devices);
        this.permissions = permissions != null ? new ArrayList<>(permissions) :
                new ArrayList<>(Collections.singletonList(Permission.VIEW));
        this.fullName = fullName;
        this.photoUrl = photoUrl;
        this.blocked = blocked;
        this.joinedDate = joinedDate != null ? joinedDate : new Date();
    }

    public static Account newAccount(String mobileNumber, boolean isAdmin, String aDeviceType, String aDeviceId) {
        Device device = Device.of(aDeviceId, aDeviceType);

        Account account = new Account(
                AccountId.generate(),
                MobileNumber.of(mobileNumber),
                new ArrayList<>(Collections.singletonList(device)),
                new ArrayList<>(Collections.singletonList(Permission.of(isAdmin))),
                null,
                null,
                false,
                new Date()
        );

        return account;
    }

    public Tokens authenticate(IJWTGenerator jwtGenerator, String deviceId, String aDeviceType) {
        var usedDevice = usedDevice(deviceId, aDeviceType);

        var refreshToken = jwtGenerator.generateRefreshToken(this, usedDevice);
        usedDevice.updateRefreshToken(refreshToken);

        var accessToken = jwtGenerator.generateAccessToken(this, usedDevice);
        return new Tokens(refreshToken, accessToken);
    }

    private Device usedDevice(String deviceId, String aDeviceType) {
        Device usedDevice = getDevice(deviceId);
        if (usedDevice == null) {
            usedDevice = Device.of(deviceId, aDeviceType);
            devices.add(usedDevice);
        }
        return usedDevice;
    }

    private Device getDevice(String deviceId) {
        return devices.stream()
                .filter(device -> device.getDeviceId().value().equals(deviceId))
                .findFirst()
                .orElse(null);
    }


    // Private helper methods

    // Getters
    public List<Permission> getPermissions() {
        return Collections.unmodifiableList(permissions);
    }

    public Date getJoinedDate() {
        return new Date(joinedDate.getTime());
    }

    public List<Device> getDevices() {
        return Collections.unmodifiableList(devices);
    }

    public MobileNumber getMobileNumber() {
        return mobileNumber;
    }
}