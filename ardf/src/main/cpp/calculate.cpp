#include <jni.h>
#include <string>
#include <android/log.h>

#include <assert.h>
#include <stdlib.h>
#include <string.h>

#define TAG "计算"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
#define COUNT(x) ((int) (sizeof(x) / sizeof((x)[0])))

#define MIN(x, y) x < y ? x : y
#define MAX(x, y) x > y ? x : y

using namespace std;


extern "C"
{

JNIEXPORT jint JNICALL test(JNIEnv *env, jobject jobj, jint jin) {
    return jin+1;
}


}

//------------------------------- 注册方法
static const JNINativeMethod Mth_HomeActivity[] = {
        {"testAdd1", "(Ljava/lang/Integer;)Ljava/lang/Integer;", (void *) test}
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
    regstNtv(env, "com/lfp/ardf/util/ColorUtils", Mth_HomeActivity,
             COUNT(Mth_HomeActivity));
    //registerNatives_Main(env);

    LOGI("natives success");
    return JNI_VERSION_1_4;
}
