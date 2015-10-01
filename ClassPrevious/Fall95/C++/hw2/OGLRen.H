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


#ifndef _OGLR_RENDERER_H_
#define _OGLR_RENDERER_H_

#include <stdlib.h>
#include <X11/Xlib.h>
#include <X11/Xutil.h>
#include <X11/Intrinsic.h>
#include <X11/Shell.h>
#include <X11/StringDefs.h>
#include "GL/gl.h"
#include "GL/glu.h"
#include "GL/glx.h"

#include "Renderer.H"

typedef void (*OGLRkeyboardCB) (unsigned char, int, int);

class OGLRenderer : public Renderer {
  protected:
    int x, y, width, height;
    Display *DisplayId;
    Window   WindowId;
    Widget   Top;
    XtAppContext App;
    Colormap ColorMap;
    GLXContext ContextId;
    OGLRkeyboardCB *KeyB;
    void Reshape(int w, int h);
    void Key(unsigned char key, int x, int y);
    friend void OGLRendererCB(Widget w,XtPointer client_data, 
			      XEvent *event, Boolean *ctd);

  public:
    OGLRenderer() { x = 10; y = 10; width = 400; height = 300;
		    Top = 0;App = 0;DisplayId = 0;}
    ~OGLRenderer() { }
    void BeginDraw(RenderType mode);
    void EndDraw();
    void Vertex(double x, double y, double z = 0);
//    void AddKeyHandler(OGLRkeyboardCB *keyb);
    void Initialize(int argc, char *argv[]);
    void MainLoop();
    void Render(void);

};

#endif

