LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Demos
LOCAL_SRC_FILES := Demos.cpp md5.cpp
LOCAL_LDLIBS := -llog  

include $(BUILD_SHARED_LIBRARY)
