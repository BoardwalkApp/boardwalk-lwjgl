package org.lwjgl.opengl.glu;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;

/**
 * Project.java
 * 
 * 
 * Created 11-jan-2004
 * @author Erik Duijs
 */
public class Project extends Util implements GLUConstants {

	private static FloatBuffer matrix = createFloatBuffer(16);
	private static FloatBuffer finalMatrix = createFloatBuffer(16);

	/**
	 * Make matrix an identity matrix
	 */
	private static void __gluMakeIdentityf(FloatBuffer m) {
		m.put(0 + 4 * 0, 1);
		m.put(0 + 4 * 1, 0);
		m.put(0 + 4 * 2, 0);
		m.put(0 + 4 * 3, 0);
		m.put(1 + 4 * 0, 0);
		m.put(1 + 4 * 1, 1);
		m.put(1 + 4 * 2, 0);
		m.put(1 + 4 * 3, 0);
		m.put(2 + 4 * 0, 0);
		m.put(2 + 4 * 1, 0);
		m.put(2 + 4 * 2, 1);
		m.put(2 + 4 * 3, 0);
		m.put(3 + 4 * 0, 0);
		m.put(3 + 4 * 1, 0);
		m.put(3 + 4 * 2, 0);
		m.put(3 + 4 * 3, 1);
	}

	/**
	 * Method __gluMultMatrixVecf
	 * @param finalMatrix
	 * @param in
	 * @param out
	 */
	private static void __gluMultMatrixVecf(FloatBuffer finalMatrix, float[] in, float[] out) {

		for (int i=0; i<4; i++) {
			out[i] = 
				in[0] * finalMatrix.get(0*4+i) +
				in[1] * finalMatrix.get(1*4+i) +
				in[2] * finalMatrix.get(2*4+i) +
				in[3] * finalMatrix.get(3*4+i);
		}
	}

	/**
	 * @param finalMatrix
	 * @param finalMatrix2
	 * @return
	 */
	private static boolean __gluInvertMatrixf(FloatBuffer src, FloatBuffer inverse) {
		int i, j, k, swap;
		float t;
		float[][] temp = new float[4][4];

		for (i=0; i<4; i++) {
			for (j=0; j<4; j++) {
				temp[i][j] = src.get(i*4+j);
			}
		}
		__gluMakeIdentityf(inverse);

		for (i = 0; i < 4; i++) {
			/*
			 ** Look for largest element in column
			 */
			swap = i;
			for (j = i + 1; j < 4; j++) {
				/*if (fabs(temp[j][i]) > fabs(temp[i][i])) {
				 swap = j;
				 }*/
				if (Math.abs(temp[j][i]) > Math.abs(temp[i][i])) {
					swap = j;
				}
			}

			if (swap != i) {
				/*
				 ** Swap rows.
				 */
				for (k = 0; k < 4; k++) {
					t = temp[i][k];
					temp[i][k] = temp[swap][k];
					temp[swap][k] = t;

					t = inverse.get(i*4+k);
					inverse.put(i*4+k, inverse.get(swap*4+k));
					inverse.put(swap*4+k,t);
				}
			}

			if (temp[i][i] == 0) {
				/*
				 ** No non-zero pivot.  The matrix is singular, which shouldn't
				 ** happen.  This means the user gave us a bad matrix.
				 */
				return false;
			}

			t = temp[i][i];
			for (k = 0; k < 4; k++) {
				temp[i][k] /= t;
				inverse.put(i*4+k, inverse.get(i*4+k) / t);
			}
			for (j = 0; j < 4; j++) {
				if (j != i) {
					t = temp[j][i];
					for (k = 0; k < 4; k++) {
						temp[j][k] -= temp[i][k]*t;
						inverse.put(j*4+k, inverse.get(j*4+k) - inverse.get(i*4+k)*t);
					}
				}
			}
		}
		return true;
	}

