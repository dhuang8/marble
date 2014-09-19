package com.example.app5;

import java.io.IOException;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.Log;

public class LoadingScreen extends Screen{
	AndroidPicture loading;

	public LoadingScreen(GLGraphics game) {
		super(game);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK); 
    	Bitmap b= Bitmap.createBitmap(400, 100, Config.ARGB_8888);
    	Canvas c = new Canvas (b);
    	//paint.setStyle(Style.STROKE);
    	//paint.setStrokeWidth(10);
        //c.drawRect(0, 0, 99, 499, paint);
    	paint.setColor(Color.WHITE);
    	paint.setTextSize(50);
    	paint.setTextAlign(Align.CENTER);
    	paint.setStrokeWidth(5);
    	paint.setStyle(Style.FILL);
        c.drawText("Loading...",200,50,paint);
        loading = new AndroidPicture(b);
	}

	@Override
	public void update(float deltaTime) {
        loading.draw((int)Assets.targetwidth/2-800, (int)Assets.targetheight/2-200,(int)Assets.targetwidth/2+800, (int)Assets.targetheight/2+200);
	}

	@Override
	public void present(float deltaTime) {
    	load();
		Paint paint = new Paint();
		paint.setColor(Color.BLACK); 
    	Bitmap b= Bitmap.createBitmap(800, 60, Config.ARGB_8888);
    	Canvas c = new Canvas (b);
    	//paint.setStyle(Style.STROKE);
    	//paint.setStrokeWidth(10);
        //c.drawRect(0, 0, 99, 499, paint);
    	paint.setColor(Color.WHITE);
    	paint.setTextSize(50);
    	paint.setTextAlign(Align.CENTER);
    	paint.setStrokeWidth(5);
    	paint.setStyle(Style.FILL);
        c.drawText("Version 0.04a",400,60,paint);
        Assets.version = new AndroidPicture(b);
        
		game.setScreen(new MainScreen(game));
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public static void load(){
		AssetManager assets = Assets.assets;
		Bitmap bitmap;
		try {
			bitmap = BitmapFactory.decodeStream(assets.open("pad.png"));
	    	Bitmap bitmap2 = BitmapFactory.decodeStream(assets.open("center.png"));
			Assets.controller = new Controller(bitmap, bitmap2, 300);
			Assets.controller2 = new Controller(bitmap, bitmap2, 300);
			bitmap = BitmapFactory.decodeStream(assets.open("ration.png"));
			Assets.ship = new AndroidPicture(bitmap);
			bitmap = BitmapFactory.decodeStream(assets.open("hadouken.png"));
			Assets.hadouken = new AndroidPicture(bitmap);
			bitmap = BitmapFactory.decodeStream(assets.open("stage.png"));
			Assets.stage = new AndroidPicture(bitmap);
			bitmap = BitmapFactory.decodeStream(assets.open("marble.png"));
			Assets.marble = new AndroidPicture(bitmap);
			bitmap = BitmapFactory.decodeStream(assets.open("logo.png"));
			Assets.logo = new AndroidPicture(bitmap);
			bitmap = BitmapFactory.decodeStream(assets.open("playbutton.png"));
			Assets.playbutton = new AndroidPicture(bitmap);
			bitmap = BitmapFactory.decodeStream(assets.open("laser.png"));
			Assets.laser = new AndroidPicture(bitmap);
			bitmap = BitmapFactory.decodeStream(assets.open("red.png"));
			Assets.red = new AndroidPicture(bitmap);
			bitmap = BitmapFactory.decodeStream(assets.open("homing.png"));
			Assets.homing = new AndroidPicture(bitmap);
			bitmap = BitmapFactory.decodeStream(assets.open("homing2.png"));
			Assets.homing2 = new AndroidPicture(bitmap);
			bitmap = BitmapFactory.decodeStream(assets.open("hypersonic.png"));
			Assets.sonic = new SpriteSheet(bitmap,17);
			for (int n=0;n<7;n++){
				Assets.sonic.add(0, 1+n*37, 37, 37+n*37);
			}
			for (int n=0;n<7;n++){
				Assets.sonic.add(42, 0+n*37, 42+36, 36+n*37);
			}
			Assets.sonic.add(84, 10,84+15,10+15);
			Assets.sonic.add(104, 2, 104+31, 2+31);
			Assets.sonic.add(142, 6, 142+47, 6+47);
			
			bitmap = BitmapFactory.decodeStream(assets.open("gsicons.png"));
			Assets.weapons = new SpriteSheet(bitmap,5);
			Assets.weapons.add(4*16, 0, 15+4*16, 15);
			for (int n=0;n<4;n++){
				Assets.weapons.add(n*16, 0, 15+n*16, 15);
			}
			bitmap = BitmapFactory.decodeStream(assets.open("font.png"));
			Assets.font = new SpriteSheet(bitmap,62);
			for (int n=0;n<10;n++){
				Assets.font.add(1+n*8, 9, 8+n*8, 16, 7); //0-9 numbers
			}
			for (int n=0;n<15;n++){
				Assets.font.add(9+n*8, 17, 16+n*8, 24, 7); //cap letters
			}
			for (int n=0;n<11;n++){
				Assets.font.add(1+n*8, 25, 8+n*8, 32, 7); //cap letters
			}
			for (int n=0;n<15;n++){
				Assets.font.add(9+n*8, 33, 16+n*8, 40, 7); //lower letters
			}
			for (int n=0;n<11;n++){
				Assets.font.add(1+n*8, 41, 8+n*8, 48, 7); //lower letters
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}