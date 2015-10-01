/***************************************************************************
                          cgpointshape.cpp  -  description                              
                             -------------------                                         
    begin                : Thu Mar 23 2000                                           
    copyright            : (C) 2000 by Tom Citriniti                         
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


#include "cgpointshape.h"
#ifdef _WIN32
#include "cgmswindow.h"
#else
#include "cgxwindow.h"
#endif

CGPointShape::CGPointShape(){
}
CGPointShape::~CGPointShape(){
}

/** Method called when update requested */
void CGPointShape::Draw(CGWindow *cgw, CGTransform &cgt)
{
	unsigned int	ii;
	CGPointList pts(*m_Points);
	
	cgt.TransformPoints(pts);
	
	for (ii=0;ii<m_vNodeList.size();ii++) {
		if (m_Debug)
			cout << "Point: " << pts[m_vNodeList[ii]].x << "," << pts[m_vNodeList[ii]].y << endl;
		cgw->DrawPoint(pts[m_vNodeList[ii]].x,pts[m_vNodeList[ii]].y);
	}
}










