/* Copyright 1997 Free Software Foundation, Inc.
 */

 /*
 * Javier Perez -- 1997
 *
 * Helper functions for obtain info of java methods used in callbacks
 */


#include "methodinfo.h"


int GetMethodInfo( methodinfo* mi, JNIEnv* env, jobject obj, const char* method_name, const char* signature ) {

	jclass cls1;

	/* If the methodinfo structure was allready used for another method
	   first we free the global references */
	if( mi->cls != 0 )
		(*mi->env)->DeleteGlobalRef( mi->env, mi->cls );
	if( mi->obj != 0 )
		(*mi->env)->DeleteGlobalRef( mi->env, mi->obj );

	mi->env = env;
	mi->obj = 0;
	mi->cls = 0;
	mi->mid = 0;

	mi->obj = (*env)->NewGlobalRef( env, obj );
	if( mi->obj == 0 )
		/* Error */
		return 1;
		
	cls1 = (*env)->GetObjectClass( env, obj );
	if( cls1 == 0 )
		/* Error */
		return 2;

	mi->cls = (*env)->NewGlobalRef( env, cls1 );
	if( mi->cls == 0 )
		/* Error */
		return 3;

	mi->mid = (*env)->GetMethodID( env, mi->cls, method_name, signature );
	if( mi->mid == 0)
		/* Error */
		return 4;

	return 0;
}


void MethodError( JNIEnv* env, char* msg )
{
	jclass	excCls;

	excCls = (*env)->FindClass( env, "java/lang/IllegalArgumentException" );
	if( excCls == 0 )
		(*env)->FatalError( env, msg );

	(*env)->ThrowNew( env, excCls, msg );
}
