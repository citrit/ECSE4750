/* Copyright 1997 Free Software Foundation, Inc.

 */

/*

 * Leo Chan -- 1995

 * lchan@cgl.uwaterloo.ca

 *

 * This mammoth C file takes care of all the native implementation for the

 * bulk of OpenGL commands

 */



/* 

 * Tommy Reilly -- 1997

 * changed to JNI

 */

#ifdef WIN32

	#include <windows.h>

#endif


#include <stdlib.h>

#include "jogl_JoglCanvas.h"

#include "jogl_lib.h"

#include <GL/gl.h>



/**

 * herein lies the native JAVA methods for the OpenGL functions.  

 */



JC_VOID_LIB_FUNC(accum) 

ARGS2(jint op, jfloat value )

{

#ifdef DEBUG

   switch( op ) 

      {

      case GL_ACCUM:

	 glAccum( GL_ACCUM, value );

	 break;

      case GL_LOAD:

	 glAccum( GL_LOAD, value );

	 break;

      case GL_ADD:

	 glAccum( GL_ADD, value );

	 break;

      case GL_MULT:

	 glAccum( GL_MULT, value );

	 break;

      case GL_RETURN:

	 glAccum( GL_RETURN, value );

	 break;

      default:

	 BAD_VALUE("Bad operation passed to accum().");

	 break;

      }

#else

   glAccum( op, value);

#endif

}



JC_VOID_LIB_FUNC(alphaFunc) 

ARGS2(jint func, jfloat value)

{

#ifdef DEBUG

   switch( func ) 

      {

      case GL_NEVER:

	 glAlphaFunc( GL_NEVER, value );

	 break;

      case GL_LESS:

	 glAlphaFunc( GL_LESS, value );

	 break;

      case GL_EQUAL:

	 glAlphaFunc( GL_EQUAL, value );

	 break;

      case GL_LEQUAL:

	 glAlphaFunc( GL_LEQUAL, value );

	 break;

      case GL_GREATER:

	 glAlphaFunc( GL_GREATER, value );

	 break;

      case GL_NOTEQUAL:

	 glAlphaFunc( GL_NOTEQUAL, value );

	 break;

      case GL_GEQUAL:

	 glAlphaFunc( GL_GEQUAL, value );

	 break;

      case GL_ALWAYS:

	 glAlphaFunc( GL_ALWAYS, value );

	 break;

      default:

	 BAD_VALUE("Bad function passed to alphaFunc().");

      }

#else

   glAlphaFunc( func, value);

#endif

}



JC_VOID_LIB_FUNC(begin) 

ARGS1(jint mode )

{

#ifdef DEBUG

   switch( mode ) 

      {

      case GL_POINTS:

	 glBegin( GL_POINTS );

	 break;	

      case GL_LINES:

	 glBegin( GL_LINES );

	 break;

      case GL_LINE_STRIP:

	 glBegin( GL_LINE_STRIP );

	 break;

      case GL_LINE_LOOP:

	 glBegin( GL_LINE_LOOP );

	 break;

      case GL_TRIANGLES:

	 glBegin( GL_TRIANGLES );

	 break;

      case GL_TRIANGLE_STRIP:

	 glBegin( GL_TRIANGLE_STRIP );

	 break;

      case GL_TRIANGLE_FAN:

	 glBegin( GL_TRIANGLE_FAN );

	 break;

      case GL_QUADS:

	 glBegin( GL_QUADS );

	 break;

      case GL_QUAD_STRIP:

	 glBegin( GL_QUAD_STRIP );

	 break;

      case GL_POLYGON:

	 glBegin( GL_POLYGON );

	 break;

      default:

	 BAD_VALUE("Invalid arg to begin().");

      }

#else

   glBegin( (GLenum) mode);

#endif

}



JC_VOID_LIB_FUNC(bindTexture)

ARGS2(jint target, jint texture)

{

#ifdef HAVE_SGI_EXTENSIONS

   glBindTextureEXT(target, texture);

#endif

}



JC_VOID_LIB_FUNC(blendFunc) 

ARGS2(jint sfactor, jint dfactor )

{

#ifdef DEBUG

   GLenum gl_sfactor, gl_dfactor;



   switch( sfactor ) 

      {

      case GL_ZERO:

	 gl_sfactor = GL_ZERO;

	 break;

      case GL_ONE:

	 gl_sfactor = GL_ONE;

	 break;

      case GL_DST_COLOR:

	 gl_sfactor = GL_DST_COLOR;

	 break;

      case GL_ONE_MINUS_DST_COLOR:

	 gl_sfactor = GL_ONE_MINUS_DST_COLOR;

	 break;

      case GL_SRC_ALPHA:

	 gl_sfactor = GL_SRC_ALPHA;

	 break;

      case GL_ONE_MINUS_SRC_ALPHA:

	 gl_sfactor = GL_ONE_MINUS_SRC_ALPHA;

	 break;

      case GL_DST_ALPHA:

	 gl_sfactor = GL_DST_ALPHA;

	 break;

      case GL_ONE_MINUS_DST_ALPHA:

	 gl_sfactor = GL_ONE_MINUS_DST_ALPHA;

	 break;

      case GL_SRC_ALPHA_SATURATE:

	 gl_sfactor = GL_SRC_ALPHA_SATURATE;

	 break;

      default:

	 BAD_VALUE("Bad source blending factor passed to blendFunc().");

	 return;			/* do nothing on unrecognized option */

      }

   switch( dfactor ) 

      {

      case GL_ZERO:

	 gl_dfactor = GL_ZERO;

	 break;

      case GL_ONE:

	 gl_dfactor = GL_ONE;

	 break;

      case GL_ONE_MINUS_SRC_COLOR:

	 gl_dfactor = GL_ONE_MINUS_SRC_COLOR;

	 break;

      case GL_SRC_ALPHA:

	 gl_dfactor = GL_SRC_ALPHA;

	 break;

      case GL_ONE_MINUS_SRC_ALPHA:

	 gl_dfactor = GL_ONE_MINUS_SRC_ALPHA;

	 break;

      case GL_DST_ALPHA:

	 gl_dfactor = GL_DST_ALPHA;

	 break;

      case GL_ONE_MINUS_DST_ALPHA:

	 gl_dfactor = GL_ONE_MINUS_DST_ALPHA;

	 break;

      default:

	 BAD_VALUE("Bad destination blending factor passed to blendFunc().");

	 return;			/* do nothing on unrecognized option */

      }

   

   glBlendFunc( gl_sfactor, gl_dfactor );

#else

   glBlendFunc( (GLenum) sfactor, (GLenum) dfactor); 

#endif

}



JC_VOID_LIB_FUNC(clear) 

ARGS1( jint mask )

{

#ifdef DEBUG

   switch (mask) 

      {

      case GL_COLOR_BUFFER_BIT:

	 glClear( GL_COLOR_BUFFER_BIT );

	 break;

      case GL_DEPTH_BUFFER_BIT:

	 glClear( GL_DEPTH_BUFFER_BIT );

	 break;

      case GL_ACCUM_BUFFER_BIT:

	 glClear( GL_ACCUM_BUFFER_BIT );

	 break;

      case GL_STENCIL_BUFFER_BIT:

	 glClear( GL_STENCIL_BUFFER_BIT );

	 break;

      default:

	 BAD_VALUE("Invalid bit passed to clear().");

	 return;

      }

#else

   glClear( (GLbitfield) mask );

#endif

}



JC_VOID_LIB_FUNC(clearAccum) 

ARGS4(jfloat red, jfloat green, jfloat blue, jfloat alpha)

{

   glClearAccum( red, green, blue, alpha );

}



JC_VOID_LIB_FUNC(clearColor) 

ARGS4(jfloat red, jfloat green, jfloat blue, jfloat alpha)

{

   glClearColor( red, green, blue, alpha );

}



JC_VOID_LIB_FUNC(clearDepth) 

ARGS1(jdouble depth)

{

   glClearDepth( (GLclampd) depth );

}



JC_VOID_LIB_FUNC(clearIndex) 

ARGS1(jfloat c)

{

   glClearIndex( (GLfloat) c );

}



JC_VOID_LIB_FUNC(clearStencil) 

ARGS1(jint s)

{

   glClearStencil( (GLint) s );

}



JC_VOID_LIB_FUNC(clipPlane) 

ARGS5(jint plane, jdouble A, jdouble B, jdouble C, jdouble D)

{

   GLdouble equation[4];



   equation[0] = (GLdouble) A;

   equation[1] = (GLdouble) B;

   equation[2] = (GLdouble) C;

   equation[3] = (GLdouble) D;



#ifdef DEBUG

   switch( plane ) 

      {

      case GL_CLIP_PLANE0:

	 glClipPlane( GL_CLIP_PLANE0, equation );

	 break;

      case GL_CLIP_PLANE1:

	 glClipPlane( GL_CLIP_PLANE1, equation );

	 break;

      case GL_CLIP_PLANE2:

	 glClipPlane( GL_CLIP_PLANE2, equation );

	 break;

      case GL_CLIP_PLANE3:

	 glClipPlane( GL_CLIP_PLANE3, equation );

	 break;

      case GL_CLIP_PLANE4:

	 glClipPlane( GL_CLIP_PLANE4, equation );

	 break;

      case GL_CLIP_PLANE5:

	 glClipPlane( GL_CLIP_PLANE5, equation );

	 break;

      default:

	 BAD_VALUE("Invalid plane passed to clipPlane().");

	 return;

      }

#else

   glClipPlane( (GLenum) plane, equation);

#endif

}



JC_VOID_LIB_FUNC(color3b) 

ARGS3(jbyte red, jbyte green, jbyte blue)

{

   glColor3b( (GLbyte)red, (GLbyte)green, (GLbyte)blue );

}



JC_VOID_LIB_FUNC(color3d) 

ARGS3(jdouble red, jdouble green, jdouble blue)

{

   glColor3d( (GLdouble) red, (GLdouble) green, (GLdouble) blue );

}



JC_VOID_LIB_FUNC(color3f) 

ARGS3(jfloat red, jfloat green, jfloat blue)

{

   glColor3f( (GLfloat) red, (GLfloat) green, (GLfloat) blue );

}



JC_VOID_LIB_FUNC(color3i) 

ARGS3(jint red, jint green, jint blue)

{

   glColor3i( (GLint) red, (GLint)green, (GLint)blue );

}

JC_VOID_LIB_FUNC(color3s) 

ARGS3(jshort red, jshort green, jshort blue)

{

	glColor3s( (GLshort) red, (GLshort) green, (GLshort) blue );

}

JC_VOID_LIB_FUNC(color4b) 

ARGS4(jbyte r, jbyte g, jbyte b, jbyte a)

{

   glColor4b( (GLbyte)r, (GLbyte)g, (GLbyte)b, (GLbyte)a );

}

JC_VOID_LIB_FUNC(color4d) 

ARGS4(jdouble r, jdouble g, jdouble b, jdouble a)

{

   glColor4d( (GLdouble) r,(GLdouble) g,(GLdouble) b,(GLdouble) a );

}

JC_VOID_LIB_FUNC(color4f) 

ARGS4(jfloat r, jfloat g, jfloat b, jfloat a)

{

   glColor4f( (GLfloat) r,(GLfloat) g,(GLfloat) b,(GLfloat) a );

}

JC_VOID_LIB_FUNC(color4i) 

ARGS4(jint r, jint g, jint b, jint a)

{

	glColor4i( (GLint)r, (GLint)g, (GLint)b, (GLint)a );



}

JC_VOID_LIB_FUNC(color4s) 

ARGS4(jshort r, jshort g,jshort b, jshort a)

{

	glColor4s( (GLshort) r,(GLshort) g,(GLshort) b,(GLshort) a );

}



JC_VOID_LIB_FUNC(colorMask) 

ARGS4(jboolean red, jboolean green, jboolean blue, jboolean alpha)

{

   glColorMask( (GLboolean) red, (GLboolean) green, (GLboolean) blue, (GLboolean) alpha );

}



JC_VOID_LIB_FUNC(colorMaterial) 

ARGS2(jint face, jint mode)

{

#ifdef DEBUG

   GLenum gl_face, gl_mode;

   

   switch( face ) 

      {

      case GL_FRONT:

	 gl_face = GL_FRONT;

	 break;

      case GL_BACK:

	 gl_face = GL_BACK;

	 break;

      case GL_FRONT_AND_BACK:

	 gl_face = GL_FRONT_AND_BACK;

	 break;

      default:

	 BAD_VALUE("Bad face passed to colorMaterial()");

	 return;

      }



   switch( mode ) 

      {

      case GL_EMISSION:

	 gl_mode = GL_EMISSION;

	 break;

      case GL_AMBIENT:

	 gl_mode = GL_AMBIENT;

	 break;

      case GL_DIFFUSE:

	 gl_mode = GL_DIFFUSE;

	 break;

      case GL_SPECULAR:

	 gl_mode = GL_SPECULAR;

	 break;

      case GL_AMBIENT_AND_DIFFUSE:

	 gl_mode = GL_AMBIENT_AND_DIFFUSE;

	 break;

      default:	

	 BAD_VALUE("Bad mode passed to colorMaterial()");

	 return;

      }

#else

   glColorMaterial( (GLenum) face, (GLenum) mode);

#endif

}



JC_VOID_LIB_FUNC(copyPixels) 

ARGS5(jint x, jint y, jint width, jint height, jint type)

{

#ifdef DEBUG

   switch( type ) 

      {

      case GL_COLOR:

	 glCopyPixels( (GLint)x, (GLint)y, (GLsizei)width, (GLsizei)height,

		       GL_COLOR );

	 break;

      case GL_DEPTH:

	 glCopyPixels( (GLint)x, (GLint)y, (GLsizei)width, (GLsizei)height,

		       GL_DEPTH );

	 break;

      case GL_STENCIL:

	 glCopyPixels( (GLint)x, (GLint)y, (GLsizei)width, (GLsizei)height,

		       GL_STENCIL );

	 break;

      default:

	 return;

      }

#else

   glCopyPixels( (GLint)x, (GLint)y, (GLsizei)width, (GLsizei)height, (GLenum) type);

#endif

}

JC_VOID_LIB_FUNC(cullFace) 

ARGS1(jint mode)

