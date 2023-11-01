package net.encmys.ykdz;

import java.io.File;
import java.io.IOException;

import org.bstats.charts.SimplePie;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.bstats.bukkit.Metrics;

public final class CustomMMOItemsStats extends JavaPlugin {

    private File configFile;
    private FileConfiguration config;

    public @NotNull FileConfiguration getConfig() {
        return this.config;
    }

    public void createConfig() {

        configFile = new File(getDataFolder(),"config.yml");
        config = new YamlConfiguration();

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml",false);
        }

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException error) {
            error.printStackTrace();
        }
    }

    @Override
    public void onLoad() {
        this.createConfig();
        StatRegister.registerCustomStats();
        getLogger().info("CustomMMOItemsStats enabled! ~ By YK_DZ");
    }

    @Override
    public void onEnable() {
        if (!config.getBoolean("options.bstats")) {
            int pluginId = 18246;
            Metrics metrics = new Metrics(this, pluginId);
            getLogger().info("The bstats is enabled.");
        } else {
            getLogger().info("The bstats is disabled.");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomMMOItemsStats disabled! ~ By YK_DZ");
    }
}
