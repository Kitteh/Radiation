package com.seriousminecraft.Radiation.listeners;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.seriousminecraft.Radiation.RadiationPlugin;
import com.seriousminecraft.Radiation.player.PlayerHandler;
import com.seriousminecraft.Radiation.util.Messenger;
import com.seriousminecraft.Radiation.util.RadiationConfig;

public class RadiationPlayerListener extends PlayerListener{
	
	RadiationPlugin radiationPlugin;
	
	public RadiationPlayerListener(RadiationPlugin r) {
		radiationPlugin = r;
	}

	@Override
	public void onPlayerMove(PlayerMoveEvent e){
		if(radiationPlugin.regionHandler.contains(e.getPlayer().getLocation())){
			if(PlayerHandler.checkRad(e.getPlayer())){
				e.getPlayer().setHealth(e.getPlayer().getHealth() - RadiationConfig.radiationDamage);
				Messenger.messagePlayer(e.getPlayer(),"You are suffering from radiation!");
			}
		}
	}
	
}
