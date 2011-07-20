package com.seriousminecraft.Radiation.player;

import com.seriousminecraft.Radiation.util.RadiationConfig;

public class RadiationPlayer {
	private long lastRad;
	
	public void setRad(){
		lastRad = System.currentTimeMillis();
	}
	public boolean checkRad(){
		if (lastRad + RadiationConfig.radiationInterval*1000 < System.currentTimeMillis()){
			setRad();
			return true;
		}
		return false;
	}
}
