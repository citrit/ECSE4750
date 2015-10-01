/* 
 * xhello.c:  Simplest X program.  Uses Motif label widget.
 * 
 * Wm. Randolph Franklin
 * 
 * Time-stamp: </s/cg-f99/Classes/02/xhello.c, Thu,  2 Sep 1999, 14:57:26 EDT, wrf@benvolio.ecse.rpi.edu>
 * 
 */


#include <Xm/Xm.h>		/* Needed by all Motif programs. */
#include <Xm/Label.h>		/* This is for the Motif Label Widget */

/* 
 * main
 */

void
main (int argc, char **argv)
{
    Widget  top_level_widget, hello_widget;
    XtAppContext app;

    top_level_widget = XtVaAppInitialize (
       &app,			/* ignore */
       "XHello",		/* ignore */
       NULL, 0,			/* ignore */
       &argc, argv,		/* Enable processing standard command line options */
       NULL,			/* ignore */
       NULL			/* ignore */
       );

    /* 
     * Make a label widget that says "hello".
     */

    hello_widget = XtVaCreateManagedWidget (
       "hello",			/* name     */
       xmLabelWidgetClass,	/* class    */
       top_level_widget,	/* parent   */
       NULL			/* ignore     */
       );

    XtRealizeWidget (top_level_widget);		/* Figure window sizes and  locations, and
						   create windows.  */

    XtAppMainLoop (app);	/* Wait for events to process */
}
