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

#ifndef _PARENT_OBJECT_H_
#define _PARENT_OBJECT_H_

#include <sys/types.h>
#include <time.h>
#include <iostream.h>


class ParentObject {
  protected:
    time_t ObjectTime;
    short  Debug;

  public:
    // Initialize the objects time, used in all derived classes
    ParentObject() { ObjectTime = time(NULL); Debug = 0; }
    // simple desctructor, make it virtual for derived classes
    virtual ~ParentObject() { }
    // Get the instance current time.
    time_t GetTime() { return ObjectTime; }
    // Used if this instance has been changed, usefull for pipelines
    void UpdateTime() { ObjectTime = time(NULL); }
    // Return the current objects type, redefined in all derived classes
    virtual char* ObjectType() { return "ParentObject"; }
    // Usefull little method to print our objects.
    virtual void PrintSelf(ostream& os) {
      os << "Object: " << ObjectType() << "\tTime: "
	   << GetTime() << endl;
    }
    // Set debugging for this class to on=1 or off=0
    void DebugOn() { Debug = 1; }
    void DebugOff() { Debug = 0; }
};

#endif
