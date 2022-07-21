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
                        player.getInventory().addItem(plugin.getGeneratorsHandler().getCobblestoneGenerator().getGeneratorItem());
                        player.sendMessage(ChatColor.YELLOW + "+ Cobblestone");
                        break;
                    case "dirt":
                        player.getInventory().addItem(plugin.getGeneratorsHandler().getDirtGenerator().getGeneratorItem());
                        player.sendMessage(ChatColor.YELLOW + "+ Dirt");
                        break;
                    case "diamond":
                        player.getInventory().addItem(plugin.getGeneratorsHandler().getDiamondGenerator().getGeneratorItem());
                        player.sendMessage(ChatColor.YELLOW + "+ Diamond");
                        break;
                }
                break;
        }

        return true;
    }
}
