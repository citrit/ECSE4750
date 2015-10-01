/***************************************************************************
                          cgcoords.h  -  description                              
                             -------------------                                         
    begin                : Tue Mar 14 2000                                           
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
#ifndef CGCOORDS_H
#define CGCOORDS_H

#include <vector>

class CGPoint : public CGObject{
public:
	CGPoint(): x(0), y(0), z(0) { };
	CGPoint(double sx, double sy, double sz=0): x(sx), y(sy), z(sz) { };

	/** Coordinate values */
	double x, y, z;
};

class CGPointList : public CGObject {
public: // Public methods
  /** Add a point to the collection. */
  void AddPoint(CGPoint &pt) { m_Points.push_back(pt); }

  CGPoint& operator[] (int id) { return m_Points[id]; }

  /** Returns the size of the array */
  unsigned long Size() { return m_Points.size(); }

protected: // Protected attributes
  /** Vector on internal points. */
	std::vector<CGPoint> m_Points;
};

#endif // CGCOORDS_H
