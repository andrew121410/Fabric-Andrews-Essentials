package com.andrew121410.fabric;

import com.andrew121410.fabric.commands.back;
import com.andrew121410.fabric.commands.gm.gma;
import com.andrew121410.fabric.commands.gm.gmc;
import com.andrew121410.fabric.commands.gm.gms;
import com.andrew121410.fabric.commands.gm.gmsp;
import com.andrew121410.fabric.commands.home.delhome;
import com.andrew121410.fabric.commands.home.home;
import com.andrew121410.fabric.commands.home.sethome;
import com.andrew121410.fabric.commands.spawn;
import com.andrew121410.fabric.commands.tpa.tpa;
import com.andrew121410.fabric.commands.tpa.tpaccept;
import com.andrew121410.fabric.commands.tpa.tpdeny;
import com.andrew121410.fabric.commands.tps;
import com.andrew121410.fabric.commands.version;
import com.andrew121410.fabric.utils.PlayerInitializer;
import com.andrew121410.fabric.utils.SetListMap;
import com.andrew121410.fabric.utils.TpsHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;

public class Main implements ModInitializer {

    private MinecraftDedicatedServer minecraftDedicatedServer;

    private static Main main;

    private SetListMap setListMap;
    private PlayerInitializer playerInitializer;
    private TpsHelper tpsHelper;

    @Override
    public void onInitialize() {
        main = this;
        this.minecraftDedicatedServer = (MinecraftDedicatedServer) FabricLoader.getInstance().getGameInstance();
        System.out.println("Loading Andrews Essentials");
        this.setListMap = new SetListMap();
        this.playerInitializer = new PlayerInitializer(this);

        regCommands();

        this.tpsHelper = new TpsHelper();
        ServerTickCallback.EVENT.register(this.tpsHelper::tick);
    }

    public void onShutdown() {
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
}
