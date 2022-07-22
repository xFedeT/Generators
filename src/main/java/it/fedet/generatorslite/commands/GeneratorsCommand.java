package it.fedet.generatorslite.commands;

import it.fedet.generatorslite.GeneratorsLite;
import it.fedet.generatorslite.generators.*;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

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
                Map<String, Generator> generators = plugin.getGeneratorsHandler().getGenerators();

                for (String key : generators.keySet()) {
                    if (!key.toLowerCase().equals(args[0])) continue;
                    player.getInventory().addItem(generators.get(key).getGeneratorItem());
                    player.sendMessage(ChatColor.YELLOW + "+ " + ChatColor.GOLD + key);
                }

                break;
        }

        return true;
    }
}
