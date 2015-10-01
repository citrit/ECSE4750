dnl aclocal.m4 generated automatically by aclocal 1.2

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
# Like AC_CONFIG_HEADER, but automatically create stamp file.

AC_DEFUN(AM_CONFIG_HEADER,
[AC_PREREQ([2.12])
AC_CONFIG_HEADER([$1])
dnl When config.status generates a header, we must update the stamp-h file.
dnl This file resides in the same directory as the config header
dnl that is generated.  We must strip everything past the first ":",
dnl and everything past the last "/".
AC_OUTPUT_COMMANDS(changequote(<<,>>)dnl
ifelse(patsubst(<<$1>>, <<[^ ]>>, <<>>), <<>>,
<<test -z "<<$>>CONFIG_HEADERS" || echo timestamp > patsubst(<<$1>>, <<^\([^:]*/\)?.*>>, <<\1>>)stamp-h<<>>dnl>>,
<<am_indx=1
for am_file in <<$1>>; do
  case " <<$>>CONFIG_HEADERS " in
  *" <<$>>am_file "*<<)>>
    echo timestamp > `echo <<$>>am_file | sed -e 's%:.*%%' -e 's%[^/]*$%%'`stamp-h$am_indx
    ;;
  esac
  am_indx=`expr "<<$>>am_indx" + 1`
done<<>>dnl>>)
changequote([,]))])

# Do all the work for Automake.  This macro actually does too much --
# some checks are only needed if your package does certain things.
# But this isn't really a big deal.

# serial 1

dnl Usage:
dnl AM_INIT_AUTOMAKE(package,version, [no-define])

AC_DEFUN(AM_INIT_AUTOMAKE,
[AC_REQUIRE([AM_PROG_INSTALL])
PACKAGE=[$1]
AC_SUBST(PACKAGE)
VERSION=[$2]
AC_SUBST(VERSION)
dnl test to see if srcdir already configured
if test "`cd $srcdir && pwd`" != "`pwd`" && test -f $srcdir/config.status; then
  AC_MSG_ERROR([source directory already configured; run "make distclean" there first])
fi
ifelse([$3],,
AC_DEFINE_UNQUOTED(PACKAGE, "$PACKAGE")
AC_DEFINE_UNQUOTED(VERSION, "$VERSION"))
AM_SANITY_CHECK
AC_ARG_PROGRAM
dnl FIXME This is truly gross.
missing_dir=`cd $ac_aux_dir && pwd`
AM_MISSING_PROG(ACLOCAL, aclocal, $missing_dir)
AM_MISSING_PROG(AUTOCONF, autoconf, $missing_dir)
AM_MISSING_PROG(AUTOMAKE, automake, $missing_dir)
AM_MISSING_PROG(AUTOHEADER, autoheader, $missing_dir)
AM_MISSING_PROG(MAKEINFO, makeinfo, $missing_dir)
AC_PROG_MAKE_SET])


# serial 1

AC_DEFUN(AM_PROG_INSTALL,
[AC_REQUIRE([AC_PROG_INSTALL])
test -z "$INSTALL_SCRIPT" && INSTALL_SCRIPT='${INSTALL_PROGRAM}'
AC_SUBST(INSTALL_SCRIPT)dnl
])

#
# Check to make sure that the build environment is sane.
#

AC_DEFUN(AM_SANITY_CHECK,
[AC_MSG_CHECKING([whether build environment is sane])
# Just in case
sleep 1
echo timestamp > conftestfile
# Do `set' in a subshell so we don't clobber the current shell's
# arguments.  Must try -L first in case configure is actually a
# symlink; some systems play weird games with the mod time of symlinks
# (eg FreeBSD returns the mod time of the symlink's containing
# directory).
if (
   set X `ls -Lt $srcdir/configure conftestfile 2> /dev/null`
   if test "$@" = "X"; then
      # -L didn't work.
      set X `ls -t $srcdir/configure conftestfile`
   fi
   test "[$]2" = conftestfile
   )
then
   # Ok.
   :
else
   AC_MSG_ERROR([newly created file is older than distributed files!
Check your system clock])
fi
rm -f conftest*
AC_MSG_RESULT(yes)])

