package com.seriousminecraft.Radiation.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipException;

import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;


public class ConfigLoader {
	
	File file;
	Configuration config;
	Plugin plugin;
	
	public ConfigLoader(Plugin p){
		this.plugin = p;
	}
	
	public void newConfig(String dir, String filename){
		File folder = new File(dir);
		if (!folder.exists()){
			folder.mkdir();
		}
		file = new File(dir + filename);
		if (file.exists()){
			
		}else{
			createFile(filename);
		}
		
		config = new Configuration(file);
		if (config!=null)
			config.load();
		else
			Messenger.consoleMessage("Error while reading configuration file, delete and retry");
		return;
	}
	
	/*
	 * Create a new file 
	 */
	private void createFile(String n){
		try {
			file.createNewFile();
		} catch (IOException e) {
			Messenger.consoleMessage(" Error creating new config file, Are you sure the correct file permissions are in place?");
		}
		//Begin copying the file
		try{
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(n);
			if (is!=null){
				
				int len;
				FileOutputStream fos = new FileOutputStream(file);
				byte buf[] = new byte[1024];
				while ( (len=is.read(buf)) > 0 ){
					fos.write(buf,0,len);
				}
				fos.close();
				is.close();
				Messenger.consoleMessage("new config file written");
				
			}else{
				Messenger.consoleMessage("Failed to read config file from jar");
			}
		} catch (ZipException e){
			Messenger.consoleMessage("Error while creating new config file - try restarting your server! and report this error: " + e);
		} catch (IOException e){
			Messenger.consoleMessage(" Error creating new config file, Are you sure the correct file permissions are in place?" + e);
		} catch (Exception e ){
			Messenger.consoleMessage("Error while writing config file " + e);
		}
		return;
	}
	

	/*
	 * Reload the configuration File
	 */
	public void reloadConfig(){
		config.load();
	}
	
	public boolean setupRadiationConfig(){
		if (config!=null){
			RadiationConfig.setConfig(config);
			RadiationConfig.setupConfig();
			return true;
		}
		return false;
	}
}
