#include <jni.h>
#include <string>
#include <android/log.h>

#include <assert.h>
#include <stdlib.h>
#include <string.h>

#define TAG "LOG_JNI"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
#define COUNT(x) ((int) (sizeof(x) / sizeof((x)[0])))

using namespace std;

extern "C"
{

JNIEXPORT jstring JNICALL
getTestString(JNIEnv *env, jobject jobj, jstring jstr) {
    char *c_jstr = (char *) env->GetStringUTFChars(jstr, NULL);
    char *content = (char *) malloc(sizeof(char) * 100);
    memset(content, 0, 100);
    strcat(content, c_jstr);
    strcat(content, ":被附加的数据");
    env->ReleaseStringUTFChars(jstr, c_jstr);
    return env->NewStringUTF(content);
}
}

//------------------------------- 注册方法
static const JNINativeMethod Mth_HomeActivity[] = {
        {"JniLoadding", "(Ljava/lang/String;)Ljava/lang/String;", (void *) getTestString}
};

static int regstNtv(JNIEnv *engv, const char *jclass_path, const JNINativeMethod *methods,
                    const int method_size) {
    jclass clazz;
    clazz = engv->FindClass(jclass_path);
    if (clazz == NULL) {
        LOGE("clazz is null");
        return JNI_FALSE;
    }

    if (engv->RegisterNatives(clazz, methods, method_size) < 0) {
        LOGE("natives error");
        return JNI_FALSE;
    }
    return JNI_TRUE;
}
//------------------------------- 动态注册
JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("ERROR: GetEnv failed\n");
        return -1;
    }
    assert(env != NULL);
    //
    regstNtv(env, "com/lfp/androidrapiddevelopmentframework/Constants", Mth_HomeActivity,
             COUNT(Mth_HomeActivity));
    //registerNatives_Main(env);

    LOGI("natives success");
    return JNI_VERSION_1_4;
}
