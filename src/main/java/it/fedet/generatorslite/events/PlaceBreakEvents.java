package it.fedet.generatorslite.events;

import it.fedet.generatorslite.generators.GeneratorsHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

@RequiredArgsConstructor
public class PlaceBreakEvents implements Listener {
    private final GeneratorsHandler generatorsHandler;

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        generatorsHandler.onPlace(event);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        generatorsHandler.onBreak(event);
    }
}