dnl AM_MISSING_PROG(NAME, PROGRAM, DIRECTORY)
dnl The program must properly implement --version.
AC_DEFUN(AM_MISSING_PROG,
[AC_MSG_CHECKING(for working $2)
# Run test in a subshell; some versions of sh will print an error if
# an executable is not found, even if stderr is redirected.
# Redirect stdin to placate older versions of autoconf.  Sigh.
if ($2 --version) < /dev/null > /dev/null 2>&1; then
   $1=$2
   AC_MSG_RESULT(found)
else
   $1="$3/missing $2"
   AC_MSG_RESULT(missing)
fi
AC_SUBST($1)])


# serial 12 AM_PROG_LIBTOOL
AC_DEFUN(AM_PROG_LIBTOOL,
[AC_REQUIRE([AC_CANONICAL_HOST])
AC_REQUIRE([AC_PROG_RANLIB])
AC_REQUIRE([AC_PROG_CC])
AC_REQUIRE([AM_PROG_LD])
AC_REQUIRE([AM_PROG_NM])
AC_REQUIRE([AC_PROG_LN_S])

# Always use our own libtool.
LIBTOOL='$(top_builddir)/libtool'
AC_SUBST(LIBTOOL)

dnl Allow the --disable-shared flag to stop us from building shared libs.
AC_ARG_ENABLE(shared,
[  --enable-shared         build shared libraries [default=yes]],
[if test "$enableval" = no; then
  enable_shared=no
else
  enable_shared=yes
fi])
libtool_shared=
test "$enable_shared" = no && libtool_shared=" --disable-shared"

dnl Allow the --disable-static flag to stop us from building static libs.
AC_ARG_ENABLE(static,
[  --enable-static         build static libraries [default=yes]],
[if test "$enableval" = no; then
  enable_static=no
else
  enable_static=yes
fi])
libtool_static=
test "$enable_static" = no && libtool_static=" --disable-static"

libtool_flags="$libtool_shared$libtool_static"
test "$silent" = yes && libtool_flags="$libtool_flags --silent"
test "$ac_cv_prog_gcc" = yes && libtool_flags="$libtool_flags --with-gcc"
test "$ac_cv_prog_gnu_ld" = yes && libtool_flags="$libtool_flags --with-gnu-ld"

# Some flags need to be propagated to the compiler or linker for good
# libtool support.
[case "$host" in
*-*-irix6*)
  ac_save_CFLAGS="$CFLAGS"
  # -n32 always needs to be added to the linker when using GCC.
  test "$ac_cv_prog_gcc" = yes && CFLAGS="$CFLAGS -n32"
  for f in '-32' '-64' '-cckr' '-n32' '-mips1' '-mips2' '-mips3' '-mips4'; do
    if echo " $CC $CFLAGS " | egrep -e "[ 	]$f[	 ]" > /dev/null; then
      LD="${LD-ld} $f"
    fi
  done
  CFLAGS="$ac_save_CFLAGS"
  ;;

*-*-sco3.2v5*)
  # On SCO OpenServer 5, we need -belf to get full-featured binaries.
  CFLAGS="$CFLAGS -belf"
  ;;
esac]

# Actually configure libtool.  ac_aux_dir is where install-sh is found.
CC="$CC" CFLAGS="$CFLAGS" CPPFLAGS="$CPPFLAGS" \
LD="$LD" NM="$NM" RANLIB="$RANLIB" LN_S="$LN_S" \
${CONFIG_SHELL-/bin/sh} $ac_aux_dir/ltconfig \
$libtool_flags --no-verify $ac_aux_dir/ltmain.sh $host \
|| AC_MSG_ERROR([libtool configure failed])
])

