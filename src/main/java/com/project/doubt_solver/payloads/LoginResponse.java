package com.project.doubt_solver.payloads;

public class LoginResponse {
    private String token;
    private UserDto user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
    public LoginResponse(String token, UserDto user) {
        super();
        this.token = token;
        this.user = user;
    }
    public LoginResponse(String token) {
        this.token = token;
    }
}
