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


#ifndef _POLY_CELL_H_
#define _POLY_CELL_H_

#include "PointSet.H"
#include "Cell.H"
#include "Renderer.H"

class PolygonCell : public Cell {
  private:

  public:
    PolygonCell() {}
    ~PolygonCell() {}
    void Render(Renderer *aren);
    char *CellType() { return "PolygonCell"; }
    
};

void
PolygonCell::Render(Renderer *aren)
{
  aren->BeginDraw(Renderer::POLYGON);
  for (int i=0;i<this->Count();i++) {
    aren->Vertex((*PtSet)[(*this)[i]]->x, 
		   (*PtSet)[(*this)[i]]->y, 
		   (*PtSet)[(*this)[i]]->z);
  }
  aren->EndDraw();

}


#endif
