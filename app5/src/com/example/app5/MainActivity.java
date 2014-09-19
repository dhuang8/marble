package com.example.app5;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity{
	
	GLSurfaceView glView;
	AndroidAudio audio; 
	AndroidInput input;
	Screen screen;
	long startTime = System.nanoTime(); 
	private static Display display;
	private boolean set=false;
	
	public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //full screen
        glView = new MyGLSurfaceView(this,this); 
        audio = new AndroidAudio(this); 
        setContentView(glView); 
        Assets.targetwidth=1280;
        Assets.targetheight=720;
        Assets.assets=getAssets();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        setInput();
    }
	
	@Override 
	public void onResume() { 
		super.onPause(); 
		glView.onResume(); 
	}
	
	public AndroidInput getInput(){
		return input;
	}
	
	@Override 
	public void onPause() { 
		super.onPause();
		glView.onPause();
	/*	synchronized(stateChanged) { 
			if(isFinishing())             
				state = GLGameState.Finished; 
			else 
				state = GLGameState.Paused; 
			while(true) { 
				try { 
					stateChanged.wait(); 
					break; 
				} catch(InterruptedException e) {  
				} 
			} 
		} 
		//wakeLock.release(); 
		glView.onPause();   
		super.onPause(); */
	} 
	
	@SuppressWarnings("deprecation")
	static void setDimensions(){
		if (android.os.Build.VERSION.SDK_INT >= 13){
        	Point size = new Point();
        	display.getSize(size);
        	Assets.width = (float) size.x;
        	Assets.height = (float) size.y;
        }
		else{
			Assets.width = display.getWidth();  // deprecated
			Assets.height = display.getHeight();  // deprecated
		}
		Log.d("width",""+Assets.width); 
	}
	
	public void setInput(){
		Assets.input = new AndroidInput(this, glView, 1, 1); 
	}
}

/*
class MySurfaceView extends SurfaceView{

	Bitmap framebuffer;

	public MySurfaceView(Context context, Bitmap framebuffer, SurfaceHolder h) {
		super(context);

        h = getHolder();
		// Set the Renderer for drawing on the GLSurfaceView
	        
		// Render the view only when there is a change in the drawing data
		//setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
}*/

class MyGLSurfaceView extends GLSurfaceView {

	private final GLGraphics mRenderer;

	public MyGLSurfaceView(Context context, MainActivity a) {
		super(context);
		// Create an OpenGL ES 2.0 context.
		setEGLContextClientVersion(2);

		// Set the Renderer for drawing on the GLSurfaceView
		mRenderer = new GLGraphics(a);
		setRenderer(mRenderer);
		if (android.os.Build.VERSION.SDK_INT >= 11){
			setPreserveEGLContextOnPause (true);
		}
		// Render the view only when there is a change in the drawing data
		//setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
}
