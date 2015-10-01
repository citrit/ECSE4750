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


#ifndef _TRIANGLE_CELL_H_
#define _TRIANGLE_CELL_H_

#include "PointSet.h"
#include "Cell.h"
#include "Renderer.h"

class TriangleCell : public Cell {
  public:
    TriangleCell() {}
    ~TriangleCell() {}

	// declare the render function
    void Render(Renderer *aren);

	// declare the type in a simple manner if we ask for it
    char *CellType() { return "TriangleCell"; }
  private:
    
};

void
TriangleCell::Render(Renderer *aren)
{
  if (this->Count() != 4) 
      cout << "This triangle does not have three points, 
               therefore it can not be a triangle" << endl;
	// begin the rendering of the triangle: sequence of points!!
	// yes, a triangle is a polygon
  aren->BeginDraw(Renderer::POLYGON);
  aren->Color(red, green, blue, alpha);
  aren->Color(1.0, 0.0, 0.0, alpha);

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
