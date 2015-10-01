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

#include "PointSet.H"
#include "Cell.H"
#include "Renderer.H"

class PointCell : public Cell {
  private:

  public:
    PointCell() {}
    ~PointCell() {}
    void Render(Renderer *aren);
    char *CellType() { return "PointCell"; }
    
};

void
PointCell::Render(Renderer *aren)
{
  aren->BeginDraw(Renderer::POINT);
  for (int i=0;i<this->Count();i++) {
    aren->Vertex((*PtSet)[(*this)[i]]->x, 
		   (*PtSet)[(*this)[i]]->y, 
		   (*PtSet)[(*this)[i]]->z);
  }
  aren->EndDraw();
}


#endif
