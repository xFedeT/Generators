package it.fedet.generatorslite.utils;

import org.bukkit.ChatColor;

public class Format {
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}