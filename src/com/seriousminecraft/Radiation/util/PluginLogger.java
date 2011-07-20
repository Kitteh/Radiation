package com.seriousminecraft.Radiation.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;


public class PluginLogger{
	
	public enum LogType{
		ERROR,WARNING,TRY,CHANGE
	}
	
	private static File logFile;
	public static Logger log;
	
	public static void setupLogger(String dir, String fileName){
		try{
			if (new File(dir).exists())
				logFile = new File(dir + fileName);
			else{
				new File(dir).mkdir();
				setupLogger(dir, fileName);
			}
			if (!logFile.exists()){
				logFile.createNewFile();
			}
		}catch(IOException e){
			Messenger.consoleMessage("Could not intialize logger : " + e.getMessage());
		}
	}
	
	public static void log(LogType type, String msg){
		try {
			Calendar c = Calendar.getInstance();
			String date = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + " "
							+ c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH) 
							+ "-" + c.get(Calendar.YEAR);
			BufferedWriter out = new BufferedWriter(new FileWriter(logFile, true));
			out.append("("+ date + ") " + "[" + type + "] " + msg);
			out.newLine();
			out.close();
		} catch (IOException e) {
			Messenger.consoleMessage("Error logging message : " + e.getMessage());
		}
	}
	
	public static void criticalError(Exception e){
		Messenger.consoleMessage(LogType.ERROR + " " + e.getMessage());
		log(LogType.ERROR,e.getStackTrace().toString());
	}
}
