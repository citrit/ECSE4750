dnl This file contains autoconf macros used by jogl. -*-sh-*-
dnl This file is part of Jogl.
dnl Copyright (C) 1997 Free Software Foundation, Inc.
dnl 
dnl These are the macros provided:
dnl 
dnl AC_PATH_OPENGL
dnl AC_PATH_GLUT
dnl AC_PATH_JNI
dnl AC_PATH_JDK
dnl AC_PROG_JAVAC
dnl AC_PROG_JAVAH


dnl Find OpenGL libs and includes
dnl The results go in OGL_CFLAGS and OGL_LIBS
dnl These can then be added to CPPFLAGS and LIBS or
dnl AC_SUBST can be used so they can be used in a Makefile
AC_DEFUN(AC_PATH_OPENGL,
[
AC_REQUIRE([AC_PATH_XTRA])
AC_REQUIRE([AC_CANONICAL_HOST])

OGL_CFLAGS=""
OGL_LIBS=""

AC_ARG_WITH(opengl-prefix,
[  --with-opengl-prefix=PREFIX_DIR  Where your OpenGL stuff is located.],
[
	OGL_PREFIX=${withval}
	if test -d "${withval}/include"; then
	    OGL_CFLAGS="-I${withval}/include"
	fi
])

dnl First find the gl, glu and glx headers and see if we're Mesa
dnl or real GL

GL_LIB="GL"
GLU_LIB="GLU"
GL_EXTRAS=""

if test ! -z "${OGL_CFLAGS}"; then
    CPP_SAVE="${CPPFLAGS}"
    CPPFLAGS="${CPPFLAGS} ${OGL_CFLAGS} ${X_CFLAGS}"
fi

AC_CHECK_HEADERS(GL/gl.h GL/glu.h,,
[
    AC_ERROR([Couldn't find OpenGL header files.  If they aren't in
a standard place please use the --with-opengl-prefix configure option.])
])

dnl See if we're Mesa
dnl There's probably a clearer way to do this but it works 

AC_TRY_CPP([
#include <GL/gl.h>
#ifdef MESA_MAJOR_VERSION
#include "_not_a_file.h"
#endif],,
[
GL_LIB="MesaGL"
GLU_LIB="MesaGLU"
])

dnl This is the extra stuff we need

if test ! -z "${OGL_PREFIX}" && test -d "${OGL_PREFIX}/lib"; then
EXTRA_LIBS="-L${OGL_PREFIX}/lib ${X_LIBS} ${X_PRE_LIBS} -lXext -lX11 ${X_EXTRA_LIBS} ${GL_EXTRAS} -lm"
else
EXTRA_LIBS="${X_LIBS} ${X_PRE_LIBS} -lXext -lX11 ${X_EXTRA_LIBS} ${GL_EXTRAS} -lm "
fi


AC_CHECK_LIB(${GL_LIB}, glBegin,
[
    if test ! -z "${OGL_PREFIX}" ; then
	OGL_LIBS="-L${OGL_PREFIX}/lib -l${GL_LIB}"
    else
	OGL_LIBS="-l${GL_LIB}"
    fi
    
    EXTRA_LIBS="${EXTRA_LIBS} -l${GL_LIB}"

    AC_CHECK_LIB(${GLU_LIB}, gluErrorString,
    [
	OGL_LIBS="${OGL_LIBS} -l${GLU_LIB}"
    ],
    [
	AC_ERROR([Couldn't find GL library.  If it isn't in
a standard place please use the --with-opengl-prefix configure option.])
    ],
    [
	"${EXTRA_LIBS}"
    ])
],
[
    AC_ERROR([Couldn't find GL library.  If it isn't in
a standard place please use the --with-opengl-prefix configure option.])
],
[
    "${EXTRA_LIBS}"
])

AC_CHECK_FUNC(glBindTextureEXT, 
[
AC_DEFINE(HAVE_SGI_EXTENSIONS)
])

OGL_LIBS="${OGL_LIBS} ${GL_EXTRAS}"

AC_DEFINE_UNQUOTED(OGL_CFLAGS)
AC_DEFINE_UNQUOTED(OGL_LIBS)

CPPFLAGS="${CPP_SAVE}"

])

dnl Find Java includes for native methods 
dnl Results go in JNI_CFLAGS
AC_DEFUN(AC_PATH_JNI,
[
AC_REQUIRE([AC_PATH_JDK])

CPP_SAVE="${CPPFLAGS}"

# Pick these up from the JDK if it has'em
if test ! -z "${JDK_HOME}" ; then
	JNI_CFLAGS="-I${JDK_HOME}/include -I${JDK_HOME}/include/${JAVA_PLATFORM}"
	CPPFLAGS="${CPPFLAGS} ${JNI_CFLAGS}"
fi

AC_CHECK_HEADER(jni.h, ,
[
# We either find these suckers or bail

AC_MSG_ERROR([Couldn't locate Java header files.  
Please reconfigure using --with-jdk-home=/path/to/jdk
Or use -with-java-inc=/path/to/java/includes if they
are not in a standard JDK like place. ])
])

CPPFLAGS="${CPP_SAVE}"

AC_DEFINE_UNQUOTED(JNI_CFLAGS)

])


dnl Find the JDK
dnl Results go in JDK_HOME
dnl Also sets JAVA_PLATFORM
AC_DEFUN(AC_PATH_JDK,
[
AC_REQUIRE([AC_CANONICAL_HOST])

AC_ARG_WITH(jdk-home,
[ --with-jdk-home=JDK_HOME Where your java header files are
 if not located in a JDK like structure.],
[
	JDK_HOME=${withval}
])

case "${host_os}" in

irix*.*)
    JAVA_PLATFORM="irix"

# If gcc then we don't need the -32 and if it we aren't using gcc this
# only applies to 6.2. If we are using gcc then we need to tell
# the loader to use the old linker
    if test -z "${GCC}" ; then
	if test "${host_os}" = "irix6.2" ; then
	    LDFLAGS="-32"
	    CFLAGS="-g -32"
        fi
    else
	LDFLAGS="-old_ld"
    fi

    ;;

linux-gnu)
    JAVA_PLATFORM="genunix"
    
    ;;

solaris2.*)
    JAVA_PLATFORM="solaris"

    ;;

