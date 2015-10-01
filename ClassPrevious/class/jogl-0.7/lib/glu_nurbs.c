/* Copyright 1997 Free Software Foundation, Inc.

Native implementations of Nurbs functions.

Written by Javier Perez
Torn up by Tommy Reilly

*/

#include <stdlib.h>
#include "jogl_lib.h"
#include "jogl_glu_Nurbs.h"
#include "methodinfo.h"
#include <GL/glu.h>
#include "glu_fixer.h"


#define NURBS_OBJ_FIELD "nurbsobj"
#define GET_NURBS_OBJ_FIELD() ( (GLUnurbs*) GET_INT_FIELD( NURBS_OBJ_FIELD ) )

static methodinfo mi_callback;

GN_BOOL_LIB_FUNC( newNurbsRenderer )
ARGS()
{
	GLUnurbs* nurbsobj;
	THIS_CLASS();
	
	nurbsobj = gluNewNurbsRenderer();

	if( nurbsobj ) {
		SET_INT_FIELD( NURBS_OBJ_FIELD, (jint) nurbsobj );
		return JNI_TRUE;
	}
	else
		return JNI_FALSE;
}


GN_VOID_LIB_FUNC( deleteNurbsRenderer )
ARGS()
{
	THIS_CLASS();
	gluDeleteNurbsRenderer( GET_NURBS_OBJ_FIELD() );
}


GN_VOID_LIB_FUNC( loadSamplingMatrices )
ARGS3( jfloatArray modelMatrix, jfloatArray projMatrix, jintArray viewport )
{
	jfloat*		jmodelMatrix = GET_FLOAT_ARRAY( modelMatrix, NULL );
	jfloat*		jprojMatrix = GET_FLOAT_ARRAY( projMatrix, NULL );
	jint*		jviewport = GET_INT_ARRAY( viewport, NULL );
	THIS_CLASS();

	gluLoadSamplingMatrices( GET_NURBS_OBJ_FIELD(), 
							 (GLfloat*) jmodelMatrix, 
							 (GLfloat*) jprojMatrix, 
							 (GLint*) jviewport );

	FREE_FLOAT_ARRAY( modelMatrix, jmodelMatrix, 0 );
	FREE_FLOAT_ARRAY( projMatrix, jprojMatrix, 0 );
	FREE_INT_ARRAY( viewport, jviewport, 0 );
}


GN_VOID_LIB_FUNC( nurbsProperty )
ARGS2( jint property, jfloat value )
{
	THIS_CLASS();
	gluNurbsProperty( GET_NURBS_OBJ_FIELD(), property, value );
}


GN_VOID_LIB_FUNC( getNurbsProperty )
ARGS2( jint property, jobject value )
{
	GLfloat v;
	THIS_CLASS();

	gluGetNurbsProperty( GET_NURBS_OBJ_FIELD(), property, &v );

	SET_FIELD( value, "value" , Float, "F", v );
}


GN_VOID_LIB_FUNC( beginCurve )
ARGS()
{
	THIS_CLASS();
	gluBeginCurve( GET_NURBS_OBJ_FIELD() );
}


GN_VOID_LIB_FUNC( endCurve )
ARGS()
{
	THIS_CLASS();
	gluEndCurve( GET_NURBS_OBJ_FIELD() );
}


GN_VOID_LIB_FUNC( nurbsCurve )
ARGS6( jint nknots, jfloatArray knot,
	   jint stride, 
	   jfloatArray ctlarray, jint order,
	   jint type )
{
	jfloat*	jknot = GET_FLOAT_ARRAY( knot, NULL );
	jfloat*	jctlarray = GET_FLOAT_ARRAY( ctlarray, NULL );
	THIS_CLASS();

	gluNurbsCurve( GET_NURBS_OBJ_FIELD(), 
				   nknots, (GLfloat*) jknot, 
				   stride, 
				   (GLfloat*) jctlarray, order, 
				   type );

	FREE_FLOAT_ARRAY( knot, jknot, 0 );
	FREE_FLOAT_ARRAY( ctlarray, jctlarray, 0 );
}


GN_VOID_LIB_FUNC( beginSurface )
ARGS()
{
	THIS_CLASS();
	gluBeginSurface( GET_NURBS_OBJ_FIELD() );
}


GN_VOID_LIB_FUNC( endSurface )
ARGS()
{
	THIS_CLASS();
	gluEndSurface( GET_NURBS_OBJ_FIELD() );
}


GN_VOID_LIB_FUNC( nurbsSurface )
ARGS10( jint sknot_count, jfloatArray sknot,
		jint tknot_count, jfloatArray tknot,
		jint s_stride, jint t_stride,
		jfloatArray ctlarray,
		jint sorder, jint torder,
		jint type )
{
	jfloat*	jsknot = GET_FLOAT_ARRAY( sknot, NULL );
	jfloat*	jtknot = GET_FLOAT_ARRAY( tknot, NULL );
	jfloat*	jctlarray = GET_FLOAT_ARRAY( ctlarray, NULL );
	THIS_CLASS();

	gluNurbsSurface( GET_NURBS_OBJ_FIELD(), 
					 sknot_count, (GLfloat*) jsknot, 
					 tknot_count, (GLfloat*) jtknot, 
					 s_stride, t_stride, 
					 (GLfloat*) jctlarray, 
					 sorder, torder, 
					 type );

	FREE_FLOAT_ARRAY( sknot, jsknot, 0 );
	FREE_FLOAT_ARRAY( tknot, jtknot, 0 );
	FREE_FLOAT_ARRAY( ctlarray, jctlarray, 0 );
}


GN_VOID_LIB_FUNC( beginTrim )
ARGS()
{
	THIS_CLASS();
	gluBeginTrim( GET_NURBS_OBJ_FIELD() );
}


GN_VOID_LIB_FUNC( endTrim )
ARGS()
{
	THIS_CLASS();
	gluEndTrim( GET_NURBS_OBJ_FIELD() );
}


GN_VOID_LIB_FUNC( pwlCurve )
ARGS4( jint count, jfloatArray array, jint stride, jint type )
{
	jfloat*	jarray = GET_FLOAT_ARRAY( array, NULL );
	THIS_CLASS();

	gluPwlCurve( GET_NURBS_OBJ_FIELD(), count, (GLfloat*) jarray, stride, type );

	FREE_FLOAT_ARRAY( array, jarray, 0 );
}


/* Callback functions */

void CALLBACK NurbsCallback( GLenum error )
{
	CALL_VOID_METHOD_MI1( mi_callback, (jint) error );
}


GN_VOID_LIB_FUNC( nurbsCallback )
ARGS3( jint which, jobject obj, jstring method )
{
	GLUnurbs*		nurbs_obj;
	const char*		pmethod;
	char			buf[128];
	THIS_CLASS();

	nurbs_obj = GET_NURBS_OBJ_FIELD();
	pmethod = (*env)->GetStringUTFChars( env, method, NULL );

	if( nurbs_obj ) {
		if( GetMethodInfo( &mi_callback, env, obj, pmethod, "(I)V" ) == 0 ) {
			gluNurbsCallback( nurbs_obj, which, NurbsCallback );
			return;
		}

		else {
			sprintf( buf, "Invalid method in GLUnurbs.nurbsCallback (%s)", pmethod );
			MethodError( env, buf );
		}
	}

	gluNurbsCallback( nurbs_obj, which, NULL );
}
