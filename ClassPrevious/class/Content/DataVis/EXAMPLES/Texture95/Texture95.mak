# Microsoft Developer Studio Generated NMAKE File, Format Version 4.20
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Console Application" 0x0103

!IF "$(CFG)" == ""
CFG=Texture95 - Win32 Debug
!MESSAGE No configuration specified.  Defaulting to Texture95 - Win32 Debug.
!ENDIF 

!IF "$(CFG)" != "Texture95 - Win32 Release" && "$(CFG)" !=\
 "Texture95 - Win32 Debug"
!MESSAGE Invalid configuration "$(CFG)" specified.
!MESSAGE You can specify a configuration when running NMAKE on this makefile
!MESSAGE by defining the macro CFG on the command line.  For example:
!MESSAGE 
!MESSAGE NMAKE /f "Texture95.mak" CFG="Texture95 - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "Texture95 - Win32 Release" (based on\
 "Win32 (x86) Console Application")
!MESSAGE "Texture95 - Win32 Debug" (based on "Win32 (x86) Console Application")
!MESSAGE 
!ERROR An invalid configuration is specified.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 
################################################################################
# Begin Project
# PROP Target_Last_Scanned "Texture95 - Win32 Debug"
RSC=rc.exe
CPP=cl.exe

!IF  "$(CFG)" == "Texture95 - Win32 Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "Release"
# PROP BASE Intermediate_Dir "Release"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Release"
# PROP Intermediate_Dir "Release"
# PROP Target_Dir ""
OUTDIR=.\Release
INTDIR=.\Release

ALL : "$(OUTDIR)\Texture95.exe"

CLEAN : 
	-@erase "$(INTDIR)\texture.obj"
	-@erase "$(OUTDIR)\Texture95.exe"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

# ADD BASE CPP /nologo /W3 /GX /O2 /D "WIN32" /D "NDEBUG" /D "_CONSOLE" /YX /c
# ADD CPP /nologo /GX /O2 /D "WIN32" /D "NDEBUG" /D "_CONSOLE" /YX /c
CPP_PROJ=/nologo /ML /GX /O2 /D "WIN32" /D "NDEBUG" /D "_CONSOLE"\
 /Fp"$(INTDIR)/Texture95.pch" /YX /Fo"$(INTDIR)/" /c 
CPP_OBJS=.\Release/
CPP_SBRS=.\.
# ADD BASE RSC /l 0x409 /d "NDEBUG"
# ADD RSC /l 0x409 /d "NDEBUG"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
BSC32_FLAGS=/nologo /o"$(OUTDIR)/Texture95.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
# ADD BASE LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /machine:I386
# ADD LINK32 opengl32.lib glu32.lib glut.lib glaux.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /machine:I386
LINK32_FLAGS=opengl32.lib glu32.lib glut.lib glaux.lib kernel32.lib user32.lib\
 gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib\
 oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console\
 /incremental:no /pdb:"$(OUTDIR)/Texture95.pdb" /machine:I386\
 /out:"$(OUTDIR)/Texture95.exe" 
LINK32_OBJS= \
	"$(INTDIR)\texture.obj"

"$(OUTDIR)\Texture95.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "Texture95 - Win32 Debug"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Texture0"
# PROP BASE Intermediate_Dir "Texture0"
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Texture0"
# PROP Intermediate_Dir "Texture0"
# PROP Target_Dir ""
OUTDIR=.\Texture0
INTDIR=.\Texture0

ALL : "$(OUTDIR)\Texture95.exe"

CLEAN : 
	-@erase "$(INTDIR)\texture.obj"
	-@erase "$(INTDIR)\vc40.idb"
	-@erase "$(INTDIR)\vc40.pdb"
	-@erase "$(OUTDIR)\Texture95.exe"
	-@erase "$(OUTDIR)\Texture95.ilk"
	-@erase "$(OUTDIR)\Texture95.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

# ADD BASE CPP /nologo /W3 /Gm /GX /Zi /Od /D "WIN32" /D "_DEBUG" /D "_CONSOLE" /YX /c
# ADD CPP /nologo /Gm /GX /Zi /Od /D "WIN32" /D "_DEBUG" /D "_CONSOLE" /YX /c
CPP_PROJ=/nologo /MLd /Gm /GX /Zi /Od /D "WIN32" /D "_DEBUG" /D "_CONSOLE"\
 /Fp"$(INTDIR)/Texture95.pch" /YX /Fo"$(INTDIR)/" /Fd"$(INTDIR)/" /c 
CPP_OBJS=.\Texture0/
CPP_SBRS=.\.
# ADD BASE RSC /l 0x409 /d "_DEBUG"
# ADD RSC /l 0x409 /d "_DEBUG"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
BSC32_FLAGS=/nologo /o"$(OUTDIR)/Texture95.bsc" 
BSC32_SBRS= \
	
LINK32=link.exe
# ADD BASE LINK32 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /debug /machine:I386
# ADD LINK32 opengl32.lib glu32.lib glut.lib glaux.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console /debug /machine:I386
LINK32_FLAGS=opengl32.lib glu32.lib glut.lib glaux.lib kernel32.lib user32.lib\
 gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib\
 oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /subsystem:console\
 /incremental:yes /pdb:"$(OUTDIR)/Texture95.pdb" /debug /machine:I386\
 /out:"$(OUTDIR)/Texture95.exe" 
LINK32_OBJS= \
	"$(INTDIR)\texture.obj"

"$(OUTDIR)\Texture95.exe" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ENDIF 

.c{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.cpp{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.cxx{$(CPP_OBJS)}.obj:
   $(CPP) $(CPP_PROJ) $<  

.c{$(CPP_SBRS)}.sbr:
   $(CPP) $(CPP_PROJ) $<  

.cpp{$(CPP_SBRS)}.sbr:
   $(CPP) $(CPP_PROJ) $<  

.cxx{$(CPP_SBRS)}.sbr:
   $(CPP) $(CPP_PROJ) $<  

################################################################################
# Begin Target

# Name "Texture95 - Win32 Release"
# Name "Texture95 - Win32 Debug"

!IF  "$(CFG)" == "Texture95 - Win32 Release"

!ELSEIF  "$(CFG)" == "Texture95 - Win32 Debug"

!ENDIF 

################################################################################
# Begin Source File

SOURCE="\FrontPage Webs\Content\DataVis\examples\texture.c"
DEP_CPP_TEXTU=\
	{$(INCLUDE)}"\GL\gl.h"\
	{$(INCLUDE)}"\GL\glaux.h"\
	{$(INCLUDE)}"\GL\glu.h"\
	{$(INCLUDE)}"\GL\glut.h"\
	

"$(INTDIR)\texture.obj" : $(SOURCE) $(DEP_CPP_TEXTU) "$(INTDIR)"
   $(CPP) $(CPP_PROJ) $(SOURCE)


# End Source File
# End Target
# End Project
################################################################################
