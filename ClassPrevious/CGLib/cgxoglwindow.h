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

#include <GL/gl.h>
#include <GL/glut.h>

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
  void DrawPoint(const CGPoint &pt);
  /** Base function used as an interface to drawing algorithms */
  void DrawPoints(const CGPointList &pts);
  /** Callback that happens after internal drawing routine completes, */
  void SetDrawCallback(CGDrawingCallback cb, void *arg) {
		m_DrawingCallback = cb;
		m_DrawArg = arg;
		this->Modified();
	}
  /** Draw a line from pt1 to pt2 */
  void DrawLine(const CGPoint &pt1, const CGPoint &pt2);
  /** Set the window size */
  void SetSize(int w, int h);
  /** Set the current color of the drawing system */
  void SetColor(const CGColor &cgc);
  /** Timer call back function. */
  void Tick();
  /** Clear the drawing area. */
  void ClearWindow();
	
  /** Add a shape for drawing. */
  void AddActor(CGActor *act) { m_Actors.push_back(act); }
  /** Draw a line from 0 to 1 to 2 to 3 to 4 etc. */
  void DrawPolyline(const CGPointList &pts);
  /** Set the World coordinates for the window */
  void SetWindow(const CGPoint pt1, const CGPoint pt2);
  /** Draw a filled polygon, this function will close the shape */
  void DrawPolygon(const CGPointList &pts);
  /** Rotate the window about the origin by the specified angle */
  void SetWindowAngle(double angle);
  /** Destroy the display list */
  void DeleteDisplayList(unsigned int id);
  /** Generate a display list identifier */
  unsigned int GenDisplayList();
  /** Finish storign display list */
  void EndDisplayList(unsigned int id);
  /** Begin storing this display list */
  void BeginDisplayList(unsigned int id);
  /** Execute the display list */
  void CallDisplayList(unsigned int id);

	/** friend method to handle X Events */
	//friend void ExposeCB(Widget, XtPointer, XtPointer);

protected:

	CGKeyCallback m_KeyCallback;
	CGDrawingCallback m_DrawingCallback;
	CGIdleCallback m_IdleCallback;
	void 					*m_KeyArg;
	void					*m_DrawArg;
	void					*m_IdleArg;
	int 					m_Size[2];
	vector <CGActor*> m_Actors;
	CGRect				m_WindowRect;
};

#endif




































