package com.example.app5;

import java.util.LinkedList;

import android.util.Log;

public class Homing extends Bullet{
	float maxangle=(float)Math.PI*2*2; //2 full rotations
	float curangle=0;
	float angledif=5f; //angle change per sec
	Enemy target=null;
	
	public Homing(float x, float y, float xspeed, float yspeed, AndroidPicture sprite, int size, int xmax, int ymax){
		super(x,y,xspeed,yspeed,sprite,size,xmax,ymax);
		speed=5f;
		float[] speeddir = MathStuff.outsideCircle(xspeed,yspeed,100);
		dir[0]=speeddir[0]*speed;
		dir[1]=speeddir[1]*speed;
	}
	
	public void setDouble(float multiplier, float rotations, float angledif){
		dir[0]*=multiplier;
		dir[1]*=multiplier;
		this.maxangle=(float)Math.PI*2*rotations;
		this.angledif=angledif;
	}
	
	private boolean setTarget(LinkedList<Enemy> enemy){
		if (enemy==null) return false;
		target=enemy.get((int)(Math.random()*enemy.size()));
		return true;
	}
	
	public boolean move (float deltaTime){
		if (target==null || !target.status()) {
			setTarget(Assets.world.enemy);
		}
		if (target!=null && curangle<maxangle){
			double anglebet =(MathStuff.getAngle(target.pos[1]-pos[1],target.pos[0]-pos[0])-MathStuff.getAngle(dir[1],dir[0]))%(2*Math.PI);
			if (anglebet<0) anglebet+=Math.PI*2;
			if (anglebet>Math.PI*2) anglebet-=Math.PI*2;
			if (Math.abs(anglebet)<angledif*deltaTime) {
				dir[0]=target.pos[0]-pos[0];
				dir[1]=target.pos[1]-pos[1];
				dir=MathStuff.outsideCircle(dir[0],dir[1],speed*100);
				dir=MathStuff.withinCircle(dir[0],dir[1],speed*100);
			}
			else if ( anglebet<Math.PI ){
				dir=MathStuff.rotate(dir[1], dir[0], angledif*deltaTime);
				curangle+=angledif;
			}
			else {
				dir=MathStuff.rotate(dir[1], dir[0], -angledif*deltaTime);
				curangle+=angledif;
			}
		}
		pos[0]+=dir[0]*deltaTime;
		pos[1]+=dir[1]*deltaTime;
		if (outofBounds(pos)) {
			return false;
		}
		return true;
	}
}
