package com.example.app5;

public class Enemy extends GameObject{
	float wait;
	float maxhealth;
	float health;
	public void move(Ship ship, float deltaTime) {
	}	
	public int check(Ship ship) {
		return 0;
	}
	public boolean status(){
		if(health<=0) return false;
		return true;
	}
	public void draw(float[] camera) {
	}
	public void drawHealth(float[] camera) {
		Assets.red.setOpacity(.8f);
		int xcoor=(int) (pos[0]-(camera[0]-Assets.targetwidth/2));
		int ycoor=(int) (pos[1]-(camera[1]-Assets.targetheight/2));
		Assets.red.draw(xcoor-size/2, ycoor-size/2-5, xcoor-size/2+size*health/maxhealth, ycoor-size/2);
	}
	public void healthLoss(float damage){
		health-=damage;
	}
}