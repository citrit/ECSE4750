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
#include "OGLWin32Ren.h"

static char *lpszClassName = "OpenGLWin32Renderer";

// Fix C calling conventions
void mygluCombine(GLdouble coords[3], void * vertex_data[4], GLfloat weight[4], void **outData) {
	GLdouble *newv = new GLdouble[3];
	newv[0] = coords[0];newv[1] = coords[1];newv[2] = coords[2];
	*outData = newv;
	cout << " Call to combine " << endl;
}
void mygluError(GLenum err) {
	cout << "MyError: " << endl;
	cout << "Error: " << gluErrorString(err) << endl;
}
// new_vertex
// Globals 
//
OGLRenderer::OGLRenderer() { x = 10; y = 10; width = 400; height = 300; 
					gluTess = gluNewTess ();
					gluTessCallback(gluTess, GLU_TESS_BEGIN, (void (_stdcall *) ())&glBegin);  
					gluTessCallback(gluTess, GLU_TESS_VERTEX, (void (_stdcall *) ())&glVertex3dv);  
					gluTessCallback(gluTess, GLU_TESS_END, glEnd);
					gluTessCallback(gluTess, GLU_ERROR, (void (_stdcall *) ())&mygluError);
					//gluTessCallback(gluTess, GLU_TESS_COMBINE, (void (_stdcall *) ())&mygluCombine);
}

OGLRenderer::~OGLRenderer() { gluDeleteTess(gluTess); }


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
  case TESSPOLYGON:
 	gluTessBeginPolygon(gluTess, NULL);
	//gluNextContour(gluTess, GLU_EXTERIOR);
	gluTessBeginContour(gluTess);
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
  case LINE_LOOP:
    glBegin(GL_LINE_LOOP);
    break;
  default:
    break;
  }
  renMode = mode;
}

void
OGLRenderer::BeginDL(unsigned int& dlnum)
{
	if (glIsList(dlnum))
		glDeleteLists(dlnum, 1);
	dlnum = glGenLists((GLsizei)1);
	glNewList(dlnum, GL_COMPILE_AND_EXECUTE);
//	BeginDraw(mode);
}


void
OGLRenderer::EndDraw()
{
	if (renMode == TESSPOLYGON) {
		gluTessEndContour(gluTess);
		gluTessEndPolygon(gluTess);   
	}
	else {
		glEnd();
	}
	glFlush();
}

void
OGLRenderer::EndDL()
{
  glEndList();
}

void 
OGLRenderer::DrawDisplayList(unsigned int dlnum) 
{
	glCallList(dlnum);
}

void
OGLRenderer::Vertex(double x, double y, double z)
{
	GLdouble *v;
	//cout << x << " " << y << " " << z << endl;
	if (renMode == TESSPOLYGON) {
		v = new double[3];
		v[0] = x;v[1] = y;v[2] = z;
		gluTessVertex(gluTess, v, v);
	}
	else {
		glVertex3f(x, y, z);
	}
}

void
OGLRenderer::Render()
{
  glClear((GLbitfield)(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT) );
  wglMakeCurrent(this->DeviceContext, this->ContextId);
  if (this->CameraSet.Get(0))
	(this->CameraSet.Get(0))->Render(this);
  for (int i = 0;i < this->LightSet.Count();i++) {
    (this->LightSet.Get(i))->Render(this);
  }

  glMatrixMode(GL_MODELVIEW);
  for (i=0;i<this->ActorSet.Count();i++) {
      (this->ActorSet.Get(i))->Render(this);
  }
  auxSwapBuffers();
}

void
OGLRenderer::Initialize(int argc, char *argv[])
{

  auxInitPosition(x, y, width, height);
  auxInitDisplayMode(AUX_RGB | AUX_DOUBLE);

  if (auxInitWindow(this->ObjectType()) == GL_FALSE) {
	  auxQuit();
  }

  this->WindowId = auxGetHWND();
  this->DeviceContext = auxGetHDC();
  this->ContextId = auxGetHGLRC();


  this->OldProc = (WNDPROC)GetWindowLong(this->WindowId,GWL_WNDPROC);
  SetWindowLong(this->WindowId,GWL_USERDATA,(LONG)this);
  SetWindowLong(this->WindowId,GWL_WNDPROC,(LONG)HandleMessage);
  wglMakeCurrent(this->DeviceContext, this->ContextId);

  glDepthFunc( GL_LEQUAL );
  glEnable( GL_DEPTH_TEST );
  glTexEnvf( GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE );

  // initialize blending for transparency
  glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );

  //glEnable( GL_NORMALIZE );
  glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, GL_TRUE);
}

