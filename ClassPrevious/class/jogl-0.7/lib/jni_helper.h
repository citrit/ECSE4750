/* Copyright 1997 Free Software Foundation, Inc.
 
This file contains the necessary includes to get JNI
working with some nice macros 

These are really scary and I feel I should apologize up front for
them.  Sorry.  But heh, if all the C files look nice at the expense of
one ugly header, who cares?!

They could be better organized and more complete but they
serve our purposes for now.  

 - Tommy Reilly
*/

#ifndef __JNI_HELPER_H__
#define __JNI_HELPER_H__

#define _GENERIC_LIB_FUNC(_name, _class, _package) Java_ ## _package ## _class ## _name

#define JNI_FUNC(_type, _name, _class, _package) JNIEXPORT _type JNICALL _GENERIC_LIB_FUNC(_ ## _name, _ ##_class, _package)


#define ARGS()                                   (JNIEnv *env, jobject this)
#define ARGS1(_a1)                               (JNIEnv *env, jobject this,_a1)
#define ARGS2(_a1,_a2)                           (JNIEnv *env, jobject this,_a1,_a2)
#define ARGS3(_a1,_a2,_a3)                       (JNIEnv *env, jobject this,_a1,_a2,_a3)
#define ARGS4(_a1,_a2,_a3,_a4)                   (JNIEnv *env, jobject this,_a1,_a2,_a3,_a4)
#define ARGS5(_a1,_a2,_a3,_a4,_a5)               (JNIEnv *env, jobject this,_a1,_a2,_a3,_a4,_a5)
#define ARGS6(_a1,_a2,_a3,_a4,_a5,_a6)           (JNIEnv *env, jobject this,_a1,_a2,_a3,_a4,_a5,_a6)
#define ARGS7(_a1,_a2,_a3,_a4,_a5,_a6,_a7)       (JNIEnv *env, jobject this,_a1,_a2,_a3,_a4,_a5,_a6,_a7)
#define ARGS8(_a1,_a2,_a3,_a4,_a5,_a6,_a7,_a8)   (JNIEnv *env, jobject this,_a1,_a2,_a3,_a4,_a5,_a6,_a7,_a8)
#define ARGS9(_a1,_a2,_a3,_a4,_a5,_a6,_a7,_a8,_a9) \
(JNIEnv *env, jobject this,_a1,_a2,_a3,_a4,_a5,_a6,_a7,_a8,_a9)
#define ARGS10(_a1,_a2,_a3,_a4,_a5,_a6,_a7,_a8,_a9,_a10) \
(JNIEnv *env, jobject this,_a1,_a2,_a3,_a4,_a5,_a6,_a7,_a8,_a9,_a10)

#define STRING_SIG "Ljava/lang/String;"

/* Generic object Get/Set field macros */

#define GET_FIELD(_obj,_name,_type,_sig) \
(*env)->Get##_type##Field(env, _obj, (*env)->GetFieldID(env, _obj, _name, _sig))
#define SET_FIELD(_obj,_name,_type,_sig,_value) \
(*env)->Set##_type##Field(env, _obj, (*env)->GetFieldID(env, _obj, _name, _sig), _value)

     /* This needs to be placed in the variables decl section of
	any method wishing to use the macros below */
#define THIS_CLASS() jclass this_class = (*env)->GetObjectClass(env, this);

     /* Method macros */

#define METHOD_ID(_name, _sig) (*env)->GetMethodID(env, this_class, _name, _sig)

#define CALL_INT_METHOD(_name) (*env)->CallIntMethod(env, this, METHOD_ID(_name, "()I"))
#define CALL_OBJECT_METHOD(_name, _sig) (*env)->CallObjectMethod(env, this, METHOD_ID(_name, _sig))

#define CALL_STRING_METHOD(_name) CALL_OBJECT_METHOD(_name, "()Ljava/lang/String;")

     /* Field macros */

#define FIELD_ID(_name, _sig) (*env)->GetFieldID(env, this_class, _name, _sig)
#define GET_OBJECT_FIELD(_name, _sig) (*env)->GetObjectField(env, this, FIELD_ID(_name, _sig))
#define GET_INT_FIELD(_name) (*env)->GetIntField(env, this, FIELD_ID(_name, "I"))
#define GET_BOOL_FIELD(_name) (*env)->GetBooleanField(env, this, FIELD_ID(_name, "Z"))

#define SET_INT_FIELD(_name, _value) (*env)->SetIntField(env, this, FIELD_ID(_name, "I"), _value)


#define GET_STRING_FIELD(_name) (*env)->GetObjectField(env, this, FIELD_ID(_name, STRING_SIG))

#define GET_STRING_CHARS(_string, _isCopy) (*env)->GetStringUTFChars(env, _string, _isCopy)

#define GET_TYPED_ARRAY(_type, _jbArray, _isCopy) \
(*env)->Get##_type##ArrayElements(env, _jbArray, _isCopy)
#define FREE_TYPED_ARRAY(_type, _jbArray, _jbptr, _mode) \
(*env)->Release##_type##ArrayElements(env, _jbArray, _jbptr, _mode)

#define GET_BOOL_ARRAY(_jbArray, _isCopy) GET_TYPED_ARRAY(Boolean, _jbArray, _isCopy)
#define FREE_BOOL_ARRAY(_jbArray, _jbptr, _mode) FREE_TYPED_ARRAY(Boolean, _jbArray, _jbptr, _mode)

#define GET_INT_ARRAY(_jiArray, _isCopy) GET_TYPED_ARRAY(Int, _jiArray, _isCopy)
#define FREE_INT_ARRAY(_jiArray, _jiptr, _mode) FREE_TYPED_ARRAY(Int, _jiArray, _jiptr, _mode)

#define GET_FLOAT_ARRAY(_jfArray, _isCopy) GET_TYPED_ARRAY(Float, _jfArray, _isCopy)
#define FREE_FLOAT_ARRAY(_jfArray, _jfptr, _mode) FREE_TYPED_ARRAY(Float, _jfArray, _jfptr, _mode)

#define GET_DOUBLE_ARRAY(_jdArray, _isCopy) GET_TYPED_ARRAY(Double, _jdArray, _isCopy)
#define FREE_DOUBLE_ARRAY(_jdArray, _jdptr, _mode) FREE_TYPED_ARRAY(Double, _jdArray, _jdptr, _mode)

#define GET_BYTE_ARRAY(_jbArray, _isCopy) GET_TYPED_ARRAY(Byte, _jbArray, _isCopy)
#define FREE_BYTE_ARRAY(_jbArray, _jbptr, _mode) FREE_TYPED_ARRAY(Byte, _jbArray, _jbptr, _mode)


#endif
