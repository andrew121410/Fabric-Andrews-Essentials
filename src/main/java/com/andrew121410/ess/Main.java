package com.andrew121410.ess;

import com.andrew121410.ess.commands.back;
import com.andrew121410.ess.commands.gm.gma;
import com.andrew121410.ess.commands.gm.gmc;
import com.andrew121410.ess.commands.gm.gms;
import com.andrew121410.ess.commands.gm.gmsp;
import com.andrew121410.ess.commands.hide.hide;
import com.andrew121410.ess.commands.hide.unhide;
import com.andrew121410.ess.commands.home.delhome;
import com.andrew121410.ess.commands.home.home;
import com.andrew121410.ess.commands.home.sethome;
import com.andrew121410.ess.commands.spawn;
import com.andrew121410.ess.commands.tpa.tpa;
import com.andrew121410.ess.commands.tpa.tpaccept;
import com.andrew121410.ess.commands.tpa.tpdeny;
import com.andrew121410.ess.commands.tps;
import com.andrew121410.ess.commands.version;
import com.andrew121410.ess.commands.warp.delwarp;
import com.andrew121410.ess.commands.warp.setwarp;
import com.andrew121410.ess.commands.warp.warp;
import com.andrew121410.ess.managers.HomeManager;
import com.andrew121410.ess.managers.WarpManager;
import com.andrew121410.ess.utils.DiscordAddon;
import com.andrew121410.ess.utils.PlayerInitializer;
import com.andrew121410.ess.utils.SetListMap;
import com.andrew121410.ess.utils.TpsHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;

import java.io.File;

public class Main implements ModInitializer {

    private static Main main;
    private MinecraftDedicatedServer minecraftDedicatedServer;

    private File modConfigFolder;

    private SetListMap setListMap;
    private PlayerInitializer playerInitializer;
    private TpsHelper tpsHelper;

    private DiscordAddon discordAddon;

    private WarpManager warpManager;
    private HomeManager homeManager;

    @Override
    public void onInitialize() {
        main = this;
        this.minecraftDedicatedServer = (MinecraftDedicatedServer) FabricLoader.getInstance().getGameInstance();
        this.modConfigFolder = new File("Andrews-Config/");

        if (!modConfigFolder.isDirectory()) {
            this.modConfigFolder.mkdir();
        }

        System.out.println("Loading Andrews Essentials");
        this.setListMap = new SetListMap();

        this.homeManager = new HomeManager(this);

        this.warpManager = new WarpManager(this);
        this.warpManager.load();

        this.playerInitializer = new PlayerInitializer(this);

        regCommands();

        this.tpsHelper = new TpsHelper();
        ServerTickCallback.EVENT.register(this.tpsHelper::tick);

        this.discordAddon = new DiscordAddon(true);
        this.discordAddon.sendServerStartMessage();
    }

    public void onShutdown() {
        this.discordAddon.sendServerQuitMessage();
        System.out.println("[SHUTDOWN] Fabric-Andrews-Essentials.");
    }

    public void regCommands() {
        CommandRegistry.INSTANCE.register(false, new version(this)::register);
        CommandRegistry.INSTANCE.register(false, new tps(this)::register);

        CommandRegistry.INSTANCE.register(false, new tpa(this)::register);
        CommandRegistry.INSTANCE.register(false, new tpaccept(this)::register);
        CommandRegistry.INSTANCE.register(false, new tpdeny(this)::register);

        CommandRegistry.INSTANCE.register(false, new home(this)::register);
        CommandRegistry.INSTANCE.register(false, new sethome(this)::register);
        CommandRegistry.INSTANCE.register(false, new delhome(this)::register);

        CommandRegistry.INSTANCE.register(false, new gma(this)::register);
        CommandRegistry.INSTANCE.register(false, new gmc(this)::register);
        CommandRegistry.INSTANCE.register(false, new gms(this)::register);
        CommandRegistry.INSTANCE.register(false, new gmsp(this)::register);

        CommandRegistry.INSTANCE.register(false, new spawn(this)::register);
        CommandRegistry.INSTANCE.register(false, new back(this)::register);

        CommandRegistry.INSTANCE.register(false, new hide(this)::register);
        CommandRegistry.INSTANCE.register(false, new unhide(this)::register);

        CommandRegistry.INSTANCE.register(false, new warp(this)::register);
        CommandRegistry.INSTANCE.register(false, new setwarp(this)::register);
        CommandRegistry.INSTANCE.register(false, new delwarp(this)::register);
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

    public DiscordAddon getDiscordAddon() {
        return discordAddon;
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
}
