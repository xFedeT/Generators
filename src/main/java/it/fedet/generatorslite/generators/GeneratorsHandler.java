package it.fedet.generatorslite.generators;

import it.fedet.generatorslite.GeneratorsLite;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

public class GeneratorsHandler {
    private final GeneratorsLite plugin;
    private final List<Generator> generators = new ArrayList<>();
    @Getter private DiamondGenerator diamondGenerator;
    @Getter private DirtGenerator dirtGenerator;
    @Getter private CobblestoneGenerator cobblestoneGenerator;
    @Getter private GeneratorsConfig generatorsConfig;
    private FileConfiguration config;

    public GeneratorsHandler(GeneratorsLite plugin) {
        this.plugin = plugin;

        setup();
    }

    private void setup() {
        diamondGenerator = new DiamondGenerator(plugin);
        addGenerator(diamondGenerator);
        dirtGenerator = new DirtGenerator(plugin);
        addGenerator(dirtGenerator);
        cobblestoneGenerator = new CobblestoneGenerator(plugin);
        addGenerator(cobblestoneGenerator);

        Bukkit.getWorlds().forEach(world -> {
            world.getEntities().stream().filter(entity -> entity instanceof ArmorStand).forEach(armorStand -> {
                if (armorStand.getCustomName().startsWith("generator: ")) {
                    generators.forEach(generator -> {
                        if (armorStand.getCustomName().contains(generator.getGeneratorName()))
                            generator.addGeneratorPoint((ArmorStand) armorStand);
                    });
                }
            });
        });
    }


    public void onBreak(BlockBreakEvent event) {
        for (Generator generator : generators) generator.onBreak(event);
    }

    public void onPlace(BlockPlaceEvent event) {
        for (Generator generator : generators) generator.onPlace(event);
    }

    public void addGenerator(Generator generator) {
        generators.add(generator);
    }
}
