package com.andrew121410.fabric;

import com.andrew121410.fabric.commands.gm.gma;
import com.andrew121410.fabric.commands.gm.gmc;
import com.andrew121410.fabric.commands.gm.gms;
import com.andrew121410.fabric.commands.gm.gmsp;
import com.andrew121410.fabric.commands.home.delhome;
import com.andrew121410.fabric.commands.home.home;
import com.andrew121410.fabric.commands.home.sethome;
import com.andrew121410.fabric.commands.tpa.tpa;
import com.andrew121410.fabric.commands.tpa.tpaccept;
import com.andrew121410.fabric.commands.tpa.tpdeny;
import com.andrew121410.fabric.commands.versionCommand;
import com.andrew121410.fabric.utils.PlayerInitializer;
import com.andrew121410.fabric.utils.SetListMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;

public class Main implements ModInitializer {

    private static Main main;

    private SetListMap setListMap;
    private PlayerInitializer playerInitializer;

    @Override
    public void onInitialize() {
        main = this;
        System.out.println("Loading Andrews Essentials");
        this.setListMap = new SetListMap();
        this.playerInitializer = new PlayerInitializer(this);

        regCommands();
    }

    public void regCommands() {
        CommandRegistry.INSTANCE.register(false, new versionCommand(this)::register);
//        CommandRegistry.INSTANCE.register(false, new tpsCommand(this)::register);

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
}