# AM_PROG_LD - find the path to the GNU or non-GNU linker
AC_DEFUN(AM_PROG_LD,
[AC_ARG_WITH(gnu-ld,
[  --with-gnu-ld           assume the C compiler uses GNU ld [default=no]],
test "$withval" = no || with_gnu_ld=yes, with_gnu_ld=no)
AC_REQUIRE([AC_PROG_CC])
ac_prog=ld
if test "$ac_cv_prog_gcc" = yes; then
  # Check if gcc -print-prog-name=ld gives a path.
  AC_MSG_CHECKING([for ld used by GCC])
  ac_prog=`($CC -print-prog-name=ld) 2>&5`
  case "$ac_prog" in
  # Accept absolute paths.
  /*) ;;
  "")
    # If it fails, then pretend we aren't using GCC.
    ac_prog=ld
    ;;
  *)
    # If it is relative, then search for the first ld in PATH.
    with_gnu_ld=unknown
    ;;
  esac
elif test "$with_gnu_ld" = yes; then
  AC_MSG_CHECKING([for GNU ld])
else
  AC_MSG_CHECKING([for non-GNU ld])
fi
AC_CACHE_VAL(ac_cv_path_LD,
[LD=${LD-$ac_prog}
case "$LD" in
  /*)
  ac_cv_path_LD="$LD" # Let the user override the test with a path.
  ;;
  *)
  IFS="${IFS= 	}"; ac_save_ifs="$IFS"; IFS="${IFS}:"
  for ac_dir in $PATH; do
    test -z "$ac_dir" && ac_dir=.
    if test -f "$ac_dir/$ac_prog"; then
      ac_cv_path_LD="$ac_dir/$ac_prog"
      # Check to see if the program is GNU ld.  I'd rather use --version,
      # but apparently some GNU ld's only accept -v.
      # Break only if it was the GNU/non-GNU ld that we prefer.
      if "$ac_cv_path_LD" -v 2>&1 < /dev/null | egrep '(GNU|with BFD)' > /dev/null; then
	test "$with_gnu_ld" != no && break
      else
        test "$with_gnu_ld" != yes && break
      fi
    fi
  done
  IFS="$ac_save_ifs"
  ;;
esac])
LD="$ac_cv_path_LD"
if test -n "$LD"; then
  AC_MSG_RESULT($LD)
else
  AC_MSG_RESULT(no)
fi
test -z "$LD" && AC_MSG_ERROR([no acceptable ld found in \$PATH])
AC_SUBST(LD)
AM_PROG_LD_GNU
])

AC_DEFUN(AM_PROG_LD_GNU,
[AC_CACHE_CHECK([if the linker ($LD) is GNU ld], ac_cv_prog_gnu_ld,
[# I'd rather use --version here, but apparently some GNU ld's only accept -v.
if $LD -v 2>&1 </dev/null | egrep '(GNU|with BFD)' 1>&5; then
  ac_cv_prog_gnu_ld=yes
else
  ac_cv_prog_gnu_ld=no
fi])
])

# AM_PROG_NM - find the path to a BSD-compatible name lister
AC_DEFUN(AM_PROG_NM,
[AC_MSG_CHECKING([for BSD-compatible nm])
AC_CACHE_VAL(ac_cv_path_NM,
[case "$NM" in
/*)
  ac_cv_path_NM="$NM" # Let the user override the test with a path.
  ;;
*)
  IFS="${IFS= 	}"; ac_save_ifs="$IFS"; IFS="${IFS}:"
  for ac_dir in /usr/ucb:$PATH:/bin; do
    test -z "$ac_dir" && dir=.
    if test -f $ac_dir/nm; then
      # Check to see if the nm accepts a BSD-compat flag.
      if ($ac_dir/nm -B /dev/null 2>&1; exit 0) | grep /dev/null >/dev/null; then
        ac_cv_path_NM="$ac_dir/nm -B"
      elif ($ac_dir/nm -p /dev/null 2>&1; exit 0) | grep /dev/null >/dev/null; then
        ac_cv_path_NM="$ac_dir/nm -p"
      else
        ac_cv_path_NM="$ac_dir/nm"
      fi
      break
    fi
  done
  IFS="$ac_save_ifs"
  test -z "$ac_cv_path_NM" && ac_cv_path_NM=nm
  ;;
esac])
NM="$ac_cv_path_NM"
AC_MSG_RESULT([$NM])
AC_SUBST(NM)
])