esac


AC_ARG_WITH(java-platform, 
[ --with-java-platform=PLATFORM tells configure the name
of the platform specific include dir needed by the JNI],
[
    JAVA_PLATFORM=${withval}
])


if test -z "${JDK_HOME}"; then
    for i in \
	/ \
	/usr \
	/usr/local \
	/usr/lib \
	/usr/local/lib 
    do
	for j in \
	    java \
	    jdk \
	    jdk1.1.4 \
	    jdk1.1.3 \
	    jdk1.1.2 \
	    jdk1.1.1 
	do
	    if test -d "${i}/${j}/bin" && test -d "${i}/${j}/include"  ; then
		JDK_HOME="${i}/${j}"
	    fi
	done
    done
fi


])


dnl Find the java compiler
dnl Result goes in JAVAC
AC_DEFUN(AC_PROG_JAVAC,
[
AC_REQUIRE([AC_PATH_JDK])
AC_CHECK_PROG(JAVAC, javac, javac, "")


# also look in the JDK_HOME as it may 
if test -z "${JAVAC}" && test ! -z "${JDK_HOME}"; then
    AC_MSG_CHECKING(for javac in ${JDK_HOME}/bin)
    if test -x "${JDK_HOME}/bin/javac"; then
	JAVAC="${JDK_HOME}/bin/javac"
	AC_MSG_RESULT(yes)
    else
	AC_MSG_RESULT(no)
    fi
fi

if test -z "${JAVAC}"; then
    AC_MSG_WARN([No javac program could be found.  The java classes
    won't be regenerated if you choose to modify the source code.])
fi

])

dnl Find the java native header generator
dnl Result goes in JAVAC
AC_DEFUN(AC_PROG_JAVAH,
[
AC_REQUIRE([AC_PATH_JDK])
AC_CHECK_PROG(JAVAH, javah, javah, "")

if test -z "${JAVAH}" && test ! -z "${JDK_HOME}"; then
    AC_MSG_CHECKING([for javah in "${JDK_HOME}"/bin])
    if test -x "${JDK_HOME}/bin/javah"; then
	JAVAH="${JDK_HOME}/bin/javah"
	AC_MSG_RESULT([yes])
    else
	AC_MSG_RESULT([no])
	
    fi    
fi

if test -z "${JAVAH}"; then
    AC_MSG_WARN([No javah program could be found.  The native
    headers won't be regenerated if you choose to modify the Java native code.])
fi
])

dnl Find the GLUT header and library
dnl Result goes in GLUT_LIBS and GLUT_CFLAGS if any
AC_DEFUN(AC_PATH_GLUT,
[
AC_REQUIRE([AC_PATH_XTRA])
AC_REQUIRE([AC_PATH_OPENGL])

GLUT_LIB=""
GLUT_CFLAGS=""

AC_ARG_WITH(glut-prefix,
[ --with-glut-prefix Where your glut header files and
 libraries are if not located in a standard place. ],
[
    GLUT_PREFIX="${withval}"
    if test -d "${withval}/include"; then
	GLUT_CFLAGS="-I${withval}/include"
    fi
])

if test ! -z "${GLUT_CFLAGS}"; then
CPP_SAVE="${CPPFLAGS}"
CPPFLAGS="${CPPFLAGS} ${OGL_CFLAGS} ${X_CFLAGS}"
fi

AC_CHECK_HEADER(GL/glut.h,,
    AC_MSG_ERROR([Couldn't find glut header file. Please specify its
location with --with-glut-prefix.])
)

CPPFLAGS="${CPP_SAVE}"

if test ! -z "${GLUT_PREFIX}" && test -d "${GLUT_PREFIX}/lib"; then
EXTRA_LIBS="-L${GLUT_PREFIX}/lib ${X_LIBS} ${X_PRE_LIBS} -lXext -lX11 ${X_EXTRA_LIBS} ${OGL_LIBS} -lm"
else
EXTRA_LIBS="${X_LIBS} ${X_PRE_LIBS} -lXext -lX11 ${X_EXTRA_LIBS} ${OGL_LIBS} -lm"
fi

AC_CHECK_LIB(glut, glutInit,
[
    if test ! -z "${GLUT_PREFIX}" ; then
	GLUT_LIBS="-L${GLUT_PREFIX}/lib -l${GL_LIB}"
    else
	GLUT_LIBS="-l${GL_LIB}"
    fi
],
[
    AC_ERROR([Couldn't find GLUT library.  If it isn't in
a standard place please use the --with-glut-prefix configure option.])
],
[
    "${EXTRA_LIBS}"
])


CPPFLAGS="${CPP_SAVE}"
])