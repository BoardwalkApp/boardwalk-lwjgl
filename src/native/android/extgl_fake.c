#include <jni.h>
#include <dlfcn.h>
#include <android/log.h>

#include "extgl.h"

static void* gles1;

bool extgl_Open(JNIEnv *env) {
	gles1 = dlopen("/system/lib/libGLESv1_CM.so", RTLD_LAZY);
	return gles1 != NULL;
}

void *extgl_GetProcAddress(const char *name) {
	void *t = dlsym(gles1, name);
	if (t == NULL) {
		//printfDebug("Could not locate symbol %s\n", name);
		//try default
		t = dlsym(RTLD_DEFAULT, name);
		//if we are still null, give up
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
}
