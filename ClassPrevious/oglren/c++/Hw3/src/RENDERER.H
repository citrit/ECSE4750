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

#include "PObject.H"
#include "VectorT.H"
#include "CellSet.H"
#include "Light.H"
#include "Camera.H"
#include "MatSet.H"

class Renderer: public ParentObject {
  protected:
    VectorType<CellSet *> CellSets;
    VectorType<Light *> LightSet;
    VectorType<Camera *> CameraSet;

  public:
    int x, y, width, height;
    enum RenderType { POLYGON, LINE, TRIANGLE, LINE_LOOP, POINT, POLYLINE };

    Renderer() { }
    virtual ~Renderer() { }
    char *ObjectType() { return "Renderer"; }
    void AddCellSet(CellSet *ds) { CellSets += ds; }
    void AddLight(Light *lght) { LightSet += lght; }
    void AddCamera(Camera *cam) { CameraSet += cam; }

    virtual void BeginDraw(RenderType mode) = 0;
    virtual void EndDraw() = 0;
    virtual void Vertex(double x, double y, double z = 0) = 0;
    virtual void Render() = 0;
    
    virtual void Initialize(int argc, char *argv[]) = 0;
    virtual void MainLoop() = 0;

    virtual void SetMaterial(Material *mat) = 0;
};

#endif
