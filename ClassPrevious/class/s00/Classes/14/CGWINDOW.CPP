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


#include "cgwindow.h"


/** Expose event callback to redraw things. */
void ExposeCB(Widget w,XtPointer client_data,XtPointer call_data)
{
	 CGWindow *me = (CGWindow *)client_data;
	 me->Draw();
}

/** Constructor Destructor */
CGWindow::CGWindow()
: m_GC(0),
	m_Toplevel(0),
	m_DrawingArea(0),
	m_Context(0),
	m_KeyCallback(NULL),
	m_DrawingCallback(NULL),
	m_KeyArg(NULL),
	m_DrawArg(NULL)
{
}
CGWindow::~CGWindow()
{

}

/**
  * Initialize the window system
  */
void CGWindow::Initialize(int argc, char *argv[])
{
	Arg al[10];
	int ac;

	/* create the toplevel shell */
  ac=0;
  XtSetArg(al[ac],XtNtitle,"CGWindow Phase1"); ac++;
  m_Toplevel = XtAppInitialize(&m_Context,"",NULL,0,&argc,argv,
                             NULL,al,ac);

  /* set window size. */
  ac=0;
  XtSetArg(al[ac],XmNheight,300); ac++;
  XtSetArg(al[ac],XmNwidth,300); ac++;
  XtSetValues(m_Toplevel,al,ac);

  /* create a drawing area widget. */
  ac=0;
  m_DrawingArea=XmCreateDrawingArea(m_Toplevel,"drawing_area",al,ac);
  XtManageChild(m_DrawingArea);

  setupGC();

	XtAddCallback(m_DrawingArea,XmNexposeCallback,ExposeCB,(XtPointer)this);

}
	
/** Main loop to capture events */
int CGWindow::MainLoop()
{
	//setup_gc(&di);

	XtRealizeWidget(m_Toplevel);
	XtAppMainLoop(m_Context);

	return 0;
}

void CGWindow::setupGC()
{
  int foreground,background;
  XGCValues vals;
  Arg al[10];
  int ac;

  /* get the current fg and bg colors. */
  ac=0;
  XtSetArg(al[ac],XmNforeground,&foreground); ac++;
  XtSetArg(al[ac],XmNbackground,&background); ac++;
  XtGetValues(m_DrawingArea,al,ac);

  /* create the gc. */
  vals.foreground = foreground;
  vals.background = background;
  m_GC=XtGetGC(m_DrawingArea,GCForeground | GCBackground,&vals);
}

/** Set the window title */
void CGWindow::SetTitle(char *title)
{

	XtVaSetValues(m_Toplevel,
								XtNtitle, title,
								NULL);
}

/** Method to redraw the window */
void CGWindow::Draw()
{
	static unsigned int isCnt(0);
	try {
		if (!m_Toplevel) throw 1;
		cout << "Time to draw at time: " << isCnt++ << endl;

		if (m_DrawingCallback) {
			(m_DrawingCallback)(this, m_DrawArg);
		}

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
void CGWindow::DrawPoint(int x, int y)
{

	XDrawPoint(XtDisplay(m_DrawingArea),XtWindow(m_DrawingArea),
  	                    m_GC, x, y);

}

/** Draw a line from (x1,y1) to (x2,y2) */
void CGWindow::DrawLine(int x1, int y1, int x2, int y2)
{

	XDrawLine(XtDisplay(m_DrawingArea),XtWindow(m_DrawingArea),
  	                    m_GC, x1, y1, x2, y2);

}

/** Set the window size */
void CGWindow::SetSize(int w, int h)
{
  Arg al[10];
  int ac;

  /* set window size. */
  ac=0;
  XtSetArg(al[ac],XmNheight,h); ac++;
  XtSetArg(al[ac],XmNwidth,w); ac++;
  XtSetValues(m_Toplevel,al,ac);

}


/** Set the current color of the drawing system */
void CGWindow::SetColor(short r, short g, short b)
{
	Display *dpy = XtDisplay(m_Toplevel);
	int scr = DefaultScreen(dpy);
	Colormap cmap = DefaultColormap(dpy, scr);
 	XColor color;
  XGCValues vals;

	color.red = r;
	color.green = g;
	color.blue = b;

	if (!XAllocColor(dpy, cmap, &color)) {
		cerr << "Unable to allocate color (" << r << "," << g << "," << b << ")." << endl;
		color.pixel = BlackPixel(dpy, scr);
	}	

  /* Set the gc. */
  if (m_Debug) {
	  cout << "Setting the current color to pixel: "<< color.pixel << endl;
	}
  vals.foreground = color.pixel;
  XChangeGC(dpy, m_GC, GCForeground, &vals);

  this->Modified();

}




