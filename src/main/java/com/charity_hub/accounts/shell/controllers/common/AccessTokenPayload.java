package com.charity_hub.accounts.shell.controllers.common;

import com.charity_hub.accounts.core.models.account.Permission;
import com.charity_hub.accounts.shell.controllers.authconfig.JWTPayload;
import io.jsonwebtoken.Claims;
import lombok.Getter;

import java.util.*;

/**
 * See the list of standard claims here <a href="http://www.iana.org/assignments/jwt/jwt.xhtml">...</a>
 * @ issuer    The "iss" (issuer) claim identifies the principal that issued the JWT.
 * @ subject   The "sub" (subject) claim identifies the principal that is the subject of the JWT.
 * @ audience  The "aud" (audience) claim identifies the recipients that the JWT is intended for.
 * @ jwtId     The "jti" (JWT ID) claim provides a unique identifier for the JWT.
 * @ expireAt  The "exp" (expiration time) claim identifies the expiration time on or after which the JWT MUST NOT be accepted for processing.
 * @ issuedAt  The "iat" (issued at) claim identifies the time at which the JWT was issued.
 */
@Getter
public class AccessTokenPayload extends JWTPayload {
    private final String uuid;
    private final String fullName;
    private final String photoUrl;
    private final boolean blocked;
    private final String mobileNumber;
    private final String deviceId;
    private final List<String> permissions;

    public AccessTokenPayload(
            String audience,         // audience
            String jwtId,            // jwtId
            Date expireAt,           // expireAt
            Date issuedAt,           // issuedAt
            String uuid,             // uuid
            String fullName,         // fullName
            String photoUrl,         // photoUrl
            boolean blocked,         // blocked
            String mobileNumber,     // mobileNumber
            String deviceId,         // deviceId
            List<String> permissions // permissions
    ) {
        super(
           "accessToken",
            audience,
            jwtId,
            expireAt,
            issuedAt
        );
        this.uuid = uuid;
        this.fullName = fullName;
        this.photoUrl = photoUrl;
        this.blocked = blocked;
        this.mobileNumber = mobileNumber;
        this.deviceId = deviceId;
        this.permissions = permissions;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> userData = new HashMap<>();
        userData.put("uuid", uuid);
        userData.put("blocked", blocked);
        userData.put("type", getType());
        userData.put("device_id", deviceId);
        userData.put("mobile_number", mobileNumber);
        userData.put("permissions", permissions);
        
        if (fullName != null) {
            userData.put("full_name", fullName);
        }
        if (photoUrl != null) {
            userData.put("photo_url", photoUrl);
        }
        
        return new HashMap<>(userData);
    }

    public UUID getUserId() {
        return UUID.fromString(uuid);
    }

    public boolean hasPermission(List<Permission> permissions) {
        for (Permission permission : permissions) {
            if (this.permissions.contains(permission.name())) {
                return true;
            }
        }
        return false;
    }

    public static AccessTokenPayload fromPayload(Claims payload) {
        return new AccessTokenPayload(
            payload.getAudience().iterator().next(),
            payload.getId(),
            payload.getExpiration(),
            payload.getIssuedAt(),
            getClaimString(payload, "uuid"),
            getClaimString(payload, "full_name"),
            getClaimString(payload, "photo_url"),
            getClaimBoolean(payload, "blocked"),
            getClaimString(payload, "mobile_number"),
            getClaimString(payload, "device_id"),
            getClaimArray(payload, "permissions")
        );
    }

    private static String getClaimString(Claims claims, String key) {
        Object value = claims.get(key);
        return value != null ? value.toString() : null;
    }

    private static boolean getClaimBoolean(Claims claims, String key) {
        Object value = claims.get(key);
        return value != null && 
               value.toString().toLowerCase(Locale.getDefault()).equals("true");
    }

    @SuppressWarnings("unchecked")
    private static List<String> getClaimArray(Claims claims, String key) {
        Object value = claims.get(key);
        return value instanceof ArrayList ? (ArrayList<String>) value : new ArrayList<>();
    }

    // Getters
    public List<String> getPermissions() {
        return Collections.unmodifiableList(permissions);
    }
}