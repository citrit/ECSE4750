/* Copyright 1997 Free Software Foundation, Inc.

 */

/*

 * Leo Chan -- 1995

 *

 * This C file takes care of all the native implementation for the

 * OpenGL utility commands

 */



/* 

 * need to include the JAVA internal header files for macros and function

 * prototypes required to maipulated JAVA data structures and functions

 *

 *

 */



#ifdef WIN32

	#include <windows.h>

#endif



#include "jogl_lib.h"

#include "array_ptr.h"

#include "jogl_JoglCanvas.h"

#include <GL/glu.h>



/*--------------------------------------------------------------------------

 * here on in is just regular apple pie C

 */



/*

 * next put any C UNIX specific header files that are necessary to implement

 * this native code

 */			



JC_VOID_LIB_FUNC( lookAt )

ARGS9( jdouble eyex, jdouble eyey, jdouble eyez, 

	   jdouble centerx, jdouble centery, jdouble centerz, 

	   jdouble upx, jdouble upy, jdouble upz )

{

	gluLookAt( eyex, eyey, eyez, centerx, centery, centerz, upx, upy, upz );

}





JC_VOID_LIB_FUNC( ortho2D )

ARGS4( jdouble left, jdouble right, jdouble bottom, jdouble top )

{

	gluOrtho2D( left, right, bottom, top );

}





JC_VOID_LIB_FUNC( perspective )

ARGS4( jdouble fovy, jdouble aspect, jdouble zNear, jdouble zFar )

{

	gluPerspective( fovy, aspect, zNear, zFar );

}





JC_VOID_LIB_FUNC( pickMatrix )

ARGS5( jdouble x, jdouble y, jdouble width, jdouble height, jintArray viewport )

{

	jint* jviewport = GET_INT_ARRAY( viewport, NULL );

   

	gluPickMatrix( x, y, width, height, (GLint*) jviewport );



	FREE_INT_ARRAY( viewport, jviewport, 0 );

}





JC_INT_LIB_FUNC( project )

ARGS9( jdouble objx, jdouble objy, jdouble objz,

		jdoubleArray modelMatrix,

		jdoubleArray projMatrix,

		jintArray viewport,

		jobject winx, jobject winy, jobject winz )

{

	jdouble*	jmodelMatrix = GET_DOUBLE_ARRAY( modelMatrix, NULL );

	jdouble*	jprojMatrix = GET_DOUBLE_ARRAY( projMatrix, NULL );

	jint*		jviewport = GET_INT_ARRAY( viewport, NULL );

	GLdouble	cwinx, cwiny, cwinz;



	jint ret = (jint) gluProject( objx, objy, objz, 

								  jmodelMatrix, 

								  jprojMatrix, 

								  jviewport, 

								  &cwinx, &cwiny, &cwinz );



	/*

	(*env)->SetDoubleField( env, winx, (*env)->GetFieldID( env, winx, "value" , "D" ), cwinx );

	(*env)->SetDoubleField( env, winy, (*env)->GetFieldID( env, winy, "value" , "D" ), cwiny );

	(*env)->SetDoubleField( env, winz, (*env)->GetFieldID( env, winz, "value" , "D" ), cwinz );

	*/



	SET_FIELD( winx, "value" , Double, "D", cwinx );

	SET_FIELD( winy, "value" , Double, "D", cwiny );

	SET_FIELD( winz, "value" , Double, "D", cwinz );



	FREE_DOUBLE_ARRAY( modelMatrix, jmodelMatrix, 0 );

	FREE_DOUBLE_ARRAY( projMatrix, jprojMatrix, 0 );

	FREE_INT_ARRAY( viewport, jviewport, 0 );



	return ret;

}





JC_INT_LIB_FUNC( unProject )

ARGS9( jdouble winx, jdouble winy, jdouble winz,

		jdoubleArray modelMatrix,

		jdoubleArray projMatrix,

		jintArray viewport,

		jobject objx, jobject objy, jobject objz )

{

	jdouble*	jmodelMatrix = GET_DOUBLE_ARRAY( modelMatrix, NULL );

	jdouble*	jprojMatrix = GET_DOUBLE_ARRAY( projMatrix, NULL );

	jint*		jviewport = GET_INT_ARRAY( viewport, NULL );

	GLdouble	cobjx, cobjy, cobjz;



	jint ret = (jint) gluUnProject( winx, winy, winz, 

									jmodelMatrix, 

									jprojMatrix, 

									jviewport, 

									&cobjx, &cobjy, &cobjz );



	(*env)->SetDoubleField( env, objx, (*env)->GetFieldID( env, objx, "value" , "D" ), cobjx );

	(*env)->SetDoubleField( env, objy, (*env)->GetFieldID( env, objy, "value" , "D" ), cobjy );

	(*env)->SetDoubleField( env, objz, (*env)->GetFieldID( env, objz, "value" , "D" ), cobjz );



	FREE_DOUBLE_ARRAY( modelMatrix, jmodelMatrix, 0 );

	FREE_DOUBLE_ARRAY( projMatrix, jprojMatrix, 0 );

	FREE_INT_ARRAY( viewport, jviewport, 0 );



	return ret;

}





JC_STRING_LIB_FUNC( errorString )

ARGS1( jint errorCode )

{

	char* buf = (char*) gluErrorString( errorCode );

	return (*env)->NewStringUTF( env, buf );

}





JC_INT_LIB_FUNC( scaleImage )

ARGS9( jint format, 

	  jint widthin, jint heightin, jint typein, jobject datain, 

	  jint widthout, jint heightout, jint typeout, jobject dataout )

{



	void*	jdatain = GetVoidArrayElements( env, typein, datain, NULL );

	void*	jdataout = GetVoidArrayElements( env, typeout, datain, NULL );



	jint ret = (jint) gluScaleImage( format, 

									 widthin, heightin, typein, jdatain, 

									 widthout, heightout, typeout, jdataout );



	ReleaseVoidArrayElements( env, typein, datain, jdatain, 0 );

	ReleaseVoidArrayElements( env, typeout, dataout, jdataout, 0 );



	return ret;

}





JC_INT_LIB_FUNC( build1DMipmaps )

ARGS6( jint target, jint components, jint width, jint format, jint type, jobject data )

{



	void* jdata = GetVoidArrayElements( env, type, data, NULL );



	jint ret = (jint) gluBuild1DMipmaps( target, components, width, format, type, jdata );



	ReleaseVoidArrayElements( env, type, data, jdata, 0 );



	return ret;

}





JC_INT_LIB_FUNC( build2DMipmaps )

ARGS7( jint target, jint components, jint width, jint height, jint format, jint type, jobject data )

{



	void* jdata = GetVoidArrayElements( env, type, data, NULL );



	jint ret = (jint) gluBuild2DMipmaps( target, components, width, height, format, type, jdata );



	ReleaseVoidArrayElements( env, type, data, jdata, 0 );



	return ret;

}





JC_STRING_LIB_FUNC( getString )

ARGS1( jint name )

{

	char* buf = (char*) gluGetString( name );

	return (*env)->NewStringUTF( env, buf );

}

