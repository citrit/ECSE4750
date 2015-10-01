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


/* Includes required */
#include "OGLRen.H"

void 
OGLRenderer::BeginDraw(RenderType mode)
{
  switch(mode) {
  case POINT:
    glBegin(GL_POINTS);
    break;
  case POLYGON:
    glBegin(GL_POLYGON);
	 break;
  case POLYLINE:
    glBegin(GL_LINE_STRIP);
    break;
  case LINE:
    glBegin(GL_LINES);
    break;
  default:
    break;
  }
}

void
OGLRenderer::EndDraw()
{
  glEnd();
  glFlush();
}

void 
OGLRenderer::Vertex(double x, double y, double z)
{
//  cout << x << " " << y << " " << z << endl;
  glVertex3f(x, y, z);
}

void 
OGLRenderer::Render()
{
  glClear((GLbitfield)(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT) );

  this->CameraSet[0]->Render(this);
  for (int i = 0;i < this->LightSet.Count();i++) {
    this->LightSet[i]->Render(this);
  }

  glMatrixMode(GL_MODELVIEW);
  for (i=0;i<this->DataSets.Count();i++) {
		this->DataSets[i]->Render(this);
  }
  WMesaSwapBuffers();
}

void 
OGLRenderer::Initialize(int argc, char *argv[])
{

	static char szAppName[] = "WOGLRen" ;

	if (! this->hPrevInstance) {
		wndClass.style          = CS_HREDRAW | CS_VREDRAW ;
		wndClass.lpfnWndProc    = ::WndProc ;
		wndClass.cbClsExtra     = 0;
		wndClass.cbWndExtra     = sizeof( OGLRenderer * );
		wndClass.hInstance      = this->hCurInstance ;
		wndClass.hIcon          = LoadIcon (NULL, IDI_APPLICATION) ;
		wndClass.hCursor        = LoadCursor (NULL, IDC_ARROW);
		wndClass.hbrBackground  = GetStockObject (WHITE_BRUSH);
		wndClass.lpszMenuName   = NULL ;
		wndClass.lpszClassName  = szAppName ;

		RegisterClass (&wndClass) ;
	}

	hWnd = CreateWindow (szAppName,           /* window class name 	*/
							  "WOGL Renderer",      /* window caption    	*/
							  WS_OVERLAPPEDWINDOW,  /* window style      	*/
							  this->x,              /* initial x position	*/
							  this->y,              /* initial y position	*/
							  this->width,          /* initial x size  		*/
							  this->height,         /* initial y size .		*/
							  NULL,                 /* parent window handle-*/
							  NULL,                 /* window menu handle-  */
							  this->hCurInstance,   /* program instance handle*/
							  (LPSTR) this) ;       /* creation parameters-  */


	ShowWindow (this->hWnd, argc) ;
	ContextId=WMesaCreateContext(hWnd,hPalette,GL_TRUE,GL_TRUE);
	WMesaMakeCurrent(ContextId);
	UpdateWindow (this->hWnd) ;

  /* Init OpenGL */

//  glClearColor (0.0, 0.0, 0.0, 0.0);
//  glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//  glEnable(GL_DEPTH_TEST);
//  glShadeModel(GL_SMOOTH);

}

void
OGLRenderer::MainLoop()
{
	MSG         msg;

	while (GetMessage (&msg, NULL, 0, 0)) {
			TranslateMessage (&msg) ;
			DispatchMessage (&msg) ;
	}
//	return msg.wParam;

}

void
OGLRenderer::Key(unsigned char key, int x, int y)
{

  switch (key) {
  case 'h':
    this->CameraSet[0]->Rotate(15.0, 0.0,1.0,0.0);
	 break;
  case 'j':
    this->CameraSet[0]->Rotate(15.0, 1.0,0.0,0.0);
    break;
  case 'k':
    this->CameraSet[0]->Rotate(-15.0, 1.0,0.0,0.0);
	 break;
  case 'l':
    this->CameraSet[0]->Rotate(-15.0, 0.0,1.0,0.0);
	 break;
  case '+':
    this->CameraSet[0]->Translate(0.0, 0.0, 1.0);
    break;
  case '-':
    this->CameraSet[0]->Translate(0.0, 0.0, -1.0);
    break;
  case 'r':
	 this->Reshape(this->width, this->height);
    break;
  case 27:           /* Esc will quit */
    exit(1);
    break;
  default:
    break;
  }
  Render();
}

void 
OGLRenderer::Reshape(int w, int h)
{
  this->width = w;
  this->height = h;
  glViewport (0, 0, w, h);            /*  define the viewport */
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();
  gluPerspective(25.0, 1.0*(GLfloat)w/(GLfloat)h, 0.1, 100.0);
  glTranslatef (0.0, 0.0, -5.0);     /*  viewing transformation      */
  glMatrixMode (GL_MODELVIEW);        /* back to modelview matrix  */
}

