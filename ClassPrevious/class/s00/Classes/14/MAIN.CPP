/***************************************************************************
                          main.cpp  -  description                              
                             -------------------                                         
    begin                : Fri Feb 11 20:07:44 EST 2000
                                           
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

#ifdef HAVE_CONFIG_H
#include <config.h>
#endif

#include <iostream.h>
#include <stdlib.h>
#ifdef _WIN32
#include "cgmswindow.h"
#define RGB_RANGE 255
#else
#include "cgwindow.h"
#include <unistd.h>
#define RGB_RANGE 65535
#endif

void myDraw(CGWindow *cgw, void *arg)
{
 	cout << "Draw called: " << (int)arg << endl;

	for (int i=10;i<200;i++) {
		cgw->SetColor((short)i*(RGB_RANGE/200),0,0);
		cgw->DrawPoint(i,i-10);
		cgw->DrawPoint(i,i-9);
		cgw->DrawPoint(i,i-8);
		cgw->DrawPoint(i,i-7);
	}
}

#ifdef _WIN32
int WINAPI WinMain(
  HINSTANCE argv,      // handle to current instance
  HINSTANCE hPrevInstance,  // handle to previous instance
  LPSTR lpCmdStr,          // command line
  int argc              // show state
)
#else
int main(int argc, char** argv)
#endif
{
	CGWindow *cgw = new CGWindow();
	cout << "cgw Object creation time: " << cgw->GetModifiedTime() << endl;
	cgw->Initialize(argc, (char **)argv);
	CGObject *cgo = new CGObject();
	cout << "cgo Object creation time: " << cgo->GetModifiedTime() << endl;
	cgw->Modified();
	cgo->Modified();
	cout << "Hello, World!" << endl;
	cout << "cgw New updatetime: " << cgw->GetModifiedTime() << endl;
	cout << "cgo New updatetime: " << cgo->GetModifiedTime() << endl;
	cgw->SetTitle("Ain't this cool");
	cgw->SetDrawCallback(myDraw, (void *) 99);
	cgw->SetSize(320,240);
	return cgw->MainLoop();
}


























