package com.reliableplugins.genbucket.runnable;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.manager.GenBucketManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.Iterator;

public class GeneratorTask implements Runnable {

    private final int MAX_GENERATORS = 20000;

    private GenBucket plugin;

    public GeneratorTask(GenBucket plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        int amount = 0;
        // Do multi block change
        for (Generator generator : plugin.getGeneratorMap().values()) {
            for (Iterator<GeneratorData> iterator = generator.getLocations().iterator(); iterator.hasNext();) {
                GeneratorData data = iterator.next();

                if (++amount >= MAX_GENERATORS) break;

                if (data.getIndex() >= generator.getMaxBlocks()) {
                    iterator.remove();
                } else {
                    generator.onTick(data);
                    data.setIndex(data.getIndex() + 1);
                }
            }
        }
    }

}
