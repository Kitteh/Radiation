package com.seriousminecraft.Radiation.player;

import java.util.HashMap;

import org.bukkit.entity.Player;

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
	
	public static boolean checkRad(Player p){
		if (radiationPlayers.containsKey(p)){
			return radiationPlayers.get(p).checkRad();
		}else{
			addRadiationPlayer(p);
			return false;
		}
	}
	
	public static void addRadiationPlayer(Player p){
		radiationPlayers.put(p, new RadiationPlayer());
	}
}
