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


CCFLAGS=-g -I/usr/lpp/xlC/include -I/locker/44/000644/Mesa/@sys/include  \
        -I/usr/include
CCC=xlC

# CCC=g++
# CCFLAGS=-g -I/locker/44/000644/Mesa/@sys/include


LDFLAGS=-L/locker/44/000644/Mesa/@sys/lib -lMesaGLU \
        -lMesaGL -lXmu -lXt -lX11 -lm

hw2: hw2.o OGLLight.o OGLCam.o OGLRen.o
	$(CCC) -o $@  hw2.o OGLLight.o OGLCam.o  OGLRen.o $(LDFLAGS)

objTest: objTest.o OGLLight.o OGLCam.o OGLRen.o 
	$(CCC) -o $@  objTest.o  OGLLight.o OGLCam.o OGLRen.o $(LDFLAGS)


clean:
	rm -f *~ core objTest *.o

tidy:
	rm -f core *~ *.o

hw2.o: PtCell.H LineCell.H PlyCell.H PlyLine.H Triangle.H DataSet.H OGLRen.H
OGLRen.o: Renderer.H VectorT.H DataSet.H