{

#ifdef DEBUG

   switch( mode ) 

      {

      case GL_FRONT:

	 glCullFace( GL_FRONT );

	 break;

      case GL_BACK:

	 glCullFace( GL_BACK );

	 break;

      default:

	 BAD_VALUE("Bad mode passed to cullFace().");

	 return;

      }

#else

   glCullFace( (GLenum) mode );

#endif

}

JC_VOID_LIB_FUNC(depthFunc) 

ARGS1(jint func)

{

#ifdef DEBUG

   switch( func ) 

      {

      case GL_NEVER:

	 glDepthFunc( GL_NEVER );

	 break;

      case GL_LESS:

	 glDepthFunc( GL_LESS );

	 break;

      case GL_EQUAL:

	 glDepthFunc( GL_EQUAL );

	 break;

      case GL_LEQUAL:

	 glDepthFunc( GL_LEQUAL );

	 break;

      case GL_GREATER:

	 glDepthFunc( GL_GREATER );

	 break;

      case GL_NOTEQUAL:

	 glDepthFunc( GL_NOTEQUAL );

	 break;

      case GL_GEQUAL:

	 glDepthFunc( GL_GEQUAL );

	 break;

      case GL_ALWAYS:

	 glDepthFunc( GL_ALWAYS );

	 break;

      default:

	 BAD_VALUE("Bad function passed to depthFunc().");

	 return;

      }

#else

   glDepthFunc( (GLenum) func );

#endif

}



JC_VOID_LIB_FUNC(depthMask) 

ARGS1(jboolean  flag)

{

   glDepthMask( (GLboolean) flag );

}



JC_VOID_LIB_FUNC(depthRange) 

ARGS2(jdouble nearVar, jdouble farVar)

     /*ARGS2(jdouble near, jdouble far) Windows blows! */ 

{

   /*  glDepthRange( (GLdouble) near, (GLdouble) far  );*/

   glDepthRange( (GLdouble) nearVar, (GLdouble)farVar  );

}



JC_VOID_LIB_FUNC(drawBuffer) 

ARGS1(jint mode)

{

#ifdef DEBUG

   switch( mode ) 

      {

      case GL_NONE:

	 glDrawBuffer( GL_NONE );

	 break;

      case GL_FRONT_LEFT:

	 glDrawBuffer( GL_FRONT_LEFT );

	 break;

      case GL_FRONT_RIGHT:

	 glDrawBuffer( GL_FRONT_RIGHT );

	 break;

      case GL_BACK_LEFT:

	 glDrawBuffer( GL_BACK_LEFT );

	 break;

      case GL_BACK_RIGHT:

	 glDrawBuffer( GL_BACK_RIGHT );

	 break;

      case GL_FRONT:

	 glDrawBuffer( GL_FRONT );

	 break;

      case GL_BACK:

	 glDrawBuffer( GL_BACK );

	 break;

      case GL_RIGHT:

	 glDrawBuffer( GL_RIGHT );

	 break;

      case GL_FRONT_AND_BACK:

	 glDrawBuffer( GL_FRONT_AND_BACK );

	 break;

      case GL_AUX0:

	 glDrawBuffer( GL_AUX0 );

	 break;

      case GL_AUX1:

	 glDrawBuffer( GL_AUX1 );

	 break;

      case GL_AUX2:

	 glDrawBuffer( GL_AUX2 );

	 break;

      case GL_AUX3:

	 glDrawBuffer( GL_AUX3 );

	 break;

      default:

	 break;

      }

#else

   glDrawBuffer( (GLenum) mode );

#endif

}



JC_VOID_LIB_FUNC(edgeFlag) 

ARGS1(jboolean flag) 

{

   glEdgeFlag( GL_FALSE );

}



JC_VOID_LIB_FUNC(enable) 

ARGS1(jint cap)

{

#ifdef DEBUG

   switch( cap ) 

      {

      case GL_ALPHA_TEST:

	 glEnable( GL_ALPHA_TEST );

	 break;

      case GL_AUTO_NORMAL:

	 glEnable( GL_AUTO_NORMAL );

	 break;

      case GL_BLEND:

	 glEnable( GL_BLEND );

	 break;

      case GL_CLIP_PLANE0:

	 glEnable( GL_CLIP_PLANE0 );

	 break;

      case GL_CLIP_PLANE1:

	 glEnable( GL_CLIP_PLANE1 );

	 break;

      case GL_CLIP_PLANE2:

	 glEnable( GL_CLIP_PLANE2 );

	 break;

      case GL_CLIP_PLANE3:

	 glEnable( GL_CLIP_PLANE3 );

	 break;

      case GL_CLIP_PLANE4:

	 glEnable( GL_CLIP_PLANE4 );

	 break;

      case GL_CLIP_PLANE5:

	 glEnable( GL_CLIP_PLANE5 );

	 break;

      case GL_COLOR_MATERIAL:

	 glEnable( GL_COLOR_MATERIAL );

	 break;

      case GL_CULL_FACE:

	 glEnable( GL_CULL_FACE );

	 break;

      case GL_DEPTH_TEST:

	 glEnable( GL_DEPTH_TEST );

	 break;

      case GL_DITHER:

	 glEnable( GL_DITHER );

	 break;

      case GL_FOG:

	 glEnable( GL_FOG );

	 break;

      case GL_LIGHT0:

	 glEnable( GL_LIGHT0 );

	 break;

      case GL_LIGHT1:

	 glEnable( GL_LIGHT1 );

	 break;

      case GL_LIGHT2:

	 glEnable( GL_LIGHT2 );

	 break;

      case GL_LIGHT3:

	 glEnable( GL_LIGHT3 );

	 break;

      case GL_LIGHT4:

	 glEnable( GL_LIGHT4 );

	 break;

      case GL_LIGHT5:

	 glEnable( GL_LIGHT5 );

	 break;

      case GL_LIGHT6:

	 glEnable( GL_LIGHT6 );

	 break;

      case GL_LIGHT7:

	 glEnable( GL_LIGHT7 );

	 break;

      case GL_LIGHTING:

	 glEnable( GL_LIGHTING );

	 break;

      case GL_LINE_SMOOTH:

	 glEnable( GL_LINE_SMOOTH );

	 break;

      case GL_LINE_STIPPLE:

	 glEnable( GL_LINE_STIPPLE );

	 break;

      case GL_LOGIC_OP:

	 glEnable( GL_LOGIC_OP );

	 break;

      case GL_MAP1_COLOR_4:

	 glEnable( GL_MAP1_COLOR_4 );

	 break;

      case GL_MAP1_INDEX:

	 glEnable( GL_MAP1_INDEX );

	 break;

      case GL_MAP1_NORMAL:

	 glEnable( GL_MAP1_NORMAL);

	 break;

      case GL_MAP1_TEXTURE_COORD_1:

	 glEnable( GL_MAP1_TEXTURE_COORD_1);

	 break;

      case GL_MAP1_TEXTURE_COORD_2:

	 glEnable( GL_MAP1_TEXTURE_COORD_2);

	 break;

      case GL_MAP1_TEXTURE_COORD_3:

	 glEnable( GL_MAP1_TEXTURE_COORD_3);

	 break;

      case GL_MAP1_TEXTURE_COORD_4:

	 glEnable( GL_MAP1_TEXTURE_COORD_4);

	 break;

      case GL_MAP1_VERTEX_3:

	 glEnable( GL_MAP1_VERTEX_3);

	 break;

      case GL_MAP1_VERTEX_4:

	 glEnable( GL_MAP1_VERTEX_4);

	 break;

      case GL_MAP2_COLOR_4:

	 glEnable( GL_MAP2_COLOR_4);

	 break;

      case GL_MAP2_INDEX:

	 glEnable( GL_MAP2_INDEX);

	 break;

      case GL_MAP2_NORMAL:

	 glEnable( GL_MAP2_NORMAL);

	 break;

      case GL_MAP2_TEXTURE_COORD_1:

	 glEnable( GL_MAP2_TEXTURE_COORD_1);

	 break;

      case GL_MAP2_TEXTURE_COORD_2:

	 glEnable( GL_MAP2_TEXTURE_COORD_2);

	 break;

      case GL_MAP2_TEXTURE_COORD_3:

	 glEnable( GL_MAP2_TEXTURE_COORD_3);

	 break;

      case GL_MAP2_TEXTURE_COORD_4:

	 glEnable( GL_MAP2_TEXTURE_COORD_4);

	 break;

      case GL_MAP2_VERTEX_3:

	 glEnable( GL_MAP2_VERTEX_3);

	 break;

      case GL_MAP2_VERTEX_4:

	 glEnable( GL_MAP2_VERTEX_4);

	 break;

      case GL_NORMALIZE:

	 glEnable( GL_NORMALIZE);

	 break;

      case GL_POINT_SMOOTH:

	 glEnable( GL_POINT_SMOOTH);

	 break;

      case GL_POLYGON_SMOOTH:

	 glEnable( GL_POLYGON_SMOOTH);

	 break;

      case GL_POLYGON_STIPPLE:

	 glEnable( GL_POLYGON_STIPPLE);

	 break;

      case GL_SCISSOR_TEST:

	 glEnable( GL_SCISSOR_TEST);

	 break;

      case GL_STENCIL_TEST:

	 glEnable( GL_STENCIL_TEST);

	 break;

      case GL_TEXTURE_1D:

	 glEnable( GL_TEXTURE_1D);

	 break;

      case GL_TEXTURE_2D:

	 glEnable( GL_TEXTURE_2D);

	 break;

      case GL_TEXTURE_GEN_Q:

	 glEnable( GL_TEXTURE_GEN_Q);

	 break;

      case GL_TEXTURE_GEN_R:

	 glEnable( GL_TEXTURE_GEN_R);

	 break;

      case GL_TEXTURE_GEN_S:

	 glEnable( GL_TEXTURE_GEN_S);

	 break;

      case GL_TEXTURE_GEN_T:

	 glEnable( GL_TEXTURE_GEN_T);

	 break;

      default:

	 BAD_VALUE("Bad capability passed to enable().");

	 return;

      }

#else

   glEnable( (GLenum) cap );

#endif

}



JC_VOID_LIB_FUNC(disable)

ARGS1(jint cap)

{

#ifdef DEBUG

   switch( cap ) 

      {

      case GL_ALPHA_TEST:

	 glDisable( GL_ALPHA_TEST );

	 break;

      case GL_AUTO_NORMAL:

	 glDisable( GL_AUTO_NORMAL );

	 break;

      case GL_BLEND:

	 glDisable( GL_BLEND );

	 break;

      case GL_CLIP_PLANE0:

	 glDisable( GL_CLIP_PLANE0 );

	 break;

      case GL_CLIP_PLANE1:

	 glDisable( GL_CLIP_PLANE1 );

	 break;

      case GL_CLIP_PLANE2:

	 glDisable( GL_CLIP_PLANE2 );

	 break;

      case GL_CLIP_PLANE3:

	 glDisable( GL_CLIP_PLANE3 );

	 break;

      case GL_CLIP_PLANE4:

	 glDisable( GL_CLIP_PLANE4 );

	 break;

      case GL_CLIP_PLANE5:

	 glDisable( GL_CLIP_PLANE5 );

	 break;

      case GL_COLOR_MATERIAL:

	 glDisable( GL_COLOR_MATERIAL );

	 break;

      case GL_CULL_FACE:

	 glDisable( GL_CULL_FACE );

	 break;

      case GL_DEPTH_TEST:

	 glDisable( GL_DEPTH_TEST );

	 break;

      case GL_DITHER:

	 glDisable( GL_DITHER );

	 break;

      case GL_FOG:

	 glDisable( GL_FOG );

	 break;

      case GL_LIGHT0:

	 glDisable( GL_LIGHT0 );

	 break;

      case GL_LIGHT1:

	 glDisable( GL_LIGHT1 );

	 break;

      case GL_LIGHT2:

	 glDisable( GL_LIGHT2 );

	 break;

      case GL_LIGHT3:

	 glDisable( GL_LIGHT3 );

	 break;

      case GL_LIGHT4:

	 glDisable( GL_LIGHT4 );

	 break;

      case GL_LIGHT5:

	 glDisable( GL_LIGHT5 );

	 break;

      case GL_LIGHT6:

	 glDisable( GL_LIGHT6 );

	 break;

      case GL_LIGHT7:

	 glDisable( GL_LIGHT7 );

	 break;

      case GL_LIGHTING:

	 glDisable( GL_LIGHTING );

	 break;

      case GL_LINE_SMOOTH:

	 glDisable( GL_LINE_SMOOTH );

	 break;

      case GL_LINE_STIPPLE:

	 glDisable( GL_LINE_STIPPLE );

	 break;

      case GL_LOGIC_OP:

	 glDisable( GL_LOGIC_OP );

	 break;

      case GL_MAP1_COLOR_4:

	 glDisable( GL_MAP1_COLOR_4 );

	 break;

      case GL_MAP1_INDEX:

	 glDisable( GL_MAP1_INDEX );

	 break;

      case GL_MAP1_NORMAL:

	 glDisable( GL_MAP1_NORMAL);

	 break;

      case GL_MAP1_TEXTURE_COORD_1:

	 glDisable( GL_MAP1_TEXTURE_COORD_1);

	 break;

      case GL_MAP1_TEXTURE_COORD_2:

	 glDisable( GL_MAP1_TEXTURE_COORD_2);

	 break;

      case GL_MAP1_TEXTURE_COORD_3:

	 glDisable( GL_MAP1_TEXTURE_COORD_3);

	 break;

      case GL_MAP1_TEXTURE_COORD_4:

	 glDisable( GL_MAP1_TEXTURE_COORD_4);

	 break;

      case GL_MAP1_VERTEX_3:

	 glDisable( GL_MAP1_VERTEX_3);

	 break;

      case GL_MAP1_VERTEX_4:

	 glDisable( GL_MAP1_VERTEX_4);

	 break;

      case GL_MAP2_COLOR_4:

	 glDisable( GL_MAP2_COLOR_4);

	 break;

      case GL_MAP2_INDEX:

	 glDisable( GL_MAP2_INDEX);

	 break;

      case GL_MAP2_NORMAL:

	 glDisable( GL_MAP2_NORMAL);

	 break;

      case GL_MAP2_TEXTURE_COORD_1:

	 glDisable( GL_MAP2_TEXTURE_COORD_1);

	 break;

      case GL_MAP2_TEXTURE_COORD_2:

	 glDisable( GL_MAP2_TEXTURE_COORD_2);

	 break;

      case GL_MAP2_TEXTURE_COORD_3:

	 glDisable( GL_MAP2_TEXTURE_COORD_3);

	 break;

      case GL_MAP2_TEXTURE_COORD_4:

	 glDisable( GL_MAP2_TEXTURE_COORD_4);

	 break;

      case GL_MAP2_VERTEX_3:

	 glDisable( GL_MAP2_VERTEX_3);

	 break;

      case GL_MAP2_VERTEX_4:

	 glDisable( GL_MAP2_VERTEX_4);

	 break;

      case GL_NORMALIZE:

	 glDisable( GL_NORMALIZE);

	 break;

      case GL_POINT_SMOOTH:

	 glDisable( GL_POINT_SMOOTH);

	 break;

      case GL_POLYGON_SMOOTH:

	 glDisable( GL_POLYGON_SMOOTH);

	 break;

      case GL_POLYGON_STIPPLE:

	 glDisable( GL_POLYGON_STIPPLE);

	 break;

      case GL_SCISSOR_TEST:

	 glDisable( GL_SCISSOR_TEST);

	 break;

      case GL_STENCIL_TEST:

	 glDisable( GL_STENCIL_TEST);

	 break;

      case GL_TEXTURE_1D:

	 glDisable( GL_TEXTURE_1D);

	 break;

      case GL_TEXTURE_2D:

	 glDisable( GL_TEXTURE_2D);

	 break;

      case GL_TEXTURE_GEN_Q:

	 glDisable( GL_TEXTURE_GEN_Q);

	 break;

      case GL_TEXTURE_GEN_R:

	 glDisable( GL_TEXTURE_GEN_R);

	 break;

      case GL_TEXTURE_GEN_S:

	 glDisable( GL_TEXTURE_GEN_S);

	 break;

      case GL_TEXTURE_GEN_T:

	 glDisable( GL_TEXTURE_GEN_T);

	 break;

      default:

	 BAD_VALUE("Bad capability passed to disable().");

	 return;

      }

#else

   glDisable( (GLenum) cap );

#endif

}



