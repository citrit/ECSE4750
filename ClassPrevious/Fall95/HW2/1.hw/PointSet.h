//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used 
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    October 3, 1995
//
//////////////////////////////////////////////////////////////////////////


#ifndef _POINT_SET_H_
#define _POINT_SET_H_

#include "VectorT.h"

class PointType {
  public:
    double x, y, z;

	// generic constructor
    PointType() { x = y = z = 0; }

	// given an array of three floats, put them into the structure
    PointType(float pt[3]) 
      { x = pt[0]; y = pt[1]; z = pt[2]; }

	// a copy operator using the = sign
    PointType& operator=(float pt[3])
      { x = pt[0]; y = pt[1]; z = pt[2]; return *this; }

	// not sure why this exists... returns a pointer to the struct
    PointType& operator!(void) {return *this; }
};

typedef VectorType<PointType *> PointSet;

#endif
