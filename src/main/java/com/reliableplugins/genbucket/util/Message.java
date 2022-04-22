package com.reliableplugins.genbucket.util;

public enum Message {

    ERROR_PERMISSION("error-permission", "&cNot enough permissions."),
    NOT_ENOUGH_MONEY("error-no-money", "&cYou do not have enough money to place a GenBucket!"),
    WORLD_NOT_WHITELISTED("error-whitelist", "&cThis world is currently not whitelisted for the /gen test command!"),
    ERROR_NOT_PLAYER("error-not-player", "&cOnly players may execute this command"),
    GEN_WILDERNESS("gen-wilderness", "&c You cannot use a GenBucket in Wilderness!"),
    PLAYER_CANT_GEN_HERE("player-cant-gen-here", "&c You cannot use a GenBucket here!");

    private final String config;
    private String text;

    Message(String config, String text) {
        this.config = config;
        this.text = text;
    }

    public String getMessage() {
        return Util.color(this.text);
    }

    public void setMessage(String text) {
        this.text = text;
    }

    public String getConfig() {
        return config;
    }

}