package com.charity_hub.accounts.core.services;

public record AuthenticateResponse(String accessToken, String refreshToken) {
}