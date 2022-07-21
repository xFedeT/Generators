package it.fedet.generatorslite.commands;

import it.fedet.generatorslite.GeneratorsLite;
import it.fedet.generatorslite.generators.*;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GeneratorsCommand implements CommandExecutor {
    private final GeneratorsLite plugin;
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Non sei un player, Bastardo!");
            return true;
        }

        Player player = (Player) sender;

        switch (args.length) {
            case 0:
                player.sendMessage(ChatColor.RED + "Errore di sintassi!");
                break;
            case 1:
                switch (args[0]) {
                    case "cobblestone":
                        Generator cobblestoneGenerator = new CobblestoneGenerator(plugin);
                        plugin.getGeneratorsHandler().addGenerator(cobblestoneGenerator);
                        player.getInventory().addItem(cobblestoneGenerator.getGeneratorItem());
                        player.sendMessage(ChatColor.YELLOW + "+ Cobblestone");
                        break;
                    case "dirt":
                        Generator dirtGenerator = new DirtGenerator(plugin);
                        plugin.getGeneratorsHandler().addGenerator(dirtGenerator);
                        player.getInventory().addItem(dirtGenerator.getGeneratorItem());
                        player.sendMessage(ChatColor.YELLOW + "+ Dirt");
                        break;
                    case "diamond":
                        Generator diamondGenerator = new DiamondGenerator(plugin);
                        plugin.getGeneratorsHandler().addGenerator(diamondGenerator);
                        player.getInventory().addItem(diamondGenerator.getGeneratorItem());
                        player.sendMessage(ChatColor.YELLOW + "+ Diamond");
                        break;
                }
                break;
        }

        return true;
    }
}
