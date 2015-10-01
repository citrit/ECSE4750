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

# CCFLAGS=-g -I/usr/lpp/xlC/include -I/locker/44/000644/Mesa/@sys/include  \
#       -I/usr/include
CCC=xlC

# CCC=g++
CCFLAGS= -I/locker/44/000644/Mesa/@sys/include \
	-I/locker/44/000644/vtk1.0/@sys/include

OBJS= 

LDFLAGS=-L/locker/44/000644/vtk1.0/@sys/lib -L/locker/44/000644/Mesa/@sys/lib \
	-lvtk -lMesaGLU -lMesaGL -lXmu -lXt -lX11 -lm

hw3: hw3.o $(OBJS)
	$(CCC) -o $@  hw3.o $(OBJS) $(LDFLAGS)

