package com.charity_hub.domain.models.device;

import lombok.Getter;

import java.util.Date;

@Getter
public class Device {
    // Getters

    private final DeviceId deviceId;
    private final DeviceType deviceType;
    private RefreshToken refreshToken;
    private FCMToken fcmToken;
    private Date lastAccessTime;

    private Device(
            DeviceId deviceId,
            DeviceType deviceType,
            Date lastAccessTime,
            RefreshToken refreshToken,
            FCMToken fcmToken
    ) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.lastAccessTime = lastAccessTime;
        this.refreshToken = refreshToken;
        this.fcmToken = fcmToken;
    }

    public static Device of(String aDeviceId, String aDeviceType, String aRefreshToken) {
        return create(aDeviceId, aDeviceType, aRefreshToken);
    }

    public static Device of(String aDeviceId, String aDeviceType) {
        return create(aDeviceId, aDeviceType, null);
    }

    public static Device create(
            String aDeviceId,
            String aDeviceType,
            String aRefreshToken,
            String aFCMToken,
            Long aLastAccessTime
    ) {
        DeviceType deviceType = DeviceType.create(aDeviceType);
        DeviceId deviceId = DeviceId.create(aDeviceId);

        RefreshToken refreshToken = aRefreshToken != null ? RefreshToken.create(aRefreshToken) : null;

        FCMToken fcmToken = aFCMToken != null ? FCMToken.create(aFCMToken) : null;

        Date lastAccessTime = aLastAccessTime != null ? new Date(aLastAccessTime) : new Date();

        return new Device(deviceId, deviceType, lastAccessTime, refreshToken, fcmToken);
    }

    public static Device create(String aDeviceId, String aDeviceType, String aRefreshToken) {
        return create(aDeviceId, aDeviceType, aRefreshToken, null, null);
    }

    public void updateRefreshToken(String newRefreshToken) {
        this.refreshToken = RefreshToken.create(newRefreshToken);
        this.lastAccessTime = new Date();
    }

    public void updateFCMToken(String fcmToken) {
        this.fcmToken = FCMToken.create(fcmToken);
        this.lastAccessTime = new Date();
    }

    public Date getLastAccessTime() {
        return new Date(lastAccessTime.getTime());
    }

    public DeviceId getDeviceId() {
        return deviceId;
    }
}