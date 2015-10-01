static char sccsid[] = "@(#)39	1.5  R2/OPENGL/test/samples/demos/lorenz.c, gltest, r5gos325, 9334325c 8/25/93 15:13:28";
/*
 *   COMPONENT_NAME: GLTEST
 *
 *   FUNCTIONS: SetColorIndices
 *		WaitForMapNotify
 *		draw_hexagon
 *		draw_hexcube
 *		draw_hexplane
 *		init_3d
 *		init_graphics
 *		main
 *		move_eye
 *		next_line
 *		parse_args
 *		perspective
 *		print_usage
 *		redraw
 *		sphdraw
 *		tmp_draw_hexplane
 *		
 *
 *   ORIGINS: 21,27
 *
 *   This module contains IBM CONFIDENTIAL code. -- (IBM
 *   Confidential Restricted when combined with the aggregated
 *   modules for this product)
 *   OBJECT CODE ONLY SOURCE MATERIALS
 *
 *   (C) COPYRIGHT International Business Machines Corp. 1992,1993
 *   All Rights Reserved
 *   US Government Users Restricted Rights - Use, duplication or
 *   disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

/*
 *  Lorenz Attractor Demo
 *
 * This demo has been ported to OpenGL from the  GL4 program by Aaron Ferrucci.
 *
 * Description:
 *
 * This program shows some particles stuck in a Lorenz attractor (the parameters
 * used are r=28, b=8/3, sigma=10). The eye is attracted to the red particle,
 * with a force directly proportionate to distance. A command line
 * puts the whole mess inside a box made of hexagons. I think this helps to
 * maintain the illusion of 3 dimensions, but it can slow things down.
 * Other options allow you to play with the redraw rate and the number of new
 * lines per redraw. So you can customize it to the speed of your machine.
 * Try lorenz -h for more info.
 * 
 * For general info on Lorenz attractors I recommend "An Introduction to
 * the Lorenz Equations", IEEE Transactions on Circuits and Systems, August '83.
 *
 * Notes on OpenGL port:
 * 
 * The timer functions do not exist in OpenGL, so the drawing occurs in a
 * continuous loop, controlled by step, stop and go input from the keyboard.
 * Perhaps system function could be called to control timing.
 */

#define __EXTENSIONS__
#include <GL/glx.h>
#include <GL/glu.h>
#include <X11/keysym.h>

#include <sys/time.h>
#include <math.h>
#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#ifdef AIXV3 
/*
#include <getopt.h>
*/
#endif

static Display *dpy;
static Window window;

#define POINTMASK (unsigned long)511
#define G (0.002)	/* eyept to red sphere gravity */
#define LG (0.3)
#define CUBESIDE (120.)
#define CUBESCALE (23.)
#define CUBEOFFX (-4.)
#define CUBEOFFY (0.)
#define CUBEOFFZ (57.)
#define FALSE 0
#define TRUE 1

/* globals */
GLint blackIndex, whiteIndex, redIndex, greenIndex, blueIndex, cyanIndex,
	magentaIndex, yellowIndex, darkRedIndex, darkGreenIndex, darkBlueIndex;
float sigma = 10., r = 28., b = 8./3., dt = 0.003;
unsigned long rp = 0, bp = 0, gp = 0, yp = 0, mp = 0;
long xmax, ymax, zmax, zmin;
float rv[POINTMASK+1][3],			/* red points */
	bv[POINTMASK+1][3],		/* blue points */
	gv[POINTMASK+1][3],		/* green points */
	yv[POINTMASK+1][3],		/* yellow points */
	mv[POINTMASK+1][3];		/* magenta points */

int lpf;				/* number of new lines per frame */

float eyex[3],	/* eye location */
	 eyev[3],	/* eye velocity */
	 eyel[3];	/* lookat point location */
GLint fovy = 600;
float dx, dy, dz;
GLUquadricObj *quadObj;

