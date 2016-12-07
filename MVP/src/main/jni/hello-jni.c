#include <string.h>
#include <jni.h>


JNIEXPORT jstring JNICALL Java_com_mvp_rxandroid_ndk_TestNdk_stringFromJNI( JNIEnv* env,
                                                  jobject thiz ){
    return (*env)->NewStringUTF(env, "Hello from JNI !");
}