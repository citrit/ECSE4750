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


#ifndef _RENDERER_H_
#define _RENDERER_H_

#include "VectorT.H"
#include "DataSet.H"

class Renderer {
  protected:
    VectorType<DataSet *> DataSets;

  public:
    enum RenderType { POLYGON, LINE, POINT };

    Renderer() { }
    virtual ~Renderer() { }
    void AddDataSet(DataSet *ds) { DataSets += ds; }

    virtual void BeginDraw(RenderType mode) = 0;
    virtual void EndDraw() = 0;
    virtual void Vertex(double x, double y, double z = 0) = 0;
    
    virtual void Initialize(int argc, char *argv[]) = 0;
    virtual void MainLoop() = 0;
};



#endif
