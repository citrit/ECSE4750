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
#include "OGLRen.h"

static OGLRenderer *currentRenderer;
static GLint Black, Red, Green, Blue;

void 
OGLRenderer::BeginDraw(RenderType mode)
{
	// we know what data type we are being called with, so thats
	// what we need GL to begin
  switch(mode) {
  case POINT:
    glBegin(GL_POINTS);
    break;
  case POLYGON:
    glBegin(GL_POLYGON);
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
	// simple: we finished the data, so end the item and flush teh
	// pipe!
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
OGLRenderer::Color(float r, float g, float b, float a)
{
//cout << "RBG: " << r << " " << g << " " << b << endl;
  glColor4f(r, g, b, a);
}

void 
OGLRenderer::Render(void)
{
  glClear((GLbitfield)(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT) );

//  cout << "Start Render" << endl;
  glMatrixMode(GL_MODELVIEW);

#ifdef CAMERA
    glLoadIdentity();
        // switch the position we are looking at the scene from...
    gluLookAt(eyex, eyey, eyez,
              centerx, centery, centerz,
              0.0, 1.0, 0.0);
#endif

  for (int i=0;i<this->DataSets.Count();i++) {
    for (int j=0;j<this->DataSets[i]->Count();j++) {
      (*(this->DataSets)[i])[j]->Render(this);
    }
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

#ifdef CAMERA
	// initialize the camera coordinates
    eyex=0.0;
    eyey=0.0;
    eyez=2.0;
    centerx=0.0;
    centery=0.0;
    centerz=0.0;
#endif

  /* Init OpenGL */
  static float lightPos[4] = {2.0, 4.0, 2.0, 1.0};
  static float lightDir[4] = {0.0, 0.0, 0.0, 1.0};
  static float lightAmb[4] = {1.0, 1.0, 1.0, 1.0};
       
//glEnable(GL_LIGHTING);
//glEnable(GL_LIGHT0);
  glLightfv(GL_LIGHT0, GL_POSITION, lightPos);
  glLightfv(GL_LIGHT0, GL_AMBIENT, lightAmb);
  

  glClearColor (0.0, 0.0, 0.0, 0.0);
  glColor3f(1.0, 1.0, 1.0);
  glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  glEnable(GL_DEPTH_TEST);
  glShadeModel(GL_SMOOTH);
  glClear((GLbitfield)(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT) );
}

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

#ifdef CAMERA
void
OGLRenderer::SetCamera(float ex, float ey, float ez,
                       float cx, float cy, float cz)
{
    eyex += ex;
    eyey += ey; 
    eyez += ez; 
    centerx += cx; 
    centery += cy; 
    centerz += cz; 
}
#endif


void
OGLRenderer::Key(unsigned char key, int x, int y)
{
  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  switch (key) {
#ifdef CAMERA
    case 'w': SetCamera( 0.5, 0.0, 0.0, 0.0, 0.0, 0.0); break;
    case 'q': SetCamera(-0.5, 0.0, 0.0, 0.0, 0.0, 0.0); break;
    case 's': SetCamera( 0.0, 0.5, 0.0, 0.0, 0.0, 0.0); break;
    case 'a': SetCamera( 0.0,-0.5, 0.0, 0.0, 0.0, 0.0); break;
    case 'x': SetCamera( 0.0, 0.0, 0.5, 0.0, 0.0, 0.0); break;
    case 'z': SetCamera( 0.0, 0.0,-0.5, 0.0, 0.0, 0.0); break;
    case 'r': SetCamera( 0.0, 0.0, 0.0, 0.5, 0.0, 0.0); break;
    case 'e': SetCamera( 0.0, 0.0, 0.0,-0.5, 0.0, 0.0); break;
    case 'f': SetCamera( 0.0, 0.0, 0.0, 0.0, 0.5, 0.0); break;
    case 'd': SetCamera( 0.0, 0.0, 0.0, 0.0,-0.5, 0.0); break;
    case 'v': SetCamera( 0.0, 0.0, 0.0, 0.0, 0.0, 0.5); break;
    case 'c': SetCamera( 0.0, 0.0, 0.0, 0.0, 0.0,-0.5); break;
#endif
  case 'h':
    glRotatef(10.0, 0.0,1.0,0.0);
    break;
  case 'j':
    glRotatef(10.0, 1.0,0.0,0.0);
    break;
  case 'k':
    glRotatef(-10.0, 1.0,0.0,0.0);
    break;
  case 'l':
    glRotatef(-10.0, 0.0,1.0,0.0);
    break;
  case '+':
    glTranslatef(0.0, 0.0, 1.0);
    break;
  case '-':
    glTranslatef(0.0, 0.0, -1.0);
    break;
  case 'n':
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
    XLookupString((XKeyEvent *)event,buffer,20,&ks,NULL);
    aren->Key(buffer[0], 0, 0);
    break;
  }
}