JC_VOID_LIB_FUNC(end) 

ARGS()

{

   glEnd();

}



JC_VOID_LIB_FUNC(evalCoord1d) 

ARGS1(jdouble u )

{

   glEvalCoord1d( u );

}



JC_VOID_LIB_FUNC(evalCoord2d) 

ARGS2(jdouble u, jdouble v)

{

   glEvalCoord2d( (GLdouble) u, (GLdouble)v );

}



JC_VOID_LIB_FUNC(evalCoord1f) 

ARGS1(jfloat u )

{

	glEvalCoord1f( (GLfloat)  u );

}



JC_VOID_LIB_FUNC(evalCoord2f) 

ARGS2(jfloat u, jfloat v)

{

   glEvalCoord2f( (GLfloat)  u,(GLfloat) v );

}

JC_VOID_LIB_FUNC(evalMesh1) 

ARGS3(jint mode, jint i1, jint i2 )

{

#ifdef DEBUG

   switch( mode ) 

      {

      case  GL_POINT:

	 glEvalMesh1( GL_POINT, (GLint)i1, (GLint)i2 );

	 break;

      case GL_LINE:

	 glEvalMesh1( GL_LINE, (GLint)i1, (GLint)i2 );

	 break;

      default:

	 BAD_VALUE("Bad mode passed to evalMesh1");

	 return;

      }

#else

   glEvalMesh1( (GLenum) mode, (GLint)i1, (GLint)i2 );

#endif

}



JC_VOID_LIB_FUNC(evalMesh2) 

ARGS5(jint mode, jint i1, jint i2, jint j1, jint j2 )

{

#ifdef DEBUG

	switch( mode ) 

	   {

	   case  GL_POINT:

	      glEvalMesh2( GL_POINT, (GLint)i1, (GLint)i2, (GLint)j1, (GLint)j2 );

	      break;

	   case GL_LINE:

	      glEvalMesh2( GL_LINE, (GLint)i1, (GLint)i2, (GLint)j1, (GLint)j2 );

	      break;

	   case GL_FILL:

	      glEvalMesh2( GL_FILL, (GLint)i1, (GLint)i2, (GLint)j1, (GLint)j2 );

	      break;

	   default:

	      BAD_VALUE("Bad value passed to glEvalMesh2().");

	      return;

	   }

#else

	glEvalMesh2( (GLenum) mode, (GLint)i1, (GLint)i2, (GLint)j1, (GLint)j2 );

#endif

}



JC_VOID_LIB_FUNC(evalPoint1) 

ARGS1(jint i )

{

   glEvalPoint1( (GLint)i );

}



JC_VOID_LIB_FUNC(evalPoint2) 

ARGS2(jint i, jint j)

{

   glEvalPoint2( (GLint)i, (GLint)j );

}



JC_VOID_LIB_FUNC(finish)

ARGS()

{

   glFinish();

}



JC_VOID_LIB_FUNC(flush)

ARGS()

{

   glFlush();

}



JC_VOID_LIB_FUNC(fogf) 

ARGS2(jint pname, jfloat param)

{

#ifdef DEBUG

   switch( pname ) 

      {

      case GL_FOG_MODE:

	 glFogf( GL_FOG_MODE, (GLfloat) param );

	 break;

      case GL_FOG_DENSITY:

	 glFogf( GL_FOG_DENSITY, (GLfloat) param );

	 break;

      case GL_FOG_START:

	 glFogf( GL_FOG_START, (GLfloat) param );

	 break;

      case GL_FOG_END:

	 glFogf( GL_FOG_END, (GLfloat) param );

	 break;

      case GL_FOG_INDEX:

	 glFogf( GL_FOG_INDEX, (GLfloat) param );

	 break; 

      }

#else

   glFogf( (GLenum) pname, (GLfloat) param );

#endif

}



JC_VOID_LIB_FUNC(fogi) 

ARGS2(jint pname, jint param)

{

#ifdef DEBUG

   switch( pname ) 

      {

      case GL_FOG_MODE:

	 glFogi( GL_FOG_MODE, (GLint)param );

	 break;

      case GL_FOG_DENSITY:

	 glFogi( GL_FOG_DENSITY, (GLint)param );

	 break;

      case GL_FOG_START:

	 glFogi( GL_FOG_START, (GLint)param );

	 break;

      case GL_FOG_END:

	 glFogi( GL_FOG_END, (GLint)param );

	 break;

      case GL_FOG_INDEX:

	 glFogi( GL_FOG_INDEX, (GLint)param );

	 break;

      }

#else

   glFogi( (GLenum) pname,  (GLint)param );

#endif

}



JC_VOID_LIB_FUNC(fogfv) 

ARGS5(jint pname, jfloat r, jfloat g, jfloat b, jfloat a )

{

   GLfloat rgba[4];

   

   rgba[0] = (GLfloat) r; rgba[1] = (GLfloat) g; 

   rgba[2] = (GLfloat) b; rgba[3] = (GLfloat) a;

   glFogfv( (GLenum) pname, rgba );

}



JC_VOID_LIB_FUNC(fogiv) 

ARGS5(jint pname, jint r, jint g, jint b, jint a )

{

   GLint rgba[4];



   rgba[0] = (GLint)r; rgba[1] = (GLint)g; rgba[2] = (GLint)b;

   rgba[3] = (GLint)a;

   glFogiv((GLenum) pname, rgba );

}



JC_VOID_LIB_FUNC(frontFace) 

ARGS1(jint mode)

{

#ifdef DEBUG

   switch( mode ) 

      {

      case GL_CW:

	 glFrontFace( GL_CW );

	 break;

      case GL_CCW:

	 glFrontFace( GL_CCW );

	 break;

      default:

	 BAD_VALUE("Bad mode passed to frontFace().");

   }

#else

   glFrontFace( (GLenum) mode);

#endif

}



JC_VOID_LIB_FUNC(frustum) 

ARGS6(jdouble left, jdouble right, jdouble bottom, jdouble top, jdouble nearVar, jdouble farVar)

{

   glFrustum( (GLdouble) left, (GLdouble) right, (GLdouble) bottom, 

	      (GLdouble) top, (GLdouble) nearVar, (GLdouble) farVar );

}



JC_INT_LIB_FUNC(genTextures)

ARGS1(jint size)

{

#ifdef HAVE_SGI_EXTENSIONS

   GLuint tex_name;



   glGenTexturesEXT(1, &tex_name);

   

   return (jint) tex_name;

#else

   return 0;

#endif

}



static int GetNumElements(GLenum pname)

{

   switch( pname ) 

      {

      case GL_ACCUM_ALPHA_BITS:

      case GL_ACCUM_BLUE_BITS:

      case GL_ACCUM_GREEN_BITS:

      case GL_ACCUM_RED_BITS:

      case GL_ALPHA_BIAS:

      case GL_ALPHA_BITS:

      case GL_ALPHA_SCALE:

      case GL_ALPHA_TEST:

      case GL_ALPHA_TEST_FUNC:

      case GL_ALPHA_TEST_REF:

      case GL_AUTO_NORMAL:

      case GL_AUX_BUFFERS:

      case GL_BLEND:

      case GL_BLEND_DST:

      case GL_BLEND_SRC:

      case GL_BLUE_BIAS:

      case GL_BLUE_BITS:

      case GL_BLUE_SCALE:

      case GL_CLIP_PLANE0:

      case GL_CLIP_PLANE1:

      case GL_CLIP_PLANE2:

      case GL_CLIP_PLANE3:

      case GL_CLIP_PLANE4:

      case GL_CLIP_PLANE5:

      case GL_COLOR_MATERIAL:

      case GL_COLOR_MATERIAL_PARAMETER:

      case GL_CULL_FACE:

      case GL_CULL_FACE_MODE:

      case GL_CURRENT_INDEX:

      case GL_CURRENT_RASTER_DISTANCE:

      case GL_CURRENT_RASTER_INDEX:

      case GL_CURRENT_RASTER_POSITION_VALID:

      case GL_DEPTH_BIAS:

      case GL_DEPTH_CLEAR_VALUE:

      case GL_DEPTH_FUNC:

      case GL_DEPTH_RANGE:

      case GL_DEPTH_SCALE:

      case GL_DEPTH_TEST:

      case GL_DEPTH_WRITEMASK:

      case GL_DITHER:

      case GL_DOUBLEBUFFER:

      case GL_DRAW_BUFFER:

      case GL_EDGE_FLAG:

      case GL_FOG:

      case GL_FOG_DENSITY:

      case GL_FOG_END:

      case GL_FOG_HINT:

      case GL_FOG_INDEX:

      case GL_FOG_MODE:

      case GL_FOG_START:

      case GL_FRONT_FACE:

      case GL_GREEN_BIAS:

      case GL_GREEN_BITS:

      case GL_GREEN_SCALE:

      case GL_INDEX_BITS:

      case GL_INDEX_CLEAR_VALUE:

      case GL_INDEX_MODE:

      case GL_INDEX_OFFSET:

      case GL_INDEX_SHIFT:

      case GL_INDEX_WRITEMASK:

      case GL_LIGHT0:

      case GL_LIGHT1:

      case GL_LIGHT2:

      case GL_LIGHT3:

      case GL_LIGHT4:

      case GL_LIGHT5:

      case GL_LIGHT6:

      case GL_LIGHT7:

      case GL_LIGHTING:

      case GL_LIGHT_MODEL_LOCAL_VIEWER:

      case GL_LIGHT_MODEL_TWO_SIDE:

      case GL_LINE_SMOOTH:

      case GL_LINE_SMOOTH_HINT:

      case GL_LINE_STIPPLE:

      case GL_LINE_STIPPLE_PATTERN:

      case GL_LINE_STIPPLE_REPEAT:

      case GL_LINE_WIDTH:

      case GL_LINE_WIDTH_GRANULARITY:

      case GL_LINE_WIDTH_RANGE:

      case GL_LIST_BASE:

      case GL_LIST_INDEX:

      case GL_LIST_MODE:

      case GL_LOGIC_OP:

      case GL_LOGIC_OP_MODE:

      case GL_MAP1_COLOR_4:

      case GL_MAP1_GRID_SEGMENTS:

      case GL_MAP1_INDEX:

      case GL_MAP1_NORMAL:

      case GL_MAP1_TEXTURE_COORD_1:

      case GL_MAP1_TEXTURE_COORD_2:

      case GL_MAP1_TEXTURE_COORD_3:

      case GL_MAP1_TEXTURE_COORD_4:

      case GL_MAP1_VERTEX_3:

      case GL_MAP1_VERTEX_4:

      case GL_MAP2_COLOR_4:

      case GL_MAP2_INDEX:

      case GL_MAP2_NORMAL:

      case GL_MAP2_TEXTURE_COORD_1:

      case GL_MAP2_TEXTURE_COORD_2:

      case GL_MAP2_TEXTURE_COORD_3:

      case GL_MAP2_TEXTURE_COORD_4:

      case GL_MAP2_VERTEX_3:

      case GL_MAP2_VERTEX_4:

      case GL_MAP_COLOR:

      case GL_MAP_STENCIL:

      case GL_MATRIX_MODE:

      case GL_MAX_ATTRIB_STACK_DEPTH:

      case GL_MAX_CLIP_PLANES:

      case GL_MAX_EVAL_ORDER:

      case GL_MAX_LIGHTS:

      case GL_MAX_LIST_NESTING:

      case GL_MAX_MODELVIEW_STACK_DEPTH:

      case GL_MAX_NAME_STACK_DEPTH:

      case GL_MAX_PIXEL_MAP_TABLE:

      case GL_MAX_PROJECTION_STACK_DEPTH:

      case GL_MAX_TEXTURE_SIZE:

      case GL_MAX_TEXTURE_STACK_DEPTH:

      case GL_MODELVIEW_STACK_DEPTH:

      case GL_NAME_STACK_DEPTH:

      case GL_NORMALIZE:

      case GL_PACK_ALIGNMENT:

      case GL_PACK_LSB_FIRST:

      case GL_PACK_ROW_LENGTH:

      case GL_PACK_SKIP_PIXELS:

      case GL_PACK_SKIP_ROWS:

      case GL_PACK_SWAP_BYTES:

      case GL_PERSPECTIVE_CORRECTION_HINT:

      case GL_PIXEL_MAP_A_TO_A_SIZE:

      case GL_PIXEL_MAP_B_TO_B_SIZE:

      case GL_PIXEL_MAP_G_TO_G_SIZE:

      case GL_PIXEL_MAP_I_TO_A_SIZE:

      case GL_PIXEL_MAP_I_TO_B_SIZE:

      case GL_PIXEL_MAP_I_TO_G_SIZE:

      case GL_PIXEL_MAP_I_TO_I_SIZE:

      case GL_PIXEL_MAP_I_TO_R_SIZE:

      case GL_PIXEL_MAP_R_TO_R_SIZE:

      case GL_PIXEL_MAP_S_TO_S_SIZE:

      case GL_POINT_SIZE:

      case GL_POINT_SIZE_GRANULARITY:

      case GL_POINT_SMOOTH:

      case GL_POINT_SMOOTH_HINT:

      case GL_POLYGON_SMOOTH:

      case GL_POLYGON_SMOOTH_HINT:

      case GL_POLYGON_STIPPLE:

      case GL_PROJECTION_STACK_DEPTH:

      case GL_READ_BUFFER:

      case GL_RED_BIAS:

      case GL_RED_BITS:

      case GL_RED_SCALE:

      case GL_RENDER_MODE:

      case GL_RGBA_MODE:

      case GL_SCISSOR_TEST:

      case GL_SHADE_MODEL:

      case GL_STENCIL_BITS:

      case GL_STENCIL_CLEAR_VALUE:

      case GL_STENCIL_FAIL:

      case GL_STENCIL_FUNC:

      case GL_STENCIL_PASS_DEPTH_FAIL:

      case GL_STENCIL_PASS_DEPTH_PASS:

      case GL_STENCIL_REF:

      case GL_STENCIL_TEST:

      case GL_STENCIL_VALUE_MASK:

      case GL_STENCIL_WRITEMASK:

      case GL_STEREO:

      case GL_SUBPIXEL_BITS:

      case GL_TEXTURE_1D:

      case GL_TEXTURE_2D:

      case GL_TEXTURE_ENV_MODE:

      case GL_TEXTURE_GEN_S:

      case GL_TEXTURE_GEN_T:

      case GL_TEXTURE_GEN_R:

      case GL_TEXTURE_GEN_Q:

      case GL_TEXTURE_STACK_DEPTH:

      case GL_UNPACK_ALIGNMENT:

      case GL_UNPACK_LSB_FIRST:

      case GL_UNPACK_ROW_LENGTH:

      case GL_UNPACK_SKIP_PIXELS:

      case GL_UNPACK_SKIP_ROWS:

      case GL_UNPACK_SWAP_BYTES:

      case GL_ZOOM_X:

      case GL_ZOOM_Y:

	 return 1;

	 break;

      

      case GL_ACCUM_CLEAR_VALUE:

      case GL_COLOR_CLEAR_VALUE:

      case GL_COLOR_WRITEMASK:

      case GL_CURRENT_COLOR:

      case GL_CURRENT_RASTER_COLOR:

      case GL_CURRENT_RASTER_POSITION:

      case GL_CURRENT_RASTER_TEXTURE_COORDS:

      case GL_CURRENT_TEXTURE_COORDS:

      case GL_FOG_COLOR:

      case GL_LIGHT_MODEL_AMBIENT:

      case GL_MAP2_GRID_DOMAIN:

      case GL_SCISSOR_BOX:

      case GL_TEXTURE_ENV_COLOR:

      case GL_VIEWPORT:

	 return 4;

	 break;

   

      case GL_CURRENT_NORMAL:

	 return 3;

	 break;

      

      case GL_MAP1_GRID_DOMAIN:

      case GL_MAP2_GRID_SEGMENTS:

      case GL_MAX_VIEWPORT_DIMS:

      case GL_POINT_SIZE_RANGE:

      case GL_POLYGON_MODE:

	 return 2;

	 break;



	

      case GL_MODELVIEW_MATRIX:

      case GL_PROJECTION_MATRIX:

      case GL_TEXTURE_MATRIX:

	 return 16;

	 break;

   

      default:

	 return -1;

      }

}



