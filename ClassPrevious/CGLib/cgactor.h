/***************************************************************************
                          cgactor.h  -  description                              
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


#ifndef CGACTOR_H
#define CGACTOR_H

#include <vector>

#include "cgobject.h"
#include "cgtransform.h"
#include "cgshape.h"

/**Container for holding shapes and transforms
  *@author Tom Citriniti
  */

class CGActor : public CGObject  {
public:
	CGActor();
	~CGActor();
	
  /** Scale the object about the fixed point refPt */
  void Scale(double sx, double sy, CGPoint &refPt);;
  /** Translate the object */
  void Translate(double tx, double ty);
  /** Rotate the object about the reference point */
  void Rotate(double angle, CGPoint &refPt);
  /** Time to draw */
  void Draw(CGWindow *cgw);
  /** Add a shape to the actors collection */
  void AddShape(CGShape *shp) { m_vShapes.push_back(shp); }

protected:
	vector <CGShape *> 			m_vShapes;
	CGTransform							m_Trans;
};

#endif









