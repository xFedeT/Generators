package it.fedet.generatorslite.generators;

import it.fedet.generatorslite.GeneratorsLite;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DiamondGenerator extends Generator {
    public DiamondGenerator(GeneratorsLite plugin) {
        super(5, Material.STONE, Material.DIAMOND_ORE, Effect.CRIT,
                new ItemStack(Material.DIAMOND, 2), ChatColor.AQUA + "Diamond " + ChatColor.GOLD + "Generator", plugin);
    }
}
