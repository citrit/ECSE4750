/* Copyright 1997 Free Software Foundation, Inc.
 */
/*
 * Leo Chan -- 1995
 *
 * This file takes care of the C implementation of opening up an
 * X window, assigning an OpenGL graphics context to it, storing that
 * graphics context in the java class data structure.
 *
 * also contains the use() and swap() functions for double buffering
 *
 * Adam King -- 1997
 *
 * Modified Creation window code to use the currently showing Java Frame
 * window instead of creating a new window.
 *
 * Tommy Reilly
 *
 */

/* 
 * need to include the JAVA internal header files for macros and function
 * prototypes required to maipulated JAVA data structures and functions
 *
 */

#include <stdio.h>
#include "jogl_JoglCanvas.h"
#include "jogl_lib.h"
#include <config.h>
#include <GL/glx.h>
#include <X11/Xatom.h>

static int glAttributes[] = 
{
   GLX_RGBA, GLX_RED_SIZE, 1, GLX_GREEN_SIZE, 1, GLX_BLUE_SIZE, 1,
   GLX_DOUBLEBUFFER, GLX_DEPTH_SIZE, 1, None
};

static int createGC( Display *display, Window win, XVisualInfo *visual,
                     GLXContext *gc );
static XVisualInfo *findVisual( Display *display);
static Colormap allocateColourmap( Display *display, XVisualInfo *visual );

/*
 * initGL
 *
 * Make the window suitable for gl calls (or create a sub window 
 * if need be, ie SGIs)
 *
 */
JC_BOOL_LIB_FUNC(initGL) 
ARGS()
{
  Display *display;
  XVisualInfo *visualInfo;   
  Window canvas, window;
  GLXContext gc;
  THIS_CLASS(); /* creates a this_class variable (of type jclass) for use in other macros or otherwise*/

  display = XOpenDisplay( NULL );
   
  if( display == NULL ) 
    {
      /* return FALSE to tell JAVA that we couldn't open.  could also use
       * SignalError() here if we would prefer to throw an exception
       */
      return FALSE;
    }

  SET_INT_FIELD("display", (long) display);

  canvas = (Window) GET_INT_FIELD("window");

  /* Check to see if the Xserver supports OpenGL */
  if( !glXQueryExtension(display, (int *) 0, (int *) 0) ) 
    {
      return FALSE;
    }  

  visualInfo = findVisual(display);

  if( !visualInfo )
    {
      printf( "Couldn't get visual. Exiting...\n" );
      return FALSE;
     } 
  
#ifdef NEED_OWN_WINDOW
  {
    XSetWindowAttributes attribs;
    int width, height;

    width  = CALL_INT_METHOD("getWidth");
    height = CALL_INT_METHOD("getHeight");

    attribs.event_mask = ExposureMask;
    attribs.border_pixel = BlackPixel(display, DefaultScreen(display));
    attribs.colormap = allocateColourmap( display, visualInfo );
/*     attribs.colormap = (Colormap) GET_INT_FIELD("colormap"); */
    attribs.background_pixel = 0xFFFFFFFF;  
   
    window = XCreateWindow(display,
                           canvas, 0,0, width,
                           height, 0,
                           visualInfo->depth,
                           InputOutput, visualInfo->visual,
                           CWEventMask | CWColormap | CWBorderPixel | CWBackPixel,
                           &attribs);

    
    XMapWindow( display, window );

    /* the window to this new window so calls to use and swap work */
    SET_INT_FIELD("window", (long) window);
  }
  
#else
  /* on systems other than SGI the actual canvas suits our needs 
     so use that when creating the context below */
    window = canvas;
#endif

   /* create the graphics context for this widget */
   if( createGC( display, window, visualInfo, &(gc) ) != 0 )	
      {
#ifdef NEED_OWN_WINDOW
	 XDestroyWindow( display, window );
         XCloseDisplay( display );
#endif
	 return FALSE;
      }
   
   SET_INT_FIELD("renderContext", (long) gc);

   XFlush( display );   

   return TRUE;
}

JC_BOOL_LIB_FUNC(use) 
ARGS()
{
   Display *disp;
   GLXContext gc;
   Window win; 
   THIS_CLASS();


   disp = (Display *) GET_INT_FIELD("display");
   win = (Window) GET_INT_FIELD("window");
   gc = (GLXContext) GET_INT_FIELD("renderContext");

   if( !gc )
      return 0;
   
   if( !glXMakeCurrent( disp, win, gc ) ) {
      return 0;
   }

   return 1;
}

