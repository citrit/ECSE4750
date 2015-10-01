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


# CCFLAGS=-g -I/usr/lpp/xlC/include -I/usr/include
CCFLAGS=-g -I/usr/lpp/xlC/include -I/locker/44/000644/Mesa/@sys/include  \
	 -I/usr/include
CCC=xlC

# CCC=g++
# CCFLAGS=-g -I/locker/44/000644/Mesa/@sys/include

OBJS= OGLLight.o OGLCam.o OGLRen.o CellSet.o PtCell.o PlyGCell.o \
	PlyLCell.o TriCell.o LineCell.o

# LDFLAGS= -L/usr/lpp/OpenGL/lib -lGLU -lGL \
LDFLAGS=-L/locker/44/000644/Mesa/@sys/lib -lMesaGLU -lMesaGL \
	-lXmu -lXt -lX11 -lm

hw3: hw3.o $(OBJS)
	$(CCC) -o $@  hw3.o $(OBJS) $(LDFLAGS)

objTest: objTest.o $(OBJS)
	$(CCC) -o $@  objTest.o $(OBJS) $(LDFLAGS)

dataTest: dataTest.o StructG.o $(OBJS)
	$(CCC) -o $@ dataTest.o StructG.o  $(OBJS) $(LDFLAGS)

hullTest: hullTest.o StructG.o $(OBJS)
	$(CCC) -o $@ hullTest.o StructG.o  $(OBJS) $(LDFLAGS)


clean:
	rm -f *~ core hw3 *.o *.brs

tidy:
	rm -f core *~ *.o

# hw2.C: PtCell.H LineCell.H PlyCell.H PlyLine.H Triangle.H DataSet.H OGLRen.H
# OGLRen.C: Renderer.H VectorT.H DataSet.H

# DO NOT DELETE THIS LINE -- make depend depends on it.