JC_VOID_LIB_FUNC(getb) 

ARGS2(jint pname, jbooleanArray parameters)

{

   GLboolean params[16] = { 0, 0, 0, 0,

			    0, 0, 0, 0,

			    0, 0, 0, 0,

			    0, 0, 0, 0 };



   jboolean isCopy;

   jboolean *ptr = GET_BOOL_ARRAY(parameters, &isCopy);

   GLboolean *use_this = (GLboolean*) ptr;

   int i, num_elems = GetNumElements((GLenum) pname);



   if(isCopy)

      use_this = params;



   if(num_elems == -1)

      {

	 BAD_VALUE("Bad parameter name passed to getb()");

      }

   else

      {

	 glGetBooleanv( (GLenum) pname, use_this);



	 if(isCopy)

	    for(i=0;i<num_elems;i++, ptr++)

	       *ptr = (jboolean) params[i];

      }



   /* this won't have any effect if we aren't a copy */

   if(isCopy)

      FREE_BOOL_ARRAY(parameters, params, JNI_COMMIT);

}



JC_VOID_LIB_FUNC(getd) 

ARGS2(jint pname, jdoubleArray parameters)

{



   GLdouble params[16] = { 0.0, 0.0, 0.0, 0.0,

			   0.0, 0.0, 0.0, 0.0,

			   0.0, 0.0, 0.0, 0.0,

			   0.0, 0.0, 0.0, 0.0 };

   jboolean isCopy;

   jdouble *ptr = GET_DOUBLE_ARRAY(parameters, &isCopy);

   GLdouble *use_this = (GLdouble *) ptr;

   int i, num_elems = GetNumElements((GLenum) pname);



   if(isCopy)

      use_this = params;



   if(num_elems == -1)

      {

	 BAD_VALUE("Bad parameter name passed to getd()");

      }

   else

      {

	 glGetDoublev( (GLenum) pname, use_this);



	 if(isCopy)

	    for(i=0;i<num_elems;i++, ptr++)

	       *ptr = (jdouble) params[i];

      }



   /* this won't have any effect if we aren't a copy */

   if(isCopy)

      FREE_DOUBLE_ARRAY(parameters, params, JNI_COMMIT);



}



JC_VOID_LIB_FUNC(geti) 

ARGS2(jint pname, jintArray parameters)

{



   GLint params[16] = { 0, 0, 0, 0,

			0, 0, 0, 0,

			0, 0, 0, 0,

			0, 0, 0, 0 };



   jboolean isCopy;

   jint *ptr = GET_INT_ARRAY(parameters, &isCopy);

   GLint *use_this = (GLint *) ptr;

   int i, num_elems = GetNumElements((GLenum) pname);

   

   if(isCopy)

      use_this = params;



   if(num_elems == -1)

      {

	 BAD_VALUE("Bad parameter name passed to geti()");

      }

   else

      {

	 glGetIntegerv( (GLenum) pname, use_this);



	 if(isCopy)

	    for(i=0;i<num_elems;i++, ptr++)

	       *ptr = (jint) params[i];

      }



   /* this won't have any effect if we aren't a copy */

   if(isCopy)

      FREE_INT_ARRAY(parameters, ptr, JNI_COMMIT);

}



JC_VOID_LIB_FUNC(getf) 

ARGS2(jint pname, jfloatArray parameters)

{

   GLfloat params[16] = { 0.0, 0.0, 0.0, 0.0,

			  0.0, 0.0, 0.0, 0.0,

			  0.0, 0.0, 0.0, 0.0,

			  0.0, 0.0, 0.0, 0.0 };



   jboolean isCopy;

   jfloat *ptr = GET_FLOAT_ARRAY(parameters, &isCopy);

   GLfloat *use_this = (GLfloat *) ptr;

   int i, num_elems = GetNumElements((GLenum) pname);



   if(isCopy)

      use_this = params;



   if(num_elems == -1)

      {

	 BAD_VALUE("Bad parameter name passed to geti()");

      }

   else

      {

	 glGetFloatv( (GLenum) pname, use_this);

	 

	 if(isCopy)

	    for(i=0;i<num_elems;i++, ptr++)

	       *ptr = (jfloat) params[i];

      }



   /* this won't have any effect if we aren't a copy */

   if(isCopy)

      FREE_FLOAT_ARRAY(parameters, params, JNI_COMMIT);   

}

/* TO DO: glGenLists */

JC_INT_LIB_FUNC(genLists) 

ARGS1(jint range)

{

   GLuint retval;

   retval = glGenLists( range );

   return (jint) retval;

}



JC_VOID_LIB_FUNC(hint) 

ARGS2(jint target, jint mode)

{

#ifdef DEBUG

   GLenum gl_target, gl_mode;

	

   switch( target ) 

      {

      case GL_FOG_HINT:

	 gl_target = GL_FOG_HINT;

	 break;

      case GL_LINE_SMOOTH_HINT:

	 gl_target = GL_LINE_SMOOTH_HINT;

	 break;

      case GL_PERSPECTIVE_CORRECTION_HINT:

	 gl_target = GL_PERSPECTIVE_CORRECTION_HINT;

	 break;

      case GL_POINT_SMOOTH_HINT:

	 gl_target = GL_POINT_SMOOTH_HINT;

	 break;

      case GL_POLYGON_SMOOTH_HINT:

	 gl_target = GL_POLYGON_SMOOTH_HINT;

	 break;

      default:

	 return;

      }

   

   switch( mode ) 

      {

      case GL_FASTEST:

	 gl_mode = GL_FASTEST;

	 break;

      case GL_NICEST:

	 gl_mode = GL_NICEST;

	 break;

      case GL_DONT_CARE:

	 gl_mode = GL_DONT_CARE;

	 break;

      default:

	 return;

      }

   glHint( gl_target, gl_mode );

#else

   glHint( (GLenum) target, (GLenum) mode);

#endif

}



JC_VOID_LIB_FUNC(indexd) 

ARGS1(jdouble c)

{

   glIndexd( (GLdouble) c );

}



JC_VOID_LIB_FUNC(indexf) 

ARGS1(jfloat c)

{

   glIndexf( (GLfloat) c );

}



JC_VOID_LIB_FUNC(indexi) 

ARGS1(jint c)

{

   glIndexi( (GLint) c );

}



JC_VOID_LIB_FUNC(indexs) 

ARGS1(jshort c)

{

   glIndexs( (GLshort) c );

}



JC_VOID_LIB_FUNC(indexMask) 

ARGS1(jint mask)

{

   glIndexMask( (GLuint)mask );

}



/* TO DO: glInitNames */



JC_BOOL_LIB_FUNC(isEnabled) 

ARGS1(jint cap)

