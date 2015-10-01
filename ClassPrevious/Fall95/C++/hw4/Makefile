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

CCC=xlC

# CCC=g++
CCFLAGS= -I/campus/visualization/mesa-ogl/1.2.7/@sys/include -I/campus/visualization/vtk/1.1/@sys/include

OBJS= 

LDFLAGS= -L/campus/visualization/vtk/1.1/@sys/lib \
	-lvtk -L/campus/visualization/mesa-ogl/1.2.7/@sys/lib -lMesaGLU -lMesaGL -lXmu -lXt -lX11 -lm

$(TARGET): $(TARGET).o $(OBJS)
	$(CCC) -o $@  $(TARGET).o $(OBJS) $(LDFLAGS)

clean:
	rm -f *.o *~ core 

