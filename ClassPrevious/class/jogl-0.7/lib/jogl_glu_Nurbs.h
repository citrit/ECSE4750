/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class jogl_glu_Nurbs */

#ifndef _Included_jogl_glu_Nurbs
#define _Included_jogl_glu_Nurbs
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     jogl_glu_Nurbs
 * Method:    newNurbsRenderer
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_jogl_glu_Nurbs_newNurbsRenderer
  (JNIEnv *, jobject);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    deleteNurbsRenderer
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_deleteNurbsRenderer
  (JNIEnv *, jobject);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    loadSamplingMatrices
 * Signature: ([F[F[I)V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_loadSamplingMatrices
  (JNIEnv *, jobject, jfloatArray, jfloatArray, jintArray);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    nurbsProperty
 * Signature: (IF)V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_nurbsProperty
  (JNIEnv *, jobject, jint, jfloat);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    getNurbsProperty
 * Signature: (ILjava/lang/Float;)V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_getNurbsProperty
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    beginCurve
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_beginCurve
  (JNIEnv *, jobject);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    endCurve
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_endCurve
  (JNIEnv *, jobject);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    nurbsCurve
 * Signature: (I[FI[FII)V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_nurbsCurve
  (JNIEnv *, jobject, jint, jfloatArray, jint, jfloatArray, jint, jint);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    beginSurface
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_beginSurface
  (JNIEnv *, jobject);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    endSurface
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_endSurface
  (JNIEnv *, jobject);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    nurbsSurface
 * Signature: (I[FI[FII[FIII)V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_nurbsSurface
  (JNIEnv *, jobject, jint, jfloatArray, jint, jfloatArray, jint, jint, jfloatArray, jint, jint, jint);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    beginTrim
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_beginTrim
  (JNIEnv *, jobject);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    endTrim
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_endTrim
  (JNIEnv *, jobject);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    pwlCurve
 * Signature: (I[FII)V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_pwlCurve
  (JNIEnv *, jobject, jint, jfloatArray, jint, jint);

/*
 * Class:     jogl_glu_Nurbs
 * Method:    nurbsCallback
 * Signature: (ILjava/lang/Object;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_jogl_glu_Nurbs_nurbsCallback
  (JNIEnv *, jobject, jint, jobject, jstring);

#ifdef __cplusplus
}
#endif
#endif