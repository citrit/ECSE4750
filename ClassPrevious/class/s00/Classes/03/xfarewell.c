/** xfarewell.c - Attaching User Defined Actions to Events
 *
 * Modified by Wm. Randolph Franklin and
 * Shivkumar (Shiva) Ramabadran from a program in O'Reilly.
 *
 * Time-stamp: </dept/ecse/graphics/xfarewell.c, Fri,  5 Sep 1997, 13:43:54 PDT, wrf@mab.ecse.rpi.edu>
*/

#include <stdio.h>		/* For printf() */
#include <Xm/Xm.h>		/* Needed by all Motif programs. */
#include <Xm/PushB.h>		/* Motif PushButton Widget */

/*
 * Confirm action routine
 */

void
Confirm (Widget w, XButtonEvent * event, String * params, 
	 Cardinal * num_params)
{
  fprintf (stderr, "Are you sure you want to exit?\n\
          Click with the middle pointer button if you're sure.\n");
}

/*
 * Quit action routine.    Action routine args are different from 
 * callback routine args.
 */

void
Quit (Widget w, XButtonEvent * event, String * params,
      Cardinal * num_params)
{
  fprintf (stderr, "It was nice knowing you.\n");
  exit (0);
}

/* MAIN */

main (int argc, char **argv)
{
  Widget top_level, farewell_widget;
  XtAppContext app_context;

  /* Associate action names, which are char strings also used in the
     translation table in the resources file, with C routines.  */

  static XtActionsRec my_actions[] =
  {
    {"confirm", (XtActionProc) Confirm},
    {"quit", (XtActionProc) Quit},
  };

  top_level = XtVaAppInitialize (&app_context,	/* application context */
				 "XFarewell",	/* Application class */
				 NULL, 0,	/* command line option list */
				 &argc, argv,	/* command line args */
				 NULL,	/* fallback resources */
				 NULL);		/* args */

  farewell_widget = XtVaCreateManagedWidget (
		    "farewell",	/* name   */
		    xmPushButtonWidgetClass,	/* class  */
		    top_level,		/* parent */
		    NULL	/* args   */
		    );

  XtAppAddActions (app_context, my_actions, XtNumber (my_actions));

  XtRealizeWidget (top_level);	/* Create windows for widgets and map them. */

  XtAppMainLoop (app_context);	/* Loop for events. */
}
