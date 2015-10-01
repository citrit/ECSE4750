/*
   xlots.c Create lots of label widgets, stored in an array.  Make the
   toplevel window shorter and wider, and watch the widgets rearrange
   themselves.

   Wm. Randolph Franklin
   Time-stamp: </dept/ecse/graphics/xlots.c, Tue, 27 Jan 1998, 17:07:34 PST, wrf@mab.ecse.rpi.edu>

   */


#include <stdio.h>
#include <Xm/Xm.h>		/* Needed by all Motif programs. */
#include <Xm/RowColumn.h>
#include <Xm/Label.h>

#define N 100

void
main (int argc, char **argv)
{
    Widget  top_level;		/* top level widget    */
    Widget  w[N];
    Widget  bb;			/* manager of buttons  */
    XtAppContext app_context;	/* Application Context */
    XmString pressme_label;	/* label for pressme   */
    int     i;
    char    s[100];		/* ascii string of the current widget name */

    /* Initialize the Intrinsics Toolkit */
    top_level = XtVaAppInitialize (
		  &app_context,	/* Application Context */
		  "XLots",	/* class               */
		  NULL,	/* options             */
		  0,	/* num_options         */
		  &argc,	/* argc                */
		  argv,	/* argv                */
		  NULL,	/* fallback_resources  */
		  NULL);	/* args                */


    bb = XtVaCreateManagedWidget (
		 "rowcol",	/* name     */
		 xmRowColumnWidgetClass,	/* class    */
		 top_level,		/* parent   */
		 NULL);	/* args     */

    for (i = 0; i < N; i++)
      {
	  sprintf (s, "%d", i);
	  w[i] = XtVaCreateManagedWidget (s, xmLabelWidgetClass, bb, NULL);

      }
    XtRealizeWidget (top_level);	/* Set the ball rolling */
    XtAppMainLoop (app_context);
}
