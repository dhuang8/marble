package com.example.app5;

public class Laser extends Bullet{
	
	public Laser(float x, float y, float xspeed, float yspeed, AndroidPicture bullet, int size, int xmax, int ymax) {
		super(x, y, xspeed, yspeed, bullet, size, xmax, ymax);
		type=1;
	}
	
	public boolean move (float deltaTime){
		return true;
	}
	
	public void update(Ship ship,float xdir,float ydir){
		pos=ship.pos;
		dir=MathStuff.outsideCircle(xdir, ydir, 60);
	}
	
	public void draw(float camera[]){
		sprite.setRotate((float) Math.toDegrees(Math.atan2(dir[1], dir[0])));
		float x=pos[0]+dir[0];
		float y=pos[1]+dir[1];
		while (x>0 && x<xmax && y>0 && y<ymax){
			int xcoor=(int) (x-dir[0]/2-(camera[0]-Assets.targetwidth/2));
			int ycoor=(int) (y-dir[1]/2-(camera[1]-Assets.targetheight/2));
			sprite.draw(xcoor-size/2,ycoor-3,xcoor+size/2,ycoor+3);
			x+=dir[0];
			y+=dir[1];
		}
	}
}
