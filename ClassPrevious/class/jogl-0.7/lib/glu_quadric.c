/* Copyright 1997 Free Software Foundation, Inc.

Native wrappers of Nurbs functions.

Written by Javier Perez
Torn up by Tommy Reilly

*/

#include <stdlib.h>
#include "jogl_lib.h"
#include "jogl_glu_Quadric.h"
#include "methodinfo.h"
#include <GL/glu.h>
#include "glu_fixer.h"

#define QOBJ_FIELD "qobj"

#define GET_QOBJ_FIELD() ( (GLUquadric*) GET_INT_FIELD( QOBJ_FIELD ) )


static methodinfo mi_callback;


GQ_BOOL_LIB_FUNC( newQuadric )
ARGS()
{
	GLUquadric* qobj;
	THIS_CLASS();
	
	qobj = gluNewQuadric();

	if( qobj ) {
		SET_INT_FIELD( QOBJ_FIELD, (jint) qobj );
		return JNI_TRUE;
	}
	else
		return JNI_FALSE;
}


GQ_VOID_LIB_FUNC( deleteQuadric )
ARGS()
{
	THIS_CLASS();
	gluDeleteQuadric( GET_QOBJ_FIELD() );
}


GQ_VOID_LIB_FUNC( quadricDrawStyle )
ARGS1( jint drawStyle )
{
	THIS_CLASS();
	gluQuadricDrawStyle( GET_QOBJ_FIELD(), drawStyle );
}


GQ_VOID_LIB_FUNC( quadricOrientation )
ARGS1( jint orientation )
{
	THIS_CLASS();
	gluQuadricOrientation( GET_QOBJ_FIELD(), orientation );
}


GQ_VOID_LIB_FUNC( quadricNormals )
ARGS1( jint normals )
{
	THIS_CLASS();
	gluQuadricNormals( GET_QOBJ_FIELD(), normals );
}


GQ_VOID_LIB_FUNC( quadricTexture )
ARGS1( jint textureCoords )
{
	THIS_CLASS();
	gluQuadricTexture( GET_QOBJ_FIELD(), (GLboolean) textureCoords );
}


GQ_VOID_LIB_FUNC( cylinder )
ARGS5( jdouble baseRadius, jdouble topRadius, jdouble height, jint slices, jint stacks )
{
	THIS_CLASS();
	gluCylinder( GET_QOBJ_FIELD(), baseRadius, topRadius, height, slices, stacks );
}


GQ_VOID_LIB_FUNC( sphere )
ARGS3( jdouble radius, jint slices, jint stacks )
{
	THIS_CLASS();
	gluSphere( GET_QOBJ_FIELD(), radius, slices, stacks );
}


GQ_VOID_LIB_FUNC( disk )
ARGS4( jdouble innerRadius, jdouble outerRadius, jint slices, jint loops )
{
	THIS_CLASS();
	gluDisk( GET_QOBJ_FIELD(), innerRadius, outerRadius, slices, loops );
}


GQ_VOID_LIB_FUNC( partialDisk )
ARGS6( jdouble innerRadius, jdouble outerRadius, jint slices, jint loops, jdouble startAngle, jdouble sweepAngle )
{
	THIS_CLASS();
	gluPartialDisk( GET_QOBJ_FIELD(), innerRadius, outerRadius, slices, loops, startAngle, sweepAngle );
}


/* Callback functions */

void CALLBACK QuadricCallback( GLenum error )
{
	CALL_VOID_METHOD_MI1( mi_callback, (jint) error );
}


GQ_VOID_LIB_FUNC( quadricCallback )
ARGS3( jint which, jobject obj, jstring method )
{
	GLUquadric*		qobj;
	const char*		pmethod;
	char			buf[128];

	THIS_CLASS();

	qobj = GET_QOBJ_FIELD();
	pmethod = (*env)->GetStringUTFChars( env, method, NULL );

	if( qobj ) {
		if( GetMethodInfo( &mi_callback, env, obj, pmethod, "(I)V" ) == 0 ) {
			gluQuadricCallback( qobj, which, QuadricCallback );
			return;
		}

		else {
			sprintf( buf, "Invalid method in GLUquadric.quadricCallback (%s)", pmethod );
			MethodError( env, buf );
		}
	}

	gluQuadricCallback( qobj, which, NULL );
}
