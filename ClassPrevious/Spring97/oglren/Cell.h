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
//  Date:    December 1996
//
//////////////////////////////////////////////////////////////////////////


#ifndef _CELL_H_
#define _CELL_H_

#include "PObject.h"
#include "VectorT.h"
#include "PointSet.h"
#include "MatSet.h"

class Renderer;

class Cell :  public VectorType<int> {
  protected:
    PointSet *PtSet;
    MaterialSet *MtSet;

  public:
    Cell() {}
    virtual ~Cell() {}
    // Virtual method for derived classes to fill out, they send messages
    // to the renderer on how to draw itself
    virtual void  Render(Renderer *aren) = 0;
    // Same old song
    char *ObjectType() { return "AbstractCell"; }
    // Define the Points and materials of the
    void SetPoints(PointSet *pts) { PtSet = pts; }
    void SetMaterials(MaterialSet *p) { MtSet = p; }
};

#endif
