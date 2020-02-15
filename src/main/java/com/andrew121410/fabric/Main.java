package com.andrew121410.fabric;

import com.andrew121410.fabric.commands.versionCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;

public class Main implements ModInitializer {

    @Override
    public void onInitialize() {
        System.out.println("Loading Andrews Essentials");
        regCommands();
    }

    public void regCommands() {
        CommandRegistry.INSTANCE.register(false, new versionCommand(this)::register);
    }
}
