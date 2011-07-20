package com.seriousminecraft.Radiation.util;

import org.bukkit.util.config.Configuration;

public class RadiationConfig {

	private static Configuration config;
	
	public static double version;
	
	public static int radiationInterval;
	public static int radiationDamage;
	public static boolean permissionEnabled;
	public static void setConfig(Configuration c){
		config = c;
	}
	
	public static void setupConfig(){
		config.load();
		checkVersion(config.getDouble("Info.Version", 0));
		radiationInterval = config.getInt("Settings.RadiationInterval", 10);
		radiationDamage = config.getInt("Settings.RadiationDamage", 1);
	}
	
	public static boolean checkVersion(double d){
		if (version > d)
			return updateVersion(d);
		else
			return true;
	}
	
	private static boolean updateVersion(double d){
		config.setProperty("Info.Version", version);
		return true;
	}
}
