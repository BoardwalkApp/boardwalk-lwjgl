/*
 * Copyright (c) 2002 Lightweight Java Game Library Project
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
 * * Neither the name of 'Light Weight Java Game Library' nor the names of
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
/*
 * Created by LWJGL.
 * User: spasi
 * Date: 2003-12-16
 * Time: 21:04:41
 */

package org.lwjgl.opengl.ati;

import java.nio.IntBuffer;

public class ATIDrawBuffers {

	/*
	 * Accepted by the <pname> parameters of GetIntegerv, GetFloatv,
	 * and GetDoublev:
	*/
	public static final int GL_MAX_DRAW_BUFFERS_ATI = 0x8824;
	public static final int GL_DRAW_BUFFER0_ATI = 0x8825;
	public static final int GL_DRAW_BUFFER1_ATI = 0x8826;
	public static final int GL_DRAW_BUFFER2_ATI = 0x8827;
	public static final int GL_DRAW_BUFFER3_ATI = 0x8828;
	public static final int GL_DRAW_BUFFER4_ATI = 0x8829;
	public static final int GL_DRAW_BUFFER5_ATI = 0x882A;
	public static final int GL_DRAW_BUFFER6_ATI = 0x882B;
	public static final int GL_DRAW_BUFFER7_ATI = 0x882C;
	public static final int GL_DRAW_BUFFER8_ATI = 0x882D;
	public static final int GL_DRAW_BUFFER9_ATI = 0x882E;
	public static final int GL_DRAW_BUFFER10_ATI = 0x882F;
	public static final int GL_DRAW_BUFFER11_ATI = 0x8830;
	public static final int GL_DRAW_BUFFER12_ATI = 0x8831;
	public static final int GL_DRAW_BUFFER13_ATI = 0x8832;
	public static final int GL_DRAW_BUFFER14_ATI = 0x8833;
	public static final int GL_DRAW_BUFFER15_ATI = 0x8834;

	// ---------------------------
	public static void glDrawBuffersATI(IntBuffer buffers) {
		assert buffers.remaining() > 0 : "<buffers> must have at least 1 integer available.";
		nglDrawBuffersATI(buffers.remaining(), buffers, buffers.position());
	}

	private static native void nglDrawBuffersATI(int size, IntBuffer buffers, int buffersOffset);
	// ---------------------------

}