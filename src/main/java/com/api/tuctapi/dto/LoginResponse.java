package com.api.tuctapi.dto;

public class LoginResponse {
    private String token;
    private String type;
    private long expiresIn;

    public LoginResponse(
            String token,
            String type,
            long expiresIn) {

        this.token = token;
        this.type = type;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}
