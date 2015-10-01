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

//
// Globals 
//

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
  case LINE_LOOP:
    glBegin(GL_LINE_LOOP);
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
  // cout << x << " " << y << " " << z << endl;
  glVertex3f(x, y, z);
}

void
OGLRenderer::Render()
{
  glClear((GLbitfield)(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT) );
  wglMakeCurrent(this->DeviceContext, this->ContextId);
  if (this->CameraSet[0])
	this->CameraSet[0]->Render(this);
  for (int i = 0;i < this->LightSet.Count();i++) {
    this->LightSet[i]->Render(this);
  }

  glMatrixMode(GL_MODELVIEW);
  for (i=0;i<this->ActorSet.Count();i++) {
      this->ActorSet[i]->Render(this);
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
  wglMakeCurrent(GetDC(this->WindowId), this->ContextId);
  this->width = w;
  this->height = h;
  glViewport (0, 0, w, h);            /*  define the viewport */
  this->CameraSet[0]->Reshape(this);
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

LRESULT CALLBACK HandleMessage(HWND hWnd,UINT uMsg,
                               WPARAM wParam, LPARAM lParam) {

  static LPARAM lastPos;
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
// OpenGL Actor methods
//
/////////////////////////

void
Actor::Render(Renderer *aren) {

  Matrix _tmpmat;
  
  this->Mat.GetMatrix(_tmpmat);
  _tmpmat.Transpose();
  // Set the current orientation
  glMatrixMode(GL_MODELVIEW);
  glPushMatrix();
  glLoadMatrixf(_tmpmat.GetMatrix());
  // Render all the included cells
  for (int i=0;i<this->Count();i++) {
    (*this)[i]->Render(aren);
  }
  glPopMatrix();
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
Camera::Initialize() {
  glMatrixMode(GL_PROJECTION);
  this->Mat.Identity();
}

void
Camera::PrintSelf(ostream &os) {

  CameraBC::PrintSelf(os);
  os << "Clipping Range: " << ClipRange[0] << ", " << ClipRange[1] << endl
     << "Eye ange: " << eyeAngle << endl;
}
  
void
Camera::Reshape(Renderer *aren) {

  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();//glLoadMatrixf(this->Mat.GetMatrix());
  gluPerspective(this->eyeAngle, 
		 1.0*(GLfloat)aren->width/(GLfloat)aren->height, 
		 ClipRange[0], ClipRange[1]);
  glGetFloatv(GL_PROJECTION_MATRIX, (this->Mat.GetMatrix()));
  
}

void
Camera::Render(Renderer *aren) {

  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  glLoadMatrixf(this->Mat.GetMatrix());  
}

void
Camera::Rotate(float angle, float x, float y, float z) {

  //Matrix _tmp;
  //_tmp.RotateWXYZ(angle, x, y, z);
  //this->Mat.Concatenate(_tmp);
  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  glLoadMatrixf(this->Mat.GetMatrix());  
  glRotatef(GLfloat(angle), GLfloat(x), GLfloat(y), GLfloat(z));
  glGetFloatv(GL_PROJECTION_MATRIX, (this->Mat.GetMatrix()));
}

void
Camera::Translate(float x, float y, float z) {

  //Matrix _tmp;
  //_tmp.Translate(x, y, z);
  //this->Mat.Concatenate(_tmp);
  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  glLoadMatrixf(this->Mat.GetMatrix());
  glTranslatef(GLfloat(x), GLfloat(y), GLfloat(z));
  glGetFloatv(GL_PROJECTION_MATRIX, (this->Mat.GetMatrix()));
}

void
Camera::SetPosition(float x, float y, float z,
			     float cx, float cy, float cz,
			     float ux, float uy, float uz) {

  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  glLoadIdentity();
  gluLookAt(x, y, z,
	    cx, cy, cz,
	    ux, uy, uz);
/*  gluPerspective(this->eyeAngle, 
		 1.0*(GLfloat)width/(GLfloat)this->height, 
		 ClipRange[0], ClipRange[1]);*/
  glGetFloatv(GL_PROJECTION_MATRIX, (this->Mat.GetMatrix()));
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
  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  glLoadIdentity();
/*  gluLookAt(x, y, z, cx, cy, cz,
	    UpVec[0], UpVec[1], UpVec[2]);
  gluPerspective(this->eyeAngle, 
		 1.0*(GLfloat)aren->width/(GLfloat)aren->height, 
		 ClipRange[0], ClipRange[1]);
   glGetFloatv(GL_PROJECTION_MATRIX, (this->Mat.GetMatrix())); */
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

