package com.reliableplugins.genbucket.util;

public enum Message {

    ERROR_PERMISSION(Util.color("&cNot enough permissions.")),
    NOT_ENOUGH_MONEY(Util.color("&cYou do not have enough money to place a GenBucket!")),
    WORLD_NOT_WHITELISTED(Util.color("&cThis world is currently not whitelisted for the /gen test command!")),
    ERROR_NOT_PLAYER(Util.color("&cOnly players may execute this command"));

    private final String text;

    Message(String text) {
        this.text = text;
    }

    public String getMessage() {
        return this.text;
    }
}