float cubeoffx = CUBEOFFX;
float cubeoffy = CUBEOFFY;
float cubeoffz = CUBEOFFZ;
float farplane = 80.;

/* option flags */
#ifdef AIXV3
GLboolean hexflag,		/* hexagons? */
	sflag, 			
        rflag,
	fflag,			
	wflag,
	gflag,
	debug;
#else
GLboolean hexflag,		/* hexagons? */
	sflag, 			
	fflag,			
	wflag,
	gflag,
	debug;
#endif

/* option values */
short hexbright;	/* brightness for hexagon color */
#ifdef AIXV3
int doubleBuffer=1;
#endif
int speed,		/* speed (number of new line segs per redraw) */
    frame;		/* frame rate (actually noise value for TIMER0) */
float a = 0,
    da;			/* hexagon rotational velocity (.1 degree/redraw) */
float gravity;

/* function declarations */
void init_3d(void);
void init_graphics(void);
void draw_hexcube(void);
void draw_hexplane(void);
void draw_hexagon(void);
void move_eye(void);
void redraw(void);
void next_line(float v[][3], unsigned long *p);
void parse_args(int argc, char **argv);
void print_usage(char*);
void sphdraw(float args[4]);
void perspective(int angle, float aspect, float zNear, float zFar);


int main(int argc, char **argv)
{
    int i,j;
    int animate = 1, doRedraw = 1;

    parse_args(argc, argv);

    init_3d();
    init_graphics();

    /* draw the first POINTMASK points in each color */
    while(rp < POINTMASK) {
	next_line(rv, &rp);
	next_line(bv, &bp);
	next_line(gv, &gp);
	next_line(yv, &yp);
	next_line(mv, &mp);
    }

    eyex[0] = eyex[1] = eyex[2] = 0.;
    eyel[0] = rv[rp][0];
    eyel[1] = rv[rp][1];
    eyel[2] = rv[rp][2];
	
    glPushMatrix();
    move_eye();
    redraw();
    glPopMatrix();

    for (;;) {
	XEvent ev;

	if (XPending(dpy)) {
	  XNextEvent(dpy, &ev);
	  switch (ev.type) {
	    case KeyPress:
	      {
		char buf[100];
		int ret;
		KeySym ks;
		ret = XLookupString(&ev.xkey, buf, sizeof(buf), &ks, 0);
		if (ret) {
		    switch (ks) {
		      case XK_Escape:
			gluDeleteQuadric(quadObj);
			return 0;
		      case XK_space:
			doRedraw = 1;
			break;
		      case XK_g:
			animate = 1;
			break;
		      case XK_S: case XK_s:
			 animate = 0;
			 break;
		      case XK_X: case XK_x:
			cubeoffx += ((ev.xkey.state & ShiftMask) ? -1. : 1.);
			printf("<cubeoffx,cubeoffy,cubeoffz> = <%f,%f,%f>\n",
			       cubeoffx, cubeoffy, cubeoffz);
			break;
		      case XK_Y: case XK_y:
			cubeoffy += ((ev.xkey.state & ShiftMask) ? -1. : 1.);
			printf("<cubeoffx,cubeoffy,cubeoffz> = <%f,%f,%f>\n",
			       cubeoffx, cubeoffy, cubeoffz);
			break;
		      case XK_Z: case XK_z:
			cubeoffz += ((ev.xkey.state & ShiftMask) ? -1. : 1.);
			printf("<cubeoffx,cubeoffy,cubeoffz> = <%f,%f,%f>\n",
			       cubeoffx, cubeoffy, cubeoffz);
			break;
		      case XK_minus:
			farplane -= 5.;
			if(farplane <= 0)
			    farplane = 10.;
			perspective(fovy, (float)xmax/(float)ymax,
				    0.00001, farplane);
			printf("farplane = %f\n", farplane);
			break;
		      case XK_equal:
			farplane += 5.;
			perspective(fovy, (float)xmax/(float)ymax,
				    0.00001, farplane);
			printf("farplane = %f\n", farplane);
			break;
		      default:
			break;
		    }
		}
	    }
	    break;
	    case ConfigureNotify:
		xmax = ev.xconfigure.width;
		ymax = ev.xconfigure.height;
		glViewport(0, 0, xmax, ymax);
		break;
	  }
      } 
      if (doRedraw || animate) {
	    i = speed;
	    while (i--) {
		next_line(rv, &rp);
		next_line(bv, &bp);
		next_line(gv, &gp);
		next_line(yv, &yp);
		next_line(mv, &mp);
	    }
	    glPushMatrix();
	    move_eye();
	    redraw();
	    glPopMatrix();
	    doRedraw = 0;
      }
    }
}

