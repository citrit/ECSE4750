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


#ifndef _DATA_TYPE_H_
#define _DATA_TYPE_H_

#include "PObject.H"

class DataObject : public ParentObject {
  private:
  protected:
  public:
    DataObject() { }
    ~DataObject() { }
    char *ObjectType() { return "DataObject"; }

  };


#endif
