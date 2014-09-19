package com.example.app5;

import java.io.IOException;
import java.util.List;

import com.example.app5.Input.TouchEvent;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class GameScreen extends Screen{
	
	Controller controller;
	Controller controller2;
	AndroidPicture q;
	Ship ship;
	World bg;
	
	public GameScreen(GLGraphics game){
		super (game);
		controller = Assets.controller;
		controller2 =  Assets.controller2;
		ship = new Ship(100);
		//ship.reset();
		bg = new World(ship);
		Assets.world=bg;
	}

	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvents = Assets.input.getTouchEvents();
		int len = touchEvents.size(); 
		for(int i = 0; i < len; i++) { 
			TouchEvent event = touchEvents.get(i); 
			if(event.type == TouchEvent.TOUCH_DOWN) {
				if(inBounds(event, 0, 120, 600, 720) ) {
					if (controller.isSet()==false) controller.setPointer(event.pointer);
					//Log.d("paddown",""+event.pointer);
				}
				else if(inBounds(event, 680, 120, 1280, 720) ) {
					if (controller2.isSet()==false) controller2.setPointer(event.pointer);
					//Log.d("paddown",""+event.pointer);
				}
			}
			if(event.type == TouchEvent.TOUCH_UP) {
				//Log.d("touch up","2");
				if (event.pointer==controller.pointer) controller.unSetPointer();
				if (event.pointer==controller2.pointer) controller2.unSetPointer();
			}
			if(event.type == TouchEvent.TOUCH_DRAGGED) {
				if (event.pointer==controller.pointer) controller.update(event.x,event.y);
				if (event.pointer==controller2.pointer) controller2.update(event.x,event.y);
			}
		}
		
		//Log.d(""+Assets.input.isTouchDown(0),""+Assets.input.isTouchDown(1));
		if (bg.move(controller.getOutput(), controller2.getOutput(),deltaTime)==false){
			game.setScreen(new MainScreen(game));
			bg=null;
			controller.unSetPointer();
			controller2.unSetPointer();
			ship = null;
		}
	}

	private boolean inBounds(TouchEvent event, int left, int top, int right, int bot) { 
        if(event.x > left && event.x < right &&  
           event.y > top && event.y < bot)  
            return true; 
        else 
            return false; 
    }

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		//stage.draw(0f,0f,Assets.targetwidth,Assets.targetheight);
		//q.draw(500f,200f, 700f,500f);
		bg.draw();
		controller.draw();
		controller2.draw();
		//ship.draw();
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
}