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


#ifndef _CELL_H_
#define _CELL_H_

#include "PObject.H"
#include "VectorT.H"
#include "PointSet.H"
#include "MatSet.H"

class Renderer;

class Cell :  public VectorType<int> {
  protected:
    PointSet *PtSet;
    MaterialSet *MtSet;

  public:
    Cell() {}
    virtual ~Cell() {}
    virtual void  Render(Renderer *aren) = 0;
    char *ObjectType() { return "AbstractCell"; }
    void SetPoints(PointSet *pts) { PtSet = pts; }
    void SetMaterials(MaterialSet *p) { MtSet = p; }
};

#endif
