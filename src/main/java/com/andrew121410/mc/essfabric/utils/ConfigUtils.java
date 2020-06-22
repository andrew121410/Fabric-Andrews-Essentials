package com.andrew121410.mc.essfabric.utils;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Map;

public class ConfigUtils {

    public static ModConfig loadConfig(File file) {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setIndent(2);
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        try {
            if (!new File(file, "config.yml").exists()) {
                Map<String, Object> defaultConfig = ModConfig.createDefaults();
                Yaml yaml = new Yaml(dumperOptions);
                FileWriter fileWriter = new FileWriter(new File(file, "config.yml"));
                yaml.dump(defaultConfig, fileWriter);
            }
            Yaml yaml = new Yaml(dumperOptions);
            InputStream inputStream = new FileInputStream(new File(file, "config.yml"));
            Map<String, Object> map = yaml.load(inputStream);
            return new ModConfig(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}