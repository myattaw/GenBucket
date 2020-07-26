package com.reliableplugins.genbucket;

import com.reliableplugins.genbucket.command.impl.BaseCommand;
import com.reliableplugins.genbucket.runnable.GeneratorTask;
import com.reliableplugins.genbucket.listener.PlayerListener;
import com.reliableplugins.genbucket.manager.GenBucketManager;
import com.reliableplugins.genbucket.nms.NMSHandler;
import com.reliableplugins.genbucket.nms.nms.Version_1_8_R3;
import org.bukkit.plugin.java.JavaPlugin;

public class GenBucket extends JavaPlugin {

    private BaseCommand baseCommand;
    private NMSHandler nmsHandler;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        this.nmsHandler = setupNMS();
        this.baseCommand = new BaseCommand(this);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new GeneratorTask(), 20L, 20L);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        GenBucketManager.loadGenBuckets(getConfig());
    }

    public NMSHandler setupNMS()
    {
        String version = getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        switch (version)
        {
            case "v1_8_R3":
                return new Version_1_8_R3();
            default:
                return null;
        }
    }

    public NMSHandler getNMSHandler() {
        return nmsHandler;
    }
}
