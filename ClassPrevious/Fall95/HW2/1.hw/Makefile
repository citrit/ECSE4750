#//////////////////////////////////////////////////////////////////////////
#//
#//  This code is for instructional purposes only. It was generated for
#//  use in a graduate level course to show certain aspects of data
#//  storage algorithms. It has problems and should not be used 
#//  outside the class environment.
#//
#//  Author:  Thomas D. Citriniti     citrit@rpi.edu
#//  Class:   Advanced Computer Graphics and Data visualization
#//           Rensselaer Polytechnic Institute
#//  Date:    October 3, 1995
#//
#//////////////////////////////////////////////////////////////////////////


# CCFLAGS=-g -I/locker/44/000644/Mesa/@sys/include
CCFLAGS=-g -I/usr/lpp/xlC/include -I/locker/44/000644/Mesa/@sys/include  \
CXXFLAGS=-g -I/usr/lpp/xlC/include -I/locker/44/000644/Mesa/@sys/include  \
        -I/usr/include
#CCC=xlC
CCC=g++

CXXFLAGS = -g

LDFLAGS=-L/locker/44/000644/Mesa/@sys/lib -lglut -lMesaGLU \
        -lMesaGL -lXmu -lXt -lX11 -lm


objtest: objtest.o OGLRen.o 
	$(CCC) -g -o $@  $@.o OGLRen.o $(LDFLAGS)


clean:
	rm -f *~ core objtest *.o

tidy:
	rm -f core *~ *.o

# DO NOT DELETE THIS LINE -- make depend depends on it.

objtest.o: PtCell.h PointSet.h VectorT.h /usr/include/string.h
objtest.o: /usr/include/standards.h /usr/include/sys/types.h Cell.h
objtest.o: Renderer.h DataSet.h LineCell.h PolyLineCell.h PlyCell.h
objtest.o: TriangleCell.h OGLRen.h /usr/include/stdlib.h
objtest.o: /usr/include/X11/Xlib.h /usr/include/X11/X.h
objtest.o: /usr/include/X11/Xfuncproto.h /usr/include/X11/Xosdefs.h
objtest.o: /usr/include/stddef.h /usr/include/X11/Xutil.h
objtest.o: /usr/include/X11/Intrinsic.h /usr/include/X11/Xresource.h
objtest.o: /usr/include/X11/Core.h /usr/include/X11/Composite.h
objtest.o: /usr/include/X11/Constraint.h /usr/include/X11/Object.h
objtest.o: /usr/include/X11/RectObj.h /usr/include/X11/Shell.h
objtest.o: /usr/include/X11/StringDefs.h /usr/include/GL/gl.h
objtest.o: /usr/include/GL/glu.h /usr/include/GL/glx.h /usr/include/X11/Xmd.h
objtest.o: /usr/include/GL/glxproto.h /usr/include/GL/glxmd.h
