/*
   xmove.c  Move a widget by changing its resources dynamically.
   Also change a widget's label dynamically.
   Wm. Randolph Franklin
   Last modified: by Wm Randolph Franklin (wrf) on speed.ecse.rpi.edu at Wed Sep 28 18:59:03 1994

 */


#include <stdio.h>
#include <Xm/Xm.h>		/* Needed by all Motif programs. */
#include <Xm/PushB.h>		/* Motif PushButton Widget */

Widget  but;
Widget  top_level;		/* top level widget    */
char c[100];
int counter=0;

static void
Move (Widget w, XtPointer client_data, XtPointer call_data)
{
    Position     x, y;
    char s[100];
    XmString s2;

    XtVaGetValues (top_level, XmNx, &x, XmNy, &y, NULL);
    x += 20;
    y += 10;
    XtVaSetValues (top_level, XmNx, x, XmNy, y, NULL);

    sprintf(s,"#%d, at (%d,%d)\n",counter++,x,y);
    s2=XmStringCreateSimple(s);
    XtVaSetValues (but, XmNlabelString, s2, NULL);
}


void
main (int argc, char **argv)
{
    XtAppContext app_context;	/* Application Context */
    int     i;


    /* Initialize the Intrinsics Toolkit */
    top_level = XtVaAppInitialize (
	&app_context,	/* Application Context */
	"XMove",	/* class               */
	NULL,	/* options             */
	0,	/* num_options         */
	&argc,	/* argc                */
	argv,	/* argv                */
	NULL,	/* fallback_resources  */
	NULL);	/* args                */


    but = XtVaCreateManagedWidget ("but",	/* name     */
     xmPushButtonWidgetClass,	/* class    */
     top_level,	/* parent   */
     NULL);	/* args     */


    XtAddCallback (but, XmNactivateCallback, Move, 0);
    XtRealizeWidget (top_level);	/* Set the ball rolling */
    XtAppMainLoop (app_context);
}
