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


#ifndef _LINE_CELL_H_
#define _LINE_CELL_H_

#include "PointSet.H"
#include "Cell.H"
#include "Renderer.H"

class LineCell : public Cell {
  private:

  public:
    LineCell() { UpdateTime();}
    ~LineCell() {}
    void Render(Renderer *aren);
    char *ObjectType() { return "LineCell"; }
    void PrintSelf(ostream& os);
    
};



#endif