void
OGLRenderer::SetMaterial(Material *mat)
{

  glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, 
	       (const GLfloat*)&(mat->Ambient.RGBA[0]));
  glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE,
			 (const GLfloat*)&(mat->Diffuse.RGBA[0]));
  glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, 
			 (const GLfloat*)&(mat->Specular.RGBA[0]));

}

long
OGLRenderer::RealizePaletteNow( )
{
	 long Result = -1;
	 if ( NULL != SelectPalette( this->Hdc, this->hPalette, FALSE ) )
	 {
		Result = RealizePalette( this->Hdc );
		WMesaPaletteChange(this->hPalette);
	 }
	 return( Result );
}

void
OGLRenderer::ForceRedraw()
{
	 MSG Message;

	 if (!PeekMessage(&Message, this->hWnd, WM_PAINT, WM_PAINT, PM_NOREMOVE) )
	 {
		  InvalidateRect( this->hWnd, NULL, FALSE );
	 }
}

/*** Message Handler

***/

LRESULT
OGLRenderer::WndProc (UINT iMessage, WPARAM wParam, LPARAM lParam)
{
	PAINTSTRUCT ps;
	int			i;
	char        msg[80];
	static HFONT       hfontB, hfontOld;
	static HFONT       hfontS;
	HPEN        hpen, hpenOld;
	static RECT rTextArea = { 0, 0, 550, 355 };

	switch (iMessage) {
	case WM_CREATE:
		  return 0;

	case WM_SIZE:
		  this->width  = LOWORD(lParam);
		  this->height = HIWORD(lParam);
		  this->Reshape( this->width, this->height);
		  return (0);

	case WM_PAINT:
		  this->Hdc = BeginPaint(this->hWnd, &ps);
		  this->Render();
		  EndPaint(this->hWnd, &ps);
		  return 0;

	case WM_COMMAND:
		  switch (wParam) {

		  case 100:
				 PostQuitMessage (0);
				 break;
		  }
		  return 0;

	case WM_MOVE:
		  this->x = LOWORD(lParam);
		  this->y = HIWORD(lParam);
		  return 0;

	case WM_CHAR:
		  this->Key(wParam, 0, 0);
		  this->Render();
		  return 0;

	 case WM_PALETTECHANGED:
		  if ( this->hWnd != (HWND) wParam )
			 RealizePaletteNow();
		  return (0);

	 case WM_QUERYNEWPALETTE:

	 // In the foreground!  Let RealizePaletteNow do the work--
	 // if management of the static system color usage is needed,
	 // RealizePaletteNow will take care of it.

		  if ( NULL != this->hPalette )
		  {
				if ( RealizePaletteNow() > 0 )
					 ForceRedraw( );

				return (1);
		  }

		  return (0);

	 case WM_ACTIVATE:

	 // If the window is going inactive, the palette must be realized to
	 // the background.  Cannot depend on WM_PALETTECHANGED to be sent since
	 // the window that comes to the foreground may or may not be palette
	 // managed.

		  if ( LOWORD(wParam) == WA_INACTIVE )
		  {
				if ( NULL != this->hPalette )
				{
				// Realize as a background palette.  Need to call
				// RealizePaletteNow rather than RealizePalette directly to
				// because it may be necessary to release usage of the static
				// system colors.

					 if ( RealizePaletteNow() > 0 )
						  ForceRedraw();
				}
		  }

	 // Allow DefWindowProc() to finish the default processing (which includes
	 // changing the keyboard focus).

		  break;

	case WM_DESTROY:
		  PostQuitMessage (0);
		  return 0;
	}

	return DefWindowProc(this->hWnd, iMessage, wParam, lParam);
}

LRESULT  CALLBACK _export WndProc( HWND hWnd, UINT iMessage, WPARAM wParam,
											LPARAM lParam )
{
	OGLRenderer *aren = (OGLRenderer *) GetWindowLong( hWnd, 0 );
	 if ( aren == 0 )
	 {
		  if ( iMessage == WM_CREATE )
		  {
				LPCREATESTRUCT lpcs;

				lpcs = (LPCREATESTRUCT) lParam;
				 aren = (OGLRenderer *) lpcs->lpCreateParams;

				// Store a pointer to this object in the window's extra bytes;
				// this will enable us to access this object (and its member
				// functions) in WndProc where we are
				// given only a handle to identify the window.
				SetWindowLong( hWnd, 0, (LONG)aren );
				// Now let the object perform whatever
				// initialization it needs for WM_CREATE in its own
				// WndProc.
		return aren->WndProc( iMessage, wParam, lParam );
		  }
		  else
				return DefWindowProc( hWnd, iMessage, wParam, lParam );
	 }
	 else
		  return aren->WndProc( iMessage, wParam, lParam );
}

