package it.fedet.generatorslite.generators;

import it.fedet.generatorslite.GeneratorsLite;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class CobblestoneGenerator extends Generator {
    public CobblestoneGenerator(GeneratorsLite plugin) {
        super(5, Material.STONE, Material.COBBLESTONE, Effect.ITEM_BREAK, Sound.CHEST_OPEN,
                new ItemStack(Material.STONE_BUTTON, 1),
                ChatColor.DARK_GRAY + "Cobblestone " + ChatColor.GOLD + "Generator", plugin);
    }
}