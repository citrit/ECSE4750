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

#ifndef _PARENT_OBJECT_H_
#define _PARENT_OBJECT_H_

#include <sys/types.h>
#include <time.h>
#include <iostream.h>

class ParentObject {
  protected:
    time_t ObjectTime;

  public:
    ParentObject() { ObjectTime = time(NULL); }
    virtual ~ParentObject() { }
    time_t GetTime() { return ObjectTime; }
    void UpdateTime() { ObjectTime = time(NULL); }
    virtual char* ObjectType() { return "ParentObject"; }
    virtual void PrintSelf(ostream& os) {
      os << "Object: " << ObjectType() << "\tTime: "
	   << GetTime() << endl;
    }
  };

#endif