/* compute the next point on the path according to Lorenz' equations. */
void next_line(float v[][3], unsigned long *p)
{

    dx = sigma * (v[*p][1] - v[*p][0]) * dt;
    dy = (r*v[*p][0] - v[*p][1] + v[*p][0]*v[*p][2]) * dt;
    dz = (v[*p][0] *v[*p][1] + b*v[*p][2]) * dt;	
	
    v[(*p + 1) & POINTMASK][0] = v[*p][0] + dx;
    v[(*p + 1) & POINTMASK][1] = v[*p][1] + dy;
    v[(*p + 1) & POINTMASK][2] = v[*p][2] - dz;
    *p = (*p + 1) & POINTMASK;
}


void redraw(void)
{
    unsigned long p = (rp+1)&POINTMASK;
    float sph[4];
	
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    if(hexflag)
	draw_hexcube();

    /* draw red points */
    glIndexi(redIndex);
	
    glBegin(GL_LINE_STRIP);
	/* draw points in order from oldest to newest */
	while(p != rp) {
	    glVertex3fv(rv[p]);
	    p = (p + 1) & POINTMASK;
	}
	glVertex3fv(rv[rp]);
    glEnd();

    sph[0] = rv[rp][0];
    sph[1] = rv[rp][1];
    sph[2] = rv[rp][2];
    sph[3] = 0.1;
    sphdraw(sph);

    /* draw blue points */
    p = (bp + 1) & POINTMASK;
    glIndexi(blueIndex);
    glBegin(GL_LINE_STRIP);
	while(p != bp) {
	    glVertex3fv(bv[p]);
	    p = (p + 1) & POINTMASK;
	}
	glVertex3fv(bv[bp]);
    glEnd();

    sph[0] = bv[bp][0];
    sph[1] = bv[bp][1];
    sph[2] = bv[bp][2];
    sph[3] = 0.1;
    sphdraw(sph);
	
    /* draw green points */
    p = (gp + 1) & POINTMASK;
    glIndexi(greenIndex);

    glBegin(GL_LINE_STRIP);
	while(p != gp) {
	    glVertex3fv(gv[p]);
	    p = (p + 1) & POINTMASK;
	}
	glVertex3fv(gv[gp]);
    glEnd();

    sph[0] = gv[gp][0];
    sph[1] = gv[gp][1];
    sph[2] = gv[gp][2];
    sph[3] = 0.1;
    sphdraw(sph);

    /* draw yellow points */
    p = (yp + 1) & POINTMASK;
    glIndexi(yellowIndex);

    glBegin(GL_LINE_STRIP);
	while(p != yp) {
	    glVertex3fv(yv[p]);
	    p = (p + 1) & POINTMASK;
	}
	glVertex3fv(yv[yp]);
    glEnd();

    sph[0] = yv[yp][0];
    sph[1] = yv[yp][1];
    sph[2] = yv[yp][2];
    sph[3] = 0.1;
    sphdraw(sph);

    /* draw magenta points */
    p = (mp + 1) & POINTMASK;
    glIndexi(magentaIndex);

    glBegin(GL_LINE_STRIP);
	while(p != mp) {
	    glVertex3fv(mv[p]);
	    p = (p + 1) & POINTMASK;
	}
	glVertex3fv(mv[mp]);
    glEnd();

    sph[0] = mv[mp][0];
    sph[1] = mv[mp][1];
    sph[2] = mv[mp][2];
    sph[3] = 0.1;
    sphdraw(sph);

#ifdef AIXV3
    if (doubleBuffer) {
        glXSwapBuffers(dpy, window);
    }
    else {
        glFlush();
    }
#else
    glXSwapBuffers(dpy, window);
#endif
}

