/* 
 * xuserargs.c 
 * 
 * Wm. Randolph Franklin
 * 
 * Time-stamp: </h/web/cg-f99/Classes/11/xuserargs.c, Thu, 23 Sep 1999, 14:33:25 EDT, wrf@benvolio.ecse.rpi.edu>
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
    int i;

    printf("Before init, argc=%d\n", argc);

    top_level_widget = XtVaAppInitialize (
       &app,			/* ignore */
       "XHello",		/* ignore */
       NULL, 0,			/* ignore */
       &argc, argv,		/* Enable processing standard command line options */
       NULL,			/* ignore */
       NULL			/* ignore */
       );

    printf("After init, argc=%d, Remaining args (if any):\n",argc);
    for (i=0;i<argc;i++) printf(" %s ", argv[i]);
    printf("\n");


    /* 
     * Make a label widget that says "hello".
     */

    hello_widget = XtVaCreateManagedWidget (
       "hello",			/* name     */       xmLabelWidgetClass,	/* class    */
       top_level_widget,	/* parent   */
       NULL			/* ignore     */
       );

    XtRealizeWidget (top_level_widget);		/* Figure window sizes and  locations, and
						   create windows.  */

    XtAppMainLoop (app);	/* Wait for events to process */
}
