/*
 *   COMPONENT_NAME: GLTEST
 *
 *   FUNCTIONS: display
 *		main
 *		myReshape
 *		myinit
 *		spinDisplay
 *		startIdleFunc
 *		stopIdleFunc
 *		
 *
 *   ORIGINS: 21,27
 *
 *
 *   (C) COPYRIGHT International Business Machines Corp. 1993
 *   All Rights Reserved
 *   Licensed Materials - Property of IBM
 *   US Government Users Restricted Rights - Use, duplication or
 *   disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

/*
 *  double.c
 *  This program demonstrates double buffering for 
 *  flicker-free animation.  The left and middle mouse
 *  buttons start and stop the spinning motion of the square.
 */
#include <stdlib.h>
#include <GL/gl.h>
#include <GL/glut.h>

static GLfloat spin = 0.0;

void display(void)
{
    glClear (GL_COLOR_BUFFER_BIT);

    glPushMatrix ();
    glRotatef (spin, 0.0, 0.0, 1.0);
    glRectf (-25.0, -25.0, 25.0, 25.0);
    glPopMatrix ();

    glFlush();
    glutSwapBuffers ();
}

void spinDisplay (void)
{
    spin = (spin + 2.0);
    if (spin > 360.0)
	spin = spin - 360.0;
    display();
}

void myinit (void)
{
    glClearColor (0.0, 0.0, 0.0, 1.0);
    glColor3f (1.0, 1.0, 1.0);
    glShadeModel (GL_FLAT);
}

void myReshape(GLsizei w, GLsizei h)
{
    glViewport(0, 0, w, h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    if (w <= h) 
	glOrtho (-50.0, 50.0, -50.0*(GLfloat)h/(GLfloat)w, 
	    50.0*(GLfloat)h/(GLfloat)w, -1.0, 1.0);
    else 
	glOrtho (-50.0*(GLfloat)w/(GLfloat)h, 
	    50.0*(GLfloat)w/(GLfloat)h, -50.0, 50.0, -1.0, 1.0);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity ();
}

void
Select(int value)
{
    switch (value) {
    case 1:
        glutIdleFunc(spinDisplay);
        break;
    case 2:
        glutIdleFunc(NULL);
        break;
    case 3:
        exit(0);
        break;
    }
}

/*  Main Loop
 *  Open window with initial window size, title bar, 
 *  RGBA display mode, and handle input events.
 */
int main(int argc, char** argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB);
    glutInitWindowSize (500, 500);
    glutCreateWindow (argv[0]);
    myinit ();
    glutDisplayFunc(display);
    glutReshapeFunc (myReshape);
    glutIdleFunc (spinDisplay);
    glutCreateMenu(Select);
    glutAddMenuEntry("Start motion", 1);
    glutAddMenuEntry("Stop motion", 2);
    glutAddMenuEntry("Quit", 3);
    glutAttachMenu(GLUT_RIGHT_BUTTON);
    glutMainLoop();
}
