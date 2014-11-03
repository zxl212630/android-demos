#include <stdio.h>
#include <string.h>
#include <jni.h>
#include "md5.h"
#include <android/log.h>

#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, "nativeDemos", __VA_ARGS__))
#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "nativeDemos", __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, "nativeDemos", __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, "nativeDemos", __VA_ARGS__))

extern "C" {

/*
 * islower = 0,小写
 */
void bin2hex(const char* bin,int len,char* hex,int islower=0)
{
	int hex_len = 2*(len) +1;
	int i= 0;
	char* p = hex;
	memset(hex,'\0',hex_len);
	for(i=0;i<len;i++)
	{
		if(!islower)
			sprintf(p,"%02x",bin[i]) ;
		else
			sprintf(p,"%02X",bin[i]) ;
		p += 2;
	}
}

	jint Java_com_zxl_demos_jni_JNI_plus(JNIEnv* env,jint a,jint b){
		return a + b;
	}

	JNIEXPORT jstring JNICALL Java_com_zxl_demos_jni_JNI_getString
		(JNIEnv* env, jobject thiz){
		#if defined(__arm__)
		  #if defined(__ARM_ARCH_7A__)
			#if defined(__ARM_NEON__)
			  #if defined(__ARM_PCS_VFP)
				#define ABI "armeabi-v7a/NEON (hard-float)"
			  #else
				#define ABI "armeabi-v7a/NEON"
			  #endif
			#else
			  #if defined(__ARM_PCS_VFP)
				#define ABI "armeabi-v7a (hard-float)"
			  #else
				#define ABI "armeabi-v7a"
			  #endif
			#endif
		  #else
		   #define ABI "armeabi"
		  #endif
		#elif defined(__i386__)
		   #define ABI "x86"
		#elif defined(__x86_64__)
		   #define ABI "x86_64"
		#elif defined(__mips64)  /* mips64el-* toolchain defines __mips__ too */
		   #define ABI "mips64"
		#elif defined(__mips__)
		   #define ABI "mips"
		#elif defined(__aarch64__)
		   #define ABI "arm64-v8a"
		#else
		   #define ABI "unknown"
		#endif

		return (env)->NewStringUTF("Hello from JNI !  Compiled with ABI " ABI ".");
	}

	JNIEXPORT jstring JNICALL Java_com_zxl_demos_jni_JNI_md5(JNIEnv* env,jobject obj, jstring jtext){
		const char* szText = (env)->GetStringUTFChars(jtext,0);

		char md5Bin[17] = "";
		char md5Text[33]= {'\0'};
		md5(szText,strlen(szText),md5Bin);
		bin2hex(md5Bin,strlen(md5Bin),md5Text,1);
		LOGD("text=%s,len=%d",md5Text,strlen(md5Text));

		(env)->ReleaseStringUTFChars(jtext,szText);
		return (env)->NewStringUTF(md5Text);
	}

	JNIEXPORT jstring JNICALL Java_com_zxl_demos_jni_JNI_getSignature(
			JNIEnv* env,jclass jcl,jobject ctxObject){
		jclass ctxClass = env->GetObjectClass(ctxObject);

		//context.getPackageManager(),通过反射获取
		jmethodID methodID = env->GetMethodID(ctxClass,"getPackageManager","()Landroid/content/pm/PackageManager;");
		jobject pkgMangerObject = env->CallObjectMethod(ctxObject,methodID);

		if(pkgMangerObject == NULL){
			LOGE("getPackageManager() Failed!");
			return NULL;
		}

		//context.getPackageName()
		methodID = env->GetMethodID(ctxClass,"getPackageName","()Ljava/lang/String;");
		jstring pkgName = (jstring)env->CallObjectMethod(ctxObject,methodID);

		if(NULL == pkgName){
			LOGE("getPackageName() Failed!");
			return NULL;
		}

		//release context
		env->DeleteLocalRef(ctxClass);

		//PackageManager.getPackageInfo(Sting, int)
		jclass pkgManagerClass = env->GetObjectClass(pkgMangerObject);
		methodID = env->GetMethodID(pkgManagerClass,
				"getPackageInfo","(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
		env->DeleteLocalRef(pkgManagerClass);
		jobject pkgInfoObject = env->CallObjectMethod(pkgMangerObject,methodID,pkgName,64);
		if(NULL == pkgInfoObject){
			LOGE("getPackageInfo() Failed!");
			return NULL;
		}

		jclass pkgInfoClass =env->GetObjectClass(pkgInfoObject);
		jfieldID fieldID = env->GetFieldID(pkgInfoClass,"signatures","[Landroid/content/pm/Signature;");
		env->DeleteLocalRef(pkgInfoClass);
		jobjectArray signatureObjectArray = (jobjectArray)env->
				GetObjectField(pkgInfoObject,fieldID);
		if(NULL == signatureObjectArray){
			LOGE("PackageInfo.signatures[] is null");
			return NULL;
		}

		jobject signatureObject = env->GetObjectArrayElement(signatureObjectArray,0);

		env->DeleteLocalRef(pkgInfoObject);

		jclass signatureClass = env->GetObjectClass(signatureObject);
		methodID = env->GetMethodID(signatureClass,"toCharsString","()Ljava/lang/String;");
		env->DeleteLocalRef(signatureClass);
		jstring signatureString = (jstring)env->CallObjectMethod(signatureObject,methodID);

		return signatureString;
	}
}
