package com.seriousminecraft.Radiation.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import com.seriousminecraft.Radiation.RadiationPlugin;
import com.seriousminecraft.Radiation.player.PlayerHandler;
import com.seriousminecraft.Radiation.player.RadiationPlayer;
import com.seriousminecraft.Radiation.util.RadiationConfig;

public class RadiationPlayerListener extends PlayerListener {

	RadiationPlugin radiationPlugin;

	public RadiationPlayerListener(RadiationPlugin r) {
		radiationPlugin = r;
	}

	@Override
	public void onPlayerMove(PlayerMoveEvent e) {
		if (RadiationConfig.exemptPlayers && RadiationPlugin.permissionHandler!=null){
			if (RadiationPlugin.permissionHandler.has(e.getPlayer(), "Radiation.Exempt"))
				return;
		}
		if (RadiationConfig.falloutMode && !RadiationConfig.radiationWorld.equalsIgnoreCase("all")){
			if (e.getPlayer().getWorld().equals(RadiationConfig.radiationWorld))
				return;
		}
		if (RadiationConfig.falloutMode) {
			if (!radiationPlugin.regionHandler.contains(e.getPlayer()
					.getLocation())) {
				if (PlayerHandler.checkLastRad(e.getPlayer())) {
					PlayerHandler.getPlayer(e.getPlayer()).increaseRadiation(e.getPlayer());
				}
			}
		} else {
			if (radiationPlugin.regionHandler.contains(e.getPlayer()
					.getLocation())) {
				if (PlayerHandler.checkLastRad(e.getPlayer())) {
					PlayerHandler.getPlayer(e.getPlayer()).increaseRadiation(e.getPlayer());
				}
			}
		}
	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand() != null
				&& e.getPlayer().getItemInHand().getTypeId() == RadiationConfig.radAwayItemID) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR) {
				Player p = e.getPlayer();
				ItemStack oneRadAway = new ItemStack(RadiationConfig.radAwayItemID, 1);
				RadiationPlayer r = PlayerHandler.getPlayer(p);
				p.getInventory().remove(oneRadAway);
				if(r!=null)
					r.useRadAway(e.getPlayer());
			}
		}
	}

}
