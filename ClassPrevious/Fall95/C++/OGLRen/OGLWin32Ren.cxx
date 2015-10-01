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
  glClear((GLbitfield)(GL_COLOR_BUFFER_BIT | GL_DEP.h_BUFFER_BIT) );
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

  this->WindowId = auxGe.hWND();
  this->DeviceContext = auxGe.hDC();
  this->ContextId = auxGe.hGLRC();


  this->OldProc = (WNDPROC)GetWindowLong(this->WindowId,GWL_WNDPROC);
  SetWindowLong(this->WindowId,GWL_USERDATA,(LONG)this);
  SetWindowLong(this->WindowId,GWL_WNDPROC,(LONG.handleMessage);
  wglMakeCurrent(this->DeviceContext, this->ContextId);

  glDepthFunc( GL_LEQUAL );
  glEnable( GL_DEP.h_TEST );
  glTexEnvf( GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE );

  // initialize blending for transparency
  glBlendFunc( GL_SRC_AL.hA, GL_ONE_MINUS_SRC_AL.hA );

  //glEnable( GL_NORMALIZE );
  glLightModeli(GL_LI.hT_MODEL_TWO_SIDE, GL_TRUE);
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

LRESULT CALLBACK.handleMessage.hWND hWnd,UINT uMsg,
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
      me->Reshape(LOWORD(lParam).hIWORD(lParam)); 
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
	    
    case WM_.hAR:
	  me->Key(wParam, LOWORD(lParam),.hIWORD(lParam));
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

  // Set the current orientation
  glMatrixMode(GL_MODELVIEW);
  //glPushMatrix();
  //glLoadMatrixf(this->Matrix);
  // Render all the included cells
  for (int i=0;i<this->Count();i++) {
    (*this)[i]->Render(aren);
  }
  //glGetFloatv(GL_MODELVIEW_MATRIX, (float *)&(this->Matrix));
  //glPopMatrix();
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
  glLoadIdentity(); 
}
  
void
Camera::Reshape(Renderer *aren) {

  glMatrixMode(GL_PROJECTION);
  glLoadIdentity(); 
  gluLookAt(From.x, From.y, From.z,
	    To.x, To.y, To.z,
	    UpVec[0], UpVec[1], UpVec[2]);
  gluPerspective(this->eyeAngle, 
		 1.0*(GLfloat)aren->width/(GLfloat)aren->height, 
		 ClipRange[0], ClipRange[1]);
  glTranslatef(0.0,0.0,-50.0);

}

void
Camera::Render(Renderer *aren) {

	Reshape(aren);
}

void
Camera::Rotate(float angle, float x, float y, float z) {

  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  glRotatef(GLfloat(angle), GLfloat(x), GLfloat(y), GLfloat(z));

}

void
Camera::Translate(float x, float y, float z) {

  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  glTranslatef(GLfloat(x), GLfloat(y), GLfloat(z));
}

void
Camera::SetPosition(float x, float y, float z,
			     float cx, float cy, float cz,
			     float ux, float uy, float uz) {

  From.x = (double)x; From.y = (double)y; From.z = (double)z;
  To.x = (double)cx; To.y = (double)cy; To.z = (double)cz;
  UpVec[0] = ux; UpVec[1] = uy; UpVec[2] = uz;
  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  glLoadIdentity();
  gluLookAt(From.x, From.y, From.z,
	    To.x, To.y, To.z,
	    UpVec[0], UpVec[1], UpVec[2]);
//  gluPerspective(this->eyeAngle, 
//		 1.0*(GLfloat)aren->width/(GLfloat)aren->height, 
//		 ClipRange[0], ClipRange[1]);

}

void
Camera::SetROI(double *roi) {

  for (int i=0;i<6;i++)
    cout << roi[i] << " ";
  cout << endl;
  From.x = ((roi[3] - roi[0])/2) + roi[0];
  From.y = ((roi[4] - roi[1])/2) + roi[1];
  From.z = (roi[5] - roi[2]) + roi[2];
  To.x = ((roi[3] - roi[0])/2) + roi[0];
  To.y = ((roi[4] - roi[1])/2) + roi[1];
  To.z = ((roi[5] - roi[2])/2) + roi[2];
  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  glLoadIdentity();
  gluLookAt(From.x, From.y, From.z,
	    To.x, To.y, To.z,
	    UpVec[0], UpVec[1], UpVec[2]);
//  gluPerspective(this->eyeAngle, 
//		 1.0*(GLfloat)aren->width/(GLfloat)aren->height, 
//		 ClipRange[0], ClipRange[1]);


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

  static float lightPos[4] = {From.x, From.y, From.z, 0.0};
  static float lightDir[4] = {To.x, To.y, To.z, 0.0};

  if (this->On == Light::Yes) {
    glEnable(GL_LI.hTING);
    switch (Num % 8) {
    case 0: lightnum = GL_LI.hT0; break;
    case 1: lightnum = GL_LI.hT1; break;
    case 2: lightnum = GL_LI.hT2; break;
    case 3: lightnum = GL_LI.hT3; break;
    case 4: lightnum = GL_LI.hT4; break;
    case 5: lightnum = GL_LI.hT5; break;
    case 6: lightnum = GL_LI.hT6; break;
    case 7: lightnum = GL_LI.hT7; break;
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
    case 0: lightnum = GL_LI.hT0; break;
    case 1: lightnum = GL_LI.hT1; break;
    case 2: lightnum = GL_LI.hT2; break;
    case 3: lightnum = GL_LI.hT3; break;
    case 4: lightnum = GL_LI.hT4; break;
    case 5: lightnum = GL_LI.hT5; break;
    case 6: lightnum = GL_LI.hT6; break;
    case 7: lightnum = GL_LI.hT7; break;
  }
  glLightfv(lightnum, GL_DIFFUSE, Color.Diffuse.RGBA);
  glLightfv(lightnum, GL_SPECULAR, Color.Specular.RGBA);

}

