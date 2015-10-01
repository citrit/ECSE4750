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

#include "OGLCam.h"

OGLCamera::OGLCamera() { 


}

void
OGLCamera::Initialize() {
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity(); 
  gluLookAt(From.x, From.y, From.z,
	    To.x, To.y, To.z,
	    UpVec[0], UpVec[1], UpVec[2]);
}

void
OGLCamera::PrintSelf(ostream &os) {

  CameraBC::PrintSelf(os);
  os << "Positioned at: " << From.x << ", "<<From.y<<", "<<From.z << endl
    << "Looking at: " << To.x << ", " << To.y << ", " << To.z << endl
    << "Up: " << UpVec[0] << ", " << UpVec[1] << ", " << UpVec[2] << endl
    << "Clipping Range: " << ClipRange[0] << ", " << ClipRange[1] << endl
    << "Eye ange: " << eyeAngle << endl;
}
  
void
OGLCamera::Reshape(Renderer *aren) {

  glMatrixMode(GL_PROJECTION);
  glLoadIdentity(); 
  gluPerspective(this->eyeAngle, 
		 1.0*(GLfloat)aren->width/(GLfloat)aren->height, 
		 ClipRange[0], ClipRange[1]);
  gluLookAt(From.x, From.y, From.z,
	    To.x, To.y, To.z,
	    UpVec[0], UpVec[1], UpVec[2]);
//  glTranslatef(0.0,0.0,-50.0);

}

void
OGLCamera::Render(Renderer *aren) {

  glMatrixMode(GL_PROJECTION);

}

void
OGLCamera::Rotate(float angle, float x, float y, float z) {

  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  glRotatef(GLfloat(angle), GLfloat(x), GLfloat(y), GLfloat(z));

}

void
OGLCamera::Translate(float x, float y, float z) {

  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  glTranslatef(GLfloat(x), GLfloat(y), GLfloat(z));

}

void
OGLCamera::SetPosition(float x, float y, float z,
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
OGLCamera::SetROI(double *roi) {

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
OGLCamera::Zoom(float amount) {
  float distance;

  if (amount <= 0.0) return;

//  this->SetPosition(this->FocalPoint[0] +distance * this->ViewPlaneNormal[0],
//                    this->FocalPoint[1] +distance * this->ViewPlaneNormal[1],
//                    this->FocalPoint[2] +distance * this->ViewPlaneNormal[2]);

}
