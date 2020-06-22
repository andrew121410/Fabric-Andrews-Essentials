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
import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;

import java.io.File;

public class Main implements ModInitializer {

    public static String VERSION = "1.7.8";

    private ModConfig modConfig;

    private static Main main;
    private MinecraftDedicatedServer minecraftDedicatedServer;

    private File modConfigFolder;

    private SetListMap setListMap;
    private PlayerInitializer playerInitializer;
    private TpsHelper tpsHelper;

    private WarpManager warpManager;
    private HomeManager homeManager;

    @Override
    public void onInitialize() {
        main = this;

        this.modConfigFolder = new File("Andrews-Config/");
        if (!modConfigFolder.isDirectory()) {
            this.modConfigFolder.mkdir();
        }

        //Load config
        this.modConfig = ConfigUtils.loadConfig(this.modConfigFolder);

        this.minecraftDedicatedServer = (MinecraftDedicatedServer) FabricLoader.getInstance().getGameInstance();
        System.out.println("Loading Andrews Essentials");
        this.setListMap = new SetListMap();

        //Load LackAPI
        LackAPI.init(minecraftDedicatedServer);

        this.homeManager = new HomeManager(this);
        this.warpManager = new WarpManager(this);

        this.playerInitializer = new PlayerInitializer(this);

        regCommands();

        this.tpsHelper = new TpsHelper();
        ServerTickCallback.EVENT.register(this.tpsHelper::tick);

        FuelRegistry.INSTANCE.add(Items.ROTTEN_FLESH, 100);
    }

    public void onShutdown() {
        System.out.println("[SHUTDOWN] Fabric-Andrews-Essentials.");
    }

    public void regCommands() {
        CommandRegistry.INSTANCE.register(false, new VersionCMD(this)::register);
        CommandRegistry.INSTANCE.register(false, new TpsCMD(this)::register);

        CommandRegistry.INSTANCE.register(false, new TpaCMD(this)::register);
        CommandRegistry.INSTANCE.register(false, new TpacceptCMD(this)::register);
        CommandRegistry.INSTANCE.register(false, new TpdenyCMD(this)::register);

        CommandRegistry.INSTANCE.register(false, new HomeCMD(this)::register);
        CommandRegistry.INSTANCE.register(false, new SethomeCMD(this)::register);
        CommandRegistry.INSTANCE.register(false, new DelhomeCMD(this)::register);

        CommandRegistry.INSTANCE.register(false, new GmaCMD(this)::register);
        CommandRegistry.INSTANCE.register(false, new GmcCMD(this)::register);
        CommandRegistry.INSTANCE.register(false, new GmsCMD(this)::register);
        CommandRegistry.INSTANCE.register(false, new GmspCMD(this)::register);

        CommandRegistry.INSTANCE.register(false, new SpawnCMD(this)::register);
        CommandRegistry.INSTANCE.register(false, new BackCMD(this)::register);

        CommandRegistry.INSTANCE.register(false, new WarpCMD(this)::register);
        CommandRegistry.INSTANCE.register(false, new SetwarpCMD(this)::register);
        CommandRegistry.INSTANCE.register(false, new DelwarpCMD(this)::register);

        CommandRegistry.INSTANCE.register(false, new EchestCMD(this)::register);

        CommandRegistry.INSTANCE.register(false, new GetRelativeCMD(this)::register);
        CommandRegistry.INSTANCE.register(false, new XyzdxdydzCMD(this)::register);

        CommandRegistry.INSTANCE.register(false, new BottleCMD(this)::register);
    }

    public SetListMap getSetListMap() {
        return setListMap;
    }

    public PlayerInitializer getPlayerInitializer() {
        return playerInitializer;
    }

    public static Main getMain() {
        return main;
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
