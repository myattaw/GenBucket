package com.reliableplugins.genbucket.command;

import com.google.common.collect.Sets;
import com.reliableplugins.genbucket.GenBucket;
import org.bukkit.command.CommandSender;

import java.util.Set;

public abstract class AbstractCommand {

    private final String label;
    private final String[] alias;
    private final String permission;
    private final String description;
    private final boolean playerRequired;

    protected GenBucket plugin;

    public AbstractCommand() {
        this.label = getClass().getAnnotation(CommandBuilder.class).label();
        this.alias = getClass().getAnnotation(CommandBuilder.class).alias();
        this.permission = getClass().getAnnotation(CommandBuilder.class).permission();
        this.description = getClass().getAnnotation(CommandBuilder.class).description();
        this.playerRequired = getClass().getAnnotation(CommandBuilder.class).playerRequired();
    }

    public abstract void execute(CommandSender sender, String[] args);

    public String getLabel() {
        return label;
    }

    public Set<String> getAlias() {
        return Sets.newHashSet(alias);
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public GenBucket getPlugin() {
        return plugin;
    }

    public void setPlugin(GenBucket plugin) {
        this.plugin = plugin;
    }

    public boolean hasPermission() {
        return permission.length() != 0;
    }

    public boolean isPlayerRequired() {
        return playerRequired;
    }
}