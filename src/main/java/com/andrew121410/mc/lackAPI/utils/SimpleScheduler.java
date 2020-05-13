package com.andrew121410.mc.lackAPI.utils;

import net.minecraft.server.MinecraftServer;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleScheduler {

    private static long tick = 0;
    private static List<AbstractMap.SimpleEntry<Long, Runnable>> entries = new ArrayList<>();

    public static void onTick(MinecraftServer server) {
        tick++;
        Iterator<AbstractMap.SimpleEntry<Long, Runnable>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            AbstractMap.SimpleEntry<Long, Runnable> next = iterator.next();
            if (next.getKey() == tick) {
                next.getValue().run();
                iterator.remove();
            }
        }

        if (entries.isEmpty() && tick >= 10000) tick = 0;
    }

    public static void schedule(long ticks, Runnable runnable) {
        entries.add(new AbstractMap.SimpleEntry<>(ticks + tick + 1, runnable));
    }
}