package com.example.app5;

import java.util.ListIterator;

import android.util.Log;

public class MathStuff {
	public static double distance(float x1, float x2, float y1, float y2){
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}
	
	//keeps vector within distance
	public static float[] withinCircle(float x, float y, float distance){
		float[] ret = new float[2];
		ret[0]=x;
		ret[1]=y;
		double ratio = distance(x,0,y,0)/distance;
		if (ratio<1) return ret;
		ret[0]=(int) (ret[0]/ratio);
		ret[1]=(int) (ret[1]/ratio);
		return ret;
	}
	
	//stretches vector to distance
	public static float[] outsideCircle(float x, float y, float distance){
		float[] ret = new float[2];
		double ratio = distance(x,0,y,0)/distance;
		ret[0]=(int) (x/ratio);
		ret[1]=(int) (y/ratio);
		return ret;
	}
	
	public static float[] rotate(float y, float x, float angle){
		float dis=(float)distance(x,0,y,0);
		float[] ret = new float[2];
		ret[0] = (float) (x * Math.cos(angle) - y * Math.sin(angle));
		ret[1]= (float) (x * Math.sin(angle) + y * Math.cos(angle));
		return ret;
	}
	
	public static int check(Ship ship, Enemy enemy, float deltaTime){
		if (enemy.wait<0){
			if (!ship.isInvul() && distance(ship.getCoords()[0],enemy.pos[0],ship.getCoords()[1],enemy.pos[1])<(enemy.size/2+ship.size/2)){
				return 2; //hit the ship
			}
			if (ship.mainweapon!=null) checkhit(ship.mainweapon,enemy,deltaTime);
			ListIterator<Bullet> bulletit = ship.bulletlist.listIterator();
			while (bulletit.hasNext()){
				Bullet tmpbullet = bulletit.next();
				if (checkhit(tmpbullet,enemy,deltaTime)) 
					bulletit.remove();
			}
			if (enemy.health<=0) return 1;
		}
		return 0;		
	}
	
	private static boolean checkhit(Bullet tmpbullet,Enemy enemy,float deltaTime){
		if (tmpbullet.type==0){
			if (distance(tmpbullet.getPos()[0],enemy.pos[0],tmpbullet.getPos()[1],enemy.pos[1])<(enemy.size/2+tmpbullet.size/2)){
				enemy.health-=1;
				return true; //1=destroyed
			}
		}
		else if (tmpbullet.type==1){
			float a=-tmpbullet.dir[1];
			float b=tmpbullet.dir[0];
			float c=tmpbullet.dir[1]*tmpbullet.pos[0]-tmpbullet.pos[1]*tmpbullet.dir[0];
			float p=enemy.pos[0];
			float q=enemy.pos[1];
			float normalLength = (float) Math.hypot(a, b);
			float distance=  Math.abs (a*p+b*q+c) / normalLength;
			double angle=getAngle(-a,b);
			if (Math.abs(distance)>(enemy.size/2) || Math.abs(getAngle(q-tmpbullet.pos[1],p-tmpbullet.pos[0])-angle)>Math.PI/2) {
				return false;
			}
			enemy.health-=.2*deltaTime*60;
			return true;
		}
		return false;
	}
	
	public static double getAngle(float y, float x){
		return Math.atan2(y,x);
	}
	
	public static boolean checkItem(Ship ship, ItemDrop item, float deltaTime){
		if (distance(ship.getCoords()[0],item.pos[0],ship.getCoords()[1],item.pos[1])<(item.size/2+ship.size/2)){
			return true; //hit the ship
		}
		return false;
	}
}
