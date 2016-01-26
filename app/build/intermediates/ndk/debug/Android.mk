LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := gif
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/Users/samsungtunnel/src/FULLSCREEN/app/src/main/jni/Android.mk \
	/Users/samsungtunnel/src/FULLSCREEN/app/src/main/jni/Application.mk \
	/Users/samsungtunnel/src/FULLSCREEN/app/src/main/jni/gif.c \
	/Users/samsungtunnel/src/FULLSCREEN/app/src/main/jni/giflib/dgif_lib.c \
	/Users/samsungtunnel/src/FULLSCREEN/app/src/main/jni/giflib/gif_err.c \
	/Users/samsungtunnel/src/FULLSCREEN/app/src/main/jni/giflib/gif_hash.c \
	/Users/samsungtunnel/src/FULLSCREEN/app/src/main/jni/giflib/gifalloc.c \

LOCAL_C_INCLUDES += /Users/samsungtunnel/src/FULLSCREEN/app/src/main/jni
LOCAL_C_INCLUDES += /Users/samsungtunnel/src/FULLSCREEN/app/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
