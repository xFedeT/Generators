package it.fedet.generatorslite.generators;

import it.fedet.generatorslite.GeneratorsLite;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DirtGenerator extends Generator {
    public DirtGenerator(GeneratorsLite plugin) {
        super(20, Material.GRASS, Material.DIRT, Effect.ITEM_BREAK,
                new ItemStack(Material.WOOD_BUTTON, 3), ChatColor.DARK_GRAY + "Dirt " + ChatColor.GOLD + "Generator", plugin);
    }
}
