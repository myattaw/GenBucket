package com.reliableplugins.genbucket.util;

public enum Message {


    ERROR_PERMISSION(Util.color("&cNot enough permissions.")),
    ERROR_NOT_PLAYER(Util.color("&cOnly players may execute this command"));

    private final String text;

    Message(String text) {
        this.text = text;
    }

    public String getMessage() {
        return this.text;
    }
}