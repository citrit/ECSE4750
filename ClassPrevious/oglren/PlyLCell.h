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


#ifndef _POLYLINE_CELL_H_
#define _POLYLINE_CELL_H_

#include "PointSet.h"
#include "Cell.h"
#include "Renderer.h"

class PolylineCell : public Cell {
  private:

  public:
    PolylineCell() {}
    ~PolylineCell() {}
    void Render(Renderer *aren);
    char *ObjectType() { return "PolygonCell"; }
    void PrintSelf(ostream& os);
    
};


#endif
