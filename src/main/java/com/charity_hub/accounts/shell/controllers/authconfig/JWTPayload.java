package com.charity_hub.accounts.shell.controllers.authconfig;

import lombok.Getter;

import java.util.Date;
import java.util.Map;

@Getter
public abstract class JWTPayload {
    private final String issuer;
    private final String subject;
    private final String type;
    private final String audience;
    private final String jwtId;
    private final Date expireAt;
    private final Date issuedAt;

    protected JWTPayload(
            String type,
            String audience,
            String jwtId,
            Date expireAt,
            Date issuedAt
    ) {
        this.issuer ="https://tech-mentors.net";
        this.subject = "authentication";
        this.type = type;
        this.audience = audience;
        this.jwtId = jwtId;
        this.expireAt = expireAt;
        this.issuedAt = issuedAt;
    }

    public abstract Map<String, Object> toMap();

    public Date getExpireAt() {
        return new Date(expireAt.getTime());
    }

    public Date getIssuedAt() {
        return new Date(issuedAt.getTime());
    }
}