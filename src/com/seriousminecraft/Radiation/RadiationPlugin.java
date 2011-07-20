package com.seriousminecraft.Radiation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.alta189.sqlLibrary.SQLite.sqlCore;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.seriousminecraft.Radiation.listeners.RadiationBlockListener;
import com.seriousminecraft.Radiation.listeners.RadiationPlayerListener;
import com.seriousminecraft.Radiation.regions.RegionHandler;
import com.seriousminecraft.Radiation.util.CommandHandler;
import com.seriousminecraft.Radiation.util.ConfigLoader;
import com.seriousminecraft.Radiation.util.Messenger;
import com.seriousminecraft.Radiation.util.PluginLogger;
import com.seriousminecraft.Radiation.util.RadiationConfig;


public class RadiationPlugin extends JavaPlugin{
	
	private final CommandHandler commandHandler = new CommandHandler(this);
	public final RegionHandler regionHandler = new RegionHandler();
	public static PermissionHandler permissionHandler;
	public static sqlCore databaseHandler;
	@Override
	public void onDisable() {
		
	}

	@Override
	public void onEnable() {
		
		Messenger.setupName(getDescription().getName());
		
		RadiationPlayerListener playerListener = new RadiationPlayerListener(this);
		RadiationBlockListener blockListener = new RadiationBlockListener(this);
		
		getServer().getPluginManager().registerEvent(Type.PLAYER_MOVE, playerListener, Priority.Monitor, this);
		getServer().getPluginManager().registerEvent(Type.BLOCK_DAMAGE, blockListener, Priority.Low, this);
		
		PluginLogger.setupLogger("plugins/" + getDescription().getName(), "/log.txt");
		ConfigLoader configLoader = new ConfigLoader(this);
		configLoader.newConfig("plugins/" + this.getDescription().getName() + "/", "config.yml");
		configLoader.setupRadiationConfig();
		
		RadiationConfig.version = Double.parseDouble(getDescription().getVersion());
		
		databaseHandler = new sqlCore(getServer().getLogger(), getDescription().getName(), getDescription().getName(), 
				getDataFolder().getAbsolutePath());
		databaseHandler.initialize();
		databaseHandler.updateQuery("CREATE TABLE IF NOT EXISTS regions " +
				"(id INTEGER PRIMARY KEY AUTOINCREMENT, name STRING, x1 INTEGER , y1 INTEGER, z1 INTEGER, x2 INTEGER, y2, INTEGER, z2 INTEGER)");
		databaseHandler.close();
		
		regionHandler.initilize(databaseHandler);
		
		if (permissionPluginCheck()){
			RadiationConfig.permissionEnabled = true;
			Messenger.consoleMessage("Permissions Detected!");
		}else{
			RadiationConfig.permissionEnabled = false;
			Messenger.consoleMessage("Permissions not detected defaulting to OP");
		}
		
		Messenger.helpMessage = "Setup a Radiation region usage :";
		Messenger.consoleMessage("Version [" + getDescription().getVersion() +  "] Enabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return commandHandler.processCommand(sender, command, label, args);
	}
	
	public void onCriticalError(){
		getServer().getPluginManager().disablePlugin(this);
	}
	
	/*
	 * Check if permissions is enabled
	 */
	private boolean permissionPluginCheck(){
		Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
		if (permissionsPlugin == null)
			return false;
		permissionHandler = ((Permissions) permissionsPlugin).getHandler();
		return true;
	}
}
