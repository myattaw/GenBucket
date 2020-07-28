package com.reliableplugins.genbucket.hook;

import com.reliableplugins.genbucket.GenBucket;

public interface PluginHook<T> {

    T setup(GenBucket plugin);

    String[] getPlugins();

    String getName();

}