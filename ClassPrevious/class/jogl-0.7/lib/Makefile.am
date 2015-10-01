lib_LTLIBRARIES = libjogl.la
libjogl_la_SOURCES = gl.c glu.c glu_nurbs.c glu_quadric.c glu_tesselator.c joglX11.c array_ptr.c methodinfo.c
libjogl_la_LDFLAGS = -version-info $(subst .,:,$(VERSION))

EXTRA_DIST = $(shell ls *.h) joglWin32.c

