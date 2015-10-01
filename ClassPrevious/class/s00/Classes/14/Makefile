CC = gcc 
CXX = c++
MAKEINFO = makeinfo
PACKAGE = phase1
RANLIB = ranlib
VERSION = 0.1

phase1_SOURCES = cgobject.cpp cgwindow.cpp main.cpp
phase1_LDADD = -lXm -lXt -lX11

DEFS = -I. -I$(srcdir) -I..
CPPFLAGS = 
LDFLAGS =   -L/usr/X11R6/lib
LIBS =
phase1_OBJECTS =  cgobject.o cgwindow.o main.o
phase1_DEPENDENCIES =
phase1_LDFLAGS =
CXXFLAGS =   -O0 -Wall
CXXCOMPILE = $(CXX) $(DEFS) $(INCLUDES) $(AM_CPPFLAGS) $(CPPFLAGS) $(AM_CXXFLAGS) $(CXXFLAGS)
CXXLD = $(CXX)
CXXLINK = $(CXXLD) $(AM_CXXFLAGS) $(CXXFLAGS) $(LDFLAGS) -o $@
.SUFFIXES:
.SUFFIXES: .S .c .cpp .o .s
.s.o:
	$(COMPILE) -c $<

.S.o:
	$(COMPILE) -c $<

.cpp.o:
	$(CXXCOMPILE) -c $<

phase1: $(phase1_OBJECTS) $(phase1_DEPENDENCIES)
	@rm -f phase1
	$(CXXLINK) $(phase1_LDFLAGS) $(phase1_OBJECTS) $(phase1_LDADD) $(LIBS)

clean:
	rm -f *.o phase1
	


