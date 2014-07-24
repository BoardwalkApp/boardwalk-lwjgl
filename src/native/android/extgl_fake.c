#include <string.h>
#include <jni.h>
#include <dlfcn.h>
#include <android/log.h>

#include "extgl.h"

static void* gles1;
static void* glshim;

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
		__android_log_print(ANDROID_LOG_ERROR, "LWJGLWrapper", 
			"Found symbol %s\n", name);
	}
	return t;
}

void extgl_Close(void) {
	dlclose(gles1);
	dlclose(glshim);
}
