package com.seriousminecraft.Radiation.player;

public class RadiationTicker implements Runnable{
	
	private long timer;
	private boolean running;
	private Thread tickerThread;
	/*
	 * @parm time in seconds to tick
	 */
	public RadiationTicker(long timer, boolean running){
		this.timer = timer * 1000;
		this.running = running;
		tickerThread = new Thread(this);
		tickerThread.start();
	}
	
	@Override
	public void run(){
		while (running){
			try {
				PlayerHandler.updateRadiationLevels();
				Thread.sleep(timer);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	public void stop(){
		tickerThread.interrupt();
	}
}
