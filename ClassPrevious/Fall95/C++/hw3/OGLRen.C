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
  case TRIANGLE:
    glBegin(GL_TRIANGLES);
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
  for (i=0;i<this->CellSets.Count();i++) {
      this->CellSets[i]->Render(this);
  }
  glXSwapBuffers(this->DisplayId, this->WindowId);
}

void 
OGLRenderer::Initialize(int argc, char *argv[])
{
  int           index;
  static int	attributes[50];
  XVisualInfo   *v;
  XSetWindowAttributes swa;
  int           ms, dummy;
  static int dblbuf[] ={GLX_RGBA, GLX_DEPTH_SIZE, 8, 
			GLX_DOUBLEBUFFER, None};

  this->DisplayId = XOpenDisplay(NULL);
  if (!DisplayId) {
    cerr << "Could not open Display" << endl;
    exit(10);
  }

  v = glXChooseVisual(this->DisplayId, DefaultScreen(this->DisplayId),
		      dblbuf);

  this->ContextId = glXCreateContext(this->DisplayId, v, None, GL_TRUE);

  this->ColorMap = XCreateColormap(this->DisplayId, 
				   RootWindow(this->DisplayId, v->screen),
				   v->visual, AllocNone);
  swa.colormap = this->ColorMap;
  swa.border_pixel = 0;
  swa.event_mask = ExposureMask | ButtonPressMask | KeyPressMask |
                   StructureNotifyMask;

  this->WindowId = XCreateWindow(this->DisplayId,
				 RootWindow(this->DisplayId, v->screen),
				 this->x, this->y, this->width, this->height,
				 0, v->depth, InputOutput, v->visual,
				 CWBorderPixel | CWColormap | CWEventMask,
				 &swa);
  XSetStandardProperties(this->DisplayId, this->WindowId,
			 "OGLRen", "oglren", None, argv, argc, NULL);

  glXMakeCurrent(this->DisplayId, this->WindowId, this->ContextId);
  XMapWindow(this->DisplayId, this->WindowId);

  /* Init OpenGL */

  glClearColor (0.0, 0.0, 0.0, 0.0);
  glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  glEnable(GL_DEPTH_TEST);
  glShadeModel(GL_SMOOTH);

}

#ifdef _THIS_IS_NOT_DEFED_
void 
OGLRenderer::Initialize(int argc, char *argv[])
{
  int           index;
  static int	attributes[50];
  XVisualInfo   *v;
  int           ms;

  this->Top = XtAppInitialize ( &(this->App), "OGLRen", NULL, 0, 
			       &argc, argv, NULL, NULL, 0);

  this->DisplayId = XtDisplay(this->Top);
  
  // try getting exactly what we want
  index = 0;
  attributes[index++] = GLX_RGBA;
  attributes[index++] = GLX_DOUBLEBUFFER;
  attributes[index++] = GLX_DEPTH_SIZE;
  attributes[index++] = 8;
  attributes[index++] = None;

  v = glXChooseVisual(this->DisplayId, 
		      DefaultScreen(this->DisplayId), 
		      attributes );
  this->ColorMap = XCreateColormap(this->DisplayId,
				   RootWindow( this->DisplayId,  
					      v->screen),
				   v->visual, AllocNone );
  this->ContextId = glXCreateContext(this->DisplayId, v, None, GL_TRUE);

  XtVaSetValues(this->Top,
		XtNdepth, v->depth,
		XtNcolormap, this->ColorMap,
		XtNvisual, v,
		XtNx, this->x,
		XtNy, this->y,
		XtNwidth, this->width,
		XtNheight, this->height,
		NULL);

  XtRealizeWidget(this->Top);
  this->WindowId = XtWindow(this->Top);
  glXMakeCurrent(this->DisplayId, this->WindowId, this->ContextId);


  XtAddEventHandler(this->Top,
		    KeyPressMask ,
		    True,OGLRendererCB,(XtPointer)this);

//                  | ButtonPressMask | ExposureMask |
//		    StructureNotifyMask | ButtonReleaseMask | 
//		    FocusChangeMask | EnterWindowMask |
//		    LeaveWindowMask
  /* Init OpenGL */

  glClearColor (0.0, 0.0, 0.0, 0.0);
  glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  glEnable(GL_DEPTH_TEST);
  glShadeModel(GL_SMOOTH);
}
#endif

void 
OGLRenderer::MainLoop()
{
  XEvent event;
  static char buffer[20];
  KeySym key;
  XComposeStatus compose;
  int cnt;

  while(1) {
    do {
      XNextEvent(this->DisplayId, &event);
      switch (event.type) {
      case KeyPress:
	buffer[0] = NULL;
	cnt = XLookupString((XKeyEvent *)&event, buffer, 20, &key, &compose);
	this->Key(buffer[0], ((XKeyEvent *)&event)->x, ((XKeyEvent *)&event)->y);
	break;
      case ConfigureNotify:
	this->height = ((XConfigureEvent *)&event)->height;
	this->width = ((XConfigureEvent *)&event)->width;
	this->Reshape(this->width, this->height);
	this->Render();
	break;
      case Expose:
	if (((XExposeEvent *)&event)->count == 1)
	  this->Render();
	break;
      }
    } while(XPending(this->DisplayId));
  }
}

#ifdef _THIS_IS_NOT_DEFED_
void 
OGLRenderer::MainLoop()
{
    XtAppMainLoop(this->App);
}
#endif

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


void OGLRendererCB(Widget w,XtPointer client_data, 
		   XEvent *event, Boolean *ctd)
{
  OGLRenderer *aren = (OGLRenderer *)client_data;
  KeySym ks;
  static char buffer[24];
  
  switch(event->type) {
  case Expose :
    aren->Render();
    break;
  case ConfigureNotify:
    aren->width = ((XConfigureEvent *)event)->width;
    aren->height = ((XConfigureEvent *)event)->height;
    aren->Reshape(aren->width, aren->height);
    aren->Render();
    break;
  case KeyPress:
    buffer[0] = NULL;
    XLookupString((XKeyEvent *)event,buffer,20,&ks,NULL);
    aren->Key(buffer[0], 0, 0);
    break;
  }
}
