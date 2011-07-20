package com.seriousminecraft.Radiation.regions;

import org.bukkit.util.Vector;

public class Region{
	
	private Cuboid cuboid;
	private String name;
	public long[] xyzA = new long[3];
	public long[] xyzB = new long[3];

	public Region(){
		//Empty
	}
	public Region(String s,int x1, int y1, int z1, int x2, int y2, int z2){
		name = s;
		xyzA[0] = (long) x1;
		xyzA[1] = (long) y1;
		xyzA[2] = (long) z1;
		xyzB[0] = (long) x2;
		xyzB[1] = (long) y2;
		xyzB[2] = (long) z2;
		init();
	}
	
	public boolean contains(Vector v){
		return cuboid.includesPoint((long) v.getX(), (long) v.getY(), (long) v.getZ());
	}
	
	public void addPoint(Vector v){
		if (empty(xyzA)){
			xyzA[0] = (long) v.getX();
			xyzA[1] = (long) v.getY();
			xyzA[2] = (long) v.getZ();
		}else{
			xyzB[0] = (long) v.getX();
			xyzB[1] = (long) v.getY();
			xyzB[2] = (long) v.getZ();
			init();
		}
	}
	
	public void init(){
		cuboid = new Cuboid(xyzA[0],xyzA[1],xyzA[2],xyzB[0],xyzB[1],xyzB[2]);
	}
	
	public void setName(String s){
		name = s;
	}
	
	public boolean checkName(String s){
		return name.equals(s);
	}
	
	private boolean empty(long[] vertice){
		int c = 0;
		for(int i=0;i<vertice.length;i++)
			c += vertice[i];
		if (c == 0)
			return true;
		return false;
	}
	public long[] getVertice(){
		return xyzA;
	}
	
	public String getName(){
		return name;
	}
}
