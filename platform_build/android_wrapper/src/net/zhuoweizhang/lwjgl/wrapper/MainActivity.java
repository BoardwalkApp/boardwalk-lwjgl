package net.zhuoweizhang.lwjgl.wrapper;

import android.app.Activity;
import android.os.Bundle;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.*;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.lwjgl.test.opengl.*;

public class MainActivity extends Activity
{

	public static final String TAG = "LWJGLWrapperTest";

	/** Called when the activity is first created. */
	private static Gears gears;
	private GLSurfaceView mGLView;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		System.loadLibrary("glshim");
		super.onCreate(savedInstanceState);
		mGLView = new GLSurfaceView(this);
		mGLView.setRenderer(new WrappedRenderer());

		setContentView(mGLView);
		gears = new Gears();

	}

	@Override
	public void onResume() {
		super.onResume();
		mGLView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mGLView.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//TODO: destroy org.lwjgl.opengl.Display.destroy();
		System.exit(0);
	}

	public void initGears() {
		try {
			gears.init();
		} catch (Exception e) {
			e.printStackTrace();
			finish();
		}
	}

	private class WrappedRenderer implements GLSurfaceView.Renderer {
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			Log.i(TAG, "Surface created");
			initGears();
		}

		public void onSurfaceChanged(GL10 gl, int w, int h) {
			Log.i(TAG, "Surface changed");
			/*org.lwjgl.opengl.GL11.glViewport(0, 0, w, h);
			org.lwjgl.opengl.GL11.glMatrixMode(org.lwjgl.opengl.GL11.GL_PROJECTION);
			org.lwjgl.opengl.GL11.glLoadIdentity();
			org.lwjgl.opengl.GL11.glOrtho(0, w, 0, h, 1, -1);
			org.lwjgl.opengl.GL11.glMatrixMode(org.lwjgl.opengl.GL11.GL_MODELVIEW);
			*/
		}

		public void onDrawFrame(GL10 gl) {
			gears.tick();
		}
	}
}