{

   GLboolean retval;

#ifdef DEBUG

   switch( cap ) 

      {

      case GL_ALPHA_TEST:

	 retval = glIsEnabled( GL_ALPHA_TEST );

	 break;

      case GL_AUTO_NORMAL:

	 retval = glIsEnabled( GL_AUTO_NORMAL );

	 break;

      case GL_BLEND:

	 retval = glIsEnabled( GL_BLEND );

	 break;

      case GL_CLIP_PLANE0:

	 retval = glIsEnabled( GL_CLIP_PLANE0 );

	 break;

      case GL_CLIP_PLANE1:

	 retval = glIsEnabled( GL_CLIP_PLANE1 );

	 break;

      case GL_CLIP_PLANE2:

	 retval = glIsEnabled( GL_CLIP_PLANE2 );

	 break;

      case GL_CLIP_PLANE3:

	 retval = glIsEnabled( GL_CLIP_PLANE3 );

	 break;

      case GL_CLIP_PLANE4:

	 retval = glIsEnabled( GL_CLIP_PLANE4 );

	 break;

      case GL_CLIP_PLANE5:

	 retval = glIsEnabled( GL_CLIP_PLANE5 );

	 break;

      case GL_COLOR_MATERIAL:

	 retval = glIsEnabled( GL_COLOR_MATERIAL );

	 break;

      case GL_CULL_FACE:

	 retval = glIsEnabled( GL_CULL_FACE );

	 break;

      case GL_DEPTH_TEST:

	 retval = glIsEnabled( GL_DEPTH_TEST );

	 break;

      case GL_DITHER:

	 retval = glIsEnabled( GL_DITHER );

	 break;

      case GL_FOG:

	 retval = glIsEnabled( GL_FOG );

	 break;

      case GL_FOG_OFFSET_SGIX:

	 retval = glIsEnabled( GL_FOG_OFFSET_SGIX );

	 break;

		

      case GL_LIGHT0:

	 retval = glIsEnabled( GL_LIGHT0 );

	 break;

      case GL_LIGHT1:

	 retval = glIsEnabled( GL_LIGHT1 );

	 break;

      case GL_LIGHT2:

	 retval = glIsEnabled( GL_LIGHT2 );

	 break;

      case GL_LIGHT3:

	 retval = glIsEnabled( GL_LIGHT3 );

	 break;

      case GL_LIGHT4:

	 retval = glIsEnabled( GL_LIGHT4 );

	 break;

      case GL_LIGHT5:

	 retval = glIsEnabled( GL_LIGHT5 );

	 break;

      case GL_LIGHT6:

	 retval = glIsEnabled( GL_LIGHT6 );

	 break;

      case GL_LIGHT7:

	 retval = glIsEnabled( GL_LIGHT7 );

	 break;

      case GL_LIGHTING:

	 retval = glIsEnabled( GL_LIGHTING );

	 break;

      case GL_LINE_SMOOTH:

	 retval = glIsEnabled( GL_LINE_SMOOTH );

	 break;

      case GL_LINE_STIPPLE:

	 retval = glIsEnabled( GL_LINE_STIPPLE );

	 break;

      case GL_LOGIC_OP:

	 retval = glIsEnabled( GL_LOGIC_OP );

	 break;

      case GL_MAP1_COLOR_4:

	 retval = glIsEnabled( GL_MAP1_COLOR_4 );

	 break;

      case GL_MAP1_INDEX:

	 retval = glIsEnabled( GL_MAP1_INDEX );

	 break;

      case GL_MAP1_NORMAL:

	 retval = glIsEnabled( GL_MAP1_NORMAL);

	 break;

      case GL_MAP1_TEXTURE_COORD_1:

	 retval = glIsEnabled( GL_MAP1_TEXTURE_COORD_1);

	 break;

      case GL_MAP1_TEXTURE_COORD_2:

	 retval = glIsEnabled( GL_MAP1_TEXTURE_COORD_2);

	 break;

      case GL_MAP1_TEXTURE_COORD_3:

	 retval = glIsEnabled( GL_MAP1_TEXTURE_COORD_3);

	 break;

      case GL_MAP1_TEXTURE_COORD_4:

	 retval = glIsEnabled( GL_MAP1_TEXTURE_COORD_4);

	 break;

      case GL_MAP1_VERTEX_3:

	 retval = glIsEnabled( GL_MAP1_VERTEX_3);

	 break;

      case GL_MAP1_VERTEX_4:

	 retval = glIsEnabled( GL_MAP1_VERTEX_4);

	 break;

      case GL_MAP2_COLOR_4:

	 retval = glIsEnabled( GL_MAP2_COLOR_4);

	 break;

      case GL_MAP2_INDEX:

	 retval = glIsEnabled( GL_MAP2_INDEX);

	 break;

      case GL_MAP2_NORMAL:

	 retval = glIsEnabled( GL_MAP2_NORMAL);

	 break;

      case GL_MAP2_TEXTURE_COORD_1:

	 retval = glIsEnabled( GL_MAP2_TEXTURE_COORD_1);

	 break;

      case GL_MAP2_TEXTURE_COORD_2:

	 retval = glIsEnabled( GL_MAP2_TEXTURE_COORD_2);

	 break;

      case GL_MAP2_TEXTURE_COORD_3:

	 retval = glIsEnabled( GL_MAP2_TEXTURE_COORD_3);

	 break;

      case GL_MAP2_TEXTURE_COORD_4:

	 retval = glIsEnabled( GL_MAP2_TEXTURE_COORD_4);

	 break;

      case GL_MAP2_VERTEX_3:

	 retval = glIsEnabled( GL_MAP2_VERTEX_3);

	 break;

      case GL_MAP2_VERTEX_4:

	 retval = glIsEnabled( GL_MAP2_VERTEX_4);

	 break;

      case GL_NORMALIZE:

	 retval = glIsEnabled( GL_NORMALIZE);

	 break;

      case GL_POINT_SMOOTH:

	 retval = glIsEnabled( GL_POINT_SMOOTH);

	 break;

      case GL_POLYGON_SMOOTH:

	 retval = glIsEnabled( GL_POLYGON_SMOOTH);

	 break;

      case GL_POLYGON_STIPPLE:

	 retval = glIsEnabled( GL_POLYGON_STIPPLE);

	 break;

      case GL_SCISSOR_TEST:

	 retval = glIsEnabled( GL_SCISSOR_TEST);

	 break;

      case GL_STENCIL_TEST:

	 retval = glIsEnabled( GL_STENCIL_TEST);

	 break;

      case GL_TEXTURE_1D:

	 retval = glIsEnabled( GL_TEXTURE_1D);

	 break;

      case GL_TEXTURE_2D:

	 retval = glIsEnabled( GL_TEXTURE_2D);

	 break;

      case GL_TEXTURE_GEN_Q:

	 retval = glIsEnabled( GL_TEXTURE_GEN_Q);

	 break;

      case GL_TEXTURE_GEN_R:

	 retval = glIsEnabled( GL_TEXTURE_GEN_R);

	 break;

      case GL_TEXTURE_GEN_S:

	 retval = glIsEnabled( GL_TEXTURE_GEN_S);

	 break;

      case GL_TEXTURE_GEN_T:

	 retval = glIsEnabled( GL_TEXTURE_GEN_T);

	 break;

      default:

	 BAD_VALUE("Bad capability passed to isEnabled().");

	 return;

	}

#else

   retval = glIsEnabled( (GLenum) cap );

#endif

   

   if( retval == GL_TRUE )

      return 1;

   

   return 0;

}



/* TO DO: glIsList */

JC_BOOL_LIB_FUNC(isList) 

ARGS1(jint val)

{

   jboolean retval;

   retval = glIsList( val );

   return retval;

}


JC_VOID_LIB_FUNC(lightf) 

ARGS3(jint light, jint pname, jfloat param)

{

#ifdef DEBUG

   GLenum gl_lightnum, gl_pname;



   switch( light ) 

      {

      case GL_LIGHT0:

	 gl_lightnum = GL_LIGHT0;

	 break;

      case GL_LIGHT1:

	 gl_lightnum = GL_LIGHT1;

	 break;

      case GL_LIGHT2:

	 gl_lightnum = GL_LIGHT2;

	 break;

      case GL_LIGHT3:

	 gl_lightnum = GL_LIGHT3;

	 break;

      case GL_LIGHT4:

	 gl_lightnum = GL_LIGHT4;

	 break;

      case GL_LIGHT5:

	 gl_lightnum = GL_LIGHT5;

	 break;

      case GL_LIGHT6:

	 gl_lightnum = GL_LIGHT6;

	 break;

      case GL_LIGHT7:

	 gl_lightnum = GL_LIGHT7;

	 break;

      default:

	 return;

      }

   switch( pname ) {

   case GL_SPOT_EXPONENT:

      gl_pname = GL_SPOT_EXPONENT;

      break;

   case GL_SPOT_CUTOFF:

      gl_pname = GL_SPOT_CUTOFF;

      break;

   case GL_CONSTANT_ATTENUATION:

      gl_pname = GL_CONSTANT_ATTENUATION;

      break;

   case GL_LINEAR_ATTENUATION:

      gl_pname = GL_LINEAR_ATTENUATION;

      break;

   case GL_QUADRATIC_ATTENUATION:

      gl_pname = GL_QUADRATIC_ATTENUATION;

      break;

   default:

      return;

   }

   glLightf( gl_lightnum, gl_pname, param );

#else

   glLightf( (GLenum) light, (GLenum) pname, (GLfloat) param );

#endif



}



JC_VOID_LIB_FUNC(lighti) 

ARGS3(jint light, jint pname, jint param)

{

#ifdef DEBUG

   GLenum gl_lightnum, gl_pname;

   

   switch( light ) 

      {

      case GL_LIGHT0:

	 gl_lightnum = GL_LIGHT0;

	 break;

      case GL_LIGHT1:

	 gl_lightnum = GL_LIGHT1;

	 break;

      case GL_LIGHT2:

	 gl_lightnum = GL_LIGHT2;

	 break;

      case GL_LIGHT3:

	 gl_lightnum = GL_LIGHT3;

	 break;

      case GL_LIGHT4:

	 gl_lightnum = GL_LIGHT4;

	 break;

      case GL_LIGHT5:

	 gl_lightnum = GL_LIGHT5;

	 break;

      case GL_LIGHT6:

	 gl_lightnum = GL_LIGHT6;

	 break;

      case GL_LIGHT7:

	 gl_lightnum = GL_LIGHT7;

	 break;

      default:

	 BAD_VALUE("Bad light passed to lighti");

	 return;

      }

   

   switch( pname ) 

      {

      case GL_SPOT_EXPONENT:

	 gl_pname = GL_SPOT_EXPONENT;

	 break;

      case GL_SPOT_CUTOFF:

	 gl_pname = GL_SPOT_CUTOFF;

	 break;

      case GL_CONSTANT_ATTENUATION:

	 gl_pname = GL_CONSTANT_ATTENUATION;

	 break;

      case GL_LINEAR_ATTENUATION:

	 gl_pname = GL_LINEAR_ATTENUATION;

	 break;

      case GL_QUADRATIC_ATTENUATION:

	 gl_pname = GL_QUADRATIC_ATTENUATION;

	 break;

      default:

	 return;

      }

   glLightf( gl_lightnum, gl_pname, (GLint)param );

#else

   glLightf( (GLenum) light, (GLenum) pname, (GLint) param );

#endif

}





JC_VOID_LIB_FUNC(lightiv) 

ARGS6(jint light, jint pname,jint param1, jint param2, 

      jint param3, jint param4 )

{

   GLint  gl_params[4];

#ifdef DEBUG   

   GLenum gl_lightnum, gl_pname;

   switch( light ) 

      {

      case GL_LIGHT0:

	 gl_lightnum = GL_LIGHT0;

	 break;

      case GL_LIGHT1:

	 gl_lightnum = GL_LIGHT1;

	 break;

      case GL_LIGHT2:

	 gl_lightnum = GL_LIGHT2;

	 break;

      case GL_LIGHT3:

	 gl_lightnum = GL_LIGHT3;

	 break;

      case GL_LIGHT4:

	 gl_lightnum = GL_LIGHT4;

	 break;

      case GL_LIGHT5:

	 gl_lightnum = GL_LIGHT5;

	 break;

      case GL_LIGHT6:

	 gl_lightnum = GL_LIGHT6;

	 break;

      case GL_LIGHT7:

	 gl_lightnum = GL_LIGHT7;

	 break;

      default:

	 return;

      }



   switch( pname ) 

      {

      case GL_AMBIENT:

	 gl_pname = GL_AMBIENT; break;

      case GL_DIFFUSE:

	 gl_pname = GL_DIFFUSE; break;

      case GL_SPECULAR:

	 gl_pname = GL_SPECULAR; break;

      case GL_POSITION:

	 gl_pname = GL_POSITION; break;

      case GL_SPOT_DIRECTION:

	 gl_pname = GL_SPOT_DIRECTION; break;

      case GL_CONSTANT_ATTENUATION:

	 gl_pname = GL_CONSTANT_ATTENUATION; break;

      case GL_LINEAR_ATTENUATION:

	 gl_pname = GL_LINEAR_ATTENUATION; break;

      case GL_QUADRATIC_ATTENUATION:

	 gl_pname = GL_QUADRATIC_ATTENUATION; break;

      case GL_SPOT_EXPONENT:

	 gl_pname = GL_SPOT_EXPONENT; break;

      case GL_SPOT_CUTOFF:

	 gl_pname = GL_SPOT_CUTOFF; break;

      }

   

   gl_params[0] = (GLint) param1; gl_params[1] = (GLint) param2;

   gl_params[2] = (GLint) param3; gl_params[3] = (GLint) param4;

   glLightiv( gl_lightnum, gl_pname, gl_params );

#else

   gl_params[0] = param1; gl_params[1] = param2;

   gl_params[2] = param3; gl_params[3] = param4;

   glLightiv( (GLenum) light, (GLenum) pname, gl_params);

#endif

}



JC_VOID_LIB_FUNC(lightfv) 

ARGS6(jint light, jint pname,jfloat param1, jfloat param2, 

      jfloat param3, jfloat param4 )

{

   GLfloat  gl_params[4];

#ifdef DEBUG   

   GLenum gl_lightnum, gl_pname;

   switch( light ) 

      {

      case GL_LIGHT0:

	 gl_lightnum = GL_LIGHT0;

	 break;

      case GL_LIGHT1:

	 gl_lightnum = GL_LIGHT1;

	 break;

      case GL_LIGHT2:

	 gl_lightnum = GL_LIGHT2;

	 break;

      case GL_LIGHT3:

	 gl_lightnum = GL_LIGHT3;

	 break;

      case GL_LIGHT4:

	 gl_lightnum = GL_LIGHT4;

	 break;

      case GL_LIGHT5:

	 gl_lightnum = GL_LIGHT5;

	 break;

      case GL_LIGHT6:

	 gl_lightnum = GL_LIGHT6;

	 break;

      case GL_LIGHT7:

	 gl_lightnum = GL_LIGHT7;

	 break;

      default:

	 return;

      }



   switch( pname ) 

      {

      case GL_AMBIENT:

	 gl_pname = GL_AMBIENT; break;

      case GL_DIFFUSE:

	 gl_pname = GL_DIFFUSE; break;

      case GL_SPECULAR:

	 gl_pname = GL_SPECULAR; break;

      case GL_POSITION:

	 gl_pname = GL_POSITION; break;

      case GL_SPOT_DIRECTION:

	 gl_pname = GL_SPOT_DIRECTION; break;

      case GL_CONSTANT_ATTENUATION:

	 gl_pname = GL_CONSTANT_ATTENUATION; break;

      case GL_LINEAR_ATTENUATION:

	 gl_pname = GL_LINEAR_ATTENUATION; break;

      case GL_QUADRATIC_ATTENUATION:

	 gl_pname = GL_QUADRATIC_ATTENUATION; break;

      case GL_SPOT_EXPONENT:

	 gl_pname = GL_SPOT_EXPONENT; break;

      case GL_SPOT_CUTOFF:

	 gl_pname = GL_SPOT_CUTOFF; break;

      }

   

   gl_params[0] = (GLfloat) param1; gl_params[1] = (GLfloat) param2;

   gl_params[2] = (GLfloat) param3; gl_params[3] = (GLfloat) param4;

   glLightfv( gl_lightnum, gl_pname, gl_params );

#else

   gl_params[0] = (GLfloat) param1; gl_params[1] = (GLfloat) param2;

   gl_params[2] = (GLfloat) param3; gl_params[3] = (GLfloat) param4;

   glLightfv( (GLenum) light, (GLenum) pname, gl_params);

#endif

}



JC_VOID_LIB_FUNC(lightModeli) 

ARGS2(jint pname, jint param )

{

   switch( pname ) 

      {

      case GL_LIGHT_MODEL_TWO_SIDE:

	 glLightModelf( GL_LIGHT_MODEL_TWO_SIDE, (GLint) param );

	 break;

      case GL_LIGHT_MODEL_LOCAL_VIEWER:

	 glLightModelf( GL_LIGHT_MODEL_LOCAL_VIEWER, (GLint) param );

	 break;

      }

}



JC_VOID_LIB_FUNC(lightModelf) 

ARGS2(jint pname, jfloat param )

{

   switch( pname ) 

      {

      case GL_LIGHT_MODEL_TWO_SIDE:

	 glLightModelf( GL_LIGHT_MODEL_TWO_SIDE, (GLfloat) param );

	 break;

      case GL_LIGHT_MODEL_LOCAL_VIEWER:

	 glLightModelf( GL_LIGHT_MODEL_LOCAL_VIEWER, (GLfloat) param );

	 break;

      }

}



JC_VOID_LIB_FUNC(lightModeliv) 

ARGS2(jint pname, jintArray params )

