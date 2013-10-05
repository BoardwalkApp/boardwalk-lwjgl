#include <jni.h>
#include <dlfcn.h>

#include "extgl.h"

bool extgl_Open(JNIEnv *env) {
	return true;
}

void *extgl_GetProcAddress(const char *name) {
	void *t = dlsym(RTLD_DEFAULT, name);
	if (t == NULL) {
		//printfDebug("Could not locate symbol %s\n", name);
	}
	return t;
}

void extgl_Close(void) {
}
