package com.example.app5;

public class Marble extends Enemy {
	float dir[]=new float[2];
	float speed = 600f;
	final float spawntime=2f;
	
	public Marble (float x, float y, float dirx, float diry){
		pos = new float[] {x,y};
		dir = new float[] {dirx*speed,diry*speed};
		sprite = Assets.marble;
		size=100;
		maxhealth=5f;
		health=maxhealth;
		wait=spawntime;
	}
	
	public Marble (float shipx, float shipy){
		pos = new float[] {(float) (Math.random()*Assets.maxcoords[0]),(float) (Math.random()*Assets.maxcoords[1])};
		dir = vector((shipx-pos[0]),(shipy-pos[1]));
		sprite = Assets.marble;
		size=100;
		maxhealth=5f;
		health=maxhealth;
		wait=spawntime;
	}
	
	public Marble (){
		pos = new float[] {(float) (Math.random()*(Assets.maxcoords[0]-(2*size))+size),(float) (Math.random()*(Assets.maxcoords[1]-(2*size))+size)};
		double angle = Math.random()*2*Math.PI;
		dir = vector((float)Math.cos(angle),(float)Math.sin(angle));
		sprite = Assets.marble;
		size=100;
		maxhealth=5f;
		health=maxhealth;
		wait=spawntime;
	}
	
	public Marble (float[] shippos){
		this(shippos[0],shippos[1]);
		sprite = Assets.marble;
	}
		
	public void move (Ship ship, float deltaTime){
		move(deltaTime);
	}
	
	public void move (float deltaTime){
		if (wait<0){
			pos[0]+=dir[0]*deltaTime;
			pos[1]+=dir[1]*deltaTime;
			int out=outofBounds(pos);
			if (out==1) {
				dir[0]=-dir[0];
				pos[0]+=dir[0]*2*deltaTime;
			}
			else if (out==2) {
				dir[1]=-dir[1];
				pos[1]+=dir[1]*2*deltaTime;
			}
			return;
		}
		wait-=deltaTime;
	}
	
	public void draw(float[] camera){
	//	if (pos[0]+size>camera[0]-Assets.targetwidth/2 && pos[1]+size>camera[1]-Assets.targetheight/2 && pos[0]-size<camera[0]+Assets.targetwidth/2 && pos[1]-size<camera[1]+Assets.targetheight/2){
			sprite.setRotate((int)Math.toDegrees(Math.atan2(dir[1], dir[0]))); 
			if (wait>0) sprite.setOpacity(.7f);
			else sprite.setOpacity(1);
			drawSprite(camera);
			drawHealth(camera);
		//}
	}
	
	private float[] vector(float xspeed, float yspeed){
		float[] ret = new float[2];
		ret[0]=xspeed;
		ret[1]=yspeed;
		double ratio = distanceF(xspeed,0,yspeed,0)/speed;
		ret[0]=(float) (ret[0]/ratio);
		ret[1]=(float) (ret[1]/ratio);
		return ret;
	}
	private double distanceF(float x1, float x2, float y1, float y2){
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}
	private int outofBounds(float[] c){
		if (c[0]<size/2) return 1;
		else if (c[0]>Assets.maxcoords[0]-size/2) return 1;
		if (c[1]<size/2) return 2;
		else if (c[1]>Assets.maxcoords[1]-size/2) return 2;
		return 0;
	}
}