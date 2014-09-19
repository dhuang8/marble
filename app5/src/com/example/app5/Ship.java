package com.example.app5;

import java.util.LinkedList;
import java.util.ListIterator;

import android.util.Log;

public class Ship extends GameObject{
	int xmax=(int) Assets.targetwidth;
	int ymax=(int) Assets.targetheight;
	float scale=7f; //100*scale pixels per sec
	float speedx=0;
	float speedy=0;
	float decel=.1f; //retained velocity per second
	int limit=20;
	LinkedList<Bullet> bulletlist = new LinkedList<Bullet>();
	Bullet mainweapon;
	AndroidPicture bullet;
	float firerate=.1f; //time between shots
	float firecooldown=99; //keeps track of time between shots
	int weapon1=0;
	int weapon2=0;
	float health;
	float invul=0;
	
	public Ship(int size){
		this.sprite=Assets.ship;
		this.bullet = Assets.hadouken;
		this.size=size;
		pos[0]=Assets.targetwidth/2;
		pos[1]=Assets.targetheight/2;
		health=10f;
	}
	
	public void set(float x, float y){
		this.pos[0]=x;
		this.pos[1]=y;
		weapon1=((int)(Math.random()*3))+1;
	}
	
	public void set(float x, float y, int weapon){
		this.pos[0]=x;
		this.pos[1]=y;
		weapon1=weapon;
	}
	
	public void setMax(int[] x){
		xmax=x[0];
		ymax=x[1];
	}
	
	public void reset(){
		pos[0]=Assets.targetwidth/2;
		pos[1]=Assets.targetheight/2;
		speedx=0;
		speedy=0;
		health=10f;
	}
	
	public void addWeapon(int weapon){
		mainweapon=null;
		weapon2=weapon1;
		weapon1=weapon;
	}
	public void move(int x, int y, float deltaTime){
		pos[0]+=x*deltaTime*scale;
		pos[1]+=y*deltaTime*scale;
		if (pos[0]<size/2) pos[0]=size/2;
		else if (pos[0]>Assets.targetwidth-size/2) pos[0]=Assets.targetwidth-size/2;
		if (pos[1]<size/2) pos[1]=size/2;
		else if (pos[1]>Assets.targetheight-size/2) pos[1]=Assets.targetheight-size/2;
	}
	
	public void move(int[] x, int[] y, float deltaTime){
		if (invul>0){
			sprite.setOpacity(.5f);
			invul-=deltaTime;
		}
		else sprite.setOpacity(1);
		
		firecooldown+=deltaTime;
		if (x!=null){
			speedx=x[0];
			speedy=x[1];
			sprite.setRotate((int)Math.toDegrees(Math.atan2(x[1],x[0])));
		}
		else {
			speedx*=Math.pow(decel,deltaTime);
			speedy*=Math.pow(decel,deltaTime);
		}
		pos[0]+=speedx*deltaTime*scale;
		pos[1]+=speedy*deltaTime*scale;
		if (pos[0]<size/2) pos[0]=size/2;
		else if (pos[0]>xmax-size/2) pos[0]=xmax-size/2;
		if (pos[1]<size/2) pos[1]=size/2;
		else if (pos[1]>ymax-size/2) pos[1]=ymax-size/2;
		moveBullets(deltaTime);
		if (y!=null) shoot(y[0],y[1]);
		else mainweapon=null;
	}
	
	public void setInvul(float invul){
		this.invul=invul;
	}
	
	public boolean isInvul(){
		return invul>0;
	}
	
	public void draw(){
		sprite.draw(pos[0]-size/2, pos[1]-size/2, pos[0]+size/2, pos[1]+size/2);
	}
	
	public void draw(float[] c){
		drawSprite(c);
		drawBullets(c);
		if (weapon1>0){
			Assets.weapons.draw(0,0,50,50,weapon1);
		}
		if (weapon2>0){
			Assets.weapons.draw(50,0,100,50,weapon2);
		}
	}
	
	public void shoot(float xdir, float ydir){
		if (xdir==0 && ydir==0) {
			mainweapon=null;
			return;
		}
		int type=(int)Math.pow(3,(weapon1-1))+(int)Math.pow(3,weapon2-1);
		if (type==1) {
			if (firecooldown>firerate) {
				//Log.d("shoot","true");
				firecooldown=0;
				bulletlist.add(new Bullet(pos[0],pos[1],xdir,ydir,bullet,50,xmax,ymax));
			}
		}
		else if (type==2){
			if (firecooldown>firerate) {
				//Log.d("shoot","true");
				firecooldown=.02f;
				bulletlist.add(new Bullet(pos[0],pos[1],xdir,ydir,bullet,70,xmax,ymax));
			}
		}
		else if (type==3){
			if (mainweapon==null) mainweapon=new Laser(pos[0],pos[1],xdir,ydir,Assets.laser,50,xmax,ymax);
			else mainweapon.update(this,xdir,ydir);
		}
		else if (type==4){
			if (firecooldown>firerate) {
				//Log.d("shoot","true");
				firecooldown=0.05f;
				bulletlist.add(new Bullet(pos[0],pos[1],xdir,ydir,Assets.laser,50,xmax,ymax,20,6));
			}
		}
		else if (type==6){
			if (mainweapon==null) mainweapon=new Laser(pos[0],pos[1],xdir,ydir,Assets.laser,50,xmax,ymax);
			else mainweapon.update(this,xdir,ydir);
		}
		else if (type==9){
			if (firecooldown>firerate) {
				firecooldown=-.015f;
				bulletlist.add(new Homing(pos[0],pos[1],xdir,ydir,Assets.homing,20,xmax,ymax));
			}
		}
		else if (type==18){
			if (firecooldown>firerate) {
				firecooldown=.04f;
				Homing tmp=new Homing(pos[0],pos[1],xdir,ydir,Assets.homing2,20,xmax,ymax);
				tmp.setDouble(1.5f,6,10);
				bulletlist.add(new Homing(pos[0],pos[1],xdir,ydir,Assets.homing2,20,xmax,ymax));
			}
		}
		else{
			if (firecooldown>firerate) {
				firecooldown=-.03f;
				bulletlist.add(new Homing(pos[0],pos[1],xdir,ydir,Assets.homing,20,xmax,ymax));
			}
		}
	}
	
	public float[] getCoords(){
		float[] c = new float[2];
		c[0]=pos[0];
		c[1]=pos[1];
		return c;
	}
	
	public void shoot(int[] x){
		if (x!=null) if (x[0]!=0 | x[1]!=0) shoot(x[0],x[1]);
	}
	
	private void moveBullets(float deltaTime){
		if (mainweapon!=null) mainweapon.move(deltaTime);
		ListIterator<Bullet> bulletit = bulletlist.listIterator();
		while(bulletit.hasNext()){
			Bullet tmpbullet = bulletit.next();
			if (tmpbullet.move(deltaTime)==false) bulletit.remove();			
		}
	}
	
	private void drawBullets(float[] c){
		if (mainweapon!=null) mainweapon.draw(c);
		ListIterator<Bullet> bulletit = bulletlist.listIterator();
		while(bulletit.hasNext()){
			bulletit.next().draw(c);
		}
	}
}