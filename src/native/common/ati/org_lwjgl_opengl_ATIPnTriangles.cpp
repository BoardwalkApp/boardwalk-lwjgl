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
// IMPLEMENTATION OF NATIVE METHODS FOR CLASS: org.lwjgl.opengl.ATIPnTriangles
// ----------------------------------

#include "org_lwjgl_opengl_ATIPnTriangles.h"
#include "extgl.h"
#include "checkGLerror.h"

typedef void (APIENTRY * glPNTrianglesiATIPROC) (GLenum pname, GLint param);
typedef void (APIENTRY * glPNTrianglesfATIPROC) (GLenum pname, GLfloat param);

static glPNTrianglesiATIPROC glPNTrianglesiATI;
static glPNTrianglesfATIPROC glPNTrianglesfATI;

void extgl_InitATIPNTriangles(JNIEnv *env, jobject ext_set)
{
	if (!extgl_Extensions.GL_ATI_pn_triangles)
		return;
	glPNTrianglesiATI = (glPNTrianglesiATIPROC) extgl_GetProcAddress("glPNTrianglesiATI");
	glPNTrianglesfATI = (glPNTrianglesfATIPROC) extgl_GetProcAddress("glPNTrianglesfATI");
	EXTGL_SANITY_CHECK(env, ext_set, GL_ATI_pn_triangles)
}

/*
 * Class:	org.lwjgl.opengl.ATIPnTriangles
 * Method:	glPNTrianglesfATI
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ATIPnTriangles_glPNTrianglesfATI
	(JNIEnv * env, jclass clazz, jint pname, jfloat param)
{
	CHECK_EXISTS(glPNTrianglesfATI)
	glPNTrianglesfATI(pname, param);
	CHECK_GL_ERROR
}

/*
 * Class:	org.lwjgl.opengl.ATIPnTriangles
 * Method:	glPNTrianglesiATI
 */
JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ATIPnTriangles_glPNTrianglesiATI
	(JNIEnv * env, jclass clazz, jint pname, jint param)
{
	CHECK_EXISTS(glPNTrianglesiATI)
	glPNTrianglesiATI(pname, param);
	CHECK_GL_ERROR
}