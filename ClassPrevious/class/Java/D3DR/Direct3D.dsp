# Microsoft Developer Studio Project File - Name="Direct3D" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 5.00
# ** DO NOT EDIT **

# TARGTYPE "Java Virtual Machine Java Project" 0x0809

CFG=Direct3D - Java Virtual Machine Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "Direct3D.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "Direct3D.mak" CFG="Direct3D - Java Virtual Machine Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "Direct3D - Java Virtual Machine Release" (based on\
 "Java Virtual Machine Java Project")
!MESSAGE "Direct3D - Java Virtual Machine Debug" (based on\
 "Java Virtual Machine Java Project")
!MESSAGE 

# Begin Project
# PROP Scc_ProjName ""
# PROP Scc_LocalPath ""
JAVA=jvc.exe

!IF  "$(CFG)" == "Direct3D - Java Virtual Machine Release"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir ""
# PROP BASE Intermediate_Dir ""
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 0
# PROP Output_Dir ""
# PROP Intermediate_Dir ""
# PROP Target_Dir ""
# ADD BASE JAVA /O
# ADD JAVA /O

!ELSEIF  "$(CFG)" == "Direct3D - Java Virtual Machine Debug"

# PROP BASE Use_MFC 0
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir ""
# PROP BASE Intermediate_Dir ""
# PROP BASE Target_Dir ""
# PROP Use_MFC 0
# PROP Use_Debug_Libraries 1
# PROP Output_Dir ""
# PROP Intermediate_Dir ""
# PROP Target_Dir ""
# ADD BASE JAVA /g
# ADD JAVA /g

!ENDIF 

# Begin Target

# Name "Direct3D - Java Virtual Machine Release"
# Name "Direct3D - Java Virtual Machine Debug"
# Begin Source File

SOURCE=.\Actor.java
# End Source File
# Begin Source File

SOURCE=.\Camera.java
# End Source File
# Begin Source File

SOURCE=.\Cell.java
# End Source File
# Begin Source File

SOURCE=.\D3DCamera.java
# End Source File
# Begin Source File

SOURCE=.\D3DLight.java
# End Source File
# Begin Source File

SOURCE=.\D3DRenderer.java
# End Source File
# Begin Source File

SOURCE=.\Hw2.java
# End Source File
# Begin Source File

SOURCE=.\Light.java
# End Source File
# Begin Source File

SOURCE=.\LineCell.java
# End Source File
# Begin Source File

SOURCE=.\Material.java
# End Source File
# Begin Source File

SOURCE=.\MaterialSet.java
# End Source File
# Begin Source File

SOURCE=.\OGLCamera.java
# End Source File
# Begin Source File

SOURCE=.\OGLLight.java
# End Source File
# Begin Source File

SOURCE=.\OGLRenderer.java
# End Source File
# Begin Source File

SOURCE=.\ParentObject.java
# End Source File
# Begin Source File

SOURCE=.\PointCell.java
# End Source File
# Begin Source File

SOURCE=.\PointSet.java
# End Source File
# Begin Source File

SOURCE=.\PointType.java
# End Source File
# Begin Source File

SOURCE=.\PolygonCell.java
# End Source File
# Begin Source File

SOURCE=.\Renderer.java
# End Source File
# End Target
# End Project
