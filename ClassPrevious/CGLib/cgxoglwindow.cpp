/***************************************************************************
                          cgwindow.cpp  -  description                              
                             -------------------                                         
    begin                : Fri Feb 11 2000                                           
    copyright            : (C) 2000 by TC                         
    email                : citrit@rpi.edu                                     
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   * 
 *                                                                         *
 ***************************************************************************/


#include "cgxoglwindow.h"


// Global initializer for current CGWindow
CGWindow *gpCurrentCGWindow = NULL;

/** Glut defined callback for resizing. */
static void Reshape(int width, int height)
{
	if (gpCurrentCGWindow)
		gpCurrentCGWindow->SetSize(width, height);
}
/** Glut defined callback for drawing. */
static void DrawFunc(void)
{
	if (gpCurrentCGWindow)
		gpCurrentCGWindow->Draw();
}
void GLUTCALLBACK IdleCallbackFunc(void)
{
	if (gpCurrentCGWindow)
		gpCurrentCGWindow->Tick();
}
static void SpecialKeyFunc(int key, int x, int y)
{
//    glRotatef(zRotation, 0, 0, 1);
	glMatrixMode(GL_PROJECTION);
	switch (key) {
      case GLUT_KEY_LEFT:
				glRotatef(5, 0, 1, 0);
        break;
      case GLUT_KEY_RIGHT:
				glRotatef(-5, 0, 1, 0);
        break;
      case GLUT_KEY_UP:
				glRotatef(5, 1, 0, 0);
        break;
      case GLUT_KEY_DOWN:
				glRotatef(-5, 1, 0, 0);
        break;
      default:
        return;
	}
	glMatrixMode(GL_MODELVIEW);
	glutPostRedisplay();
}

/** Constructor Destructor */
CGWindow::CGWindow()
: m_KeyCallback(NULL),
	m_DrawingCallback(NULL),
	m_IdleCallback(NULL),
	m_KeyArg(NULL),
	m_DrawArg(NULL),
	m_IdleArg(NULL)
{
	m_Size[0] = m_Size[1] = 300;
}
CGWindow::~CGWindow()
{

}

/**
  * Initialize the window system
  */
void CGWindow::Initialize(int argc, char *argv[])
{
    glutInit(&argc, argv);
    glutInitWindowPosition(0, 0);
    glutInitWindowSize( m_Size[0], m_Size[1]);

    glutInitDisplayMode(GLUT_RGB | GLUT_DOUBLE);

    if (glutCreateWindow("CGXOglWindow Phase3") == GL_FALSE) {
			exit(1);
    }

    glutReshapeFunc(Reshape);
    //glutKeyboardFunc(Key);
    glutDisplayFunc(DrawFunc);
 		glutSpecialFunc(SpecialKeyFunc);


    gpCurrentCGWindow = this;
}
	
/** Main loop to capture events */
int CGWindow::MainLoop()
{
	glutMainLoop();

	return 0;
}

/** Set the window title */
void CGWindow::SetTitle(char *title)
{
	glutSetWindowTitle(title);
}

/** Method to redraw the window */
void CGWindow::Draw()
{
	unsigned int ii;
	try {		
		ClearWindow();

		for (ii=0;ii<m_Actors.size();ii++) {
			m_Actors[ii]->Draw(this);
		}
		
		if (m_DrawingCallback) {
			(m_DrawingCallback)(this, m_DrawArg);
		}

		glFlush();
		glutSwapBuffers();
  } catch (int iErr) {
  	switch (iErr) {
  		case 1:
  			cerr << "Expose prior to widget initialization" << endl;
  			break;
  		default:
  			break;
  	}
  }
}

/** Base function used as an interface to drawing algorithms */
void CGWindow::DrawPoint(const CGPoint &pt)
{
	glBegin(GL_POINTS);
		glVertex3f(pt.x, pt.y, pt.z);
	glEnd();
}

