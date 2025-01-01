package com.charity_hub.accounts.internal.shell.controllers.common;

import com.charity_hub.accounts.internal.shell.controllers.authconfig.JWTPayload;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RefreshTokenPayload extends JWTPayload {
    private final String uuid;
    private final String mobileNumber;
    private final String deviceId;

    public RefreshTokenPayload(
            String audience,
            String jwtId,
            Date expireAt,
            Date issuedAt,
            String uuid,
            String mobileNumber,
            String deviceId
    ) {
        super(
                "refreshToken",
                audience,
                jwtId,
                expireAt,
                issuedAt
        );
        this.uuid = uuid;
        this.mobileNumber = mobileNumber;
        this.deviceId = deviceId;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("type", getType());
        map.put("mobileNumber", mobileNumber);
        map.put("device_id", deviceId);
        return map;
    }

    public static RefreshTokenPayload fromPayload(Claims payload) {
        return new RefreshTokenPayload(
                payload.getAudience().iterator().next(),
                payload.getId(),
                payload.getExpiration(),
                payload.getIssuedAt(),
                getClaimString(payload, "uuid"),
                getClaimString(payload, "mobileNumber"),
                getClaimString(payload, "device_id")
        );
    }

    private static String getClaimString(Claims claims, String key) {
        Object value = claims.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Required claim '" + key + "' is missing");
        }
        return value.toString();
    }

    // Getters
    public String getUuid() {
        return uuid;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }
}