void move_eye(void)
{
    /* first move the eye */
    eyev[0] += gravity * (rv[rp][0] - eyex[0]);
    eyev[1] += gravity * (rv[rp][1] - eyex[1]);
    eyev[2] += gravity * (rv[rp][2] - eyex[2]);

    /* adjust position using new velocity */
    eyex[0] += eyev[0] * dt;
    eyex[1] += eyev[1] * dt;
    eyex[2] += eyev[2] * dt;

    /* move the lookat point */
    /* it catches up to the red point if it's moving slowly enough */
    eyel[0] += LG * (rv[rp][0] - eyel[0]);
    eyel[1] += LG * (rv[rp][1] - eyel[1]);
    eyel[2] += LG * (rv[rp][2] - eyel[2]);

    /* change view */
    gluLookAt(eyex[0], eyex[1], eyex[2], eyel[0], eyel[1], eyel[2],
	      0, 1, 0);
}

void draw_hexcube(void)
{

    a += da;
    if(a >= 720.)		/* depends on slowest rotation factor */
	a = 0.;

    /* draw hexplanes, without changing z-values */
    glDepthMask(GL_FALSE); 
    glDisable(GL_DEPTH_TEST);

    /* x-y plane */
    glIndexi(darkBlueIndex);
    glPushMatrix();
    glTranslatef(cubeoffx, cubeoffy, cubeoffz);
    glScalef(CUBESCALE, CUBESCALE, CUBESCALE);
    draw_hexplane();
    glPopMatrix();

    /* x-y plane, translated */
    glPushMatrix();
    glTranslatef(cubeoffx, cubeoffy, cubeoffz - 2*CUBESIDE);
    glScalef(CUBESCALE, CUBESCALE, CUBESCALE);
    draw_hexplane();
    glPopMatrix();

    glIndexi(darkRedIndex);
    /* x-z plane, translate low */
    glPushMatrix();
    glRotatef(90, 1.0, 0.0, 0.0);
    glTranslatef(cubeoffx, cubeoffz - CUBESIDE, -cubeoffy + CUBESIDE);
    glScalef(CUBESCALE, CUBESCALE, CUBESCALE);
    draw_hexplane();
    glPopMatrix();

    /* x-z plane, translate high */
    glPushMatrix();
    glRotatef(90, 1.0, 0.0, 0.0);
    glTranslatef(cubeoffx, cubeoffz - CUBESIDE, -cubeoffy - CUBESIDE);
    glScalef(CUBESCALE, CUBESCALE, CUBESCALE);
    draw_hexplane();
    glPopMatrix();

    glIndexi(darkGreenIndex);
    /* y-z plane, translate low */
    glPushMatrix();
    glRotatef(90, 0.0, 1.0, 0.0);
    glTranslatef(-cubeoffz + CUBESIDE, cubeoffy, cubeoffx + CUBESIDE);
    glScalef(CUBESCALE, CUBESCALE, CUBESCALE);
    draw_hexplane();
    glPopMatrix();
	
    /* y-z plane, translate high */
    glPushMatrix();
    glRotatef (90, 0.0, 1.0, 0.0);
    glTranslatef(-cubeoffz + CUBESIDE, cubeoffy, cubeoffx - CUBESIDE);
    glScalef(CUBESCALE, CUBESCALE, CUBESCALE);
    draw_hexplane();
    glPopMatrix();

    glFlush();
    glDepthMask(GL_TRUE);
    glEnable(GL_DEPTH_TEST);
}

