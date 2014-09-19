package com.example.app5;

import android.util.Log;

public class Sonic extends Enemy{
	SpriteSheet sonic=Assets.sonic;
	float time=0;
	boolean alt=false;
	float[] vel = new float[2];
	int count=0;
	float velcap=800;
	float accelscale=5;
	float decel=.9f; //vel lost every sec
	Sparkle[] sparkle = new Sparkle[4];
	float shadow=0;
	int shadowd=0;
	float[][] shadowvel=new float[2][2];
	float[][] shadowpos=new float[2][2];
	final float spawntime=2f;
	float dtime=0;
	
	public Sonic(){
		pos = new float[] {(float) (Math.random()*(Assets.maxcoords[0]-(2*size))+size),(float) (Math.random()*(Assets.maxcoords[1]-(2*size))+size)};
		vel = new float[] {0,0};
		for (int n=0;n<4;n++){
			sparkle[n]=new Sparkle(360-n*90,size);
			for (int m=0; m<n;m++){
				sparkle[m].move();
			}
		}
		maxhealth=5f;
		health=maxhealth;
		size=100;
		wait=spawntime;
		for (int n=0;n<2;n++){
			shadowpos[n][0]=pos[n];
			shadowvel[n][0]=vel[n];
			shadowpos[n][1]=pos[n];
			shadowvel[n][1]=vel[n];
		}
	}
	
	public Sonic(int x, int y, int size){
		pos[0]=x;
		pos[1]=y;
		this.size=size;
		vel[0]=(float) (10);
		vel[1]=(float) (10);
		for (int n=0;n<4;n++){
			sparkle[n]=new Sparkle(360-n*90,size);
			for (int m=0; m<n;m++){
				sparkle[m].move();
			}
		}
		wait=spawntime;
	}
	
	private int outofBounds(float[] c){
		if (c[0]<size/2) return 1;
		else if (c[0]>Assets.maxcoords[0]-size/2) return 3;
		if (c[1]<size/2) return 2;
		else if (c[1]>Assets.maxcoords[1]-size/2) return 4;
		return 0;
	}
	
	private int outofBounds(float x, float y){
		return outofBounds(new float[] {x,y});
	}
	
	public void move(Ship ship, float deltaTime){
		dtime=deltaTime;
		time+=deltaTime;
		if (time>1) time-=1;
		if (time>.917) count=6;
		else if (time<.833 && time>.75) count=5;
		else if (time<.667 && time>.583) count=4;
		else if (time<.5 && time>.417) count=3;
		else if (time<.333 && time>.25) count=2;
		else if (time<.167 && time>.083) count=1;
		else count=0;
		if (alt) {
			count+=7;
		}
		alt=!alt;
		if (wait<0){
			for (int n=0;n<4;n++)sparkle[n].move();
			vel[0]*=Math.pow(decel, deltaTime);
			vel[1]*=Math.pow(decel, deltaTime);
			vel[0]+=(ship.getCoords()[0]-pos[0])*deltaTime*accelscale;
			vel[1]+=(ship.getCoords()[1]-pos[1])*deltaTime*accelscale;
			
			vel=MathStuff.withinCircle(vel[0], vel[1], velcap);
			
			/*for (int n=0;n<2;n++){
				if (vel[n]>velcap) vel[n]=velcap;
				else if (vel[n]<-velcap) vel[n]=-velcap;
			}*/
			
			int out=outofBounds(pos[0]+vel[0]*deltaTime,pos[1]+vel[1]*deltaTime);
			if (out==1) {
				vel[0]=Math.abs(vel[0]);
			}
			else if (out==2) {
				vel[1]=Math.abs(vel[1]);
			}
			else if (out==3) {
				vel[1]=-Math.abs(vel[0]);
			}
			else if (out==4) {
				vel[1]=-Math.abs(vel[1]);
			}
	
			pos[0]+=vel[0]*deltaTime;
			pos[1]+=vel[1]*deltaTime;
			
			return;
		}
		wait-=deltaTime;
	}
	
	public void move(float deltaTime){
		for (int n=0;n<4;n++)sparkle[n].move();
		time+=deltaTime;
		if (time>1) time-=1;
		if (time>.917) count=6;
		else if (time<.833 && time>.75) count=5;
		else if (time<.667 && time>.583) count=4;
		else if (time<.5 && time>.417) count=3;
		else if (time<.333 && time>.25) count=2;
		else if (time<.167 && time>.083) count=1;
		else count=0;
		if (alt) {
			count+=7;
		}
		alt=!alt;
	}
	
	public void draw(){
		Assets.sonic.draw((int)pos[0], (int)pos[1], size, count, true);
		for (int n=0;n<4;n++)sparkle[n].draw(pos,size);
	}
	
	public void draw(float[] camera){
		if (wait>0) Assets.sonic.setOpacity(.7f);
		else Assets.sonic.setOpacity(1);
		int xcoor=(int) (pos[0]-(camera[0]-Assets.targetwidth/2));
		int ycoor=(int) (pos[1]-(camera[1]-Assets.targetheight/2));
		if (wait<=0){
			int m=0;
			if (shadowd>1) m=1;
			shadow+=dtime;
			Assets.sonic.setRotate((int)Math.toDegrees(Math.atan2(shadowvel[1][m], shadowvel[0][m]))); 
			Assets.sonic.draw((int) (shadowpos[0][m]-(camera[0]-Assets.targetwidth/2)),  (int) (shadowpos[1][m]-(camera[1]-Assets.targetheight/2)),size,count,true);
			if (shadow>.03) {
				shadowd++;
				if (shadowd>3) shadowd=0;
				if (shadowd==1 || shadowd==3) {
					for (int n=0;n<2;n++){
						shadowpos[n][-m+1]=pos[n];
						shadowvel[n][-m+1]=vel[n];
					}
				}
				shadow=0;
			}
		}
		Assets.sonic.setRotate((int)Math.toDegrees(Math.atan2(vel[1], vel[0]))); 
		Assets.sonic.draw(xcoor,  ycoor, size, count, true);
		for (int n=0;n<4;n++)sparkle[n].draw(xcoor,ycoor,size);
		drawHealth(camera);
	}
}