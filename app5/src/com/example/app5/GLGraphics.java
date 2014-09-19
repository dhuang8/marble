package com.example.app5;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class GLGraphics implements GLSurfaceView.Renderer{
	Screen screen;
	MainActivity a;
	enum GLGameState {Initialized, Running, Paused, Finished, Idle}
	GLGameState state = GLGameState.Initialized;
	Object stateChanged = new Object();
	long startTime = System.nanoTime();
	private int frames=0;
	float fpsTimer;
	
	public GLGraphics(MainActivity a){
		this.a = a;
	}
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		//Log.d("state",state.toString());
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		GLGameState state = null; 
        
		synchronized(stateChanged) { 
			state = this.state; 
		} 
	          
		if(state == GLGameState.Running) { 
			float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f; 
			startTime = System.nanoTime(); 
			//Log.d("screen","update");
			screen.update(deltaTime); 
			screen.present(deltaTime); 
			
			frames++; 
	        fpsTimer+=deltaTime;
			if(fpsTimer >= 1) { 
	            Log.d("FPSCounter", "fps: " + frames);
	            frames = 0; 
	            fpsTimer=0;
	            } 
		} 
	          
		if(state == GLGameState.Paused) { 
			screen.pause();             
			synchronized(stateChanged) { 
				this.state = GLGameState.Idle; 
				stateChanged.notifyAll(); 
			} 
		} 
	          
		if(state == GLGameState.Finished) { 
			screen.pause(); 
			screen.dispose(); 
			synchronized(stateChanged) { 
				this.state = GLGameState.Idle; 
				stateChanged.notifyAll(); 
			}             
		}
	}
	
	public MainActivity getMain(){
		return a;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) { 
		// TODO Auto-generated method stub
		Log.d("onSurfaceChanged","onSurfaceChanged");
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Assets.width=width;
		Assets.height=height;
		Assets.input.setScale(Assets.targetwidth/(float)width,Assets.targetheight/(float)height);
		AndroidPicture.reload();
		LoadingScreen.load();		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		Log.d("GLSurfaceViewTest", "surface created"); 
		synchronized(stateChanged) {  
			if(state == GLGameState.Initialized){ 
				screen = getStartScreen(); 
			}
			state = GLGameState.Running; 
			screen.resume(); 
			startTime = System.nanoTime(); 
		}
	}
	public Screen getStartScreen() {
		Log.d("once","please");
		return new LoadingScreen(this);
	}
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
}
