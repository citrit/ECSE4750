/* xgoodbye.c: motif pushbutton widget example

 * Wm. Randolph Franklin 

 * Time-stamp: </s/cg-f99/Classes/02/xgoodbye.c, Thu,  2 Sep 1999, 14:59:13 EDT, wrf@benvolio.ecse.rpi.edu>

 * This puts a message in a pushbutton widget.  Pushing it kills the
 * program.

 */

#include <stdio.h>

#include <Xm/Xm.h>		/* Needed by all Motif programs. */
#include <Xm/PushB.h>		/* Defines the pushbutton widget */

/* Routine to call when the button is pushed (activated). */

void
Quit (Widget w,			/* Address of activated widget */
   XtPointer client_data,	/* Optional user data from XtAddCallback */
   XtPointer call_data)		/* system data (unused here) */
{
    printf ("It was nice knowing you.\n");
    exit (0);
}

/* 
 * main
 */
void
main (int argc, char **argv)
{
    Widget  top_level_widget, goodbye_widget;
    XtAppContext app;

    top_level_widget = XtVaAppInitialize (
       &app,			/* Application context */
       "XGoodbye",		/* Class.  Used as part of resource file name. */
       NULL, 0,			/* Define new options on command-line. */
       &argc, argv,		/* Enable processing standard command line options */
       NULL,			/* Fallback resources, used when no resource file. */
       NULL			/* NULL-terminated resource list */
       );

    /* Create the pushButton.  Its text, which defaults to "goodbye", will be gotten from the
       resource file. */

    goodbye_widget = XtVaCreateManagedWidget (
       "goodbye",		/* Name.  Used to specify resources. */
       xmPushButtonWidgetClass,	/* Class */
       top_level_widget,	/* Parent */
       NULL			/* Args */
       );

    /* Tell the button what to do. */
    XtAddCallback (goodbye_widget,	/* Widget address (not name!) */
       XmNactivateCallback,	/* When that widget is activated ... */
       Quit,			/* call this routine ... */
       0			/* with this optional data. */
       );

    XtRealizeWidget (top_level_widget);		/* Manage and Map the Widget */

    XtAppMainLoop (app);	/* Process X events */

}
