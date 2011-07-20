package com.seriousminecraft.Radiation.util;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.seriousminecraft.Radiation.RadiationPlugin;

public class CommandHandler{
	
	RadiationPlugin plugin;
	
	public CommandHandler(RadiationPlugin p){
		plugin = p;
	}
	public boolean processCommand(CommandSender sender, Command command, String label, String[] args){
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command");
			return true;
		}
		if (command.getName().equalsIgnoreCase("radiation")){
			Player p = (Player) sender;
			if (RadiationConfig.permissionEnabled){
				if(!RadiationPlugin.permissionHandler.has(p, "Radiation.modify"))
					return true;
			}else{
				if(!p.isOp())
					return true;
			}
			
			if (args.length == 0){
				Messenger.messagePlayer(p, "You need to specify a name");
			}
			if (args.length == 2){
				if (args[0].equalsIgnoreCase("add")){
					plugin.regionHandler.userAddRegion(p, args[1]);
				}
				if (args[0].equalsIgnoreCase("delete")){
					deleteRegionCommand(p, args[1]);
				}
			}else{
				helpCommand(p);
			}
		}
		return true;
	}

	
	private void helpCommand(Player p){
		p.sendMessage(ChatColor.GREEN + Messenger.helpMessage);
		Messenger.messagePlayer(p, ChatColor.WHITE + 
				"/radiation" + ChatColor.BLUE + " [add]" + ChatColor.AQUA + " [RegionName]");
		Messenger.messagePlayer(p, ChatColor.DARK_AQUA + "e.g - " + ChatColor.WHITE + "/radiation " 
				+ ChatColor.BLUE + "add" + ChatColor.AQUA + " mybunker");
	}
	
	private void deleteRegionCommand(Player p, String s){
		if(plugin.regionHandler.deleteRegionCommand(s)){
			Messenger.messagePlayer(p, "The region: " + s + " was successfully deleted");
		}else{
			Messenger.messagePlayer(p, "There was a problem deleting your region: " + s);
		}
	}
}
