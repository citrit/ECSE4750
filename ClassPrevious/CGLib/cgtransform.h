/***************************************************************************
                          cgtransform.h  -  description                              
                             -------------------                                         
    begin                : Wed Mar 15 2000                                           
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


#ifndef CGTRANSFORM_H
#define CGTRANSFORM_H

#include "cgobject.h"
#include "cgcoords.h"

/**Transform class to handle matrix transforms
  *@author Tom Citriniti
  */

#ifndef M_PI
#define M_PI 3.14158265358979323846
#endif

class CGTransform;

class CGMatrix3x3 : public CGObject {
public:
	CGMatrix3x3();
	~CGMatrix3x3();
	
  /** Set Internal matrix to identity */
  void SetIdentity();
  /** Multiplies the internal matrix by trans stores the value internal */
  void PreMultiply(CGMatrix3x3 &trans);

  friend CGTransform;
protected:
	double m_Data[3][3];
};


class CGTransform : public CGObject  {
public: 
	CGTransform();
	~CGTransform();

  /** Translate the matrix by tx and ty */
  void Translate(double tx, double ty);
  /** Scale the object by sx and sy using refPt */
  void Scale(double sx, double sy, CGPoint &refPt);
  /** Rotate the object using a around refPt */
  void Rotate(double a, CGPoint &refPt);
  /** Translate the points using the internal matrix in place */
  void TransformPoints(CGPointList &pts);
	/** Convert degrees to Radians */
	double DegreesToRadians(double angle) { return angle*M_PI/180.0; }

protected: // Protected attributes
  /** The internal transform matrix */
  CGMatrix3x3 m_Matrix;
};

#endif















