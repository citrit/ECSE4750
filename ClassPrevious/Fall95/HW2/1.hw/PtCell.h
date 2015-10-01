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


#ifndef _POINT_CELL_H_
#define _POINT_CELL_H_

#include "PointSet.h"
#include "Cell.h"
#include "Renderer.h"

class PointCell : public Cell {
  public:
    PointCell() {}
    ~PointCell() {}

	// define the virtual render function for the PointCell
    void Render(Renderer *aren);

	// and the cell type for inquiries
    char *CellType() { return "PointCell"; }
  private:

};

void
PointCell::Render(Renderer *aren)
{
	// we're drawing POINTS here...

  aren->BeginDraw(Renderer::POINT);
  aren->Color(red, green, blue, alpha);

	// feed it x,y,z for the point
  for (int i=0;i<this->Count();i++) {
    aren->Vertex((*PtSet)[(*this)[i]]->x, 
                 (*PtSet)[(*this)[i]]->y, 
                 (*PtSet)[(*this)[i]]->z);
  }
  aren->EndDraw();
}


#endif
