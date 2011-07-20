package com.seriousminecraft.Radiation.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import com.seriousminecraft.Radiation.player.PlayerHandler;

public class RadiationEntityListener extends EntityListener{
	
	@Override
	public void onEntityDeath(EntityDeathEvent e){
		if (e.getEntity() instanceof Player){
			PlayerHandler.removeRadiationPlayer((Player)e.getEntity());
		}
	}
}
