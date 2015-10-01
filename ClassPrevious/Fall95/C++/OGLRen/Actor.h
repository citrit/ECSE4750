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


#ifndef _ACTOR_H_
#define _ACTOR_H_

#include "PObject.h"
#include "VectorT.h"
#include "Cell.h"

class ActorBC : public VectorType<Cell *> {
  public:
    virtual void Render(Renderer *aren) = 0;
    char* ObjectType() { return "Actor"; }
    void PrintSelf(ostream& os) {
        os << ObjectType() << " ***** " << endl
           << "\tNumber of Cells: " << this->Count() << endl;
    }

};

#endif