float hex_data[8][3] =  {
    {0., 0., 0.},
    {1.155, 0., 0.},
    {0.577, 1., 0.},
    {-0.577, 1., 0.},
    {-1.155, 0., 0.},
    {-0.577, -1., 0.},
    {0.577, -1., 0.},
    {1.155, 0., 0.},
};

/* draws a hexagon 2 units across, in the x-y plane, */
/* centered at <0, 0, 0> */

void draw_hexagon(void)
{
    if(wflag) {
	glPushMatrix();
	glRotatef(a, 0.0, 0.0, 1.0);
    }

    glBegin(GL_TRIANGLE_FAN);
	glVertex3fv(hex_data[0]);
	glVertex3fv(hex_data[1]);
	glVertex3fv(hex_data[2]);
	glVertex3fv(hex_data[3]);
	glVertex3fv(hex_data[4]);
	glVertex3fv(hex_data[5]);
	glVertex3fv(hex_data[6]);
	glVertex3fv(hex_data[7]);
    glEnd();

    if(wflag)
	glPopMatrix();
}

void tmp_draw_hexplane(void)
{
    glRectf(-2.0, -2.0, 2.0, 2.0);
}

/* draw 7 hexagons */
void draw_hexplane(void)
{
    if(wflag) {
	glPushMatrix();
	glRotatef(-0.5*a, 0.0, 0.0, 1.0);
    }

    /* center , <0, 0, 0> */
    draw_hexagon();

    /* 12 o'clock, <0, 4, 0> */
    glTranslatef(0., 4., 0.);
    draw_hexagon();

    /* 10 o'clock, <-3.464, 2, 0> */
    glTranslatef(-3.464, -2., 0.);
    draw_hexagon();

    /* 8 o'clock, <-3.464, -2, 0> */
    glTranslatef(0., -4., 0.);
    draw_hexagon();

    /* 6 o'clock, <0, -4, 0> */
    glTranslatef(3.464, -2., 0.);
    draw_hexagon();

    /* 4 o'clock, <3.464, -2, 0> */
    glTranslatef(3.464, 2., 0.);
    draw_hexagon();

    /* 2 o'clock, <3.464, 2, 0> */
    glTranslatef(0., 4., 0.);
    draw_hexagon();

    if(wflag)
	glPopMatrix();
}

void sphdraw(float args[4])
{
    glPushMatrix();
    glTranslatef(args[0], args[1], args[2]);
    gluSphere(quadObj, 0.3, 12, 8);
    glPopMatrix();
}

void perspective(int angle, float aspect, float zNear, float zFar)
{
    glPushAttrib(GL_TRANSFORM_BIT);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(angle * 0.1, aspect, zNear, zFar);
    glPopAttrib();
}

/* initialize global 3-vectors */
void init_3d(void)
{
    (void)srand48((long)time((time_t*)NULL));

    /* initialize colored points */
    rv[0][0] = (float)drand48() * 10.;
    rv[0][1] = (float)drand48() * 10.;
    rv[0][2] = (float)drand48() * 10. - 10.;

    bv[0][0] = rv[0][0] + (float)drand48()*5.;
    bv[0][1] = rv[0][1] + (float)drand48()*5.;
    bv[0][0] = rv[0][2] + (float)drand48()*5.;

    gv[0][0] = rv[0][0] + (float)drand48()*5.;
    gv[0][1] = rv[0][1] + (float)drand48()*5.;
    gv[0][0] = rv[0][2] + (float)drand48()*5.;

    yv[0][0] = rv[0][0] + (float)drand48()*5.;
    yv[0][1] = rv[0][1] + (float)drand48()*5.;
    yv[0][0] = rv[0][2] + (float)drand48()*5.;

    mv[0][0] = rv[0][0] + (float)drand48()*5.;
    mv[0][1] = rv[0][1] + (float)drand48()*5.;
    mv[0][0] = rv[0][2] + (float)drand48()*5.;

    /* initialize eye velocity */
    eyev[0] = eyev[1] = eyev[2] = 0.;
}

