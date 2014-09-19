package com.example.app5;

import java.util.List;

import com.example.app5.Input.TouchEvent;

import android.graphics.Bitmap;
import android.util.Log;

public class Controller {
	
	AndroidPicture pad;
	AndroidPicture center;
	int size;
	int pointer=-1;
	Input input;
	int[] coords = new int[2];
	boolean ready;
	double distance=100;
	float resize=.5f;
	int[] padc = new int[2];
	
	public Controller(Bitmap pad, Bitmap center, int size){
		this.pad=new AndroidPicture(pad);
		this.center=new AndroidPicture (center);
		this.size=size;
	}
	
	public void setPointer(int pointer){
		this.pointer=pointer;
		coords[0]=Assets.input.getTouchX(pointer);
		coords[1]=Assets.input.getTouchY(pointer);
		ready=true;
	}
	
	public boolean isSet(){
		return ready;
	}
	
	public void unSetPointer(){
		pointer=-1;
		ready=false;
	}
	
	public void update(){
		if (ready){		
			//if (Assets.input.isTouchDown(pointer)) {
				padc[0]=Assets.input.getTouchX(pointer);
				padc[1]=Assets.input.getTouchY(pointer);
				return;
			
			//}
			//Log.d("padup",""+pointer);
		}
		unSetPointer();
	}
	
	public void update(int x, int y){
		if (x==0) Log.d("WHY IS IT ","0");
		if (ready){
			padc[0]=x;
			padc[1]=y;
		}
	}
	
	private boolean inBounds(TouchEvent event, int left, int top, int right, int bot) { 
        if(event.x > left && event.x < right &&  
           event.y > top && event.y < bot)  
            return true; 
        else 
            return false; 
    }
	
	public void draw(){
		if (ready){
			center.draw(coords[0]-size/2, coords[1]-size/2, coords[0]+size/2, coords[1]+size/2);
			int[] c = withinCircle(padc);
			pad.draw(c[0]-size/2*resize, c[1]-size/2*resize, c[0]+size/2*resize, c[1]+size/2*resize);
			return;
		}
	}
	
	public int[] getOutput(){
		if (ready){
			int[] temp = withinCircle(padc);
			temp[0]-=coords[0];
			temp[1]-=coords[1];
			return temp;
		}
		return null;
	}
	
	private int[] withinCircle(int[] x){
		return withinCircle(x[0],x[1]);
	}
	
	private int[] withinCircle(int x, int y){
		int[] ret = new int[2];
		ret[0]=x;
		ret[1]=y;
		double ratio = distanceF(x,coords[0],y,coords[1])/distance;
		if (ratio<1) return ret;
		ret[0]=(int) ((ret[0]-coords[0])/ratio)+coords[0];
		ret[1]=(int) ((ret[1]-coords[1])/ratio)+coords[1];
		return ret;
	}
	
	private double distanceF(int x1, int x2, int y1, int y2){
		//Log.d(""+x1+","+x2+","+y1+","+y2,""+Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2)));
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}
	
	private double distanceF(int[] c, int x2, int y2){
		return distanceF(c[0],x2,c[1],y2);
	}
}
