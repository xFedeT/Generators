package it.fedet.generatorslite.generators;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

public class GeneratorsHandler {
    private final List<Generator> generators = new ArrayList<>();
//    @Getter private final GeneratorsConfig generatorsConfig;
//    private final FileConfiguration config;
//
//    public GeneratorsHandler(GeneratorsLite plugin) {
//        generatorsConfig = new GeneratorsConfig(plugin);
//        config = generatorsConfig.getConfig();
//    }

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
