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

#include <windows.h>

#include "cgobject.h"

#define MAX_LOADSTRING 128

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

	/** Set the window title */
	void SetKeyCallback(CGKeyCallback keyCallback, void *keyArg)
	{
		m_KeyCallback = keyCallback;
		m_KeyArg = keyArg;
		this->Modified();
	}
	
	/** Method to redraw the window */
  /** Base function used as an interface to drawing algorithms */
  void DrawPoint(int x, int y);;
  /** Callback that happens after internal drawing routine completes, */
  void SetDrawCallback(CGDrawingCallback cb, void *arg) {
		m_DrawingCallback = cb;
		m_DrawArg = arg;
		this->Modified();
	}
  /** Draw a line from (x1,y1) to (x2,y2) */
  void DrawLine(int x1, int y1, int x2, int y2);
  /** Set the window size */
  void SetSize(int w, int h);
  /** Set the current color of the drawing system */
  void SetColor(short r, short g, short b);
	void Draw();

private:

	// Foward declarations of functions included in this code module:
	ATOM				MyRegisterClass();
	BOOL				InitInstance(int);
	// Convenience functions.
	void SetInternalPen(HDC hdc, bool bSetToInternal);

	// Data members.
	HINSTANCE 				m_hInstance;					// current instance
	HWND							m_hWindow;
	TCHAR 						m_szTitle[MAX_LOADSTRING];	// The title bar text
	TCHAR 						m_szWindowClass[MAX_LOADSTRING];
	HPEN							m_hPen;
	HBRUSH						m_hBrush;

	CGKeyCallback			m_KeyCallback;
	CGDrawingCallback	m_DrawingCallback;
	void 							*m_KeyArg;
	void							*m_DrawArg;
	
};

#endif