JC_VOID_LIB_FUNC(swap)
ARGS()
{
   Display *disp;
   Window win;
   jboolean dbuf;
   THIS_CLASS();

   win = (Window) GET_INT_FIELD("window");
   dbuf = GET_BOOL_FIELD("doubleBuffer");
   disp = (Display *) GET_INT_FIELD("display");
   
   if( !disp )
      return; 
   
   if( dbuf ) 
      {
	 glXSwapBuffers( disp, win );
      } 
   else 
      { /* don't double buffer */
	 glXWaitGL();
      }
}


JC_VOID_LIB_FUNC(canvasResized)
ARGS2(jint width, jint height)
{  
#ifdef NEED_OWN_WINDOW
   Display *disp;
   Window win;
   THIS_CLASS();

   win = (Window) GET_INT_FIELD("window");
   disp = (Display *) GET_INT_FIELD("display");

   XResizeWindow(disp, win, width, height);
#endif
}


/*
 * Name      : int createGC( Window win, XVisualInfo *visual, GLXContext *gc )
 *
 * Parameters: win    - the X window use to the OpenGL context with
 *             visual - The visual to create the context for
 *             gc     - a pointer to a GLXContext structure. This is how
 *                      the created context will be returned to the caller
 *
 * Returns   : a pointer to a created GLXContext is returned through the
 *             gc argument.
 *             int - an error code: 0 means everything was fine
 *                                 -1 context creation failed
 *                                 -2 context/window association failed
 *
 * Purpose   : create an X window Graphics context and assocaite it with
 *             the window. It returns 0 if everything was fine, -1 if the
 *             context could not be created, -2 if the context could not
 *             be associated with the window
 */
static int createGC( Display *display, Window win, XVisualInfo *visual,
                   GLXContext *gc )
{
    *gc = glXCreateContext( display, visual, None, GL_FALSE );

    /* check if the context could be created */
    if( *gc == NULL ) {
        return( -1 );
    }

    /* associated the context with the X window */
    if( glXMakeCurrent( display, win, *gc ) == False) 
      {
        glXDestroyContext( display, *gc );
        return( -2 );
      }           

    return 0;
}

/*
 * Name      : Colormap allocateColourmap( Display *display,
 *                                         XVisualInfo *visual )
 *
 * Parameters: display - the X display to create the colormap on
 *             visual  - The visual to create the colormap for
 *
 * Returns   : colormap - an xwindow colormap allocated for use with the
 *                        supplied visual and display
 *
 * Purpose   : create a colour map to use with the X window. Even though
 *             the visual may be a 24 bit TrueColor visual, you still need
 *             a colour map if the visual is not the default visual used by
 *             the window manager. Most OpenGL visuals are not the default
 *             visual used by the window manager so just to be safe we
 *              always allocate a colour map.
 */
static Colormap allocateColourmap( Display *display, XVisualInfo *visual )
{
    /* now we have the visual with the best depth so lets make a colour map
       for it.  we use allocnone because this is a true colour visual and
       the colour map is read only anyway. This must be done because we
       cannot call XCreateSimpleWindow. This is really the gross part of
       working with X windows. */
    return( XCreateColormap( display,
                             RootWindow( display, visual->screen ),
                             visual->visual,
                             AllocNone ) );
}

static XVisualInfo *findVisual( Display *display)
{
    int visualAttribList[11];
    XVisualInfo *visual;

    /* Ask GLX for a visual that matches the attributes we want: */
    visualAttribList[0] = GLX_RGBA;
    visualAttribList[1] = GLX_RED_SIZE;
    visualAttribList[2] = 1;
    visualAttribList[3] = GLX_GREEN_SIZE;
    visualAttribList[4] = 1;
    visualAttribList[5] = GLX_BLUE_SIZE;
    visualAttribList[6] = 1;
    visualAttribList[7] = GLX_DOUBLEBUFFER;
    visualAttribList[8] = GLX_DEPTH_SIZE;
    visualAttribList[9] = 1;
    visualAttribList[10] = None;

    visual = glXChooseVisual( display,
                              DefaultScreen( display ),
                              visualAttribList );
    return( visual );
}