/** Base function used as an interface to drawing algorithms */
void CGWindow::DrawPoints(const CGPointList &pts)
{
	unsigned int ii;
	glBegin(GL_POINTS);
  	for (ii=0;ii<pts.Size();ii++) {
			glVertex3f(pts.Get(ii).x, pts.Get(ii).y, pts.Get(ii).z);
		}
	glEnd();
}

/** Draw a line from pt1 to pt2 */
void CGWindow::DrawLine(const CGPoint &pt1, const CGPoint &pt2)
{
	glBegin(GL_LINES);
		glVertex3f(pt1.x, pt1.y, pt1.z);
		glVertex3f(pt2.x, pt2.y, pt2.z);
	glEnd();
}

/* Draw a line from pts[0] to pts[1] to pts[2] to pts[3] */
void CGWindow::DrawPolyline(const CGPointList &pts)
{
	unsigned int ii;
	glBegin(GL_LINE_STRIP);
  	for (ii=0;ii<pts.Size();ii++) {
			glVertex3f(pts.Get(ii).x, pts.Get(ii).y, pts.Get(ii).z);
		}
	glEnd();
}
/** Set the window size */
void CGWindow::SetSize(int w, int h)
{
	m_Size[0] = w;
	m_Size[1] = h;

	glViewport(0, 0, w, h);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	const CGPoint pt1 = m_WindowRect.GetLowerLeft();
	const CGPoint pt2 = m_WindowRect.GetUpperRight();
	glOrtho(pt1.x, pt2.x, pt1.y, pt2.y, pt1.z, pt2.z);
	glMatrixMode(GL_MODELVIEW);
}


/** Set the current color of the drawing system */
void CGWindow::SetColor(const CGColor &cgc)
{
  /* Set the OpenGL State. */
  glColor3f(cgc.Red, cgc.Green, cgc.Blue);

  this->Modified();
}

/** Set the Key Callback */
void CGWindow::SetIdleCallback(CGIdleCallback idleCallback, void *idleArg)
{
	m_IdleCallback = idleCallback;
	m_IdleArg = idleArg;
	if (idleCallback) {
		glutIdleFunc(IdleCallbackFunc);
	}
	else {
		glutIdleFunc(0);
	}
	this->Modified();
}

/** Timer call back function. */
void CGWindow::Tick()
{
		if (m_IdleCallback) {
			(m_IdleCallback)(this, m_IdleArg);
		}
}

/** Clear the drawing area. */
void CGWindow::ClearWindow(){
	glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
}

/** Draw a filled polygon, this function will close the shape */
void CGWindow::DrawPolygon(const CGPointList &pts)
{
	unsigned int ii;
	glBegin(GL_POLYGON);
  	for (ii=0;ii<pts.Size();ii++) {
			glVertex3f(pts.Get(ii).x, pts.Get(ii).y, pts.Get(ii).z);
		}
	glEnd();
}

/** Set the World coordinates for the window */
void CGWindow::SetWindow(const CGPoint pt1, const CGPoint pt2)
{
	m_WindowRect.SetRect(pt1, pt2);
	SetSize(m_Size[0], m_Size[1]);
}

/** Rotate the window about the origin by the specified angle */
void CGWindow::SetWindowAngle(double angle)
{
	glMatrixMode(GL_PROJECTION);
	glRotated(angle, 0,0,1);
	glMatrixMode(GL_MODELVIEW);
}

/** Generate a display list identifier */
unsigned int CGWindow::GenDisplayList()
{
	return glGenLists(1);
}

/** Destroy the display list */
void CGWindow::DeleteDisplayList(unsigned int id)
{
	glDeleteLists(id, 1);
}

/** Execute the display list */
void CGWindow::CallDisplayList(unsigned int id)
{
	glCallList(id);
}

/** Begin storing this display list */
void CGWindow::BeginDisplayList(unsigned int id)
{
	glNewList(id, GL_COMPILE_AND_EXECUTE);
}

/** Finish storign display list */
void CGWindow::EndDisplayList(unsigned int /*id*/)
{
	glEndList();
}
