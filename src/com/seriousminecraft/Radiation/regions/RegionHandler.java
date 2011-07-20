package com.seriousminecraft.Radiation.regions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashSet;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.alta189.sqlLibrary.SQLite.sqlCore;
import com.seriousminecraft.Radiation.player.PlayerHandler;
import com.seriousminecraft.Radiation.util.Messenger;
import com.seriousminecraft.Radiation.util.PluginLogger;

public class RegionHandler {
	private LinkedHashSet<Region> regions;
	private HashMap<Player,Region> regionQue;
	private sqlCore database;
	public RegionHandler(){
		regions = new LinkedHashSet<Region>();
		regionQue = new HashMap<Player,Region>();
	}
	
	public boolean contains(Location l){
		Vector v = l.toVector();
		for(Region r: regions){
			if(r.contains(v))
				return true;
		}
		return false;
	}
	
    public void createRegion(Region r, Player p){
    	try{
    		database.initialize();
	        database.insertQuery(regionToSQL(r));
	        regions.add(r);
    	}catch(Exception e){
    		Messenger.messagePlayer(p, "Region save unsuccessful");
    		PluginLogger.criticalError(e);
    	}finally{
    		database.close();
    	}
    }
	
	private String regionToSQL(Region r) {
		String sql = "INSERT INTO regions (name, x1, y1, z1, x2, y2, z2) VALUES("
					+ "'" + r.getName() + "',"
					+ "'" + r.xyzA[0] + "',"
					+ "'" + r.xyzA[1] + "',"
					+ "'" + r.xyzA[2] + "',"
					+ "'" + r.xyzB[0] + "',"
					+ "'" + r.xyzB[1] + "',"
					+ "'" + r.xyzB[2]
					+ "');";
		return sql;
	}
			

	public void initilize(sqlCore db){
		database = db;
		regions.clear();
		try{
		db.initialize();
		ResultSet rs = db.sqlQuery("SELECT * from regions");
		while(rs.next()){
            Region r = new Region(
            		rs.getString("name"),
            		rs.getInt("x1"),
            		rs.getInt("y1"),
            		rs.getInt("z1"),
            		rs.getInt("x2"),
            		rs.getInt("y2"),
            		rs.getInt("z2"));
            regions.add(r);
		}
		}catch(SQLException e){
			PluginLogger.criticalError(e);
		}finally{
			db.close();
		}
	}
	
	public boolean checkName(String s){
		for(Region r: regions){
			if(r.checkName(s)){
				return true;
			}
		}
		return false;
	}
	public void userAddRegion(Player p, String regionName){
		if(!checkName(regionName)){
			Region r = new Region();
			r.setName(regionName);
			selectPoint(p, r);
		}else{
			Messenger.messagePlayer(p, "Sorry a region by the name " + regionName + " already exists");
		}
	}
	
	public void selectPoint(Player p, Region r) {

		if (!PlayerHandler.contains(p)) {
			regionQue.put(p, r);
			PlayerHandler.add(p);
			Messenger.messagePlayer(p,
					"Please hit your first block to begin selecting");
		}else if (!PlayerHandler.finalPoint(p)){
			Messenger.messagePlayer(p, "Please select the second point");
			PlayerHandler.setFinalPoint(p);
		}else{
			PlayerHandler.remove(p);
			Messenger.messagePlayer(p, "Region selection complete - Saving region");
			createRegion(r,p);
		}
	}
	
	public Region getRegionFromQue(Player p){
		return regionQue.get(p);
	}
	
	public void updateQue(Player p, Region r){
		regionQue.remove(p);
		regionQue.put(p, r);
	}
	
	public boolean deleteRegionCommand(String s){
		if (checkName(s)){
			if(sqlDelete(s))
				deleteRegion(s);
			return true;
		}else
			return false;
	}
	
	private boolean sqlDelete(String s){
		String sql = "DELETE FROM regions WHERE name = " + "'" + s + "'";
		database.initialize();
		try{
			database.deleteQuery(sql);
			return true;
		}catch(Exception e){
			Messenger.consoleMessage("Error deleting region");
			return false;
		}finally{
			database.close();
		}
	}
	
	private void deleteRegion(String s){
		for (Region r : regions){
			if (r.checkName(s)){
				regions.remove(r);
				return;
			}
		}
	}
}
