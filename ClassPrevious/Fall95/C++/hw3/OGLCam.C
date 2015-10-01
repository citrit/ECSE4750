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

#include "OGLCam.H"

OGLCamera::OGLCamera() { 

  glMatrixMode(GL_PROJECTION);
  this->eyeAngle = 25.0; 

}

void
OGLCamera::Reshape(Renderer *aren) {

  glMatrixMode(GL_PROJECTION);
//  glLoadIdentity(); 
  gluPerspective(this->eyeAngle, 
		 1.0*(GLfloat)aren->width/(GLfloat)aren->height, 0.1, 100.0);

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

  From.x = x; From.y = y; From.z = z;
  To.x = cx; To.y = cy; To.z = cz;
  UpVec[0] = ux; UpVec[1] = uy; UpVec[2] = uz;
  glMatrixMode (GL_PROJECTION);        /* manipulate viewport matrix  */
  glLoadIdentity();
  gluLookAt(From.x, From.y, From.z,
	    To.x, To.y, To.z,
	    UpVec[0], UpVec[1], UpVec[2]);
//  gluPerspective(this->eyeAngle, 
//		 1.0*(GLfloat)aren->width/(GLfloat)aren->height, 
//		 0.1, 100.0);

}
