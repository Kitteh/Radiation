package com.seriousminecraft.Radiation.player;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerHandler {
	private static final HashMap<Player, Boolean> regionSetters = new HashMap<Player, Boolean>();
	private static final HashMap<Player, RadiationPlayer> radiationPlayers = new HashMap<Player, RadiationPlayer>();
	
	public static boolean contains(Player p){
		return regionSetters.containsKey(p);
	}
	
	public static void add(Player p){
		if (regionSetters.containsKey(p)){
			regionSetters.put(p,true);
		}else{
			regionSetters.put(p, false);
		}
	}
	public static void remove(Player p){
		regionSetters.remove(p);
	}
	
	public static void setFinalPoint(Player p){
		regionSetters.remove(p);
		regionSetters.put(p, true);
	}
	public static boolean finalPoint(Player p){
		return regionSetters.get(p);
	}
	
	public static boolean checkLastRad(Player p){
		if (radiationPlayers.containsKey(p)){
			return radiationPlayers.get(p).checkLastRad();
		}else{
			addRadiationPlayer(p);
			return false;
		}
	}
	
	public static void addRadiationPlayer(Player p){
		radiationPlayers.put(p, new RadiationPlayer());
	}
	public static void removeRadiationPlayer(Player p){
		radiationPlayers.remove(p);
	}
	
	public synchronized static void updateRadiationLevels(){
		for (Entry<Player, RadiationPlayer> entry : radiationPlayers.entrySet()){
			entry.getValue().checkRadLevel(entry.getKey());
		}
	}
	
	public static RadiationPlayer getPlayer(Player p){
		return radiationPlayers.get(p);
	}
	
	public static void intilize(Plugin plugin){
		Player[] playerList = plugin.getServer().getOnlinePlayers();
		for (Player p : playerList){
			addRadiationPlayer(p);
		}
	}

	public static void clear() {
		radiationPlayers.clear();
	}
	
	/**
	 * Quick Fix find player by name (used in commandExecutor)
	 * this is a brute force search
	 */
	
	public static RadiationPlayer getRadiationByName(Player p){
		for (Entry<Player, RadiationPlayer> entry :radiationPlayers.entrySet()){
			if (entry.getKey().getName().equals(p.getName()))
				return entry.getValue();
		}
		return null;
	}
}
