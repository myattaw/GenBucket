package com.reliableplugins.genbucket;

import com.reliableplugins.genbucket.command.impl.BaseCommand;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.listener.InventoryListener;
import com.reliableplugins.genbucket.manager.HookManager;
import com.reliableplugins.genbucket.menu.MainMenu;
import com.reliableplugins.genbucket.runnable.GeneratorTask;
import com.reliableplugins.genbucket.listener.PlayerListener;
import com.reliableplugins.genbucket.manager.GenBucketManager;
import com.reliableplugins.genbucket.nms.NMSHandler;
import com.reliableplugins.genbucket.nms.nms.Version_1_8_R3;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class GenBucket extends JavaPlugin {

    private BaseCommand baseCommand;
    private NMSHandler nmsHandler;
    private HookManager hookManager;

    private Map<String, Generator> generatorMap = new HashMap<>();

    private final int TICK_SPEED = getConfig().getInt("settings.tick-speed");

    private MainMenu mainMenu;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        this.nmsHandler = setupNMS();
        this.baseCommand = new BaseCommand(this);
        this.hookManager = new HookManager(this);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new GeneratorTask(this), TICK_SPEED, TICK_SPEED);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        generatorMap = GenBucketManager.loadGenBuckets(getConfig(), this);

        this.mainMenu = new MainMenu(this).init();
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

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public Map<String, Generator> getGeneratorMap() {
        return generatorMap;
    }

    public HookManager getHookManager() {
        return hookManager;
    }

    public NMSHandler getNMSHandler() {
        return nmsHandler;
    }
}
