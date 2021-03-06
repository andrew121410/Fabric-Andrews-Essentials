package com.andrew121410.mc.essfabric;

import com.andrew121410.mc.essfabric.commands.*;
import com.andrew121410.mc.essfabric.commands.gm.GmaCMD;
import com.andrew121410.mc.essfabric.commands.gm.GmcCMD;
import com.andrew121410.mc.essfabric.commands.gm.GmsCMD;
import com.andrew121410.mc.essfabric.commands.gm.GmspCMD;
import com.andrew121410.mc.essfabric.commands.home.DelhomeCMD;
import com.andrew121410.mc.essfabric.commands.home.HomeCMD;
import com.andrew121410.mc.essfabric.commands.home.SethomeCMD;
import com.andrew121410.mc.essfabric.commands.tpa.TpaCMD;
import com.andrew121410.mc.essfabric.commands.tpa.TpacceptCMD;
import com.andrew121410.mc.essfabric.commands.tpa.TpdenyCMD;
import com.andrew121410.mc.essfabric.commands.utils.GetRelativeCMD;
import com.andrew121410.mc.essfabric.commands.utils.XyzdxdydzCMD;
import com.andrew121410.mc.essfabric.commands.warp.DelwarpCMD;
import com.andrew121410.mc.essfabric.commands.warp.SetwarpCMD;
import com.andrew121410.mc.essfabric.commands.warp.WarpCMD;
import com.andrew121410.mc.essfabric.managers.HomeManager;
import com.andrew121410.mc.essfabric.managers.WarpManager;
import com.andrew121410.mc.essfabric.utils.*;
import com.andrew121410.mc.lackAPI.LackAPI;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Items;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;

import java.io.File;

public class Main implements ModInitializer {

    public static String VERSION = "1.7.9.6";
    public boolean isReady = false;

    private ModConfig modConfig;

    private static Main instance;
    private MinecraftDedicatedServer minecraftDedicatedServer;

    private File modConfigFolder;

    private SetListMap setListMap;
    private PlayerInitializer playerInitializer;
    private TpsHelper tpsHelper;

    private WarpManager warpManager;
    private HomeManager homeManager;

    @Override
    public void onInitialize() {
        instance = this;
        System.out.println("Loading Andrews Essentials");

        //Make config if not exist.
        this.modConfigFolder = new File("Andrews-Config/");
        if (!modConfigFolder.isDirectory()) {
            this.modConfigFolder.mkdir();
        }

        //Load config
        this.modConfig = ConfigUtils.loadConfig(this.modConfigFolder);

        this.setListMap = new SetListMap();

        regCommands();
        onServerStarted();
    }

    public void onShutdown() {
        System.out.println("[SHUTDOWN] Fabric-Andrews-Essentials.");
    }

    public void onServerStarted() {

        ServerLifecycleEvents.SERVER_STARTED.register(t -> {
            this.minecraftDedicatedServer = (MinecraftDedicatedServer) t;

            //Load LackAPI
            LackAPI.init(minecraftDedicatedServer);

            this.homeManager = new HomeManager(this);
            this.warpManager = new WarpManager(this);
            this.warpManager.load();

            this.playerInitializer = new PlayerInitializer(this);

            this.tpsHelper = new TpsHelper();
            ServerTickEvents.START_SERVER_TICK.register(this.tpsHelper::tick);

            FuelRegistry.INSTANCE.add(Items.ROTTEN_FLESH, 100);

            isReady = true;
        });
    }

    public void regCommands() {
        CommandRegistrationCallback.EVENT.register(new VersionCMD(this)::register);
        CommandRegistrationCallback.EVENT.register(new TpsCMD(this)::register);

        CommandRegistrationCallback.EVENT.register(new TpaCMD(this)::register);
        CommandRegistrationCallback.EVENT.register(new TpacceptCMD(this)::register);
        CommandRegistrationCallback.EVENT.register(new TpdenyCMD(this)::register);

        CommandRegistrationCallback.EVENT.register(new HomeCMD(this)::register);
        CommandRegistrationCallback.EVENT.register(new SethomeCMD(this)::register);
        CommandRegistrationCallback.EVENT.register(new DelhomeCMD(this)::register);

        CommandRegistrationCallback.EVENT.register(new GmaCMD(this)::register);
        CommandRegistrationCallback.EVENT.register(new GmcCMD(this)::register);
        CommandRegistrationCallback.EVENT.register(new GmsCMD(this)::register);
        CommandRegistrationCallback.EVENT.register(new GmspCMD(this)::register);

        CommandRegistrationCallback.EVENT.register(new SpawnCMD(this)::register);
        CommandRegistrationCallback.EVENT.register(new BackCMD(this)::register);

        CommandRegistrationCallback.EVENT.register(new WarpCMD(this)::register);
        CommandRegistrationCallback.EVENT.register(new SetwarpCMD(this)::register);
        CommandRegistrationCallback.EVENT.register(new DelwarpCMD(this)::register);

        CommandRegistrationCallback.EVENT.register(new EchestCMD(this)::register);

        CommandRegistrationCallback.EVENT.register(new GetRelativeCMD(this)::register);
        CommandRegistrationCallback.EVENT.register(new XyzdxdydzCMD(this)::register);

        CommandRegistrationCallback.EVENT.register(new BottleCMD(this)::register);
    }

    public SetListMap getSetListMap() {
        return setListMap;
    }

    public PlayerInitializer getPlayerInitializer() {
        return playerInitializer;
    }

    public static Main getInstance() {
        return instance;
    }

    public TpsHelper getTpsHelper() {
        return tpsHelper;
    }

    public MinecraftDedicatedServer getMinecraftDedicatedServer() {
        return minecraftDedicatedServer;
    }

    public File getModConfigFolder() {
        return modConfigFolder;
    }

    public WarpManager getWarpManager() {
        return warpManager;
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }

    public ModConfig getModConfig() {
        return modConfig;
    }
}