{

   jboolean isCopy;

   jint *jparams = GET_INT_ARRAY(params, &isCopy);

   

   switch( pname ) 

      {

      case GL_LIGHT_MODEL_AMBIENT:

	 glLightModeliv( GL_LIGHT_MODEL_AMBIENT, (GLint *) jparams );

	 break;

      case GL_LIGHT_MODEL_LOCAL_VIEWER:

	 glLightModeliv( GL_LIGHT_MODEL_LOCAL_VIEWER, (GLint *) jparams );

	 break;

      case GL_LIGHT_MODEL_TWO_SIDE:

	 glLightModeliv( GL_LIGHT_MODEL_TWO_SIDE, (GLint *) jparams);

	 break;

      default:

	 return;

      }



   if(isCopy)

      FREE_INT_ARRAY(params, jparams, JNI_ABORT);

}



JC_VOID_LIB_FUNC(lightModelfv) 

ARGS2(jint pname, jfloatArray params )

{

   jboolean isCopy;

   jfloat *jparams = GET_FLOAT_ARRAY(params, &isCopy);



   switch( pname ) 

      {

      case GL_LIGHT_MODEL_AMBIENT:

	 glLightModelfv( GL_LIGHT_MODEL_AMBIENT, jparams );

	 break;

      case GL_LIGHT_MODEL_LOCAL_VIEWER:

	 glLightModelfv( GL_LIGHT_MODEL_LOCAL_VIEWER, jparams );

	 break;

      case GL_LIGHT_MODEL_TWO_SIDE:

	 glLightModelfv( GL_LIGHT_MODEL_TWO_SIDE, jparams );

	 break;

      default:

	 return;

      }



   if(isCopy)

      FREE_FLOAT_ARRAY(params, jparams, JNI_ABORT);

}



JC_VOID_LIB_FUNC(lineStipple) 

ARGS2(jint factor,jshort pattern)

{

   glLineStipple( (GLint) factor, pattern );

}



JC_VOID_LIB_FUNC(lineWidth) 

ARGS1(jfloat width)

{

   glLineWidth( width );

}



JC_VOID_LIB_FUNC(loadIdentity) 

ARGS()

{

   glLoadIdentity();

}



JC_VOID_LIB_FUNC(loadMatrixd) 

ARGS1(jdoubleArray jdblarr )

{

   jboolean isCopy;

   jdouble *ptr = GET_DOUBLE_ARRAY( jdblarr, &isCopy);

   glLoadMatrixd( ptr );

   if(isCopy)

      FREE_DOUBLE_ARRAY(jdblarr, ptr, JNI_ABORT);

}



JC_VOID_LIB_FUNC(loadMatrixf) 

ARGS1(jfloatArray jflarr )

{

   jboolean isCopy;

   jfloat *ptr = GET_FLOAT_ARRAY(jflarr, &isCopy);

   glLoadMatrixf( ptr );

   if(isCopy)

      FREE_FLOAT_ARRAY(jflarr, ptr, JNI_ABORT);

}



JC_VOID_LIB_FUNC(loadName) 

ARGS1(jint name )

{

   glLoadName( (GLuint) name );

}



JC_VOID_LIB_FUNC(logicOp) 

ARGS1(jint opcode)

{

#ifdef DEBUG	

   switch( opcode ) 

      {

      case GL_CLEAR:

	 glLogicOp( GL_CLEAR );

	 break;

      case GL_SET:

	 glLogicOp( GL_SET );

	 break;

      case GL_COPY:

	 glLogicOp( GL_COPY );

	 break;

      case GL_COPY_INVERTED:

	 glLogicOp( GL_COPY_INVERTED );

	 break;

      case GL_NOOP:

	 glLogicOp( GL_NOOP );

	 break;

      case GL_INVERT:

	 glLogicOp( GL_INVERT );

	 break;

      case GL_AND:

	 glLogicOp( GL_AND );

	 break;

      case GL_NAND:

	 glLogicOp( GL_NAND );

	 break;

      case GL_OR:

	 glLogicOp( GL_OR );

	 break;

      case GL_NOR:

	 glLogicOp( GL_NOR );

	 break;

      case GL_XOR:

	 glLogicOp( GL_XOR );

	 break;

      case GL_EQUIV:

	 glLogicOp( GL_EQUIV );

	 break;

      case GL_AND_REVERSE:

	 glLogicOp( GL_AND_REVERSE );

	 break;

      case GL_AND_INVERTED:

	 glLogicOp( GL_AND_INVERTED );

	 break;

      case GL_OR_REVERSE:

	 glLogicOp( GL_OR_REVERSE );

	 break;

      case GL_OR_INVERTED:

	 glLogicOp( GL_OR_INVERTED );

	 break;

      default:

	 BAD_VALUE("Unknown operation passed to logicOp().");

	 return;

      }

#else

   glLogicOp( (GLenum) opcode ) ;

#endif

}



/* TO DO: glMap1, glMap2 */



JC_VOID_LIB_FUNC(mapGrid1d) 

ARGS3(jint un, jdouble u1, jdouble u2)

{

   glMapGrid1d( (GLint)un, u1, u2 );

}



JC_VOID_LIB_FUNC(mapGrid1f) 

ARGS3(jint un,jfloat u1,jfloat u2)

{

   glMapGrid1f( (GLint)un, u1, u2 );

}

JC_VOID_LIB_FUNC(mapGrid2d) 

ARGS6(jint un,jdouble u1,jdouble u2, jint vn, jdouble v1,jdouble v2)

{

   glMapGrid2d( (GLint)un, u1, u2, (GLint) vn, v1, v2 );

}



JC_VOID_LIB_FUNC(mapGrid2f) 

ARGS6(jint un, jfloat u1, jfloat u2, jint vn, jfloat v1, jfloat v2)

{

   glMapGrid2f( (GLint)un, u1, u2, (GLint) vn, v1, v2 );

}



JC_VOID_LIB_FUNC(materialf) 

ARGS3(jint face, jint pname, jfloat param)

{

   GLenum gl_pname, gl_face;

   switch( pname ) 

      {

      case GL_SHININESS:

	 gl_pname = GL_SHININESS;	

	 break;

      default:

	 return;

      }



   switch( face ) 

      {

      case GL_FRONT:

	 gl_face = GL_FRONT;

	 break;

      case GL_BACK:

	 gl_face = GL_BACK;

	 break;

      case GL_FRONT_AND_BACK:

	 gl_face = GL_FRONT_AND_BACK;

	 break;

      default:

	 return;

      }

   glMaterialf( gl_face, gl_pname, param );

}



JC_VOID_LIB_FUNC(materiali) 

ARGS3(jint face, jint pname, jint param)

{

   GLenum gl_pname, gl_face;

   switch( pname ) 

      {

      case GL_SHININESS:

	 gl_pname = GL_SHININESS;	

	 break;

      default:

	 return;

      }

   switch( face ) 

      {

      case GL_FRONT:

	 gl_face = GL_FRONT;

	 break;

      case GL_BACK:

	 gl_face = GL_BACK;

	 break;

      case GL_FRONT_AND_BACK:

	 gl_face = GL_FRONT_AND_BACK;

	 break;

      default:

	 return;

      }

   glMateriali( gl_face, gl_pname, param );

}



JC_VOID_LIB_FUNC(materialiv) 

ARGS6(jint face, jint pname, jint p1, jint p2, jint p3, jint p4 )

{

   GLint    gl_params[4];

#ifdef DEBUG

   GLenum gl_face;





   gl_params[0] = (int)p1; gl_params[1] = (int)p2;

   gl_params[2] = (int)p3; gl_params[3] = (int)p4;

   

   switch( face ) 

      {

      case GL_FRONT:

	 gl_face = GL_FRONT;

	 break;

      case GL_BACK:

	 gl_face = GL_BACK;

	 break;

      case GL_FRONT_AND_BACK:

	 gl_face = GL_FRONT_AND_BACK;

	 break;

      default:

	 return;

      }

   

   switch( pname ) 

      {

      case GL_AMBIENT:

	 glMaterialiv( gl_face, GL_AMBIENT, gl_params );

	 break;

      case GL_DIFFUSE:

	 glMaterialiv( gl_face, GL_DIFFUSE, gl_params );

	 break;

      case GL_SPECULAR:

	 glMaterialiv( gl_face, GL_SPECULAR, gl_params );

	 break;

      case GL_EMISSION:

	 glMaterialiv( gl_face, GL_EMISSION, gl_params );

	 break;

      case GL_SHININESS:

	 glMaterialiv( gl_face, GL_SHININESS, gl_params );

	 break;

      case GL_AMBIENT_AND_DIFFUSE:

	 glMaterialiv( gl_face, GL_AMBIENT_AND_DIFFUSE, gl_params );

	 break;

      case GL_COLOR_INDEXES:

	 glMaterialiv( gl_face, GL_COLOR_INDEXES, gl_params );

	 break;

      default:

	 return;

      }

#else

   gl_params[0] = (int)p1; gl_params[1] = (int)p2;

   gl_params[2] = (int)p3; gl_params[3] = (int)p4;

   glMaterialiv( (GLenum) face, (GLenum) pname, gl_params);

#endif

}



JC_VOID_LIB_FUNC(materialfv) 

ARGS6(jint face, jint pname, jfloat p1, jfloat p2, jfloat p3, jfloat p4 )

{

	jfloat  gl_params[4];

#ifdef DEBUG

	GLenum gl_face;



	gl_params[0] = p1; gl_params[1] = p2;

	gl_params[2] = p3; gl_params[3] = p4;



	switch( face ) 

	   {

	   case GL_FRONT:

	      gl_face = GL_FRONT;

	      break;

	   case GL_BACK:

		gl_face = GL_BACK;

		break;

	   case GL_FRONT_AND_BACK:

	      gl_face = GL_FRONT_AND_BACK;

	      break;

	   default:

	      return;

	   }



	switch( pname ) 

	   {

	   case GL_AMBIENT:

	      glMaterialfv( gl_face, GL_AMBIENT, gl_params );

	      break;

	   case GL_DIFFUSE:

	      glMaterialfv( gl_face, GL_DIFFUSE, gl_params );

	      break;

	   case GL_SPECULAR:

	      glMaterialfv( gl_face, GL_SPECULAR, gl_params );

	      break;

	   case GL_EMISSION:

	      glMaterialfv( gl_face, GL_EMISSION, gl_params );

	      break;

	   case GL_SHININESS:

	      glMaterialfv( gl_face, GL_SHININESS, gl_params );

	      break;

	   case GL_AMBIENT_AND_DIFFUSE:

	      glMaterialfv( gl_face, GL_AMBIENT_AND_DIFFUSE, gl_params );

	      break;

	   case GL_COLOR_INDEXES:

	      glMaterialfv( gl_face, GL_COLOR_INDEXES, gl_params );

	      break;

	   default:

	      return;

	   }

#else

	gl_params[0] = p1; gl_params[1] = p2;

	gl_params[2] = p3; gl_params[3] = p4;

	glMaterialfv( (GLenum) face, (GLenum) pname, gl_params);

#endif

}



JC_VOID_LIB_FUNC(matrixMode) 

ARGS1(jint mode )

{

   switch( mode ) 

      {

      case GL_MODELVIEW:

	 glMatrixMode( GL_MODELVIEW );

	 break;

      case GL_PROJECTION:

	 glMatrixMode( GL_PROJECTION );

	 break;

      case GL_TEXTURE:

	 glMatrixMode( GL_TEXTURE );

	 break;

      default:

	 return;

      }

}



JC_VOID_LIB_FUNC(multMatrixd)

ARGS1(jdoubleArray jdblarr)

{

   jboolean isCopy;

   jdouble *ptr = GET_DOUBLE_ARRAY( jdblarr, &isCopy);

   glMultMatrixd( (GLdouble *) ptr );

   if(isCopy)

      FREE_DOUBLE_ARRAY(jdblarr, ptr, JNI_ABORT);

}



JC_VOID_LIB_FUNC(multMatrixf) 

ARGS1(jfloatArray jflarr )

{

   jboolean isCopy;

   jfloat *ptr = GET_FLOAT_ARRAY(jflarr, &isCopy);

   glMultMatrixf( (GLfloat *)ptr );

   if(isCopy)

      FREE_FLOAT_ARRAY(jflarr, ptr, JNI_ABORT);

}

JC_VOID_LIB_FUNC(newList) 

ARGS2(jint list,  jint mode )

{

   switch( mode )

      {

      case GL_COMPILE:

	 glNewList( list, GL_COMPILE );

	 break;

      case GL_COMPILE_AND_EXECUTE:

	 glNewList( list, GL_COMPILE_AND_EXECUTE );

	 break;

      }

}



JC_VOID_LIB_FUNC(callList) 

ARGS1(jint list)

{

   glCallList( list );

}

JC_VOID_LIB_FUNC(deleteLists) 
ARGS2 (jint	list, jint range)
{

   glDeleteLists( list, range );

}

JC_VOID_LIB_FUNC(endList) 

ARGS()

{

   glEndList();

}





JC_VOID_LIB_FUNC(normal3b) 

ARGS3(jbyte x, jbyte y, jbyte z)

{

   glNormal3b( (GLbyte)x, (GLbyte)y, (GLbyte)z );

}



JC_VOID_LIB_FUNC(normal3d) 

ARGS3(jdouble x, jdouble y, jdouble z)

{

   glNormal3d( x, y ,z );

}



JC_VOID_LIB_FUNC(normal3f) 

ARGS3(jfloat x, jfloat y, jfloat z)

{

   glNormal3f( x, y, z );

}



JC_VOID_LIB_FUNC(normal3s) 

ARGS3(jshort x, jshort y, jshort z)

{

   glNormal3s( x, y, z );

}

JC_VOID_LIB_FUNC(normal3i) 

ARGS3(jint x, jint y, jint z)

{

   glNormal3i( (GLint)x, (GLint)y, (GLint)z );

}



JC_VOID_LIB_FUNC(ortho) 

ARGS6(jdouble left, jdouble right, jdouble bottom, jdouble top, jdouble nearVar, jdouble farVar)

{

   glOrtho( left, right, bottom, top, nearVar, farVar );

}



JC_VOID_LIB_FUNC(passThrough) 

ARGS1(jfloat token)

{

   glPassThrough( token );

}



/* TO DO: glPixelMapfv, glPixelMapiv, glPixelMapsv, glPixelStore,

		  glPixelTransfer, glPixelZoom */



JC_VOID_LIB_FUNC(pixelStorei)

ARGS2(jint pname, jint param)

