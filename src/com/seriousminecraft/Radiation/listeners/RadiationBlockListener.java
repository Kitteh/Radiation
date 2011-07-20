package com.seriousminecraft.Radiation.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;

import com.seriousminecraft.Radiation.RadiationPlugin;
import com.seriousminecraft.Radiation.player.PlayerHandler;
import com.seriousminecraft.Radiation.regions.Region;

public class RadiationBlockListener extends BlockListener{

	RadiationPlugin plugin;
	public RadiationBlockListener(RadiationPlugin p){
		plugin = p;
	}
	
	@Override
	public void onBlockDamage(BlockDamageEvent e){
		if (e.getPlayer()==null)
			return;
		Player p = e.getPlayer();
		if (PlayerHandler.contains(p)){
			// What point are they selecting
			 if(!PlayerHandler.finalPoint(p)){
				 //first
				 Region r = plugin.regionHandler.getRegionFromQue(p);
				 r.addPoint(e.getBlock().getLocation().toVector());
				 plugin.regionHandler.updateQue(p, r);
				 plugin.regionHandler.selectPoint(p, r);
			 }else{
				 //last
				 Region r = plugin.regionHandler.getRegionFromQue(p);
				 r.addPoint(e.getBlock().getLocation().toVector());
				 plugin.regionHandler.updateQue(p, r);
				 plugin.regionHandler.selectPoint(p, r);
			 }
		}
		
	}
}
