## Makefile.am -*-Makefile-*-
## Tommy Reilly 1997

AUTOMAKE_OPTIONS = gnu
LIB_DIR = lib
SUBDIRS = $(LIB_DIR)

SUFFIXES += .java .class

# This makes sure our simple rule can find the source
vpath %.java $(top_srcdir)/$(PACKAGE) $(top_srcdir)/$(PACKAGE)/glu $(top_srcdir)/test $(top_srcdir)/sun/awt/motif $(top_srcdir)/sun/awt/motif $(top_srcdir)/demos
# And this will allow make to find the .class file
vpath %.class $(top_srcdir)/$(PACKAGE) $(top_srcdir)/$(PACKAGE)/glu $(top_srcdir)/test $(top_srcdir)/sun/awt/motif $(top_srcdir)/sun/awt/motif $(top_srcdir)/demos

vpath %.c $(top_srcdir)/$(LIB_DIR)
vpath %.h $(top_srcdir)/$(LIB_DIR)

################################################################
################    Variable Section    ########################
################################################################

###############
#  Variables ending in _CLASS(ES) are .class file(s) 
#  (.ie JoglCanvas.class)
###############
#  Variables ending in _OBJ(S) are the base object name(s) 
#  (.ie JoglCanvas)
###############
#  Variables ending in _NAME(S) are the fully qualified Java 
#  (.ie jogl.JoglCanvas)
###############

NATIVE_OBJ = JoglCanvas 

GLU_NATIVE_OBJS = \
	Nurbs \
	Quadric \
	Tesselator

JOGL_CLASSES = \
	$(addsuffix .class, $(NATIVE_OBJ) $(GLU_NATIVE_OBJS)) \
	JoglRenderer.class \
	JoglNativeException.class \
	JoglpDataAccess.class \
	JoglRenderer.class \
	GL.class \
	GLU.class

DATA_ACCESS_CLASS = X11pDataAccess.class

TEST_CLASSES = \
	Jogl.class \
	Texture.class \
	Cylinder.class \
	jogltest.class

DEMO_CLASSES = \
	Trackball.class \
	bounce.class \
	dino.class \
	mech.class \
	planes.class \
	spots.class

########################################################################
# This produces jogl.JoglCanvas, jogl.glu.Nurbs ...                    #

NATIVE_NAMES = $(addprefix $(PACKAGE)., $(NATIVE_OBJ))\
$(addprefix $(PACKAGE).glu., $(GLU_NATIVE_OBJS))
                                                                       # 
########################################################################

ALL_CLASSES = $(JOGL_CLASSES) $(TEST_CLASSES) $(DEMO_CLASSES)

########################################################################
# This produces jogl_JoglCanvas.h, jogl_glu_Nurbs.h .....              #

NATIVE_HEADER_FILES = $(addsuffix .h, $(addprefix $(PACKAGE)_, $(NATIVE_OBJ) $(addprefix glu_, $(GLU_NATIVE_OBJS))))
                                                                       #
########################################################################

MAINTAINERCLEANFILES = $(addprefix ./lib/,$(NATIVE_HEADER_FILES)) \
$(addprefix ./$(PACKAGE)/,$(CLASSES)) *.class

CONFIG_FILES = config/Win32/* $(addprefix config/, config.guess config.sub \
install-sh ltconfig ltmain.sh missing mkinstalldirs)

EXTRA_DIST = $(CONFIG_FILES) README.Win32 \
$(top_srcdir)/$(PACKAGE)/*.java \
$(top_srcdir)/$(PACKAGE)/*.class \
$(top_srcdir)/$(PACKAGE)/glu/*.java \
$(top_srcdir)/$(PACKAGE)/glu/*.class \
$(top_srcdir)/sun/awt/motif/X11pDataAccess.java \
$(top_srcdir)/sun/awt/motif/X11pDataAccess.class \
$(top_srcdir)/sun/awt/windows/WindowspDataAccess.java \
$(top_srcdir)/test/*.java \
$(top_srcdir)/test/*.class \
$(top_srcdir)/demos/*.java \
$(top_srcdir)/demos/*.class \
$(top_srcdir)/docs/api/* \
$(top_srcdir)/docs/api/images/*


######################################################################
######################################################################
#######   End of Variable Section:  Below are rules   ################
######################################################################
######################################################################

### This doesn't exactly work very well but it works 
$(NATIVE_HEADER_FILES) : $(addsuffix .class, $(NATIVE_OBJS))
	$(JAVAH) -jni -d $(LIB_DIR) $(NATIVE_NAMES) 

#### Some convience targets, these are all made by default
jogl : $(JOGL_CLASSES)

test : $(TEST_CLASSES)

demos: $(DEMO_CLASSES)


#### Two targets for producing jar files (X11 and windows version)
jogl.jar: $(ALL_CLASSES)
	jar -cv0f jogl.jar $(shell ls jogl/*.class) $(shell ls test/*.class) $(DATA_ACCESS_CLASS_PATH)/$(DATA_ACCESS_CLASS) $(shell ls demos/*.class)


##### Target to produce those lovely javadocs
javadocs: $(JOGL_CLASSES)
	CLASSPATH=. javadoc -d $(top_srcdir)/docs/api jogl jogl.glu

# rule for building class files (ending in .class)
# This is a very simple rule but it works nicely because
# of the vpath usage.  Mucking with the classpath is not a good
# idea because javac behaves differently on different platforms
# in subtle ways.  Best to let make handle it.
%.class		: %.java
	$(JAVAC) -g $<

# We need this to get it to generate the stub stuff before going into lib
# if need be
all-recursive : $(ALL_CLASSES) $(NATIVE_HEADER_FILES) $(DATA_ACCESS_CLASS)
