/***************************************************************************
                          cgwindow.h  -  description                              
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


#ifndef CGWINDOW_H
#define CGWINDOW_H

#include <Xm/Xm.h>
#include <Xm/DrawingA.h>

#include "cgobject.h"
#include "cgactor.h"

/**
  *@author TC
  */
class CGWindow;

/** Callback for Keystrokes */
typedef void (*CGKeyCallback)(
    CGWindow * /* Calling Window */,
    char       /* KeyStroke */
);
/** Callback for Drawing function */
typedef void (*CGDrawingCallback)(
    CGWindow *   /* Calling Window */,
    void *       /* Client Call data */
);
/** Callback for Drawing function */
typedef void (*CGIdleCallback)(
    CGWindow *   /* Calling Window */,
    void *       /* Client Call data */
);

class CGWindow : public CGObject  {
public: 
	CGWindow();
	~CGWindow();
	
	/**
	  * Initialize the window system
	  */
	void Initialize(int argc, char *argv[]);
	
	/** Main loop to capture events */
	int MainLoop();
	
	/** Set the window title */
	void SetTitle(char *title);

	/** Set the Key Callback */
	void SetKeyCallback(CGKeyCallback keyCallback, void *keyArg)
	{
		m_KeyCallback = keyCallback;
		m_KeyArg = keyArg;
		this->Modified();
	}
	/** Set the Key Callback */
	void SetIdleCallback(CGIdleCallback idleCallback, void *idleArg);
	
	/** Method to redraw the window */
	void Draw();
	/** Base function used as an interface to drawing algorithms */
	void DrawPoint(double x, double y);
	/** Callback that happens after internal drawing routine completes, */
	void SetDrawCallback(CGDrawingCallback cb, void *arg) {
		m_DrawingCallback = cb;
		m_DrawArg = arg;
		this->Modified();
	}
	/** Draw a line from (x1,y1) to (x2,y2) */
	void DrawLine(double x1, double y1, double x2, double y2);
	/** Set the window size */
	void SetSize(int w, int h);
	/** Set the current color of the drawing system */
	void SetColor(short r, short g, short b);
	/** Timer call back function. */
	void Tick();
	/** Clear the drawing area. */
	void ClearWindow();
	
	/** Add a shape for drawing. */
	void AddActor(CGActor *act) { m_Actors.push_back(act); }

	/** friend method to handle X Events */
	//friend void ExposeCB(Widget, XtPointer, XtPointer);
	
private:

	/** Create a Grphics Context for the window */
	void setupGC();

	GC				m_GC;
	Widget 				m_Toplevel;
	Widget 				m_DrawingArea;
	XtAppContext 	  m_Context;
	CGKeyCallback     m_KeyCallback;
	CGDrawingCallback m_DrawingCallback;
	CGIdleCallback    m_IdleCallback;
	void 		  *m_KeyArg;
	void              *m_DrawArg;
	void		  *m_IdleArg;
	XtIntervalId	  m_IntervalID;
	int 		  m_Size[2];
	vector <CGActor*> m_Actors;
	
};

#endif


















