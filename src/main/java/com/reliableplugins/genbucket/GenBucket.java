package com.reliableplugins.genbucket;

import com.reliableplugins.genbucket.command.impl.BaseCommand;
import com.reliableplugins.genbucket.manager.GenBucketManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GenBucket extends JavaPlugin {

    private BaseCommand baseCommand;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getServer().getConsoleSender().sendMessage("Loaded GenBucket");
        this.baseCommand = new BaseCommand(this);
        GenBucketManager.loadGenBuckets(getConfig());


    }

}
