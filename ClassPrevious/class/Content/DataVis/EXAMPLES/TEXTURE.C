#include <stdio.h>
#include <stdlib.h>

#ifdef WIN32
#include <windows.h>
#endif

#include <GL/glut.h>
#include <GL/glaux.h>

AUX_RGBImageRec *image;

/* Display Func */
void display(void)
{
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  glBegin(GL_POLYGON);
     glTexCoord2f(0.0, 0.0); glVertex3f(-1.0, -1.0, 0.0);
     glTexCoord2f(0.0, 2.0); glVertex3f(-1.0, 1.0, 0.0);
     glTexCoord2f(2.0, 2.0); glVertex3f(1.0, 1.0, 0.0);
     glTexCoord2f(2.0, 0.0); glVertex3f(1.0, -1.0, 0.0);
  glEnd();

  glutSwapBuffers(); 
}

/* Initialize texture model */
void myInit(void)
{

  glEnable(GL_DEPTH_TEST);
  glDepthFunc(GL_LEQUAL);
  glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
  glTexImage2D(GL_TEXTURE_2D, 0, 3, image->sizeX, image->sizeY, 0,
		    GL_RGB, GL_UNSIGNED_BYTE, image->data);
  glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
  glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
  glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
  glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
  glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_DECAL);
  glEnable(GL_TEXTURE_2D);
  glShadeModel(GL_FLAT);
  
  glClearColor(0.0, 0.0, 0.0, 0.0);

}

/*
 *  Called when the window is first opened and whenever 
 *  the window is reconfigured (moved or resized).
 */
void myReshape(int w, int h)
{
  glViewport (0, 0, w, h);            /*  define the viewport */
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();
  gluPerspective(45.0, 1.0*(GLfloat)w/(GLfloat)h, 1.0, 10.0);
  glTranslatef (0.0, 0.0, -5.0);     /*  viewing transformation      */
  glMatrixMode (GL_MODELVIEW);        /* back to modelview matrix  */
}

/*
 * Keyboard handler
 */
void
Key(unsigned char key, int x, int y)
{
  switch (key) {
  case 'h':
    glMatrixMode (GL_MODELVIEW);        /* manipulate modelview matrix  */
    glRotatef(15.0, 0.0,1.0,0.0);
    break;
  case 'j':
    glMatrixMode (GL_MODELVIEW);        /* manipulate modelview matrix  */
    glRotatef(15.0, 1.0,0.0,0.0);
    break;
  case 'k':
    glMatrixMode (GL_MODELVIEW);        /* manipulate modelview matrix  */
    glRotatef(-15.0, 1.0,0.0,0.0);
    break;
  case 'l':
    glMatrixMode (GL_MODELVIEW);        /* manipulate modelview matrix  */
    glRotatef(-15.0, 0.0,1.0,0.0);
    break;
  case 27:           /* Esc will quit */
    exit(1);
    break;
  default:
    break;
  }
  glutPostRedisplay();
}

/*  Handle Menus */
void
Select(int value)
{
  switch (value) {
  case 1:
    exit(0);
    break;
  }
}

/*  Main Loop */
void main(int argc, char *argv[])
{

  if (argc < 2) {
    fprintf(stdout, "usage: %s texturefilename\n", argv[0]);
    exit(1);
  }

  image = auxRGBImageLoad(argv[1]);

  glutInit(&argc, argv);
  glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGBA);
  glutCreateWindow (argv[0]);
  myInit ();
  glutKeyboardFunc(Key);
  glutReshapeFunc (myReshape);
  glutDisplayFunc(display);
  glutCreateMenu(Select);
  glutAddMenuEntry("Quit", 1);
  glutAttachMenu(GLUT_RIGHT_BUTTON);
  glutMainLoop();
}