void SetColorIndices(Colormap cmap)
{
XColor color, exact;

    XAllocNamedColor(dpy, cmap, "black", &color, &exact);
    blackIndex = color.pixel;
    XAllocNamedColor(dpy, cmap, "white", &color, &exact);
    whiteIndex = color.pixel;

    XAllocNamedColor(dpy, cmap, "red", &color, &exact);
    redIndex = color.pixel;
    color.red = color.red /2;
    color.green = color.green /2;
    color.blue = color.blue/2;
    XAllocColor(dpy, cmap, &color);
    darkRedIndex = color.pixel;

    XAllocNamedColor(dpy, cmap, "green", &color, &exact);
    greenIndex = color.pixel;
    color.red = color.red /2;
    color.green = color.green /2;
    color.blue = color.blue/2;
    XAllocColor(dpy, cmap, &color);
    darkGreenIndex = color.pixel;

    XAllocNamedColor(dpy, cmap, "blue", &color, &exact);
    blueIndex = color.pixel;
    color.red = color.red /2;
    color.green = color.green /2;
    color.blue = color.blue/2;
    XAllocColor(dpy, cmap, &color);
    darkBlueIndex = color.pixel;

    XAllocNamedColor(dpy, cmap, "yellow", &color, &exact);
    yellowIndex = color.pixel;
    XAllocNamedColor(dpy, cmap, "cyan", &color, &exact);
    cyanIndex = color.pixel;
    XAllocNamedColor(dpy, cmap, "magenta", &color, &exact);
    magentaIndex = color.pixel;
}


/***rgb settings
static int attributes[] = {
    GLX_RGBA,
    GLX_RED_SIZE, 1,
    GLX_GREEN_SIZE, 1,
    GLX_BLUE_SIZE, 1,
    GLX_DOUBLEBUFFER, 
    GLX_DEPTH_SIZE, 1,
    None,
};
********/
#ifdef AIXV3
static int attr_sb[] = {
    GLX_DEPTH_SIZE, 1,
    None,
};
#endif
static int attributes[] = {
    GLX_DOUBLEBUFFER, 
    GLX_DEPTH_SIZE, 1,
    None,
};

static Bool WaitForMapNotify(Display *d, XEvent *e, char *arg)
{
    if ((e->type == MapNotify) && (e->xmap.window == (Window)arg)) {
	return GL_TRUE;
    }
    return GL_FALSE;
}

