/*
 * Copyright (c) 2002-2010 LWJGL Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.lwjgl.opengl;

/**
 * This is the Display implementation interface. Display delegates
 * to implementors of this interface. There is one DisplayImplementation
 * for each supported platform.
 * @author elias_naur
 */

import java.awt.Canvas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.lang.reflect.InvocationTargetException;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.XRandR.Screen;
import org.lwjgl.opengles.EGL;

import org.lwjgl.input.*;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

final class AndroidDisplay implements DisplayImplementation {

	private boolean keyboard_grabbed;
	private boolean pointer_grabbed;
	private boolean input_released;
	private boolean grab;
	private boolean focused;
	private boolean minimized;
	private boolean dirty;
	private boolean close_requested;
	private long current_cursor;
	private long blank_cursor;
	private boolean mouseInside = true;
	private boolean resizable;
	private boolean resized;
	
	private int window_x;
	private int window_y;
	private int window_width;
	private int window_height;

	private Keyboard keyboard;

	public void createWindow(final DrawableLWJGL drawable, DisplayMode mode, Canvas parent, int x, int y) throws LWJGLException {
	}

	public void destroyWindow() {
	}

	public void switchDisplayMode(DisplayMode mode) throws LWJGLException {
	}

	public void resetDisplayMode() {
	}

	public int getGammaRampLength() {
			return 0;
	}

	public void setGammaRamp(FloatBuffer gammaRamp) throws LWJGLException {
			throw new LWJGLException("No gamma ramp support (Missing XF86VM extension)");
	}

	public String getAdapter() {
		return null;
	}

	public String getVersion() {
		return null;
	}

	public DisplayMode init() throws LWJGLException {
		return new DisplayMode(1280, 600, 24, 60, true);
	}

	private static DisplayMode getCurrentXRandrMode() throws LWJGLException {
		return null;
	}


	public void setTitle(String title) {
	}

	public boolean isCloseRequested() {
		return false;
	}

	public boolean isVisible() {
		return true;
	}

	public boolean isActive() {
		return true;
	}

	public boolean isDirty() {
		return false;
	}

	public PeerInfo createPeerInfo(PixelFormat pixel_format, ContextAttribs attribs) throws LWJGLException {
		return null;
	}


	public void update() {
	}

	public void reshape(int x, int y, int width, int height) {
	}

	public DisplayMode[] getAvailableDisplayModes() throws LWJGLException {
		return new DisplayMode[] {new DisplayMode(1280, 600, 24, 60, true)};
	}

	/* Mouse */
	public boolean hasWheel() {
		return true;
	}

	public int getButtonCount() {
		return 3; //mouse.getButtonCount();
	}

	public void createMouse() throws LWJGLException {
	}

	public void destroyMouse() {
	}

	public void pollMouse(IntBuffer coord_buffer, ByteBuffer buttons) {
	}

	public void readMouse(ByteBuffer buffer) {
	}

	public void setCursorPosition(int x, int y) {
	}

	public void grabMouse(boolean new_grab) {
	}

	private boolean shouldWarpPointer() {
		return false;
	}

	public int getNativeCursorCapabilities() {
		return 0;
	}

	public void setNativeCursor(Object handle) throws LWJGLException {
	}

	public int getMinCursorSize() {
		return 0;
	}

	public int getMaxCursorSize() {
		return 0;
	}

	/* Keyboard */
	public void createKeyboard() throws LWJGLException {
	}

	public void destroyKeyboard() {
	}

	public void pollKeyboard(ByteBuffer keyDownBuffer) {
	}

	public void readKeyboard(ByteBuffer buffer) {
	}

	public Object createCursor(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, IntBuffer delays) throws LWJGLException {
		return null;
	}

	private static long getCursorHandle(Object cursor_handle) {
		return 0;
	}

	public void destroyCursor(Object cursorHandle) {
	}

	public int getPbufferCapabilities() {
		return 0;
	}

	public boolean isBufferLost(PeerInfo handle) {
		return false;
	}

	public PeerInfo createPbuffer(int width, int height, PixelFormat pixel_format, ContextAttribs attribs,
			IntBuffer pixelFormatCaps,
			IntBuffer pBufferAttribs) throws LWJGLException {
		return new LinuxPbufferPeerInfo(width, height, pixel_format);
	}

	public void setPbufferAttrib(PeerInfo handle, int attrib, int value) {
		throw new UnsupportedOperationException();
	}

	public void bindTexImageToPbuffer(PeerInfo handle, int buffer) {
		throw new UnsupportedOperationException();
	}

	public void releaseTexImageFromPbuffer(PeerInfo handle, int buffer) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Sets one or more icons for the Display.
	 * <ul>
	 * <li>On Windows you should supply at least one 16x16 icon and one 32x32.</li>
	 * <li>Linux (and similar platforms) expect one 32x32 icon.</li>
	 * <li>Mac OS X should be supplied one 128x128 icon</li>
	 * </ul>
	 * The implementation will use the supplied ByteBuffers with image data in RGBA and perform any conversions necessary for the specific platform.
	 *
	 * @param icons Array of icons in RGBA mode
	 * @return number of icons used.
	 */
	public int setIcon(ByteBuffer[] icons) {
		return 0;
	}
	public int getX() {
		return window_x;
	}

	public int getY() {
		return window_y;
	}
	
	public int getWidth() {
		return window_width;
	}

	public int getHeight() {
		return window_height;
	}

	public boolean isInsideWindow() {
		return mouseInside;
	}

	public void setResizable(boolean resizable) {
	}

	public boolean wasResized() {
		return false;
	}

}
