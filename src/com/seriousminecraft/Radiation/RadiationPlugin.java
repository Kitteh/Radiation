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
import com.seriousminecraft.Radiation.listeners.RadiationEntityListener;
import com.seriousminecraft.Radiation.listeners.RadiationPlayerListener;
import com.seriousminecraft.Radiation.player.PlayerHandler;
import com.seriousminecraft.Radiation.player.RadiationTicker;
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
	private RadiationTicker radiationTicker;
	
	public void onDisable() {
		PlayerHandler.clear();
		radiationTicker.stop();
	}

	@Override
	public void onEnable() {
		
		Messenger.setupName(getDescription().getName());
		
		RadiationPlayerListener playerListener = new RadiationPlayerListener(this);
		RadiationBlockListener blockListener = new RadiationBlockListener(this);
		RadiationEntityListener entityListener = new RadiationEntityListener();
		
		getServer().getPluginManager().registerEvent(Type.PLAYER_MOVE, playerListener, Priority.Monitor, this);
		getServer().getPluginManager().registerEvent(Type.BLOCK_DAMAGE, blockListener, Priority.Low, this);
		getServer().getPluginManager().registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Type.ENTITY_DEATH, entityListener, Priority.Monitor, this);
		
		RadiationConfig.version = Double.parseDouble(getDescription().getVersion());
		PluginLogger.setupLogger("plugins/" + getDescription().getName(), "/log.txt");
		ConfigLoader configLoader = new ConfigLoader(this);
		configLoader.newConfig("plugins/" + this.getDescription().getName() + "/", "config.yml");
		configLoader.setupRadiationConfig();
		
		
		databaseHandler = new sqlCore(getServer().getLogger(), getDescription().getName(), getDescription().getName(), 
				getDataFolder().getAbsolutePath());
		databaseHandler.initialize();
		databaseHandler.createTable("CREATE TABLE IF NOT EXISTS regions " +
				"(id INTEGER PRIMARY KEY AUTOINCREMENT, name STRING, x1 INTEGER , y1 INTEGER, z1 INTEGER, x2 INTEGER, y2, z2 INTEGER)");
		databaseHandler.close();
		
		regionHandler.initilize(databaseHandler);
		
		if (permissionPluginCheck()){
			RadiationConfig.permissionEnabled = true;
			Messenger.consoleMessage("Permissions Detected!");
		}else{
			RadiationConfig.permissionEnabled = false;
			Messenger.consoleMessage("Permissions not detected defaulting to OP");
		}
		
		// Create thread for the timers
		radiationTicker = new RadiationTicker(RadiationConfig.radiationChecker, true);

		
		Messenger.helpMessage = "Setup a Radiation region usage :";
		Messenger.consoleMessage("Version [" + getDescription().getVersion() +  "] Enabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return commandHandler.processCommand(sender, command, label, args);
	}
	
	public void criticalError(){
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