CellSet.o: CellSet.H PObject.H /usr/include/sys/types.h
CellSet.o: /usr/include/standards.h /usr/include/time.h VectorT.H
CellSet.o: /usr/include/string.h Cell.H PointSet.H MatSet.H
LineCell.o: LineCell.H PointSet.H PObject.H /usr/include/sys/types.h
LineCell.o: /usr/include/standards.h /usr/include/time.h VectorT.H
LineCell.o: /usr/include/string.h Cell.H MatSet.H Renderer.H CellSet.H
LineCell.o: Light.H Camera.H
OGLCam.o: OGLCam.H /usr/include/GL/gl.h /usr/include/GL/glu.h Camera.H
OGLCam.o: PObject.H /usr/include/sys/types.h /usr/include/standards.h
OGLCam.o: /usr/include/time.h PointSet.H VectorT.H /usr/include/string.h
OGLCam.o: Renderer.H CellSet.H Cell.H MatSet.H Light.H
OGLLight.o: OGLLight.H Light.H PObject.H /usr/include/sys/types.h
OGLLight.o: /usr/include/standards.h /usr/include/time.h PointSet.H VectorT.H
OGLLight.o: /usr/include/string.h MatSet.H /usr/include/GL/gl.h Renderer.H
OGLLight.o: CellSet.H Cell.H Camera.H
OGLRen.o: OGLRen.H /usr/include/stdlib.h /usr/include/standards.h
OGLRen.o: /usr/include/X11/Xlib.h /usr/include/sys/types.h
OGLRen.o: /usr/include/X11/X.h /usr/include/X11/Xfuncproto.h
OGLRen.o: /usr/include/X11/Xosdefs.h /usr/include/stddef.h
OGLRen.o: /usr/include/X11/Xutil.h /usr/include/X11/Intrinsic.h
OGLRen.o: /usr/include/X11/Xresource.h /usr/include/string.h
OGLRen.o: /usr/include/X11/Core.h /usr/include/X11/Composite.h
OGLRen.o: /usr/include/X11/Constraint.h /usr/include/X11/Object.h
OGLRen.o: /usr/include/X11/RectObj.h /usr/include/X11/StringDefs.h
OGLRen.o: /usr/include/X11/Shell.h /usr/include/GL/gl.h /usr/include/GL/glu.h
OGLRen.o: /usr/include/GL/glx.h /usr/include/X11/Xmd.h
OGLRen.o: /usr/include/GL/glxproto.h /usr/include/GL/glxmd.h Renderer.H
OGLRen.o: PObject.H /usr/include/time.h VectorT.H CellSet.H Cell.H PointSet.H
OGLRen.o: MatSet.H Light.H Camera.H OGLLight.H OGLCam.H
PlyGCell.o: PlyGCell.H PointSet.H PObject.H /usr/include/sys/types.h
PlyGCell.o: /usr/include/standards.h /usr/include/time.h VectorT.H
PlyGCell.o: /usr/include/string.h Cell.H MatSet.H Renderer.H CellSet.H
PlyGCell.o: Light.H Camera.H
PlyLCell.o: PlyLCell.H PointSet.H PObject.H /usr/include/sys/types.h
PlyLCell.o: /usr/include/standards.h /usr/include/time.h VectorT.H
PlyLCell.o: /usr/include/string.h Cell.H MatSet.H Renderer.H CellSet.H
PlyLCell.o: Light.H Camera.H
PtCell.o: PtCell.H PointSet.H PObject.H /usr/include/sys/types.h
PtCell.o: /usr/include/standards.h /usr/include/time.h VectorT.H
PtCell.o: /usr/include/string.h Cell.H MatSet.H Renderer.H CellSet.H Light.H
PtCell.o: Camera.H
StructG.o: StructG.H CellSet.H PObject.H /usr/include/sys/types.h
StructG.o: /usr/include/standards.h /usr/include/time.h VectorT.H
StructG.o: /usr/include/string.h Cell.H PointSet.H MatSet.H DataType.H
StructG.o: Renderer.H Light.H Camera.H
TriCell.o: TriCell.H PointSet.H PObject.H /usr/include/sys/types.h
TriCell.o: /usr/include/standards.h /usr/include/time.h VectorT.H
TriCell.o: /usr/include/string.h Cell.H MatSet.H Renderer.H CellSet.H Light.H
TriCell.o: Camera.H
dataTest.o: /usr/include/stdlib.h /usr/include/standards.h DataType.H
dataTest.o: VectorT.H /usr/include/string.h PObject.H
dataTest.o: /usr/include/sys/types.h /usr/include/time.h MatSet.H StructG.H
dataTest.o: CellSet.H Cell.H PointSet.H Renderer.H Light.H Camera.H OGLRen.H
dataTest.o: /usr/include/X11/Xlib.h /usr/include/X11/X.h
dataTest.o: /usr/include/X11/Xfuncproto.h /usr/include/X11/Xosdefs.h
dataTest.o: /usr/include/stddef.h /usr/include/X11/Xutil.h
dataTest.o: /usr/include/X11/Intrinsic.h /usr/include/X11/Xresource.h
dataTest.o: /usr/include/X11/Core.h /usr/include/X11/Composite.h
dataTest.o: /usr/include/X11/Constraint.h /usr/include/X11/Object.h
dataTest.o: /usr/include/X11/RectObj.h /usr/include/X11/StringDefs.h
dataTest.o: /usr/include/X11/Shell.h /usr/include/GL/gl.h
dataTest.o: /usr/include/GL/glu.h /usr/include/GL/glx.h
dataTest.o: /usr/include/X11/Xmd.h /usr/include/GL/glxproto.h
dataTest.o: /usr/include/GL/glxmd.h OGLLight.H OGLCam.H
hw3.o: PtCell.H PointSet.H PObject.H /usr/include/sys/types.h
hw3.o: /usr/include/standards.h /usr/include/time.h VectorT.H
hw3.o: /usr/include/string.h Cell.H MatSet.H Renderer.H CellSet.H Light.H
hw3.o: Camera.H LineCell.H PlyGCell.H PlyLCell.H TriCell.H OGLRen.H
hw3.o: /usr/include/stdlib.h /usr/include/X11/Xlib.h /usr/include/X11/X.h
hw3.o: /usr/include/X11/Xfuncproto.h /usr/include/X11/Xosdefs.h
hw3.o: /usr/include/stddef.h /usr/include/X11/Xutil.h
hw3.o: /usr/include/X11/Intrinsic.h /usr/include/X11/Xresource.h
hw3.o: /usr/include/X11/Core.h /usr/include/X11/Composite.h
hw3.o: /usr/include/X11/Constraint.h /usr/include/X11/Object.h
hw3.o: /usr/include/X11/RectObj.h /usr/include/X11/StringDefs.h
hw3.o: /usr/include/X11/Shell.h /usr/include/GL/gl.h /usr/include/GL/glu.h
hw3.o: /usr/include/GL/glx.h /usr/include/X11/Xmd.h
hw3.o: /usr/include/GL/glxproto.h /usr/include/GL/glxmd.h OGLLight.H OGLCam.H
modtest.o: /usr/include/stdlib.h /usr/include/standards.h
objTest.o: /usr/include/unistd.h /usr/include/standards.h
objTest.o: /usr/include/sys/types.h /usr/include/sys/access.h PtCell.H
objTest.o: PointSet.H PObject.H /usr/include/time.h VectorT.H
objTest.o: /usr/include/string.h Cell.H MatSet.H Renderer.H CellSet.H Light.H
objTest.o: Camera.H LineCell.H PlyGCell.H PlyLCell.H TriCell.H OGLRen.H
objTest.o: /usr/include/stdlib.h /usr/include/X11/Xlib.h /usr/include/X11/X.h
objTest.o: /usr/include/X11/Xfuncproto.h /usr/include/X11/Xosdefs.h
objTest.o: /usr/include/stddef.h /usr/include/X11/Xutil.h
objTest.o: /usr/include/X11/Intrinsic.h /usr/include/X11/Xresource.h
objTest.o: /usr/include/X11/Core.h /usr/include/X11/Composite.h
objTest.o: /usr/include/X11/Constraint.h /usr/include/X11/Object.h
objTest.o: /usr/include/X11/RectObj.h /usr/include/X11/StringDefs.h
objTest.o: /usr/include/X11/Shell.h /usr/include/GL/gl.h
objTest.o: /usr/include/GL/glu.h /usr/include/GL/glx.h /usr/include/X11/Xmd.h
objTest.o: /usr/include/GL/glxproto.h /usr/include/GL/glxmd.h OGLLight.H
objTest.o: OGLCam.H
