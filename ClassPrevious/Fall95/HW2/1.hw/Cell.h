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


#ifndef _CELL_H_
#define _CELL_H_

#include "VectorT.h"
#include "PointSet.h"

class Renderer;

class Cell : public VectorType<int> {
  public:
    Cell() {}
	// all these are virtual... everything should be subclassed!!
    virtual ~Cell() {}
    virtual void  Render(Renderer *rnd) = 0;
    virtual char *CellType() { return "AbstractCell"; }

	// set the point set that this cell is associated with
    void SetPoints(PointSet *pts) { PtSet = pts; }

    void SetColor(float R, float G, float B, float A) 
                 { red = R; green = G; blue = B; alpha = A;}
  protected:
	// only give friends and children access to the PtSet structure
    PointSet *PtSet;
    float red, green, blue, alpha;

};

#endif
