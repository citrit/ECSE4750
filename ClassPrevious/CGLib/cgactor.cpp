/***************************************************************************
                          cgactor.cpp  -  description                              
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


#include "cgactor.h"

CGActor::CGActor(){
}
CGActor::~CGActor(){
}


/** Time to draw */
void CGActor::Draw(CGWindow *cgw){
	unsigned int ii;
	
	for (ii=0;ii<m_vShapes.size();ii++) {
		m_vShapes[ii]->Draw(cgw, m_Trans);
	}
}

/** Scale the object about the fixed point refPt */
void CGActor::Scale(double sx, double sy, CGPoint &refPt)
{
	m_Trans.Scale(sx, sy, refPt);
}
/** Translate the object */
void CGActor::Translate(double tx, double ty)
{
	m_Trans.Translate(tx, ty);
}
/** Rotate the object about the reference point */
void CGActor::Rotate(double angle, CGPoint &refPt)
{
	m_Trans.Rotate(angle, refPt);
}