{

   glPixelStorei(pname, param);

}



JC_VOID_LIB_FUNC(pixelStoref)

ARGS2(jint pname, jfloat param)

{

   glPixelStoref(pname, param);

}





JC_VOID_LIB_FUNC(pointSize) 

ARGS1(jfloat size )

{

   glPointSize( size );

}



JC_VOID_LIB_FUNC(polygonMode) 

ARGS2(jint face, jint mode)

{

   GLenum gl_face, gl_mode;

   

   switch( face) 

      {

      case GL_FRONT:

	 gl_face = GL_FRONT;

	 break;

      case GL_BACK:

	 gl_face = GL_BACK;

	 break;

      case GL_FRONT_AND_BACK:

	 gl_face = GL_FRONT_AND_BACK;

	 break;

      default:

	 return;

      }



   switch( mode ) 

      {

      case GL_POINT:

	 gl_mode = GL_POINT;

	 break;

      case GL_LINE:

	 gl_mode = GL_LINE;

	 break;

      case GL_FILL:

	 gl_mode = GL_FILL;

	 break;

      default:

	 return;

      }

   glPolygonMode( gl_face, gl_mode );

}



/* TO DO: glPolygonStipple */



JC_VOID_LIB_FUNC(pushAttrib) 

ARGS1(jint mask )

{

#ifdef DEBUG

   switch( mask ) 

      {

      case GL_ACCUM_BUFFER_BIT:

	 glPushAttrib( GL_ACCUM_BUFFER_BIT );

	 break;

      case GL_COLOR_BUFFER_BIT:

	 glPushAttrib( GL_COLOR_BUFFER_BIT );

	 break;

      case GL_CURRENT_BIT:

	 glPushAttrib( GL_CURRENT_BIT );

	 break;

      case GL_DEPTH_BUFFER_BIT:

	 glPushAttrib( GL_DEPTH_BUFFER_BIT );

	 break;

      case GL_ENABLE_BIT:

	 glPushAttrib( GL_ENABLE_BIT );

	 break;

      case GL_EVAL_BIT:

	 glPushAttrib( GL_EVAL_BIT );

	 break;

      case GL_FOG_BIT:

	 glPushAttrib( GL_FOG_BIT );

	 break;

      case GL_HINT_BIT:

	 glPushAttrib( GL_HINT_BIT );

	 break;

      case GL_LIGHTING_BIT:

	 glPushAttrib( GL_LIGHTING_BIT );

	 break;

      case GL_LINE_BIT:

	 glPushAttrib( GL_LINE_BIT );

	 break;

      case GL_LIST_BIT:

	 glPushAttrib( GL_LIST_BIT );

	 break;

      case GL_PIXEL_MODE_BIT:

	 glPushAttrib( GL_PIXEL_MODE_BIT );

	 break;

      case GL_POINT_BIT:

	 glPushAttrib( GL_POINT_BIT );

	 break;

      case GL_POLYGON_BIT:

	 glPushAttrib( GL_POLYGON_BIT );

	 break;

      case GL_POLYGON_STIPPLE_BIT:

	 glPushAttrib( GL_POLYGON_STIPPLE_BIT );

	 break;

      case GL_SCISSOR_BIT:

	 glPushAttrib( GL_SCISSOR_BIT );

	 break;

      case GL_STENCIL_BUFFER_BIT:

	 glPushAttrib( GL_STENCIL_BUFFER_BIT );

	 break;

      case GL_TEXTURE_BIT:

	 glPushAttrib( GL_TEXTURE_BIT );

	 break;

      case GL_TRANSFORM_BIT:

	 glPushAttrib( GL_TRANSFORM_BIT );

	 break;

      case GL_VIEWPORT_BIT:

	 glPushAttrib( GL_VIEWPORT_BIT );

	 break;

      default:

	 BAD_VALUE("Bad mode passed to pushAttrib().");

	 return;

      }

#else

   glPushAttrib( (GLbitfield) mask );

#endif

}



JC_VOID_LIB_FUNC(popAttrib) 

ARGS()

{

   glPopAttrib();

}



JC_VOID_LIB_FUNC(pushMatrix) 

ARGS()

{

   glPushMatrix();

}



JC_VOID_LIB_FUNC(popMatrix)

ARGS()

{

   glPopMatrix();

}



JC_VOID_LIB_FUNC(pushName) 

ARGS1(jint name)

{

   glPushName( (GLuint) name );

}



JC_VOID_LIB_FUNC(popName)

ARGS()

{

   glPopName();

}



JC_VOID_LIB_FUNC(rasterPos2d) 

ARGS2(jdouble x, jdouble y)

{

   glRasterPos2d( x, y );

}



JC_VOID_LIB_FUNC(rasterPos2f) 

ARGS2(jfloat x, jfloat y)

{

   glRasterPos2f( x, y );

}



JC_VOID_LIB_FUNC(rasterPos2i) 

ARGS2(jint x, jint y)

{

   glRasterPos2i( (GLint)x, (GLint)y );

}



JC_VOID_LIB_FUNC(rasterPos2s) 

ARGS2(jshort x, jshort y)

{

   glRasterPos2s( x, y );

}



JC_VOID_LIB_FUNC(rasterPos3d) 

ARGS3(jdouble x, jdouble y, jdouble z)

{

	glRasterPos3d( x, y, z );

}



JC_VOID_LIB_FUNC(rasterPos3f) 

ARGS3(jfloat x, jfloat y,jfloat z)

{

	glRasterPos3f( x, y, z );

}



JC_VOID_LIB_FUNC(rasterPos3i) 

ARGS3(jint x, jint y, jint z)

{

   glRasterPos3i( (GLint)x, (GLint)y, (GLint)z );

}



JC_VOID_LIB_FUNC(rasterPos3s) 

ARGS3(jshort x, jshort y, jshort z)

{

	glRasterPos3s( x, y, z );

}



JC_VOID_LIB_FUNC(readBuffer)

ARGS1(jint mode)

{

#ifdef DEBUG



   switch( mode ) 

      {

      case GL_FRONT_LEFT:

	 glReadBuffer( GL_FRONT_LEFT );

	 break;

      case GL_FRONT_RIGHT:

	 glReadBuffer( GL_FRONT_RIGHT );

	 break;

      case GL_BACK_LEFT:

	 glReadBuffer( GL_BACK_LEFT );

	 break;

      case GL_BACK_RIGHT:

	 glReadBuffer( GL_BACK_RIGHT );

	 break;

      case GL_FRONT:

	 glReadBuffer( GL_FRONT );

	 break;

      case GL_BACK:

	 glReadBuffer( GL_BACK );

	 break;

      case GL_LEFT:

	 glReadBuffer( GL_LEFT );

	 break;

      case GL_RIGHT:

	 glReadBuffer( GL_RIGHT );

	 break;

      case GL_AUX0:

	 glReadBuffer( GL_AUX0 );

	 break;

      case GL_AUX1:

	 glReadBuffer( GL_AUX1 );

	 break;

      case GL_AUX2:

	 glReadBuffer( GL_AUX2 );

	 break;

      case GL_AUX3:

	 glReadBuffer( GL_AUX3 );

	 break;

      default:

	 BAD_VALUE("Bad mode passed to readBuffer().");

	 return;

      }

#else

   glReadBuffer( (GLenum) mode );

#endif

}



/* TO DO: glReadPixels */



JC_VOID_LIB_FUNC(rectd) 

ARGS4(jdouble x1, jdouble y1,jdouble x2, jdouble y2)

{

   glRectd( x1, y1, x2, y2 );

}



JC_VOID_LIB_FUNC(rectf) 

ARGS4(jfloat x1, jfloat y1, jfloat x2, jfloat y2)

{

   glRectf( x1, y1, x2, y2 );

}



JC_VOID_LIB_FUNC(recti) 

ARGS4(jint x1, jint y1, jint x2, jint y2)

{

   glRecti( (GLint)x1, (GLint)y1, (GLint)x2, (GLint)y2 );

}



JC_VOID_LIB_FUNC(renderMode) 

ARGS1(jint mode)

{

   switch( mode ) 

      {

      case GL_RENDER:

	 glRenderMode( GL_RENDER );

	 break;

      case GL_SELECT:

	 glRenderMode( GL_SELECT );

	 break;

      case GL_FEEDBACK:

	 glRenderMode( GL_FEEDBACK );

	 break;

      default:

	 return;

      }

}



JC_VOID_LIB_FUNC(rotated) 

ARGS4(jdouble angle, jdouble x, jdouble y, jdouble z)

{

   glRotated( angle, x, y, z );	

}



JC_VOID_LIB_FUNC(rotatef) 

ARGS4(jfloat angle, jfloat x, jfloat y, jfloat z)

{

   glRotatef( angle, x, y, z );	

}



JC_VOID_LIB_FUNC(scaled) 

ARGS3(jdouble x, jdouble y, jdouble z)

{

   glScaled( x, y, z );

}

JC_VOID_LIB_FUNC(scalef) 

ARGS3(jfloat x, jfloat y, jfloat z)

{

   glScalef( x, y, z );

}



JC_VOID_LIB_FUNC(scissor) 

ARGS4(jint x, jint y, jint width,jint height)

{

   glScissor( (GLint)x, (GLint)y, (GLsizei)width, (GLsizei)height );

}



/* TO DO: glSelectBuffer */



JC_VOID_LIB_FUNC(shadeModel) 

ARGS1(jint mode )

{

   switch( mode ) 

      {

      case GL_FLAT:

	 glShadeModel( GL_FLAT );

	 break;

      case GL_SMOOTH:

	 glShadeModel( GL_SMOOTH );

	 break;

      default:

	 return;

      }

}



JC_VOID_LIB_FUNC(stencilFunc) 

ARGS3(jint func, jint ref, jint mask)

{

#ifdef DEBUG

   switch( func ) 

      {

      case GL_NEVER:

	 glStencilFunc( GL_NEVER, (GLint)ref, (GLuint)mask );

	 break;

      case GL_LESS:

	 glStencilFunc( GL_LESS, (GLint)ref, (GLuint)mask );

	 break;

      case GL_LEQUAL:

	 glStencilFunc( GL_LEQUAL, (GLint)ref, (GLuint)mask );

	 break;

      case GL_GREATER:

	 glStencilFunc( GL_GREATER, (GLint)ref, (GLuint)mask );

	 break;

      case GL_GEQUAL:

	 glStencilFunc( GL_GEQUAL, (GLint)ref, (GLuint)mask );

	 break;

      case GL_EQUAL:

	 glStencilFunc( GL_EQUAL, (GLint)ref, (GLuint)mask );

	 break;

      case GL_NOTEQUAL:

	 glStencilFunc( GL_NOTEQUAL, (GLint)ref, (GLuint)mask );

	 break;

      case GL_ALWAYS:

	 glStencilFunc( GL_ALWAYS, (GLint)ref, (GLuint)mask );

	 break;

      default:

	 return;

      }

#else

   glStencilFunc( (GLenum) func, (GLint) ref, (GLuint) mask);

#endif

}

JC_VOID_LIB_FUNC(stencilMask) 

ARGS1(jint mask)

{

	glStencilMask( (GLuint)mask );

}

JC_VOID_LIB_FUNC(stencilOp) 

ARGS3(jint fail, jint zfail, jint zpass)

{

#ifdef DEBUG

   GLenum gl_fail, gl_zfail, gl_zpass;

   

   switch( fail ) 

      {

	 

      case GL_KEEP:

	 gl_fail = GL_KEEP;

	 break;

      case GL_ZERO:

	 gl_fail = GL_ZERO;

	 break;

      case GL_REPLACE:

	 gl_fail = GL_REPLACE;

	 break;

      case GL_INCR:

	 gl_fail = GL_INCR;

	 break;

      case GL_DECR:

	 gl_fail = GL_DECR;

	 break;

      case GL_INVERT:

	 gl_fail = GL_INVERT;

	 break;

      default:

	 return;

      }

   

   switch( zfail ) 

      {

      case GL_KEEP:

	 gl_zfail = GL_KEEP;

	 break;

      case GL_ZERO:

	 gl_zfail = GL_ZERO;

	 break;

      case GL_REPLACE:

	 gl_zfail = GL_REPLACE;

	 break;

      case GL_INCR:

	 gl_zfail = GL_INCR;

	 break;

      case GL_DECR:

	 gl_zfail = GL_DECR;

	 break;

      case GL_INVERT:

	 gl_zfail = GL_INVERT;

	 break;

      default:

	 return;

      }



   switch( zpass ) 

      {

      case GL_KEEP:

	 gl_zpass = GL_KEEP;

	 break;

      case GL_ZERO:

	 gl_zpass = GL_ZERO;

	 break;

	case GL_REPLACE:

	   gl_zpass = GL_REPLACE;

	   break;

      case GL_INCR:

	 gl_zpass = GL_INCR;

	 break;

      case GL_DECR:

	 gl_zpass = GL_DECR;

	 break;

      case GL_INVERT:

	 gl_zpass = GL_INVERT;

	 break;

      default:

	 return;

      }

   

   glStencilOp( gl_fail, gl_zfail, gl_zpass );

#else

   glStencilOp( (GLenum) fail , (GLenum) zfail , (GLenum) zpass);

#endif

}



JC_VOID_LIB_FUNC(texCoord1d)

ARGS1(jdouble s)

{

   glTexCoord1d( s );

}



JC_VOID_LIB_FUNC(texCoord1f) 

ARGS1(jfloat s)

{

   glTexCoord1f( s );

}



JC_VOID_LIB_FUNC(texCoord1i) 

ARGS1(jint s)

{

   glTexCoord1i( (GLint)s );

}



JC_VOID_LIB_FUNC(texCoord1s) 

ARGS1(jshort s)

{

   glTexCoord1s( s );

}



JC_VOID_LIB_FUNC(texCoord2d) 

ARGS2(jdouble s, jdouble t)

{

   glTexCoord2d( s,t );

}



JC_VOID_LIB_FUNC(texCoord2f) 

ARGS2(jfloat s, jfloat t)

{

   glTexCoord2f( s,t );

}



JC_VOID_LIB_FUNC(texCoord2i) 

ARGS2(jint s, jint t)

{

   glTexCoord2i( s,t );

}



JC_VOID_LIB_FUNC(texCoord2s) 

ARGS2(jshort s, jshort t)

{

   glTexCoord2s( s,t );

}



