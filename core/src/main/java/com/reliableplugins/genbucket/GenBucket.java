package com.reliableplugins.genbucket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reliableplugins.genbucket.command.impl.BaseCommand;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.generator.data.GeneratorType;
import com.reliableplugins.genbucket.listener.InventoryListener;
import com.reliableplugins.genbucket.listener.PlayerListener;
import com.reliableplugins.genbucket.manager.GenBucketManager;
import com.reliableplugins.genbucket.manager.HookManager;
import com.reliableplugins.genbucket.menu.MainMenu;
import com.reliableplugins.genbucket.nms.NMSAdapter;
import com.reliableplugins.genbucket.nms.impl.UnknownVersion;
import com.reliableplugins.genbucket.nms.impl.Version_v1_21_R4;
import com.reliableplugins.genbucket.nms.impl.Version_v1_21_R5;
import com.reliableplugins.genbucket.nms.impl.Version_v1_8_R3;
import com.reliableplugins.genbucket.runnable.GeneratorTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class GenBucket extends JavaPlugin {

    public final int verticalGenSwitchY = getConfig().getInt("settings.vertical-switch");
    private final int tickSpeed = getConfig().getInt("settings.tick-speed");
    @Getter
    private final int minimumHeight = getConfig().getInt("settings.minimum-height");

    public final List<String> worldWhitelist = getConfig().getStringList("settings.test-command.whitelisted-worlds");
    @Getter
    private BaseCommand baseCommand;
    @Getter
    public static GenBucket instance;
    private NMSAdapter nmsAdapter;
    @Getter
    private HookManager hookManager;
    @Getter
    private GenBucketManager genBucketManager;
    private Gson gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().excludeFieldsWithoutExposeAnnotation().disableHtmlEscaping().create();
    @Getter
    private Map<String, Generator> generatorMap = new HashMap<>();
    @Getter
    private MainMenu mainMenu;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        reloadConfig();

        if (getConfig().getBoolean("settings.optimize-block-place")) {
            this.nmsAdapter = setupNMS();
        }

        this.baseCommand = new BaseCommand(this);
        this.hookManager = new HookManager(this);
        this.genBucketManager = new GenBucketManager();

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new GeneratorTask(this), tickSpeed, tickSpeed);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        generatorMap = GenBucketManager.loadGenBuckets(getConfig(), this);
        GenBucketManager.loadMessages(getConfig());

        this.mainMenu = new MainMenu(this).init();
    }

    @Override
    public void onDisable() {
        Map<String, Set<GeneratorData>> locations = new HashMap<>();
        for (Map.Entry<String, Generator> generator : generatorMap.entrySet()) {
            if (generator.getValue().getGeneratorType() == GeneratorType.HORIZONTAL) continue;
            Set<GeneratorData> oldLocations = new HashSet<>();
            generator.getValue().getLocations().values().forEach(oldLocations::addAll);
            locations.put(generator.getKey(), oldLocations);
        }
        try (FileWriter writer = new FileWriter(this.getDataFolder() + File.separator + "genbucket-data.json")) {
            gson.toJson(locations, writer);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to save genbucket data!");
        }
    }

    public NMSAdapter setupNMS() {
        String version = getConfig().getString("settings.version", "v1_8_R3");
        switch (version) {
            case "v1_8_R3": return new Version_v1_8_R3();
            case "v1_21_R4": return new Version_v1_21_R4();
            case "v1_21_R5": return new Version_v1_21_R5();
            default:
                return new UnknownVersion();
        }
    }

    public NMSAdapter getNMSHandler() {
        return nmsAdapter;
    }
}
