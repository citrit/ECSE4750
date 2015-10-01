
#ifndef _OGLR_RENDERER_H_
#define _OGLR_RENDERER_H_

#include <windows.h>
#include <stdlib.h>
#include "GL/wmesa.h"
#include "GL/gl.h"
#include "GL/glu.h"

#include "Renderer.H"
#include "OGLLight.H"
#include "OGLCam.H"

typedef void (*OGLRkeyboardCB) (unsigned char, int, int);

class OGLRenderer : public Renderer {
  protected:
  int x, y, width, height;
	HWND     hWnd;
	HDC      Hdc;
	HPALETTE hPalette;
	HINSTANCE hCurInstance;
	HINSTANCE hPrevInstance;
	WNDCLASS wndClass;
	WMesaContext ContextId;
	void Reshape(int w, int h);
	void Key(unsigned char key, int x, int y);
	LRESULT WndProc( UINT iMessage, WPARAM wParam, LPARAM lParam );
	friend LRESULT CALLBACK _export WndProc( HWND hWnd, UINT iMessage,
													WPARAM wParam, LPARAM lParam );
	long RealizePaletteNow();
	void ForceRedraw();

  public:
	 OGLRenderer() { x = 10; y = 10; width = 400; height = 300;
						  hWnd = NULL; Hdc = NULL; hPalette = NULL;
						  hCurInstance = NULL; hPrevInstance = NULL;}
	 ~OGLRenderer() { }
	 void BeginDraw(RenderType mode);
	 void EndDraw();
	 void Vertex(double x, double y, double z = 0);
//    void AddKeyHandler(OGLRkeyboardCB *keyb);
	 void SetMaterial(Material *mat);
    void SetInstance(HINSTANCE hCurI) { this->hCurInstance = hCurI; }
	 void Initialize(int argc, char *argv[]);
	 void MainLoop();
	 void Render(void);

};

#endif

