package com.example.app5;

public class ItemDrop extends GameObject {
	public ItemDrop(float x, float y, int type){
		pos[0]=x;
		pos[1]=y;
		num=type;
		spritesheet=Assets.weapons;
		size=50;
	}
	
	public int getType(){
		return num;
	}
	
	public void draw(float[] camera){
		//	if (pos[0]+size>camera[0]-Assets.targetwidth/2 && pos[1]+size>camera[1]-Assets.targetheight/2 && pos[0]-size<camera[0]+Assets.targetwidth/2 && pos[1]-size<camera[1]+Assets.targetheight/2){
		drawSpriteSheet(camera);
		//}
	}
}
