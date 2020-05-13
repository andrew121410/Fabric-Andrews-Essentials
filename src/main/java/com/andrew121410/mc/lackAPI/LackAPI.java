package com.andrew121410.mc.lackAPI;

import com.andrew121410.mc.lackAPI.utils.SimpleScheduler;
import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;

public class LackAPI {

    public static void init(MinecraftDedicatedServer minecraftDedicatedServer) {
        ServerTickCallback.EVENT.register(SimpleScheduler::onTick);
    }

    public static String VERSION = "0.5.2";
}
