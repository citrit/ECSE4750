/*
 *
 *  Simple2.c 
 *
 *  Simple example of programming using the OpenGL graphics library
 *  Taken from the "OpenGL Programming Guide"
 *
 *
 */

/* Includes required */
#ifdef WIN32
#include <windows.h>
#endif
#include <GL/gl.h>
#include <GL/glut.h>

/*
 *  Clear the screen.  Set the current color to white.
 *  Draw the cube.
 */
void display(void)
{
      static float boxAmb[4] = {0.7, 0.0, 0.0, 1.0};
      static float boxDiff[4] = {0.1, 0.1, 0.1, 1.0};
      static float boxSpec[4] = {0.8, 0.8, 0.8, 1.0};

	  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	  glMaterialfv(GL_FRONT, GL_AMBIENT, boxAmb);
	  glMaterialfv(GL_FRONT, GL_DIFFUSE, boxDiff);
	  glMaterialfv(GL_FRONT, GL_SPECULAR, boxSpec);
      glutSolidSphere(1.0, 50, 50);   /*  draw the cube       */
      glutSwapBuffers(); 
}

/* Initialize shading model */
void myInit(void)
{
      static float lightPos[4] = {2.0, 4.0, 2.0, 1.0};
      static float lightDir[4] = {-2.0, -4.0, -2.0, 1.0};
      static float lightAmb[4] = {0.7, 0.7, 0.7, 1.0};
      static float lightDiff[4] = {0.8, 0.8, 0.8, 1.0};
      static float lightSpec[4] = {0.4, 0.4, 0.4, 1.0};

      glColor3f(1.0, 0.0, 0.0);
      glEnable(GL_LIGHTING);
      glEnable(GL_LIGHT0);
      glLightfv(GL_LIGHT0, GL_POSITION, lightPos);
      glLightfv(GL_LIGHT0, GL_AMBIENT, lightAmb);
      glLightfv(GL_LIGHT0, GL_DIFFUSE, lightDiff);
      glLightfv(GL_LIGHT0, GL_SPECULAR, lightSpec);
      glEnable(GL_DEPTH_TEST);
/*	  glShadeModel (GL_FLAT);
 *     glClearColor (0.0, 0.0, 0.0, 0.0);
 */
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

/*
 *  Handle Menus
 */
void
Select(int value)
{
    switch (value) {
    case 1:
        exit(0);
        break;
    }
}

/*
 *  Main Loop
 *  Open window with initial window size, title bar, 
 *  RGBA display mode, and handle input events.
 */
int main(int argc, char** argv) 
{
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

