package com.charity_hub.application.authentication;

public record AuthenticateResponse(String accessToken, String refreshToken) {
}