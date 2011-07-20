package com.seriousminecraft.Radiation.util;

import org.bukkit.util.config.Configuration;

public class RadiationConfig {

	private static Configuration config;
	
	public static double version;
	
	public static int radiationInterval;
	public static int radiationDamage;
	public static int radiationChecker;
	public static int radiationCounter;
	public static int radiationThreshold;
	public static boolean permissionEnabled;
	
	public static boolean falloutMode;
	public static String radiationWorld;
	
	public static int radAwayItemID;
	public static int radAwayAmount;
	
	public static boolean exemptPlayers;
	
	public static void setConfig(Configuration c){
		config = c;
	}
	
	public static void setupConfig(){
		config.load();
		checkVersion(config.getDouble("Info.Version", 0));
		radiationWorld = config.getString("Settings.RadiationWorld", "all");
		radiationInterval = config.getInt("Settings.RadiationInterval", 10);
		radiationDamage = config.getInt("Settings.RadiationDamage", 1);
		radiationChecker = config.getInt("Settings.RadiationChecker", 60);
		radiationCounter = config.getInt("Settings.RadiationCounter", 20);
		radiationThreshold = config.getInt("Settings.RadiationThreshold", 100);
		falloutMode = config.getBoolean("Settings.GlobalFallout", false);
	    exemptPlayers = config.getBoolean("Settings.ExemptPlayers", false);
		
		//AntiRad items
		radAwayItemID = config.getInt("Settings.RadAwayItemID", 353);
		radAwayAmount = config.getInt("Settings.RadAwayAmount", 100);
	}
	
	public static boolean checkVersion(double d){
		if (version > d)
			return updateVersion(d);
		else
			return true;
	}
	
	private static boolean updateVersion(double d){
		Messenger.consoleMessage("Updating config");
		//Update 1.0 -> 1.1
		if (d == 1.0){
			config.load();
			config.setProperty("Settings.RadiationChecker", 60);
			config.setProperty("Settings.RadiationCounter", 20);
			config.setProperty("Settings.RadiationThreshold", 100);
			config.setProperty("Settings.GlobalFallout", false);
			config.setProperty("Settings.RadAwayItemID", 353);
			config.setProperty("Setings.RadAwayAmount", 100);
			config.setProperty("Info.Version", version);
			config.save();
			Messenger.consoleMessage("Config version updated - " + version);
			return true;
		}
		//update 1.1 -> 1.2
		if (d == 1.1){
			config.load();
			config.setProperty("Settings.RadiationWorld", "all");
			config.setProperty("ExemptPlayers", false);
			config.setProperty("Info.Version", version);
			config.save();
			Messenger.consoleMessage("Config version updated - " + version);
			return true;
		}
		return false;
	}
}
