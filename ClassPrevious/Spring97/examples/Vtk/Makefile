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

#
#  Handle the cxx extension
#
.SUFFIXES: $(.SUFFIXES) .cxx
.cxx.o:
	$(CCC) $(CCFLAGS) -c $<


# For IBMs
CCC=xlC

# For SGIs
# CCC=g++

# For Suns
# CCC=CC

CCFLAGS= -I/campus/visualization/mesa-ogl/1.2.7/@sys/include \
	 -I/campus/visualization/vtk/1.3/@sys/include

LDFLAGS= -s -L/campus/visualization/vtk/1.3/@sys/lib \
	 -lVTKCommon -lVTKGraphics \
	 -L/campus/visualization/mesa-ogl/1.2.7/@sys/lib \
	 -lMesaGLU -lMesaGL -lXmu -lXt -lX11 -lm

# Enter your object files names that correspond to your source
OBJS= 

# Specify the target name here
TARGET=


$(TARGET): $(TARGET).o $(OBJS)
	$(CCC) -o $@  $(TARGET).o $(OBJS) $(LDFLAGS)

clean:
	rm -f *.o *~ core 

