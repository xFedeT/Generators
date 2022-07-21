package it.fedet.generatorslite.generators;

import it.fedet.generatorslite.GeneratorsLite;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class GeneratorsConfig {
     private final GeneratorsLite plugin;

     @Getter private FileConfiguration config;
     private File configFile;

     public GeneratorsConfig(GeneratorsLite plugin) {
          this.plugin = plugin;

          createConfig();
     }

     private void createConfig() {
          configFile = new File(plugin.getDataFolder(), "generators.yml");

          if (!configFile.exists()) {
              configFile.mkdirs();
              plugin.saveResource("generators.yml", false);
          }

          config = new YamlConfiguration();

          try {
               config.load(configFile);
          } catch (IOException | InvalidConfigurationException e) {
               throw new RuntimeException(e);
          }
     }

     public void save() {
          try {
               config.save(configFile);
          } catch (IOException e) {
               throw new RuntimeException(e);
          }
     }
}
