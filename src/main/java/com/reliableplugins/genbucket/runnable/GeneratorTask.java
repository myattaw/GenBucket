package com.reliableplugins.genbucket.runnable;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import org.bukkit.Chunk;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GeneratorTask implements Runnable {

    private final int MAX_GENERATORS;

    private GenBucket plugin;

    public GeneratorTask(GenBucket plugin) {
        this.plugin = plugin;
        this.MAX_GENERATORS = plugin.getConfig().getInt("settings.max-buckets");
    }

    @Override
    public void run() {
        int amount = 0;
        for (Generator generator : plugin.getGeneratorMap().values()) {

            for (Map.Entry<Chunk, Set<GeneratorData>> generatorData : generator.getLocations().entrySet()) {

                if (!generatorData.getKey().isLoaded()) continue;

                for (Iterator<GeneratorData> iterator = generatorData.getValue().iterator(); iterator.hasNext(); ) {
                    GeneratorData data = iterator.next();

                    if (++amount >= MAX_GENERATORS) break;

                    if (data.getIndex() >= generator.getMaxBlocks()) {
                        iterator.remove();
                    } else {
                        data.setIndex(data.getIndex() + 1);
                        generator.onTick(data);
                    }
                }
            }
        }
    }
}
