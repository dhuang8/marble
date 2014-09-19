package com.example.app5;

public class Bullet extends GameObject{
	float dir[]= new float[2];
	float speed=12f;
	int xmax;
	int ymax;
	int type=0;
	int sizey;
	
	public Bullet (float x, float y, float xspeed, float yspeed, AndroidPicture sprite, int size, int xmax, int ymax){
		pos[0]=x;
		pos[1]=y;
		float[] speeddir = MathStuff.outsideCircle(xspeed,yspeed,100);
		dir[0]=speeddir[0]*speed;
		dir[1]=speeddir[1]*speed;
		this.sprite=sprite;
		this.size=size;
		sizey=size;
		this.xmax=xmax;
		this.ymax=ymax;
	}
	
	public Bullet (float x, float y, float xspeed, float yspeed, AndroidPicture sprite, int xsize, int xmax, int ymax, int speed, int ysize){
		pos[0]=x;
		pos[1]=y;
		float[] speeddir = MathStuff.outsideCircle(xspeed,yspeed,100);
		this.speed=speed;
		dir[0]=speeddir[0]*speed;
		dir[1]=speeddir[1]*speed;
		this.sprite=sprite;
		this.size=xsize;
		sizey=ysize;
		this.xmax=xmax;
		this.ymax=ymax;
	}
	
	public boolean move (float deltaTime){
		pos[0]+=dir[0]*deltaTime;
		pos[1]+=dir[1]*deltaTime;
		if (outofBounds(pos)) {
			return false;
		}
		return true;
	}
	
	public void update(Ship ship,float xdir,float ydir){
		
	}
	
	protected boolean outofBounds(float[] c){
		if (c[0]<size/2) return true;
		else if (c[0]>xmax-size/2) return true;
		if (c[1]<size/2) return true;
		else if (c[1]>ymax-size/2) return true;
		return false;
	}
	
	public void draw(float[] c){
		sprite.setRotate((int)Math.toDegrees(Math.atan2(dir[1], dir[0])));
		//drawSprite(c);
		int xcoor=(int) (pos[0]-(c[0]-Assets.targetwidth/2));
		int ycoor=(int) (pos[1]-(c[1]-Assets.targetheight/2));
		sprite.draw(xcoor-size/2,ycoor-sizey/2,xcoor+size/2,ycoor+sizey/2);
	}
	
	public float[] getPos(){
		return pos;
	}
	
	private void dispose(){
		sprite.dispose();
	}
	
	private int[] vector(int xspeed, int yspeed){
		int[] ret = new int[2];
		ret[0]=xspeed;
		ret[1]=yspeed;
		double ratio = distanceF(xspeed,0,yspeed,0)/100;
		ret[0]=(int) (ret[0]/ratio);
		ret[1]=(int) (ret[1]/ratio);
		return ret;
	}
	
	private double distanceF(int x1, int x2, int y1, int y2){
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}
}