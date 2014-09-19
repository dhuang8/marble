package com.example.app5;

public class GameObject {
	public float pos[] = new float [2];
	AndroidPicture sprite;
	SpriteSheet spritesheet;
	int size;
	int num;
	
	public void drawSprite(float[] camera){
		int xcoor=(int) (pos[0]-(camera[0]-Assets.targetwidth/2));
		int ycoor=(int) (pos[1]-(camera[1]-Assets.targetheight/2));
		//if (xcoor+size/2>0 && xcoor-size/2<Assets.targetwidth && ycoor+size/2>0 && ycoor-size/2<Assets.targetheight)
			sprite.draw(xcoor,ycoor,size);
	}
	
	public void drawSpriteSheet(float[] camera){
		int xcoor=(int) (pos[0]-(camera[0]-Assets.targetwidth/2));
		int ycoor=(int) (pos[1]-(camera[1]-Assets.targetheight/2));
		//if (xcoor+size>0 && xcoor-size<Assets.targetwidth && ycoor+size>0 && ycoor-size<Assets.targetheight)
			spritesheet.drawCenter(xcoor,ycoor,num,size);
	}
}