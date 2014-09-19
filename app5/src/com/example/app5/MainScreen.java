package com.example.app5;

import java.util.List;

import android.util.Log;

import com.example.app5.Input.TouchEvent;

public class MainScreen extends Screen{

	Sonic sonic = new Sonic (880,450, 150);
	float timer;
	
	public MainScreen(GLGraphics game) {
		super(game);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvents = Assets.input.getTouchEvents();
		int len = touchEvents.size(); 
		for(int i = 0; i < len; i++) { 
			TouchEvent event = touchEvents.get(i); 
			if(event.type == TouchEvent.TOUCH_UP) {
				Log.d("x "+event.x,"y "+event.y);
				if(inBounds(event, 730, 100, 1230, 260)) {
					game.setScreen(new GameScreen(game));
				}
			}
		}
		timer+=deltaTime;
		if (timer>0) {
			sonic.move(deltaTime);
			timer=0;
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
		Assets.logo.draw(0, 0, (int)(Assets.targetheight/460*540*.8) , (int)(Assets.targetheight*.8));
		Assets.playbutton.draw(730, 100, 1230, 260);
		Assets.version.draw(730, 600, 1280, 720);
		sonic.draw();
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