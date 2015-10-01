/* Copyright 1997 Free Software Foundation, Inc.
 */

 /*
 * Javier Perez -- 1997
 *
 * Helper functions for obtain and release void pointers from variable
 * data array types
 */

#ifdef Win32
	#include <windows.h>
#endif

#include "array_ptr.h"
#include "jogl_lib.h"


/* 
We need to have a function:

	void* GetVoidArrayElements( JNIEnv* env, jint type, jobject jarray, jboolean* isCopy )
	
that returns a pointer to the data array obtained from the java array 
jarray depending on the value of the type:

GL_BYTE          
GL_UNSIGNED_BYTE 
GL_SHORT         
GL_UNSIGNED_SHORT
GL_INT           
GL_UNSIGNED_INT  
GL_FLOAT         
GL_2_BYTES       
GL_3_BYTES       
GL_4_BYTES       
GL_DOUBLE_EXT    
GL_BITMAP

We also need to have the inverse ReleaseVoidArray function:

	ReleaseVoidArrayElements( JNIEnv* env, jint type, jobject jarray, void* jptr, jint mode )
*/	

void ArrayTypeError( JNIEnv* env, GLenum type, char* msg )
{
	char	buf[128];
	jclass	excCls;

	sprintf( buf, "jogl, invalid array type in %s (%d)", msg, type );
	
	excCls = (*env)->FindClass( env, "java/lang/IllegalArgumentException" );
	if( excCls == 0 )
		(*env)->FatalError( env, buf );

	(*env)->ThrowNew( env, excCls, buf );
}


void* GetVoidArrayElements( JNIEnv* env, jint type, jobject jarray, jboolean* isCopy )
{

	switch( (GLenum) type ) {
	case GL_BYTE:
	case GL_UNSIGNED_BYTE:
	case GL_BITMAP:
	case GL_2_BYTES:
	case GL_3_BYTES:
	case GL_4_BYTES:
		return (*env)->GetByteArrayElements( env, (jbyteArray) jarray, isCopy );

	case GL_SHORT:
	case GL_UNSIGNED_SHORT:
		return (*env)->GetShortArrayElements( env, (jshortArray) jarray, isCopy );
	
	case GL_INT:
	case GL_UNSIGNED_INT:
		return (*env)->GetIntArrayElements( env, (jintArray) jarray, isCopy );

	case GL_FLOAT:
		return (*env)->GetFloatArrayElements( env, (jfloatArray) jarray, isCopy );

	case GL_DOUBLE_EXT:
		return (*env)->GetDoubleArrayElements( env, (jdoubleArray) jarray, isCopy );

	default:
		ArrayTypeError( env, type, "GetVoidArrayElements" );
	}

	return NULL;
}


void ReleaseVoidArrayElements( JNIEnv* env, jint type, jobject jarray, void* p_array, jint mode )
{

	switch( (GLenum) type ) {
	case GL_BYTE:
	case GL_UNSIGNED_BYTE:
	case GL_BITMAP:
	case GL_2_BYTES:
	case GL_3_BYTES:
	case GL_4_BYTES:
		(*env)->ReleaseByteArrayElements( env, (jbyteArray) jarray, (jbyte*) p_array, mode );

	case GL_SHORT:
	case GL_UNSIGNED_SHORT:
		(*env)->ReleaseShortArrayElements( env, (jshortArray) jarray, (jshort*) p_array, mode );

	case GL_INT:
	case GL_UNSIGNED_INT:
		(*env)->ReleaseIntArrayElements( env, (jintArray) jarray, (jint*) p_array, mode );

	case GL_FLOAT:
		(*env)->ReleaseFloatArrayElements( env, (jfloatArray) jarray, (jfloat*) p_array, mode );

	case GL_DOUBLE_EXT:
		(*env)->ReleaseDoubleArrayElements( env, (jdoubleArray) jarray, (jdouble*) p_array, mode );

	default:
		ArrayTypeError( env, type, "ReleaseVoidArrayElements" );
	}
}
