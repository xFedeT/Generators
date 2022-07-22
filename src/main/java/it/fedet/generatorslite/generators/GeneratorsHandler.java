package it.fedet.generatorslite.generators;

import it.fedet.generatorslite.GeneratorsLite;
import it.fedet.generatorslite.utils.Format;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorsHandler {
    private final GeneratorsLite plugin;
    @Getter private final Map<String, Generator> generators = new HashMap<>();
    @Getter private GeneratorsConfig generatorsConfig;
    private FileConfiguration config;

    public GeneratorsHandler(GeneratorsLite plugin) {
        this.plugin = plugin;
        setup();
    }

    private void setup() {
        generatorsConfig = new GeneratorsConfig(plugin);
        config = generatorsConfig.getConfig();

        ConfigurationSection generatorsSection = config.getConfigurationSection("generators");

        if (generatorsSection.getKeys(false).size() == 0) return;

        generatorsSection.getKeys(false).forEach(key -> {
            String path = "generators." + key + ".";
            int rechargeTime = config.getInt(path + "delay");
            Material chargingMaterial = Material.valueOf(config.getString(path + "material.charging"));
            Material chargedMaterial = Material.valueOf(config.getString(path + "material.charged"));
            Effect effect = Effect.valueOf(config.getString(path + "effect"));
            Sound sound = Sound.valueOf(config.getString(path + "sound"));
            ItemStack drop = new ItemStack(
                    Material.valueOf(config.getString(path + "drop.item")),
                    config.getInt(path + "drop.amount")
            );
            String name = Format.color(config.getString(path + "name"));

            generators.put(key, new Generator(rechargeTime, chargingMaterial, chargedMaterial, effect, sound, drop, name, plugin));
        });

        Bukkit.getWorlds().forEach(world -> {
            world.getEntities().stream().filter(entity -> entity instanceof ArmorStand).forEach(armorStand -> {
                if (armorStand.getCustomName().startsWith("generator: ")) {
                    generators.values().forEach(generator -> {
                        if (armorStand.getCustomName().contains(generator.getGeneratorName()))
                            generator.addGeneratorPoint((ArmorStand) armorStand);
                    });
                }
            });
        });
    }


    public void onBreak(BlockBreakEvent event) {
        for (Generator generator : generators.values()) generator.onBreak(event);
    }

    public void onPlace(BlockPlaceEvent event) {
        for (Generator generator : generators.values()) generator.onPlace(event);
    }

    public void addGenerator(String key, Generator generator) {
        generators.put(key, generator);
    }
}
