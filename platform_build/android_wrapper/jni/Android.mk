LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)  
LOCAL_LDLIBS := -llog -lGLESv1_CM
LOCAL_MODULE    := lwjgl

cfiles := $(shell find -L $(LOCAL_PATH)/native/common -name "*.c" -printf "native/common/%P \n")

cfiles += $(shell find -L $(LOCAL_PATH)/native/generated/opengl -name "*.c" -printf "native/generated/opengl/%P \n")

cfiles += $(shell find -L $(LOCAL_PATH)/native/generated/openal -name "*.c" -printf "native/generated/openal/%P \n")

cfiles += $(shell find -L $(LOCAL_PATH)/native/android -name "*.c" -printf "native/android/%P \n")

LOCAL_SRC_FILES := $(cfiles)

LOCAL_C_INCLUDES += $(LOCAL_PATH)/native/common $(LOCAL_PATH)/native/common/opengl $(LOCAL_PATH)/native/android

include $(BUILD_SHARED_LIBRARY)  
