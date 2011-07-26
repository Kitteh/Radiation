package com.seriousminecraft.Radiation.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
		int newRad = radProtect(p,RadiationConfig.radiationCounter);
		radiationLevel += newRad;
		if (newRad != 0)
			p.sendMessage(ChatColor.DARK_RED + "+" + ChatColor.DARK_GREEN + newRad
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
	
	/**
	 * Display radiation level for a player
	 * @param p Player
	 */
	public void displayRadiation(Player p){
		Messenger.messagePlayer(p, 
				ChatColor.GREEN + "Current Rad level : " + ChatColor.WHITE + radiationLevel);
	}
	
	/**
	 * Reduce the radiation given to a player through
	 * Radiation armor
	 * @param p Player
	 * @param rad Radiation Damage
	 * @return
	 */
	private int radProtect(Player p, int rad){
		if (rad <= 0)
			return 0;
		else{
			int newAmount = rad;
			ItemStack[] armor = p.getInventory().getArmorContents();
			for (int i=0;i<armor.length;i++){
				if (RadiationConfig.radProtectItems.containsKey(armor[i].getTypeId())){
					short damage = (short) (armor[i].getDurability() + 10 < 100 ? armor[i].getDurability() + 10 : 100);
					newAmount -= RadiationConfig.radProtectItems.get(armor[i].getTypeId());
					if (damage < 100){
						armor[i].setDurability(damage);
					}else{
						p.getInventory().setArmorContents(removeItemFromSet(armor,armor[i]));
					}
				}
			}
			return newAmount > 0 ? newAmount : 0; 
		}
	}
	
	/**
	 * Remove an item from an ArmorSet
	 * 
	 * @param a ItemStack of ArmorSet
	 * @param r ItemStack to remove
	 * @return
	 */
	private ItemStack[] removeItemFromSet(ItemStack[] a, ItemStack r){
		ItemStack[] armor = new ItemStack[4];
		for (int i=0;i<a.length;i++){
			armor[i] = a[i] == r ? null : a[i];
		}
		return armor;
	}
}
