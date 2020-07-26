package com.reliableplugins.genbucket.runnable;

import com.reliableplugins.genbucket.generator.Generator;
import com.reliableplugins.genbucket.generator.data.GeneratorData;
import com.reliableplugins.genbucket.manager.GenBucketManager;
import org.bukkit.Bukkit;

import java.util.Iterator;

public class GeneratorTask implements Runnable {

    @Override
    public void run() {

        // add a check if any genbuckets exist
        for (Generator generator : GenBucketManager.getGeneratorMap().values()) {
            for (Iterator<GeneratorData> iterator = generator.getLocations().iterator(); iterator.hasNext();) {
                GeneratorData data = iterator.next();
                if (data.getIndex() >= generator.getMaxBlocks()) {
                    Bukkit.broadcastMessage("Finished");
                    iterator.remove();
                } else {
                    generator.onTick(data);
                    data.addIndex();
                }
            }
        }
    }

}
