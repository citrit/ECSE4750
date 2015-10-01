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

#include "PointSet.h"
#include "Cell.h"
#include "Renderer.h"

class PolygonCell : public Cell {
  public:
    PolygonCell() {}
    ~PolygonCell() {}

	// declare the render finction
    void Render(Renderer *aren);

	// declare the type in a simple manner if we ask for it
    char *CellType() { return "PolygonCell"; }
  private:
    
};

void
PolygonCell::Render(Renderer *aren)
{
	// begin the rendering of the polygon: sequence of points!!
  aren->BeginDraw(Renderer::POLYGON);
  aren->Color(red, green, blue, alpha);

	// loop over the vertices in the polygon
  for (int i=0; i<this->Count(); i++)
    {
	// call the renderer, feeding it an individual point
      aren->Vertex((*PtSet)[(*this)[i]]->x, 
                   (*PtSet)[(*this)[i]]->y, 
                   (*PtSet)[(*this)[i]]->z);
    }

	// end the rendering set
  aren->EndDraw();

}

#endif
