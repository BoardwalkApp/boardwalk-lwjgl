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

// ----------------------------------
// IMPLEMENTATION OF NATIVE METHODS FOR CLASS: org.lwjgl.opengl.ext.EXTVertexWeighting
// ----------------------------------

#include "org_lwjgl_opengl_ext_EXTVertexWeighting.h"
#include "extgl.h"
#include "checkGLerror.h"

/*
 * Class:	org.lwjgl.opengl.ext.EXTVertexWeighting
 * Method:	glVertexWeightfEXT
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ext_EXTVertexWeighting_glVertexWeightfEXT
	(JNIEnv * env, jclass clazz, jfloat weight)
{
	CHECK_EXISTS(glVertexWeightfEXT)
	glVertexWeightfEXT(weight);
	CHECK_GL_ERROR
}

/*
 * Class:	org.lwjgl.opengl.ext.EXTVertexWeighting
 * Method:	nglVertexWeightPointerEXT
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ext_EXTVertexWeighting_nglVertexWeightPointerEXT
	(JNIEnv * env, jclass clazz, jint size, jint type, jint stride, jobject pPointer, jint pPointer_offset)
{
	CHECK_EXISTS(glVertexWeightPointerEXT)
	GLvoid *pPointer_ptr = (GLvoid *)((GLubyte *)env->GetDirectBufferAddress(pPointer) + pPointer_offset);
	glVertexWeightPointerEXT(size, type, stride, pPointer_ptr);
	CHECK_GL_ERROR
}

/*
 * Class:	org.lwjgl.opengl.ext.EXTVertexWeighting
 * Method:	nglVertexWeightPointerEXTVBO
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ext_EXTVertexWeighting_nglVertexWeightPointerEXTVBO
	(JNIEnv * env, jclass clazz, jint size, jint type, jint stride, jint buffer_offset)
{
	CHECK_EXISTS(glVertexWeightPointerEXT)
	glVertexWeightPointerEXT(size, type, stride, (GLvoid *)buffer_offset);
	CHECK_GL_ERROR
}