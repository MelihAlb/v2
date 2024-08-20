package com.soguk.soguk.controllers;

public class UserRegistrationRequest {
    private String nick;
    private String password;

    // Getter ve Setter metodları
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
