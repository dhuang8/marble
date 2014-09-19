package com.example.app5;

import android.util.Log;

public class Sparkle {
	
	float[] pos;
	private final int angledif=18;
	private int angle;
	private double dis=Math.sqrt(80);
	private int state=0;
	
	public Sparkle(int angle,int size){
		this.angle=angle;
		pos= new float[] {0,0};
		dis*=size/38;
		move();
	}
	
	public void reset(int angle){
		this.angle=angle;
		pos= new float[] {0,0};
		state=0;
	}
	
	public void move(){
		if (state<6){
			pos[0]=(float) (pos[0]+(Math.cos(angle*Math.PI/180)*dis));
			pos[1]=(float) (pos[1]+(Math.sin(angle*Math.PI/180)*dis));
			angle+=angledif;
			angle=angle%360;
			state++;
			return;
		}
		reset(angle+45);
		move();
	}
	
	public void draw(int x, int y, int size){
		draw (new float[] {x,y},size);
	}
	
	public void draw(float[] sonic, int size){
		Assets.sonic.setRotate(0); 
		if (state<3) Assets.sonic.draw((int)sonic[0]+(int)pos[0],(int)sonic[1]+(int)pos[1],15*size/38/2,14,true);
		else if (state<5) Assets.sonic.draw((int)sonic[0]+(int)pos[0],(int)sonic[1]+(int)pos[1],31*size/38/2,15,true);
		else if (state<7) Assets.sonic.draw((int)sonic[0]+(int)pos[0],(int)sonic[1]+(int)pos[1],47*size/38/2,16,true);
	}
}
