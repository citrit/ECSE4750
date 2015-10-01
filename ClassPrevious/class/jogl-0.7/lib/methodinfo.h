/* Copyright 1997 Free Software Foundation, Inc.
 */

 /*
 * Javier Perez -- 1997
 *
 * Helper functions for obtain info of java methods used in callbacks
 */

#ifndef __METHODINFO_H__
#define __METHODINFO_H__

#ifdef WIN32
	#include <windows.h>
#endif

#include <jni.h>

/* Macros for invoking java methods using methodinfo structures */

#define CALL_VOID_METHOD_MI(mi) (*(mi.env))->CallVoidMethod(mi.env, mi.obj, mi.mid)

#define CALL_VOID_METHOD_MI1(mi,_a1) (*(mi.env))->CallVoidMethod(mi.env, mi.obj, mi.mid, _a1)

#define CALL_VOID_METHOD_MI2(mi,_a1,_a2) (*(mi.env))->CallVoidMethod(mi.env, mi.obj, mi.mid, _a1,_a2)


/* The methodinfo structure; holds info needed to call java methods */

typedef struct methodinfo {
	JNIEnv*		env;
	jobject		obj;
	jclass		cls;
	jmethodID	mid;
} methodinfo ;


/* Functions  */

int GetMethodInfo( methodinfo* mi, JNIEnv* env, jobject obj, const char* method_name, const char* signature );
void MethodError( JNIEnv* env, char* msg );

#endif
