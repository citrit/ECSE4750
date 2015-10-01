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


#ifndef _OGLR_RENDERER_H_
#define _OGLR_RENDERER_H_

#include <stdlib.h>
#include <windows.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glaux.h>
// #include <GL/glut.h>

#include "Renderer.h"
#include "OGLLight.h"
#include "OGLCam.h"
#include "OGLActor.h"

typedef void (*OGLRkeyboardCB) (unsigned char, int, int);

class OGLRenderer : public Renderer {
  protected:
   HGLRC   ContextId;
   HDC     DeviceContext;
   HWND    WindowId;
   HWND    NextWindowId;
   WNDPROC OldProc;
   HANDLE  hInstance;


    void    Reshape(int w, int h);
    void    Key(unsigned char key, int x, int y);

  public:
    OGLRenderer() { x = 10; y = 10; width = 400; height = 300; }
    ~OGLRenderer() { }
    char *ObjectType() { return "OGLWin32Renderer"; }
    void BeginDraw(RenderType mode);
    void EndDraw();
    void Vertex(double x, double y, double z = 0);
    void Initialize(int argc, char *argv[]);
    void MainLoop();
    void Render();

    void SetMaterial(Material *mat);

    friend LRESULT CALLBACK HandleMessage(HWND hWnd,UINT uMsg,
                               WPARAM wParam, LPARAM lParam);

};

#endif