void init_graphics(void)
{
    XVisualInfo *vi;
    Colormap cmap;
    XSetWindowAttributes swa;
    GLXContext cx;
    XEvent event;
    GLboolean needDisplay;
    int width = 600;
    int height = 600;
    XTextProperty textProp;
    char *winName = "Lorenz Demo";

    dpy = XOpenDisplay(0);
    if (!dpy) {
	fprintf(stderr, "Can't connect to display \"%s\"\n", getenv("DISPLAY"));
	exit(-1);
    }

#ifdef AIXV3
    if (doubleBuffer) {
       vi = glXChooseVisual(dpy, DefaultScreen(dpy), attributes);
       if (!vi) {
	   fprintf(stderr, "No doublebuffered color index visual on \"%s\"\n",
		getenv("DISPLAY"));
	   exit(-1);
       }
    } else {
       vi = glXChooseVisual(dpy, DefaultScreen(dpy), attr_sb);
       if (!vi) {
     	   fprintf(stderr, "No color index visual on \"%s\"\n",
         	getenv("DISPLAY"));
           exit(-1);
       }
    }
#else
    vi = glXChooseVisual(dpy, DefaultScreen(dpy), attributes);
    if (!vi) {
        fprintf(stderr, "No doublebuffered color index visual on \"%s\"\n",getenv("DISPLAY"));
        exit(-1);
    }
#endif

    cmap = XCreateColormap(dpy, RootWindow(dpy, vi->screen), vi->visual,
			   AllocNone);
    swa.border_pixel = 0;
    swa.colormap = cmap;
    swa.event_mask = ExposureMask | StructureNotifyMask | KeyPressMask
	| KeyReleaseMask;
    window = XCreateWindow(dpy, RootWindow(dpy, vi->screen), 10, 10,
			   width, height,
			   0, vi->depth, InputOutput, vi->visual,
			   CWBorderPixel|CWColormap|CWEventMask, &swa);
    XSetWMColormapWindows(dpy, window, &window, 1);
    XStringListToTextProperty(&winName, 1, &textProp);
    XSetWMName(dpy, window, &textProp);
    SetColorIndices(cmap);
    XMapWindow(dpy, window);
    XIfEvent(dpy, &event, WaitForMapNotify, (char*)window);

    cx = glXCreateContext(dpy, vi, 0, GL_FALSE);
    if (!glXMakeCurrent(dpy, window, cx)) {
	fprintf(stderr, "Can't make window current to context\n");
	exit(-1);
    }

    xmax = width;
    ymax = height;
#ifdef AIXV3
    if (doubleBuffer) {
       glDrawBuffer(GL_BACK);
    } else {
       glDrawBuffer(GL_FRONT);
    }
#else
    glDrawBuffer(GL_BACK);
#endif
    glEnable(GL_DEPTH_TEST);
    glClearIndex(blackIndex);
    glClearDepth(1.0);

    glViewport(0, 0, xmax, ymax);
    perspective(fovy, (float)xmax/(float)ymax, 0.00001, farplane);
    quadObj = gluNewQuadric();
}

extern char *optarg;
extern int optind, opterr;

