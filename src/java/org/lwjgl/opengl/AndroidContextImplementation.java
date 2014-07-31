/*
 * Copyright (c) 2002-2008 LWJGL Project
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

import org.lwjgl.LWJGLException;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;

/**
 * @author elias_naur <elias_naur@users.sourceforge.net>
 * @version $Revision$
 *          $Id$
 */
public final class AndroidContextImplementation implements ContextImplementation {

	public static EGLDisplay display;
	public static EGLSurface draw;
	public static EGLSurface read;
	public static EGLContext context;

	public ByteBuffer create(PeerInfo peer_info, IntBuffer attribs, ByteBuffer shared_context_handle) throws LWJGLException {
		return ByteBuffer.allocate(4);
	}

	public void releaseDrawable(ByteBuffer context_handle) throws LWJGLException {
	}

	public void swapBuffers() throws LWJGLException {
		//TODO: egl swap buffers
		EGL14.eglSwapBuffers(display, draw);
	}

	public void releaseCurrentContext() throws LWJGLException {
	}

	public void update(ByteBuffer context_handle) {
	}

	public void makeCurrent(PeerInfo peer_info, ByteBuffer handle) throws LWJGLException {
		EGL14.eglMakeCurrent(display, draw, read, context);
	}


	public boolean isCurrent(ByteBuffer handle) throws LWJGLException {
		return true;
	}

	public void setSwapInterval(int value) {
	}


	public void destroy(PeerInfo peer_info, ByteBuffer handle) throws LWJGLException {
	}
}
