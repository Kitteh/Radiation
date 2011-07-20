package com.seriousminecraft.Radiation.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.seriousminecraft.Radiation.util.Messenger;
import com.seriousminecraft.Radiation.util.RadiationConfig;

public class RadiationPlayer {
	private long lastRad;
	private int radiationLevel = 0;
	
	public boolean checkLastRad(){
		if (lastRad + RadiationConfig.radiationInterval*1000 < System.currentTimeMillis()){
			return true;
		}
		return false;
	}
	
	public void increaseRadiation(Player p){
		lastRad = System.currentTimeMillis();
		radiationLevel += RadiationConfig.radiationCounter;
		p.sendMessage(ChatColor.DARK_RED + "+" + ChatColor.DARK_GREEN + RadiationConfig.radiationCounter 
				+ ChatColor.GREEN + " rads");
	}

	public void checkRadLevel(Player p){
		if (radiationLevel > RadiationConfig.radiationThreshold){
			int newHealth = (p.getHealth() - RadiationConfig.radiationDamage) > 0
			&& p.getHealth() - RadiationConfig.radiationDamage < 20 ? p.getHealth() - RadiationConfig.radiationDamage : 0;
			p.setHealth(newHealth);
			Messenger.messagePlayer(p, ChatColor.GREEN + "You are suffering from radiation sickness");
		}
	}
	
	public void useRadAway(Player p){
		radiationLevel = radiationLevel - RadiationConfig.radAwayAmount > 0 ? 
				radiationLevel - RadiationConfig.radAwayAmount : 0;
		String msg = radiationLevel < RadiationConfig.radiationThreshold ? 
				ChatColor.BLUE + "You feel much better" : ChatColor.RED + "You feel slightly better";
		Messenger.messagePlayer(p, msg);
	}
	
	public void displayRadiation(Player p){
		Messenger.messagePlayer(p, 
				ChatColor.GREEN + "Current Rad level : " + ChatColor.WHITE + radiationLevel);
	}
}
