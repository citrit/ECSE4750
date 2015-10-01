/*
 *
 *  Simple.c 
 *
 *  Simple example of programming using the OpenGL graphics library
 *  Taken from the "OpenGL Programming Guide"
 *
 *
 */

/* Includes required */
#include <stdlib.h>
#ifdef _WIN32
#include <windows.h>
#endif
#include <GL/gl.h> 
#include <GL/glut.h>

/*
 *  Clear the screen.  Set the current color to white.
 *  Draw the box
 */
void display(void)
{
      GLfloat mat[16];
      mat[5] = 3.0;
      glGetFloatv(GL_MODELVIEW_MATRIX, mat);
      glClearColor (0.0, 0.0, 0.0, 0.0);
      glClear(GL_COLOR_BUFFER_BIT);
      glColor3f(1.0, 1.0, 1.0);
      glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);
      glBegin(GL_POLYGON);
               glVertex2f(-0.5, -0.5);
               glVertex2f(-0.5, 0.5);
               glVertex2f(0.5, 0.5);
               glVertex2f(0.5, -0.5);

      glEnd();
      glFlush(); 
}

/* Initialize shading model */
void myInit(void)
{
      glShadeModel (GL_FLAT);
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
      glMatrixMode (GL_MODELVIEW);        /* back to modelview matrix  */
}

/*
 *  Key press handler
 */

void
Key(unsigned char key, int x, int y)
{
    switch (key) {
    case 27:           /* Esc will quit */
        exit(1);
        break;
    default:
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
  glutInitDisplayMode (GLUT_SINGLE | GLUT_RGB);
  glutCreateWindow("Simple example");
  glutDisplayFunc(display);
  myInit ();
  glutReshapeFunc (myReshape);
  glutKeyboardFunc(Key);
  glutMainLoop();

  return(0);
}


