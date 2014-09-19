package com.example.app5;

import android.graphics.Bitmap;

public class Ball {
	float velocityX=0; // pixels per second
	float velocityY=0;
	float posX;
	float posY;
	int size;
	AndroidPicture sprite;
	float maxSpeed=20; // max velocity
	float maxAccel=100; // max accelX and accelY
	float aScale=1f; // maxAccel*aScale is the real max accel
	float bounce=1f; //velocity after a wall hit
	float decelerate=.2f; // percentage of velocity retained every second
	
	public Ball(Bitmap bitmap, int size){
		posX=Assets.targetwidth/2;
		posY=Assets.targetheight/2;
		this.size=size;
		sprite = new AndroidPicture (bitmap);
	}
	
	public void move(float deltaTime, float accelX, float accelY){
		if (accelX>maxAccel) accelX=maxAccel;
		else if (accelX<-maxAccel)accelX=-maxAccel;
		if (accelY>maxAccel) accelY=maxAccel;
		else if (accelY<-maxAccel)accelY=-maxAccel;
		velocityX=(float) (velocityX*Math.pow(decelerate,deltaTime));
		velocityY=(float) (velocityY*Math.pow(decelerate,deltaTime));
		velocityX+=accelX*aScale*deltaTime;
		velocityY+=accelY*aScale*deltaTime;
		posX+=velocityX;
		posY+=velocityY;
		if (posX<size/2) {
			posX=size/2;
			velocityX=-velocityX*bounce;
		}
		else if (posX>Assets.targetwidth-size/2) {
			posX=Assets.targetwidth-size/2;
			velocityX=-velocityX*bounce;
		}
		if (posY<size/2) {
			posY=size/2;
			velocityY=-velocityY*bounce;
		}
		else if (posY>Assets.targetheight-size/2) {
			posY=Assets.targetheight-size/2;
			velocityY=-velocityY*bounce;
		}
		
		if (velocityX>maxSpeed) {
			velocityX=maxSpeed;
		}
		else if (velocityX<-maxSpeed) {
			velocityX=-maxSpeed;
		}
		
		if (velocityY>maxSpeed) {
			velocityY=maxSpeed;
		}
		else if (velocityY<-maxSpeed) {
			velocityY=-maxSpeed;
		}
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
	
	public void draw(){
		sprite.draw(posX-size/2, posY-size/2, posX+size/2, posY+size/2);
	}
}