#define USAGE "usage message: this space for rent\n"
void parse_args(int argc, char **argv)
{
    int c;
#ifdef AIXV3
    int i;
#endif

#ifdef AIXV3
    hexflag = sflag = rflag = fflag = wflag = gflag = debug = FALSE;
#else
    hexflag = sflag = fflag = wflag = gflag = debug = FALSE;
#endif
    opterr = 0;

#ifndef AIXV3
    while( (c = getopt(argc, argv, "Xhx:s:f:w:g:")) != -1)
	switch(c) {
#else
    for (i = 1; i < argc; i++) {
      if (argv[i][0] == '-')   {
	switch(argv[i][1]) {
#endif
	  case 'X':
	    debug = TRUE;
	    break;
	  case 'h':
	    print_usage(argv[0]);
	    exit(1);	
#ifdef AIXV3
          case 's':
            doubleBuffer = 0;
            break;
#endif
	  case 'x':
	    hexflag = TRUE;
/*****   hexbright is not used (brightness cannot be changed)    ******
	    hexbright = (short)atoi(optarg);
	    if(hexbright > (short)255 || hexbright <= (short)0) {
		fprintf(stderr, "Need a brightness value between 0 and ");
		fprintf(stderr, "255 after 'x' option.\n");
		fprintf(stderr, "Try %s -h for help\n", argv[0]);
		exit(1);
	    }
 *****   * * * * * * * * * * * * * * * * * * * * * * * * * * *   ******/
	    farplane = 300.;
	    break;
#ifdef AIXV3
	  case 'r':
	    rflag = TRUE;
            i++;
            optarg = argv[i];
	    speed = atoi(optarg);
	    if(speed < 0) {
		fprintf(stderr, "Use a small positive value for rate  ('r').\n");
		fprintf(stderr, "Try %s -h for help\n", argv[0]);
		exit(1);
	    }
#else
          case 's':
	    sflag = TRUE;
	    speed = atoi(optarg);
	    if(speed < 0) {
		fprintf(stderr, "Use a small positive value for speed  ('s').\n");
		fprintf(stderr, "Try %s -h for help\n", argv[0]);
		exit(1);
	    }
#endif
	    break;
	  case 'f':
	    fflag = TRUE;
#ifdef AIXV3
            i++;
            optarg = argv[i];
#endif
	    frame = atoi(optarg);
	    if(frame < 0) {
		fprintf(stderr, "Try a small positive value for \n");
		fprintf(stderr, "'f'; this is the number of vertical ");
		fprintf(stderr, "retraces per redraw\n");
		fprintf(stderr, "Try %s -h for help\n", argv[0]);
		exit(1);
	    }
	    break;
	  case 'w':
	    wflag = TRUE;
#ifdef AIXV3
            i++;
            optarg = argv[i];
#endif
	    da = atof(optarg);
	    if(da > 10.) {
		fprintf(stderr, "That's a large rotational velocity ('w')");
		fprintf(stderr, " but you asked for it\n");
	    }
	    break;
	  case 'g':
	    gflag = TRUE;
#ifdef AIXV3
            i++;
            optarg = argv[i];
#endif
	    gravity = atof(optarg);
	    if(gravity <= 0.0) {
		fprintf(stderr, "Gravity ('g') should be positive\n");
		fprintf(stderr, "Try %s -h for help\n", argv[0]);
	    }
	    break;
	  case '?':
	    fprintf(stderr, USAGE);
	}
       }
      }

    /* set up default values */
#ifdef AIXV3
    if(!rflag)
#else
    if(!sflag)
#endif
	speed = 3;
    if(!fflag)
	frame = 2;	
    if(!wflag)
	da = 0.;
    if(!gflag)
	gravity = G;
}


	/* while( (c = getopt(argc, argv, "Xhx:s:f:w:g:")) != -1) */
void print_usage(char *program)
{
#ifdef AIXV3
/* printf("\nUsage: %s [-h] [-x b] [-r rate] [-s single buffer]", program); */
printf("\nUsage: %s [-h] [-x] [-r rate] [-s]", program);
#else
/* printf("\nUsage: %s [-h] [-x b] [-s speed] [-s single buffer]", program); */
printf("\nUsage: %s [-h] [-x] [-s speed]", program);
#endif
printf(" [-f framenoise] [-w rot_v] [-g gravity]\n\n");
printf("-h              Print this message.\n");
/*****     Currently b is not being used to control brightness.   *****
printf("-x b            Enclose the particles in a box made of hexagons,\n");
printf("                whose brightness is b.\n");
 *****                                                            *****/
printf("-x              Enclose the particles in a box made of hexagons.\n");
#ifdef AIXV3
printf("-r rate         Sets the number of new line segments per redraw \n");
printf("                interval per line. Default value: 3.\n");
printf("-s single buffer Single buffer mode.\n");
#else
printf("-s speed        Sets the number of new line segments per redraw \n");
printf("                interval per line. Default value: 3.\n");
#endif

/*** The X port does not currently include a timer, so this feature is disabled.
printf("-f framenoise   Sets the number of vertical retraces per redraw\n");
printf("                interval. Example: -f 2 specifies one redraw per\n");
printf("                2 vertical retraces, or 30 frames per second.\n");
printf("                Default value: 2.\n");
************/

printf("-w rot_v        Spins the hexagons on their centers, and the sides\n");
printf("                of the box on their centers. Hexagons spin at the\n");
printf("                rate rot_v degrees per redraw, and box sides spin\n");
printf("                at -rot_v/2 degrees per redraw.\n");
printf("-g gravity      Sets the strength of the attraction of the eye to\n");
printf("                the red particle. Actually, it's not gravity since\n");
printf("                the attraction is proportionate to distance.\n");
printf("                Default value: 0.002. Try large values!\n");
/* input added for GLX port */
printf(" Executions control:  \n");
printf("    <spacebar>	step through single frames\n");
printf("    g		begin continuous frames\n");
printf("    s		stop continuous frames\n");
printf("\nBugs: hidden surface removal doesn't apply to hexagons, and\n");
printf("works poorly on lines when they are too close together.\n");

}
