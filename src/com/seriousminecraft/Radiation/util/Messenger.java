package com.seriousminecraft.Radiation.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
public final class Messenger {
	Plugin plugin;
	private static String pluginName;
	public static String helpMessage;
	
	public static void consoleMessage(String s){
		System.out.println("[" + pluginName + "] " + s);
	}
	
	public static void setupName(String s){
		pluginName = s;
	}
	
	public static void messagePlayer(Player p, String s){
		p.sendMessage(prefixChat(s));
	}
	
	private static String prefixChat(String s){
		return ChatColor.BLACK + "[" + ChatColor.DARK_GREEN + pluginName + ChatColor.BLACK + "]" + ChatColor.WHITE + s;
	}
	
}
