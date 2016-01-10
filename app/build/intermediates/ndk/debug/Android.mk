LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := gif
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/Users/dantheman/src/android/FULLSCREEN-AS/app/src/main/jni/Android.mk \
	/Users/dantheman/src/android/FULLSCREEN-AS/app/src/main/jni/Application.mk \
	/Users/dantheman/src/android/FULLSCREEN-AS/app/src/main/jni/gif.c \
	/Users/dantheman/src/android/FULLSCREEN-AS/app/src/main/jni/giflib/dgif_lib.c \
	/Users/dantheman/src/android/FULLSCREEN-AS/app/src/main/jni/giflib/gif_err.c \
	/Users/dantheman/src/android/FULLSCREEN-AS/app/src/main/jni/giflib/gif_hash.c \
	/Users/dantheman/src/android/FULLSCREEN-AS/app/src/main/jni/giflib/gifalloc.c \

LOCAL_C_INCLUDES += /Users/dantheman/src/android/FULLSCREEN-AS/app/src/main/jni
LOCAL_C_INCLUDES += /Users/dantheman/src/android/FULLSCREEN-AS/app/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