JC_VOID_LIB_FUNC(texCoord3d) 

ARGS3(jdouble s, jdouble t, jdouble r)

{

   glTexCoord3d( s,t,r );

}



JC_VOID_LIB_FUNC(texCoord3f) 

ARGS3(jfloat s, jfloat t, jfloat r)

{

   glTexCoord3f( s,t,r );

}



JC_VOID_LIB_FUNC(texCoord3i) 

ARGS3(jint s, jint t, jint r)

{

   glTexCoord3i( (GLint)s,(GLint)t,(GLint)r );

}



JC_VOID_LIB_FUNC(texCoord3s) 

ARGS3(jshort s, jshort t, jshort r)

{

   glTexCoord3s( s,t,r );

}



JC_VOID_LIB_FUNC(texCoord4d) 

ARGS4(jdouble s, jdouble t, jdouble r, jdouble q)

{

   glTexCoord4d( s,t,r,q );

}

JC_VOID_LIB_FUNC(texCoord4f) 

ARGS4(jfloat s, jfloat t, jfloat r, jfloat q)

{

   glTexCoord4f( (GLfloat)s , (GLfloat) t, (GLfloat) r, (GLfloat)q );

}

JC_VOID_LIB_FUNC(texCoord4i) 

ARGS4(jint s,jint t,jint r, jint q)

{

   glTexCoord4i( (GLint)s, (GLint)t, (GLint)r, (GLint)q );

}



JC_VOID_LIB_FUNC(texCoord4s) 

ARGS4(jshort s,jshort t, jshort r,jshort q)

{

   glTexCoord4s( (GLshort)s,(GLshort)t,(GLshort)r,(GLshort)q );

}



JC_VOID_LIB_FUNC(texEnvf) 

ARGS3(jint target,jint pname, jfloat param)

{

#ifdef DEBUG

   GLenum gl_target, gl_pname;

   GLfloat gl_param;

   

   switch( (int) target ) 

      {

      case GL_TEXTURE_ENV:

	 gl_target = GL_TEXTURE_ENV;

	 break;

      default:

	 return;

      }



   switch( (int) pname ) 

      {

      case GL_TEXTURE_ENV_MODE:

	 gl_pname = GL_TEXTURE_ENV_MODE;

	 break;

      default:

	 return;

      }



   switch( (int) param ) 

      {

      case GL_MODULATE:

	 gl_param = GL_MODULATE;

	 break;

      case GL_DECAL:

	 gl_param = GL_DECAL;

	 break;

      case GL_BLEND:

	 gl_param = GL_BLEND;

	 break;

      default:

	 return;

      }



   glTexEnvf( gl_target, gl_pname, gl_param );

#else

   glTexEnvf( (GLenum) target, (GLenum) pname, (GLfloat) param);

#endif

}



JC_VOID_LIB_FUNC(texEnvi) 

ARGS3(jint target, jint pname, jint param)

{

#ifdef DEBUG

   GLenum gl_target, gl_pname;

   GLint gl_param;

   

   switch( target ) 

      {

      case GL_TEXTURE_ENV:

	 gl_target = GL_TEXTURE_ENV;

	 break;

      default:

	 return;

      }



   switch( pname ) 

      {

      case GL_TEXTURE_ENV_MODE:

	 gl_pname = GL_TEXTURE_ENV_MODE;

	 break;

      default:

	 return;

      }



   switch( param ) 

      {

      case GL_MODULATE:

	 gl_param = GL_MODULATE;

	 break;

      case GL_DECAL:

	 gl_param = GL_DECAL;

	 break;

      case GL_BLEND:

	 gl_param = GL_BLEND;

	 break;

      default:

	 return;

      }



   glTexEnvi( gl_target, gl_pname, gl_param );

#else

   glTexEnvi( (GLenum) target, (GLenum) pname, (GLint) param);

#endif

}



JC_VOID_LIB_FUNC(texGend) 

ARGS3(jint coord,jint pname,jdouble param)

{

#ifdef DEBUG

   GLenum gl_coord, gl_pname;

   GLdouble gl_param;

   

   switch( coord ) 

      {

      case GL_S:

	 gl_coord = GL_S;

	 break;

      case GL_T:

	 gl_coord = GL_T;

	 break;

      case GL_R:

	 gl_coord = GL_R;

	 break;

      case GL_Q:

	 gl_coord = GL_Q;

	 break;

      default:

	 BAD_VALUE("Invalid coord passed to texGenf().");

	 return;

      }

   

   switch( pname ) 

      {

      case GL_TEXTURE_GEN_MODE:

	 gl_pname = GL_TEXTURE_GEN_MODE;

	 break;

      default:

	 BAD_VALUE("Invalid parameters name passed to texGenf().");

	 return;

      }

   

   if( param == (jdouble)GL_OBJECT_LINEAR)

      gl_param = GL_OBJECT_LINEAR;

   else if( param == (jdouble)GL_EYE_LINEAR )

      gl_param = GL_EYE_LINEAR;

   else if( param == (jdouble)GL_SPHERE_MAP )

      gl_param = GL_SPHERE_MAP;

   else

      {

	 BAD_VALUE("Invalid parameter passed to texGenf().");

	 return;

      }

   

   glTexGend( gl_coord, gl_pname, gl_param );

#else

   glTexGend( (GLenum) coord, (GLenum) pname, (GLdouble) param );

#endif

}



JC_VOID_LIB_FUNC(texGenf) 

ARGS3(jint coord, jint pname, jfloat param)

{

#ifdef DEBUG

   GLenum gl_coord, gl_pname;

   GLfloat gl_param;

   

   switch( coord ) 

      {

      case GL_S:

	 gl_coord = GL_S;

	 break;

      case GL_T:

	 gl_coord = GL_T;

	 break;

      case GL_R:

	 gl_coord = GL_R;

	 break;

      case GL_Q:

	 gl_coord = GL_Q;

	 break;

      default:

	 BAD_VALUE("Invalid coord passed to texGenf().");

	 return;

      }

   

   switch( pname ) 

      {

      case GL_TEXTURE_GEN_MODE:

	 gl_pname = GL_TEXTURE_GEN_MODE;

	 break;

      default:

	 BAD_VALUE("Invalid parameters name passed to texGenf().");

	 return;

      }

   

   if( param == (jfloat)GL_OBJECT_LINEAR)

      gl_param = GL_OBJECT_LINEAR;

   else if( param == (jfloat)GL_EYE_LINEAR )

      gl_param = GL_EYE_LINEAR;

   else if( param == (jfloat)GL_SPHERE_MAP )

      gl_param = GL_SPHERE_MAP;

   else

      {

	 BAD_VALUE("Invalid parameter passed to texGenf().");

	 return;

      }

   

   glTexGenf( gl_coord, gl_pname, gl_param );

#else

   glTexGenf( (GLenum) coord, (GLenum) pname, (GLfloat) param );

#endif

}



JC_VOID_LIB_FUNC(texGeni) 

ARGS3(jint coord, jint pname, jint param)

{

#ifdef DEBUG

   GLenum gl_coord, gl_pname;

   GLint gl_param;

   

   switch( coord ) 

      {

      case GL_S:

	 gl_coord = GL_S;

	 break;

      case GL_T:

	 gl_coord = GL_T;

	 break;

      case GL_R:

	 gl_coord = GL_R;

	 break;

      case GL_Q:

	 gl_coord = GL_Q;

	 break;

      default:

	 BAD_VALUE("Bad coord passed to texGeni().");

	 return;

      }

   

   switch( pname ) 

      {

      case GL_TEXTURE_GEN_MODE:

	 gl_pname = GL_TEXTURE_GEN_MODE;

	 break;

      default:

	 BAD_VALUE("Bad texture-coordinate generation function passed to texGeni().");

	 return;

      }

   

   switch( param ) 

      {

      case (GL_OBJECT_LINEAR):

	 gl_param = GL_OBJECT_LINEAR;

	 break;

      case (GL_EYE_LINEAR):

	 gl_param = GL_EYE_LINEAR;

	 break;

      case (GL_SPHERE_MAP):

	 gl_param = GL_SPHERE_MAP;

	 break;

      default:

	 BAD_VALUE("Bad texture generation parameter passed to texGeni().");

	 return;

      }

   glTexGeni( gl_coord, gl_pname, gl_param );

#else

   glTexGeni( (GLenum) coord, (GLenum) param, (GLint) param);

#endif

}



/* TO DO: glTexImage1D, glTexImage2D glTexParameter */

JC_VOID_LIB_FUNC(texImage1D)

ARGS8(jint target, jint level, jint components, jint width,  

      jint border, jint format, jint type, jbyteArray pixels)

{

   /*GLvoid *pixel_array;*/

	jbyte *ptr_to_bytes = (*env)->GetByteArrayElements(env, pixels, 0);


   glTexImage1D(target, level, components, width, border, format, type, ptr_to_bytes);

}



JC_VOID_LIB_FUNC(texImage2Db)

ARGS9(jint target, jint level, jint components, jint width, jint height, 

      jint border, jint format, jint type, jbyteArray pixels)

{

   /*int i;
   jboolean isCopy;
   jobject ptr_to_scan_lines = (*env)->GetObjectArrayElement(env, pixels, 0);
   jobject ptr_to_byte_array = (*env)->GetObjectArrayElement(env, ptr_to_scan_lines, 0);
   jbyte *ptr_to_bytes = GET_BYTE_ARRAY(ptr_to_byte_array, &isCopy);*/

	jbyte *ptr_to_bytes = (*env)->GetByteArrayElements(env, pixels, 0);

	glTexImage2D(target, level, components, width, height, border, format, type, (void *) ptr_to_bytes);

	//(*env)->ReleaseIntArrayElements(env, pixels, ptr_to_bytes, 0);

}

JC_VOID_LIB_FUNC(texImage2Di)

ARGS9(jint target, jint level, jint components, jint width, jint height, 

      jint border, jint format, jint type, jintArray pixels)
{

   /*int i;
   jboolean isCopy;
   jobject ptr_to_scan_lines = (*env)->GetObjectArrayElement(env, pixels, 0);
   jobject ptr_to_byte_array = (*env)->GetObjectArrayElement(env, ptr_to_scan_lines, 0);
   jbyte *ptr_to_bytes = GET_BYTE_ARRAY(ptr_to_byte_array, &isCopy);*/

	jbyte *ptr_to_bytes = (*env)->GetIntArrayElements(env, pixels, 0);

	glTexImage2D(target, level, components, width, height, border, format, type, (void *) ptr_to_bytes);

	//(*env)->ReleaseIntArrayElements(env, pixels, ptr_to_bytes, 0);

}



JC_VOID_LIB_FUNC(texParameterf)

ARGS3(jint target, jint pname, jfloat param)

{

   glTexParameterf(target, pname, param);

}



JC_VOID_LIB_FUNC(texParameteri)

ARGS3(jint target, jint pname, jint param)

{

   glTexParameteri(target, pname, param);

}

JC_VOID_LIB_FUNC (copyTexImage2D)

ARGS8(int target, int level, int internalFormat, int x, int y, int width, int height, int border)

{
 glCopyTexImage2D((GLenum)target, (GLint) level, (GLenum) internalFormat, (GLint) x,
    (GLint) y, (GLsizei) width, (GLsizei) height, (GLint) border);
}




JC_VOID_LIB_FUNC(translated) 

ARGS3(jdouble x, jdouble y, jdouble z)

{

   glTranslated( (GLdouble) x, (GLdouble) y, (GLdouble) z );

}



JC_VOID_LIB_FUNC(translatef) 

ARGS3(jfloat x, jfloat y, jfloat z)

{

	glTranslatef( (GLfloat) x, (GLfloat) y, (GLfloat) z );

}



JC_VOID_LIB_FUNC(vertex2d) 

ARGS2(jdouble x,jdouble y)

{

   glVertex2d( x, y );

}



JC_VOID_LIB_FUNC(vertex2f) 

ARGS2(jfloat x,jfloat y)

{

   glVertex2f( x,y );

}

JC_VOID_LIB_FUNC(vertex2i) 

ARGS2(jint x,jint y)

{

   glVertex2i( (GLint)x, (GLint)y );

}

JC_VOID_LIB_FUNC(vertex2s) 

ARGS2(jshort x, jshort y)

{

   glVertex2s( (GLshort) x,(GLshort) y );

}

JC_VOID_LIB_FUNC(vertex3d) 

ARGS3(jdouble x, jdouble y, jdouble z)

{

   glVertex3d( (GLdouble) x,(GLdouble) y,(GLdouble) z );

}

JC_VOID_LIB_FUNC(vertex3f) 

ARGS3(jfloat x, jfloat y, jfloat z)

{

   glVertex3f( (GLfloat) x,(GLfloat) y,(GLfloat) z );

}

JC_VOID_LIB_FUNC(vertex3i) 

ARGS3(jint x, jint y,jint z)

{

   glVertex3i( (GLint) x, (GLint)y, (GLint)z );

}

JC_VOID_LIB_FUNC(vertex3s) 

ARGS3(jshort x, jshort y, jshort z)

{

   glVertex3s( (GLshort) x,(GLshort) y,(GLshort) z );

}

JC_VOID_LIB_FUNC(vertex4d) 

ARGS4(jdouble x, jdouble y, jdouble z, jdouble w)

{

   glVertex4d( (GLdouble) x,(GLdouble) y,(GLdouble) z,(GLdouble) w );

}

JC_VOID_LIB_FUNC(vertex4f) 

ARGS4(jfloat x, jfloat y, jfloat z, jfloat w)

{

   glVertex4f( (GLfloat) x,(GLfloat) y,(GLfloat) z,(GLfloat) w );

}

JC_VOID_LIB_FUNC(vertex4i) 

ARGS4(jint x, jint y, jint z,jint w)

{

   glVertex4i( (GLint)x, (GLint)y, (GLint)z, (GLint)w );

}

JC_VOID_LIB_FUNC(vertex4s) 

ARGS4(jshort x, jshort y, jshort z, jshort w)

{

	glVertex4s( (GLshort) x,(GLshort) y,(GLshort) z,(GLshort) w );

}

JC_VOID_LIB_FUNC(viewport) 

ARGS4(jint x, jint y, jint width, jint height)

{

   glViewport( (GLint)x, (GLint)y, (GLsizei)width, (GLsizei)height ); 

}

