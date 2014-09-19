package com.example.app5;

import java.util.LinkedList;
import java.util.ListIterator;

import android.util.Log;

public class World {
	float[] camera=new float[2];
	AndroidPicture bg;
	float scale=1.5f; // map size
	Ship ship;
	float focus = .7f; //1 is ship, 0 is center
	int[] maxcoords;
	LinkedList<Enemy> enemy = new LinkedList<Enemy>();
	LinkedList<ItemDrop> item = new LinkedList<ItemDrop>();
	float enemytimer=99f;
	float erespawn=1.3f;
	int score=0;
	float end=0f;
	
	public World (Ship ship){
		Assets.maxcoords = new int[] {(int) (Assets.targetwidth*scale),(int) (Assets.targetheight*scale)};
		maxcoords=Assets.maxcoords;
		camera[0]=Assets.targetwidth/2*scale;
		camera[1]=Assets.targetheight/2*scale;
		this.bg=Assets.stage;
		this.ship=ship;
		this.ship.set(camera[0],camera[1]);
		this.ship.setMax(maxcoords);
	}
	
	public void moveCamera(int x, int y){
		camera[0]=(int) (x*focus+maxcoords[0]/2*(1-focus));
		camera[1]=(int) (y*focus+maxcoords[1]/2*(1-focus));
	}
	
	public void moveCamera (float[] x){
		moveCamera((int)x[0],(int)x[1]);
	}
	
	public boolean move (int[] x, int[] y, float deltaTime){
		if (end>0) {
			if (end<1f){
				end+=deltaTime;
				return true;
			}
			return false;
		}
		enemytimer+=deltaTime;
		if (enemytimer>erespawn){
			if (Math.random()<.5) enemy.add(new Marble());
			else enemy.add(new Sonic());
			enemytimer=0f;
		}
		ship.move(x, y, deltaTime); 
		//Log.d("sonic x "+sonic.pos[0],"y "+sonic.pos[1]);
		this.moveCamera(ship.getCoords());
		ListIterator<Enemy> enemyit= enemy.listIterator();
		while (enemyit.hasNext()){
			Enemy tmpenemy=enemyit.next();
			tmpenemy.move(ship, deltaTime);
			int res = MathStuff.check(ship, tmpenemy, deltaTime);
			if (res==2) { 
				ship.health--;
				ship.setInvul(2f);
				if (ship.health<=0) end=.01f;
			}
			else if (res==1){
				if (Math.random()>.5) item.add(new ItemDrop(tmpenemy.pos[0], tmpenemy.pos[1],(int)(Math.random()*4)));
				enemyit.remove();
				score++;
				Log.d("World","score: "+score);			
			}
		}
		ListIterator<ItemDrop> it= item.listIterator();
		while (it.hasNext()){
			ItemDrop tmpitem=it.next();
			//tmpitem.move(ship, deltaTime);
			if (MathStuff.checkItem(ship, tmpitem, deltaTime)){
				if (tmpitem.getType()>0) ship.addWeapon(tmpitem.getType());
				it.remove();
				tmpitem=null;
			}
		}
		return true;
	}
	
	public void draw (){
		//AndroidPicture.setBlend(false);
		bg.draw(-camera[0]+Assets.targetwidth/2,-camera[1]+Assets.targetheight/2,-camera[0]+Assets.targetwidth/2+maxcoords[0],-camera[1]+Assets.targetheight/2+maxcoords[1]);
		//AndroidPicture.setBlend(true);
		ListIterator<ItemDrop> it = item.listIterator();
		while (it.hasNext()){
			it.next().draw(camera);	
		}
		ListIterator<Enemy> enemyit = enemy.listIterator();
		while (enemyit.hasNext()){
			enemyit.next().draw(camera);	
		}
		ship.draw(camera);
		int health = (int)ship.health;
		if (health<0) health=0;				
		Text.draw("Health "+(int)ship.health, 150, 0, 25);
		Text.draw("Score "+(int)score, 700, 0, 25);
	}
}
