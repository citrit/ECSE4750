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


#ifndef _POINT_CELLH_
#define _POINT_CELLH_

#include "PointSet.h"
#include "Cell.h"
#include "Renderer.h"

class PointCell : public Cell {
  private:

  public:
    PointCell() {}
    ~PointCell() {}
    void Render(Renderer *aren);
    void PrintSelf(ostream& os);
    char *ObjectType() { return "PointCell"; }
    
};



#endif
