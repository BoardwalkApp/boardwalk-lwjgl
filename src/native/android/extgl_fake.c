#include <string.h>
#include <jni.h>
#include <dlfcn.h>
#include <android/log.h>

#include "extgl.h"

static void* gles1;
static void* glshim;

#define MAP(func_name, func) \
    if (strcmp(name, func_name) == 0) name = #func;

#define ARB(func_name) MAP(#func_name "ARB", func_name)

#define EXT(func_name) MAP(#func_name "EXT", func_name)

#define STUB(func_name)                       \
    if (strcmp(name, #func_name) == 0) {      \
        __android_log_print(ANDROID_LOG_INFO, "LWJGLWrapper", "glX stub: %s\n", #func_name); \
        return (void *)glXStub;               \
    }

void glXStub(void *x, ...) {
    return;
}

bool extgl_Open(JNIEnv *env) {
	gles1 = dlopen("libGLESv1_CM.so", RTLD_LAZY);
	glshim = dlopen("libglshim.so", RTLD_LAZY);
	return gles1 != NULL && glshim != NULL;
}

void *extgl_GetProcAddress(const char *name) {
	if (strstr(name, "glBlendFuncSeparate")) return NULL;
    // glX calls

    // GL_ARB_vertex_buffer_object

/*    ARB(glBindBuffer);
    ARB(glBufferData);
    ARB(glBufferSubData);
    ARB(glDeleteBuffers);
    ARB(glGenBuffers);
    ARB(glIsBuffer);
    MAP_EGL(glGetBufferParameteriARB, glGetBufferParameteriOES);
    MAP_EGL(glGetBufferPointerARB, glGetBufferPointerOES);
    MAP_EGL(glGetBufferPointervARB, glGetBufferPointervOES);
    MAP_EGL(glMapBufferARB, glMapBufferOES);
    MAP_EGL(glUnmapBufferARB, glMapBufferOES);
    STUB(glGetBufferParameterivARB);
    STUB(glGetBufferSubDataARB);*/

    // GL_EXT_vertex_array
    EXT(glArrayElement);
    EXT(glDrawArrays);
    EXT(glVertexPointer);
    EXT(glNormalPointer);
    EXT(glColorPointer);
    //EXT(glIndexPointer);	//TODO
    EXT(glTexCoordPointer);
    //EXT(glEdgeFlagPointer);	//TODO
    //EXT(glGetPointerv);	//TODO


    // OES wrapper

    // passthrough
    // batch thunking!
    #define THUNK(suffix, type)       \
    EXT(glSecondaryColor3##suffix##v); \
    EXT(glSecondaryColor3##suffix);    \
    EXT(glMultiTexCoord1##suffix##v); \
    EXT(glMultiTexCoord1##suffix);    \
    EXT(glMultiTexCoord2##suffix##v); \
    EXT(glMultiTexCoord2##suffix);    \
    EXT(glMultiTexCoord3##suffix##v); \
    EXT(glMultiTexCoord3##suffix);    \
    EXT(glMultiTexCoord4##suffix##v); \
    EXT(glMultiTexCoord4##suffix);    \
    ARB(glMultiTexCoord1##suffix##v); \
    ARB(glMultiTexCoord1##suffix);    \
    ARB(glMultiTexCoord2##suffix##v); \
    ARB(glMultiTexCoord2##suffix);    \
    ARB(glMultiTexCoord3##suffix##v); \
    ARB(glMultiTexCoord3##suffix);    \
    ARB(glMultiTexCoord4##suffix##v); \
    ARB(glMultiTexCoord4##suffix);

    THUNK(b, GLbyte);
    THUNK(d, GLdouble);
    THUNK(i, GLint);
    THUNK(s, GLshort);
    THUNK(ub, GLubyte);
    THUNK(ui, GLuint);
    THUNK(us, GLushort);
    THUNK(f, GLfloat);
    #undef THUNK

#ifdef USE_ES2
#endif

    // functions we actually define
    EXT(glActiveTexture);
    ARB(glActiveTexture);
    EXT(glArrayElement);
    EXT(glBlendColor);
    ARB(glBlendColor);
    EXT(glBlendEquationSeparatei);
    ARB(glBlendEquationSeparatei);
//  this isn't supported on all devices
    EXT(glBlendFuncSeparate);
    ARB(glBlendFuncSeparate);
    EXT(glBlendFuncSeparatei);
    ARB(glBlendFuncSeparatei);

    EXT(glClientActiveTexture);
    ARB(glClientActiveTexture);
    EXT(glDrawRangeElements);
#ifndef USE_ES2
#endif
    EXT(glSecondaryColorPointer);
	void *t = dlsym(glshim, name);
	if (t == NULL) {
		//printfDebug("Could not locate symbol %s\n", name);
		//try default
		t = dlsym(gles1, name);
		//if we are still null, give up
	}
	if (t == NULL) {
    // stubs for unimplemented functions
    STUB(glAccum);
    STUB(glAreTexturesResident);
    STUB(glClearAccum);
    STUB(glColorMaterial);
    STUB(glCopyTexImage3D);
    STUB(glCopyTexSubImage3D);
    STUB(glEdgeFlagPointer);
    STUB(glFeedbackBuffer);
    STUB(glGetClipPlane);
    STUB(glGetLightiv);
    STUB(glGetMaterialiv);
    STUB(glGetPixelMapfv);
    STUB(glGetPixelMapuiv);
    STUB(glGetPixelMapusv);
    STUB(glGetPolygonStipple);
    STUB(glGetStringi);
    STUB(glGetTexGendv);
    //STUB(glGetTexGenfv);
    STUB(glGetTexGeniv);
    STUB(glMaterialiv);
    STUB(glPassThrough);
    STUB(glPixelMapfv);
    STUB(glPixelMapuiv);
    STUB(glPixelMapusv);
    STUB(glPixelStoref);
    STUB(glPrioritizeTextures);
    STUB(glSelectBuffer);
    //STUB(glTexSubImage1D);
	}

	if (t == NULL) {
		__android_log_print(ANDROID_LOG_ERROR, "LWJGLWrapper", 
			"Could not locate symbol %s\n", name);
	} else {
#ifdef DEBUG_GETPROCADDRESS
		__android_log_print(ANDROID_LOG_ERROR, "LWJGLWrapper", 
			"Found symbol %s\n", name);
#endif
	}

	return t;
}

void extgl_Close(void) {
	dlclose(gles1);
	dlclose(glshim);
}
