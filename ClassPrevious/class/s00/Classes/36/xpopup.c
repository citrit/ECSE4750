/*
   xpopup.c  Demo popup widgets.

   Wm. Randolph Franklin
   Time-stamp: </home/wrf/cg-f99/xpopup.c, Mon, 22 Nov 1999, 16:31:21 EST, wrf@benvolio.ecse.rpi.edu>

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
    Widget  rc, rc2, popup, l1, l2 ,l3;
    XtAppContext app_context;	/* Application Context */


    /* Initialize the Intrinsics Toolkit */
    top_level = XtVaAppInitialize (
		  &app_context,	/* Application Context */
		  "XPopup",	/* class               */
		  NULL,	/* options             */
		  0,	/* num_options         */
		  &argc,	/* argc                */
		  argv,	/* argv                */
		  NULL,	/* fallback_resources  */
		  NULL);	/* args                */


    rc = XtVaCreateManagedWidget (
		 "llllll",	/* name     */
		 xmLabelWidgetClass,	/* class    */
		 top_level,		/* parent   */
		 NULL);	/* args     */


    popup = XtCreatePopupShell("popup", transientShellWidgetClass, top_level, NULL, 0);

    rc2 = XtVaCreateManagedWidget (
		 "rowcol2",	/* name     */
		 xmRowColumnWidgetClass,	/* class    */
		 popup,		/* parent   */
		 NULL);	/* args     */

    l1 = XtVaCreateManagedWidget ("1", xmLabelWidgetClass, rc2, NULL);
    l2 = XtVaCreateManagedWidget ("22", xmLabelWidgetClass, rc2, NULL);
    l3 = XtVaCreateManagedWidget ("333", xmLabelWidgetClass, rc2, NULL);
    XtPopup(popup, XtGrabNone);
    XtRealizeWidget (top_level);	/* Set the ball rolling */
    XtAppMainLoop (app_context);
}
