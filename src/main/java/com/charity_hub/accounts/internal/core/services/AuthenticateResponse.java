package com.charity_hub.accounts.internal.core.services;

public record AuthenticateResponse(String accessToken, String refreshToken) {
}