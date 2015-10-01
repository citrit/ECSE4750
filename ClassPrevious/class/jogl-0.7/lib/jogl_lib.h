/* Copyright 1997 Free Software Foundation, Inc.
 
This file contains the necessary includes to get JNI
working allow with some nice macros */

#ifndef __JOGL_LIB_H__
#define __JOGL_LIB_H__

#include "jni_helper.h"

#ifdef WIN32
	#include <windows.h>
	#define BAD_VALUE(_message) printf( _message )
#endif

/* Types of JNI functions in jogl.JoglCanvas */
#define JC_VOID_LIB_FUNC(_name) JNI_FUNC(void, _name, JoglCanvas, jogl)
#define JC_BOOL_LIB_FUNC(_name) JNI_FUNC(jboolean, _name, JoglCanvas, jogl)
#define JC_INT_LIB_FUNC(_name) JNI_FUNC(jint, _name, JoglCanvas, jogl)
#define JC_STRING_LIB_FUNC(_name) JNI_FUNC(jstring, _name, JoglCanvas, jogl)

/* Types of JNI functions in jogl.glu.Nurbs */
#define GN_VOID_LIB_FUNC(_name) JNI_FUNC(void, _name, Nurbs, jogl_glu)
#define GN_BOOL_LIB_FUNC(_name) JNI_FUNC(jboolean, _name, Nurbs, jogl_glu)

/* Types of JNI functions in jogl.glu.Quadric */
#define GQ_VOID_LIB_FUNC(_name) JNI_FUNC(void, _name, Quadric, jogl_glu)
#define GQ_BOOL_LIB_FUNC(_name) JNI_FUNC(jboolean, _name, Quadric, jogl_glu)

/* Types of JNI functions in jogl.glu.Tesselator */
#define GT_VOID_LIB_FUNC(_name) JNI_FUNC(void, _name, Tesselator, jogl_glu)
#define GT_BOOL_LIB_FUNC(_name) JNI_FUNC(jboolean, _name, Tesselator, jogl_glu)

#endif
