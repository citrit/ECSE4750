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
#include "cgxwindow.h"
#include <unistd.h>
#define RGB_RANGE 65535
#endif
#include "cgtransform.h"
#include "cgcoords.h"
#include "cgpointshape.h"
#include "cgactor.h"

void myAnimate(CGWindow *cgw, void *arg)
{
	static int cnt(0);
	CGActor *pAct = (CGActor *)arg;
	CGPoint pt(100,100);
	pAct->Rotate(5,pt);
	cout << "Current step: " << cnt << endl;
	if (++cnt > 50)
		cgw->SetIdleCallback(NULL, NULL);
	cgw->Draw();
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
 	CGPointList pts;
 	CGPoint pt;
 	pt.x = 50; pt.y = 50;
 	pts.AddPoint(pt);
 	pt.x = 150; pt.y = 50;
 	pts.AddPoint(pt);
 	pt.x = 100; pt.y = 150;
 	pts.AddPoint(pt);
 	pt.x = 100; pt.y = 100;
 	pts.AddPoint(pt);

	CGWindow *cgw = new CGWindow();
	cout << "cgw Object creation time: " << cgw->GetModifiedTime() << endl;
	cgw->Initialize(argc, (char **)argv);
	cgw->SetTitle("Ain't this cool");
	// Create a point object
	CGPointShape ptShape;
	ptShape.SetPoints(&pts);
	ptShape.AddNode(0);
	ptShape.AddNode(1);
	ptShape.AddNode(2);
	//cgw->SetDrawCallback(myDraw, (void *) &pts);
	CGActor act;
	act.AddShape(&ptShape);
	cgw->AddActor(&act);
	cgw->SetIdleCallback(myAnimate, (void *) &act);
	cgw->SetSize(400,400);
	return cgw->MainLoop();
}











































