/* Copyright 1997 Free Software Foundation, Inc.

Native wrappers of tesselator functions.

Written by Javier Perez
Torn up by Tommy Reilly

*/

#include <stdlib.h>
#include "jogl_lib.h"
#include "jogl_glu_Tesselator.h"
#include "methodinfo.h"
#include <GL/glu.h>
#include "glu_fixer.h"

#define TESSOBJ_FIELD "tessobj"

#define GET_TESSOBJ_FIELD() ( (GLUtesselator*) GET_INT_FIELD( TESSOBJ_FIELD ) )


static methodinfo	mi_begin;
static methodinfo	mi_edgeFlag;
static methodinfo	mi_vertex;
static methodinfo	mi_end;
static methodinfo	mi_error;

static jobject		jdata;


GT_BOOL_LIB_FUNC( newTess )
ARGS()
{
	GLUtesselator* tessobj;
	THIS_CLASS();
	
	tessobj = gluNewTess();

	if( tessobj ) {
		SET_INT_FIELD( TESSOBJ_FIELD, (jint) tessobj );
		return JNI_TRUE;
	}
	else
		return JNI_FALSE;
}


GT_VOID_LIB_FUNC( deleteTess )
ARGS()
{
	THIS_CLASS();
	gluDeleteTess( GET_TESSOBJ_FIELD() );
}


GT_VOID_LIB_FUNC( beginPolygon )
ARGS()
{
	THIS_CLASS();
	gluBeginPolygon( GET_TESSOBJ_FIELD() );
}


GT_VOID_LIB_FUNC( endPolygon )
ARGS()
{
	THIS_CLASS();
	gluEndPolygon( GET_TESSOBJ_FIELD() );
}


GT_VOID_LIB_FUNC( nextContour )
ARGS1( jint type )
{
	THIS_CLASS();
	gluNextContour( GET_TESSOBJ_FIELD(), (GLenum) type );
}


GT_VOID_LIB_FUNC( tessVertex )
ARGS2( jdoubleArray v, jobject data )
{
	jdouble* jv = GET_DOUBLE_ARRAY( v, NULL );
	THIS_CLASS();

	jdata = (*mi_vertex.env)->NewGlobalRef( mi_vertex.env, data );

	gluTessVertex( GET_TESSOBJ_FIELD(), (GLdouble*) jv, (void*) jdata );

	FREE_DOUBLE_ARRAY( v, jv, 0 );
}



/* Callback functions */

void CALLBACK TessBeginProc( GLenum type )
{
	CALL_VOID_METHOD_MI1( mi_begin, (jint) type );
}


void CALLBACK TessEdgeFlagProc( GLboolean flag )
{
	CALL_VOID_METHOD_MI1( mi_edgeFlag, (jint) flag );
}


void CALLBACK TessVertexProc( void* data )
{
	CALL_VOID_METHOD_MI1( mi_vertex, (jobject) data );

	(*mi_vertex.env)->DeleteGlobalRef( mi_vertex.env, jdata );
}


void CALLBACK TessEndProc()
{
	CALL_VOID_METHOD_MI( mi_end );
}


void CALLBACK TessErrorProc( GLenum error )
{
	CALL_VOID_METHOD_MI1( mi_error, (jint) error );
}


GT_VOID_LIB_FUNC( tessCallback )
ARGS3( jint which, jobject obj, jstring method )
{
	GLUtesselator*	tessobj;
	const char*		pmethod;
	char*			signature;
	void			(CALLBACK* fn)();
	methodinfo*		pmi;
	char			buf[128];

	THIS_CLASS();

	tessobj = GET_TESSOBJ_FIELD();
	pmethod = (*env)->GetStringUTFChars( env, method, NULL );

	if( tessobj ) {
		switch( which ) {
		case GLU_BEGIN:
			pmi = & mi_begin;
			signature = "(I)V";
			fn = TessBeginProc;
			break;

		case GLU_EDGE_FLAG:
			pmi = &mi_edgeFlag;
			signature = "(I)V";
			fn = TessEdgeFlagProc;
			break;

		case GLU_VERTEX:
			pmi = &mi_vertex;
			/* signature = "(Ljava/lang/Object;)V"; */
			signature = "([F)V";
			fn = TessVertexProc;
			break;

		case GLU_END:
			pmi = &mi_end;
			signature = "()V";
			fn = TessEndProc;
			break;

		case GLU_ERROR:
			pmi = &mi_error;
			signature = "(I)V";
			fn = TessErrorProc;
			break;

		default:
			sprintf( buf, "Invalid callback type in GLUtriangulator.TessCallback (%d)", which );
			MethodError( env, buf );
			return;
		}

		if( GetMethodInfo( pmi, env, obj, pmethod, signature ) == 0 ) {
			gluTessCallback( tessobj, which, (void (CALLBACK*)()) fn );
			return;
		}

		else {
			sprintf( buf, "Invalid method in GLUtesselator.tessCallback (%s)", pmethod );
			MethodError( env, buf );
		}
	}

	gluTessCallback( tessobj, which, NULL );
}
