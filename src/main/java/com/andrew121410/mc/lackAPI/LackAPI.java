package com.andrew121410.mc.lackAPI;

import com.andrew121410.mc.lackAPI.utils.SimpleScheduler;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;

public class LackAPI {

    public static void init(MinecraftDedicatedServer minecraftDedicatedServer) {
        ServerTickEvents.START_SERVER_TICK.register(SimpleScheduler::onTick);
    }

    public static String VERSION = "0.5.4";
}
