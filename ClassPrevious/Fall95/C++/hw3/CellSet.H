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


#ifndef _CELL_SET_H_
#define _CELL_SET_H_

#include "PObject.H"
#include "VectorT.H"
#include "Cell.H"

class CellSet : public VectorType<Cell *> {
  public:
    char *ObjectType() { return "CellSet"; }
    virtual void Render(Renderer *aren);

  };

#endif
