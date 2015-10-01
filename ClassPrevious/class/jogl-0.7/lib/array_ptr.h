/* Copyright 1997 Free Software Foundation, Inc.
 */

 /*
 * Javier Perez -- 1997
 *
 * Helper functions for obtain and release void pointers from variable
 * data array types
 */

#ifndef __ARRAY_PTR_H__
#define __ARRAY_PTR_H__

#ifdef WIN32
	#include <windows.h>
#endif

#include "jni.h"
#include "GL/gl.h"

#define GL_DOUBLE_EXT GL_DOUBLE

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

void ArrayTypeError( JNIEnv* env, GLenum type, char* msg );

void* GetVoidArrayElements( JNIEnv* env, jint type, jobject jarray, jboolean* isCopy );

void ReleaseVoidArrayElements( JNIEnv* env, jint type, jobject jarray, void* p_array, jint mode );

#endif