void
OGLRenderer::MainLoop()
{
  MSG msg;
  
  while (GetMessage(&msg, NULL, 0, 0))
    {
    TranslateMessage(&msg);
    DispatchMessage(&msg);
    }
//  auxMainLoop(Displa.handler);
//  glutMainLoop();
}

void
OGLRenderer::Key(unsigned char key, int x, int y)
{

  switch (key) {
  case 'h':
    (this->CameraSet.Get(0))->Rotate(15.0, 0.0,1.0,0.0);
    break;
  case 'j':
    (this->CameraSet.Get(0))->Rotate(15.0, 1.0,0.0,0.0);
    break;
  case 'k':
    this->CameraSet.Get(0)->Rotate(-15.0, 1.0,0.0,0.0);
    break;
  case 'l':
    this->CameraSet.Get(0)->Rotate(-15.0, 0.0,1.0,0.0);
    break;
  case '+':
    this->CameraSet.Get(0)->Translate(0.0, 0.0, 1.0);
    break;
  case '-':
    this->CameraSet.Get(0)->Translate(0.0, 0.0, -1.0);
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
  wglMakeCurrent(GetDC(this->WindowId), this->ContextId);
  this->width = w;
  this->height = h;
  glViewport (0, 0, w, h);            /*  define the viewport */
  this->CameraSet.Get(0)->Reshape(this);
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

/** Push the current Model transformation matrix */
void 
OGLRenderer::PushMatrix()
{
		glMatrixMode (GL_MODELVIEW);
		glPushMatrix();
}

/** Set the current orientation for consequent drawing */
void 
OGLRenderer::SetOrientation(float *rotations, float *scale, 
							PointType *pos)
{
		glTranslatef(pos->x, pos->y,pos->z);
		glScalef(scale[0], scale[1], scale[2]);
		glRotatef(rotations[0], 1, 0, 0);
		glRotatef(rotations[1], 0, 1, 0);
		glRotatef(rotations[2], 0, 0, 1);
}

/** Pop the current Model transformation matrix */
void 
OGLRenderer::PopMatrix()
{
		glMatrixMode (GL_MODELVIEW);
		glPopMatrix();
}

LRESULT CALLBACK HandleMessage(HWND hWnd,UINT uMsg,
                               WPARAM wParam, LPARAM lParam) {

  static LPARAM refPos;
  float xf,yf;
  OGLRenderer *me;

  me = (OGLRenderer *)GetWindowLong(hWnd,GWL_USERDATA);

  switch (uMsg)
    {
    case WM_PAINT: 
      me->Render();
      return DefWindowProc(hWnd,uMsg,wParam,lParam);
      break;
	    
    case WM_SIZE: 
      me->Reshape(LOWORD(lParam),HIWORD(lParam)); 
      return me->OldProc(hWnd,uMsg,wParam,lParam);
      break;
	    
    case WM_LBUTTONDOWN: 
		refPos = lParam;
      break;
	    
    case WM_LBUTTONUP:
	  break;
	    
    case WM_RBUTTONDOWN: 
      break;
	    
    case WM_RBUTTONUP:
	  break;
	    
    case WM_MOUSEMOVE:
	  break;
	    
    case WM_CHAR:
	  me->Key(wParam, LOWORD(lParam), HIWORD(lParam));
      break;

    case WM_TIMER:
	  break;
	default:
      return me->OldProc(hWnd,uMsg,wParam,lParam);
      //          return DefWindowProc(hWnd,uMsg,wParam,lParam);
    }
  return 0;
}


/////////////////////////
//
// OpenGL Actor methods, display list code imbedded here.
//
/////////////////////////

void
Actor::Render(Renderer *aren) {

	bool bRedraw;

	bRedraw = false;

	for (int i=0;i<this->Count();i++) {
		if (dList == 0 || this->Get(i)->GetTime() > this->GetTime()) {
			bRedraw = true;
			break;
		}
	}

	if (bRedraw) {
		// Begin compilling a display list
		aren->BeginDL(dList);
		// Set the current orientation
		aren->PushMatrix();
		aren->SetOrientation(orientation, scale, &position);
		for (int i=0;i<this->Count();i++) {
			this->Get(i)->Render(aren);
		}
		aren->PopMatrix();
		// End display list
		aren->EndDL();
	}
	else
		aren->DrawDisplayList(dList);
}

/////////////////////////
//
// OpenGL Camera methods
//
/////////////////////////

Camera::Camera() { 
  Initialize();
}
void
Camera::Render(Renderer *aren) {
	Width = aren->width;
	Height = aren->height;
	if(InitLess) {
		Initialize();
		InitLess = false;
	}
}

void
Camera::Initialize() {
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(eyeAngle, 
				1.0F*Width/Height, 
				ClipRange[0], ClipRange[1]);
	gluLookAt(From.x, From.y, From.z,
			  To.x, To.y, To.z,
			  Up.x, Up.y, Up.z);
}
  
void
Camera::Reshape(Renderer *aren) {

	Width = aren->GetWidth();
	Height = aren->GetHeight();
	InitLess = true;  
}

void
Camera::Rotate(float angle, float x, float y, float z) {

  glMatrixMode (GL_MODELVIEW);        /* manipulate viewport matrix  */
  glRotatef(GLfloat(angle), GLfloat(x), GLfloat(y), GLfloat(z));
}

void
Camera::Translate(float x, float y, float z) {

  glMatrixMode (GL_MODELVIEW);        /* manipulate viewport matrix  */
  glTranslatef(GLfloat(x), GLfloat(y), GLfloat(z));
}


void
Camera::SetROI(double *roi) {
  float x, y, z, cx, cy, cz;

  for (int i=0;i<6;i++)
    cout << roi[i] << " ";
  cout << endl;
  x = ((roi[3] - roi[0])/2) + roi[0];
  y = ((roi[4] - roi[1])/2) + roi[1];
  z = (roi[5] - roi[2]) + roi[2];
  cx = ((roi[3] - roi[0])/2) + roi[0];
  cy = ((roi[4] - roi[1])/2) + roi[1];
  cz = ((roi[5] - roi[2])/2) + roi[2];
  glMatrixMode (GL_MODELVIEW);        /* manipulate viewport matrix  */
  glLoadIdentity();
/*  gluLookAt(x, y, z, cx, cy, cz,
	    UpVec[0], UpVec[1], UpVec[2]);
  gluPerspective(this->eyeAngle, 
		 1.0*(GLfloat)aren->width/(GLfloat)aren->height, 
		 ClipRange[0], ClipRange[1]);*/
}

void 
Camera::Zoom(float amount) {
  float distance;

  if (amount <= 0.0) return;

//  this->SetPosition(this->FocalPoint[0] +distance * this->ViewPlaneNormal[0],
//                    this->FocalPoint[1] +distance * this->ViewPlaneNormal[1],
//                    this->FocalPoint[2] +distance * this->ViewPlaneNormal[2]);

}

/////////////////////////
//
// OpenGL Light methods
//
/////////////////////////

Light::Light() { 

  From.z = 1.0;
  Num = 0;
  On = Light::Yes; 

}

Light::Light(short num) { 

  From.z = 1.0;
  Num = num;
  On = LightBC::Yes; 

}

void
Light::Render(Renderer *aren) {
  GLenum lightnum;

  float lightPos[4] = {From.x, From.y, From.z, 0.0};
  float lightDir[4] = {To.x, To.y, To.z, 0.0};

  if (this->On == Light::Yes) {
    glEnable(GL_LIGHTING);
    switch (Num % 8) {
    case 0: lightnum = GL_LIGHT0; break;
    case 1: lightnum = GL_LIGHT1; break;
    case 2: lightnum = GL_LIGHT2; break;
    case 3: lightnum = GL_LIGHT3; break;
    case 4: lightnum = GL_LIGHT4; break;
    case 5: lightnum = GL_LIGHT5; break;
    case 6: lightnum = GL_LIGHT6; break;
    case 7: lightnum = GL_LIGHT7; break;
    }
    glEnable(lightnum);
    glLightfv(lightnum, GL_POSITION, lightPos);
    glLightfv(lightnum, GL_SPOT_DIRECTION, lightDir);
    glLightfv(lightnum, GL_AMBIENT, Color.Ambient.RGBA);
    glLightfv(lightnum, GL_DIFFUSE, Color.Diffuse.RGBA);
    glLightfv(lightnum, GL_SPECULAR, Color.Specular.RGBA);
  }
}

void 
Light::SetMaterial(Material *mat) {

  GLenum lightnum;

  Color = *mat;
  switch (Num % 8) {
    case 0: lightnum = GL_LIGHT0; break;
    case 1: lightnum = GL_LIGHT1; break;
    case 2: lightnum = GL_LIGHT2; break;
    case 3: lightnum = GL_LIGHT3; break;
    case 4: lightnum = GL_LIGHT4; break;
    case 5: lightnum = GL_LIGHT5; break;
    case 6: lightnum = GL_LIGHT6; break;
    case 7: lightnum = GL_LIGHT7; break;
  }
  glLightfv(lightnum, GL_DIFFUSE, Color.Diffuse.RGBA);
  glLightfv(lightnum, GL_SPECULAR, Color.Specular.RGBA);

}

