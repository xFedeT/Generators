package it.fedet.generatorslite.generators;

import it.fedet.generatorslite.GeneratorsLite;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@SuppressWarnings("SuspiciousMethodCalls")
public class Generator {
    @Getter private final int rechargeTime;
    @Getter private final Material chargingMaterial;
    @Getter private final Material chargedMaterial;
    @Getter private final Effect readyEffetc;
    @Getter private final Sound sound;
    @Getter private final ItemStack dropItem;
    @Getter private final String generatorName;
    private final GeneratorsLite plugin;

    private final String customID;
    private final List<String> generatroItemLore;
    @Getter final private ItemStack generatorItem;
    private final List<ArmorStand> generatorsPotins;

    public Generator(int rechargeTime, Material chargingMaterial, Material chargedMaterial,
                     Effect readyEffetc, Sound sound, ItemStack dropItem, String generatorName, GeneratorsLite plugin) {
        this.rechargeTime = rechargeTime;
        this.chargingMaterial = chargingMaterial;
        this.chargedMaterial = chargedMaterial;
        this.readyEffetc = readyEffetc;
        this.sound = sound;
        this.dropItem = dropItem;
        this.generatorName = generatorName;
        this.plugin = plugin;

        customID = "generator: " + generatorName;
        generatroItemLore = Arrays.asList(" ", ChatColor.GOLD + "Break me!", "");
        generatorItem = new ItemStack(chargedMaterial);
        ItemMeta generatorItemMeta = generatorItem.getItemMeta();
        generatorItemMeta.setDisplayName(generatorName);
        generatorItemMeta.setLore(generatroItemLore);
        generatorItem.setItemMeta(generatorItemMeta);
        generatorsPotins = new ArrayList<>();
    }

    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block placedBlock = event.getBlock();
        Location blockLocation = placedBlock.getLocation();

        if (!isGenerator(placedBlock)) return;
        event.setCancelled(true);

        if (player.isSneaking()) {
            getArmorStandFromLoc(placedBlock).remove();
            player.getWorld().dropItem(blockLocation.add(.5, 1, .5), generatorItem);
            placedBlock.setType(Material.AIR);
            return;
        }

        if (placedBlock.getType() == chargingMaterial) {
            player.sendMessage(ChatColor.RED + "Il generatore si sta ancora caricando!");
            return;
        }

        placedBlock.setType(chargingMaterial);
        player.getWorld().dropItem(blockLocation.add(.5, 1, .5), dropItem);
        generateGenerator(placedBlock);
    }

    public void onPlace(BlockPlaceEvent event) {
        ItemStack itemPlaced = event.getPlayer().getItemInHand();
        ItemMeta itemPlaceMeta = itemPlaced.getItemMeta();

        if (!(itemPlaced.getType() == chargedMaterial
            && itemPlaceMeta.getDisplayName() != null
            && itemPlaceMeta.getDisplayName().equals(generatorName)
            && itemPlaceMeta.getLore().equals(generatroItemLore))) return;

        Block placedBlock = event.getBlock();
        ArmorStand generatorArmor = (ArmorStand) placedBlock.getWorld().spawnEntity(placedBlock.getLocation().add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
        generatorArmor.setVisible(false);
        generatorArmor.setCustomName(customID);
        generatorArmor.setGravity(false);
        generatorArmor.setMarker(true);
        generatorArmor.setSmall(true);
        generatorsPotins.add(generatorArmor);
        placedBlock.setType(chargingMaterial);
        generateGenerator(placedBlock);
    }

    private void generateGenerator(Block placedBlock) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!isGenerator(placedBlock)) return;

            placedBlock.setType(chargedMaterial);
            for (int i = 0; i < 10; i++)
                placedBlock.getWorld().playEffect(placedBlock.getLocation().add(.5, .5, .5), readyEffetc, null);

            placedBlock.getWorld().playSound(placedBlock.getLocation().add(.5, .5, .5), sound, 0.3f, 0);
        }, rechargeTime * 20L);
    }

    private boolean isGenerator(Block placedBlock) {
        boolean isGenerator = false;

        for (Entity entity : placedBlock.getWorld().getNearbyEntities(placedBlock.getLocation().add(.5, 0, .5), .5, .5, .5)) {
            if (!generatorsPotins.contains(entity)) continue;
            isGenerator = true;
            break;
        }

        return isGenerator;
    }

    private ArmorStand getArmorStandFromLoc(Block placedBlock) {
        ArmorStand armorStand = null;

        for (Entity entity : placedBlock.getWorld().getNearbyEntities(placedBlock.getLocation().add(.5, 0, .5), .5, .5, .5))
            if (generatorsPotins.contains(entity)) armorStand = (ArmorStand) entity;

        return armorStand;
    }

    public void addGeneratorPoint(ArmorStand point) {
        generatorsPotins.add(point);
        generateGenerator(point.getLocation().getBlock());
    }
}