	/**
	 * @param modelMatrix
	 * @param projMatrix
	 * @param finalMatrix
	 */
	private static void __gluMultMatricesf(FloatBuffer a, FloatBuffer b, FloatBuffer r) {

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				r.put(i*4+j, 
						a.get(i*4+0)*b.get(0*4+j) +
						a.get(i*4+1)*b.get(1*4+j) +
						a.get(i*4+2)*b.get(2*4+j) +
						a.get(i*4+3)*b.get(3*4+j));
			}
		}		
	}
	
	/**
	 * Method gluPerspective.
	 * @param fovy
	 * @param aspect
	 * @param zNear
	 * @param zFar
	 */
	public static void gluPerspective(float fovy, float aspect, float zNear, float zFar) {
		float sine, cotangent, deltaZ;
		float radians = fovy / 2 * PI / 180;

		deltaZ = zFar - zNear;
		sine = (float) Math.sin(radians);
		if ((deltaZ == 0) || (sine == 0) || (aspect == 0)) {
			return;
		}
		cotangent = (float) Math.cos(radians) / sine;

		__gluMakeIdentityf(matrix);

		matrix.put(0 * 4 + 0, cotangent / aspect);
		matrix.put(1 * 4 + 1, cotangent);
		matrix.put(2 * 4 + 2, - (zFar + zNear) / deltaZ);
		matrix.put(2 * 4 + 3, -1);
		matrix.put(3 * 4 + 2, -2 * zNear * zFar / deltaZ);
		matrix.put(3 * 4 + 3, 0);

		GL11.glMultMatrixf(matrix);
	}

	/**
	 * Method gluLookAt
	 * @param eyex
	 * @param eyey
	 * @param eyez
	 * @param centerx
	 * @param centery
	 * @param centerz
	 * @param upx
	 * @param upy
	 * @param upz
	 */
	public static void gluLookAt(
			float eyex,
			float eyey,
			float eyez,
			float centerx,
			float centery,
			float centerz,
			float upx,
			float upy,
			float upz) {

		int i;
		float[] forward = new float[3];
		float[] side = new float[3];
		float[] up = new float[3];

		forward[0] = centerx - eyex;
		forward[1] = centery - eyey;
		forward[2] = centerz - eyez;

		up[0] = upx;
		up[1] = upy;
		up[2] = upz;

		normalize(forward);

		/* Side = forward x up */
		cross(forward, up, side);
		normalize(side);

		/* Recompute up as: up = side x forward */
		cross(side, forward, up);

		__gluMakeIdentityf(matrix);
		matrix.put(0 * 4 + 0, side[0]);
		matrix.put(1 * 4 + 0, side[1]);
		matrix.put(2 * 4 + 0, side[2]);

		matrix.put(0 * 4 + 1, up[0]);
		matrix.put(1 * 4 + 1, up[1]);
		matrix.put(2 * 4 + 1, up[2]);

		matrix.put(0 * 4 + 2, -forward[0]);
		matrix.put(1 * 4 + 2, -forward[1]);
		matrix.put(2 * 4 + 2, -forward[2]);

		GL11.glMultMatrixf(matrix);
		GL11.glTranslatef(-eyex, -eyey, -eyez);
	}

	/**
	 * Method gluProject
	 * @param objx
	 * @param objy
	 * @param objz
	 * @param modelMatrix
	 * @param projMatrix
	 * @param viewport
	 * @param winx
	 * @param winy
	 * @param winz
	 * @return
	 */
	public static boolean gluProject(float objx, float objy, float objz, 
			FloatBuffer modelMatrix, 
			FloatBuffer projMatrix,
			IntBuffer viewport,
			FloatBuffer winx, FloatBuffer winy, FloatBuffer winz)
	{
		float[] in = new float[4];
		float[] out = new float[4];

		in[0]=objx;
		in[1]=objy;
		in[2]=objz;
		in[3]=1.0f;
		__gluMultMatrixVecf(modelMatrix, in, out);
		__gluMultMatrixVecf(projMatrix, out, in);
		if (in[3] == 0.0) return false;
		in[0] /= in[3];
		in[1] /= in[3];
		in[2] /= in[3];
		// Map x, y and z to range 0-1
		in[0] = in[0] * 0.5f + 0.5f;
		in[1] = in[1] * 0.5f + 0.5f;
		in[2] = in[2] * 0.5f + 0.5f;

		// Map x,y to viewport
		in[0] = in[0] * viewport.get(2) + viewport.get(0);
		in[1] = in[1] * viewport.get(3) + viewport.get(1);

		winx.put(0, in[0]);
		winy.put(0, in[1]);
		winz.put(0, in[2]);
		return true;
	}

	/**
	 * Method gluUnproject
	 * @param winx
	 * @param winy
	 * @param winz
	 * @param modelMatrix
	 * @param projMatrix
	 * @param viewport
	 * @param objx
	 * @param objy
	 * @param objz
	 * @return
	 */
	public static boolean gluUnProject(float winx, float winy, float winz,
			FloatBuffer modelMatrix, 
			FloatBuffer projMatrix,
			IntBuffer viewport,
			FloatBuffer objx, FloatBuffer objy, FloatBuffer objz)
	{
		float[] in = new float[4];
		float[] out = new float[4];

		__gluMultMatricesf(modelMatrix, projMatrix, finalMatrix);
		if (!__gluInvertMatrixf(finalMatrix, finalMatrix)) return false;

		in[0]=winx;
		in[1]=winy;
		in[2]=winz;
		in[3]=1.0f;

		// Map x and y from window coordinates
		in[0] = (in[0] - viewport.get(0)) / viewport.get(2);
		in[1] = (in[1] - viewport.get(1)) / viewport.get(3);

		// Map to range -1 to 1
		in[0] = in[0] * 2 - 1;
		in[1] = in[1] * 2 - 1;
		in[2] = in[2] * 2 - 1;

		__gluMultMatrixVecf(finalMatrix, in, out);
		if (out[3] == 0.0) return false;
		out[0] /= out[3];
		out[1] /= out[3];
		out[2] /= out[3];
		objx.put(0, out[0]);
		objy.put(0, out[1]);
		objz.put(0, out[2]);
		return true;
	}

	/**
	 * Method gluPickMatrix
	 * @param x
	 * @param y
	 * @param deltax
	 * @param deltay
	 * @param viewport
	 */
	public static void gluPickMatrix(
			float x,
			float y,
			float deltax,
			float deltay,
			int[] viewport) {
		if (deltax <= 0 || deltay <= 0) {
			return;
		}

		/* Translate and scale the picked region to the entire window */
		GL11.glTranslatef(
			(viewport[2] - 2 * (x - viewport[0])) / deltax,
			(viewport[3] - 2 * (y - viewport[1])) / deltay,
			0);
		GL11.glScalef(viewport[2] / deltax, viewport[3] / deltay, 1.0f);
	}
}