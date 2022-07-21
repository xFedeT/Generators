package it.fedet.generatorslite;

import it.fedet.generatorslite.commands.GeneratorsCommand;
import it.fedet.generatorslite.events.PlaceBreakEvents;
import it.fedet.generatorslite.generators.GeneratorsHandler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class GeneratorsLite extends JavaPlugin {
    @Getter private GeneratorsHandler generatorsHandler;

    @Override
    public void onEnable() {
        generatorsHandler = new GeneratorsHandler(this);

        registerCommands();
        registerEvents();
    }

    private void registerCommands() {
        getCommand("generators").setExecutor(new GeneratorsCommand(this));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlaceBreakEvents(generatorsHandler), this);
    }
}
