/* xbox.c: 

  This illustrates these things.

  - Setting resources when a widget is created.
  - A bulletinboard widget.  You have to specify its kids'
    locations explicitly, like pinning a note on a real bulletin board.
  - Converting a text string to the Motif compound string format
    needed when setting a label inside the program.
  - Creating a popup.
  - Creating a Motif convenience popup dialog widget.
  - Putting callbacks on two of its buttons, and
  - Reading the string the user typed in it.

  Modified by Wm. Randolph Franklin and Shivkumar
 (Shiva) Ramabadran from a program in O'Reilly.

  Last modified: by Wm Randolph Franklin (wrf) Tue Jan 28 1997

 */

#include <stdio.h>
#include <Xm/Xm.h>		/* Needed by all Motif programs. */
#include <Xm/BulletinB.h>
#include <Xm/DialogS.h>
#include <Xm/Label.h>
#include <Xm/PushB.h>
#include <Xm/SelectioB.h>

/*
 * Quit:  callback for the quit button.
 */
void Quit(Widget w, XtPointer client_data, XtPointer call_data)
{
    fprintf(stdout, "That was easy.\n");
    exit(0);
}

/*
 * OkButtonCallback:  called by dialogbox OK button.
 */
void  OkButtonCallback(Widget w, XtPointer client_data, XtPointer call_data)
{
    XmSelectionBoxCallbackStruct *selection;
    char   *text;

    /* The information about the selection is passed in call_data. */
    selection = (XmSelectionBoxCallbackStruct *) call_data;

    fprintf(stdout, "You clicked on the OK Button.\n");
    if (selection->length) {
	XmStringGetLtoR(selection->value, XmFONTLIST_DEFAULT_TAG, &text);
	fprintf(stdout, "You typed '%s'\n", text);
	XtFree(text);
    }
}

/*
 * CancelButtonCallback
 * 
 * called by dialogbox CANCEL button.
 */
void CancelButtonCallback(Widget w, XtPointer client_data, XtPointer call_data)
{
    fprintf(stdout, "You hit the CANCEL button.\n");
}

/*
 * PopupDialog:  callback for the PRESSME button.
 */
void PopupDialog(Widget w, XtPointer client_data, XtPointer call_data)
{
    Widget  parent;		/* The parent to use   */
    static Widget dialogbox = (Widget) NULL;	/* dialog box          */

    /* The parent is in client_data */
    parent = (Widget) client_data;

    if (!dialogbox) {  /* Create this only the first time thru. */
	dialogbox = XmCreatePromptDialog(parent,	/* parent   */
					 "dialogbox",	/* name     */
					 NULL,	/* args     */
					 0);	/* num_args */

	/*
	 * Now setup callbacks on the OK and CANCEL Button's on the dialogbox.
	 */

	XtAddCallback(dialogbox, XmNokCallback, OkButtonCallback, 0);
	XtAddCallback(dialogbox, XmNcancelCallback, CancelButtonCallback, 0);

    }
    XtManageChild(dialogbox);
}

/*
 * main.
 */
void 
main(int argc, char **argv)
{
    Widget  top_level;		/* top level widget    */
    Widget  quit_widget, pressme_widget;	/* pushButtons.        */
    Widget  bb_widget;			/* manager of buttons  */
    XtAppContext app_context;	/* Application Context */
    XmString pressme_label;	/* label for pressme   */

    /* Initialize the Intrinsics Toolkit */
    top_level = XtVaAppInitialize(&app_context,	/* Application Context */
				  "XBox",	/* class               */
				  NULL,	/* options             */
				  0,	/* num_options         */
				  &argc,	/* argc                */
				  argv,	/* argv                */
				  NULL,	/* fallback_resources  */
				  NULL);	/* args                */

    /*
     * Create the BulletinBoard widget that will parent the pushButtons and manage
     * their geometry.
     */
    bb_widget = XtVaCreateManagedWidget(
				 "bulletinboard",	/* name     */
				 xmBulletinBoardWidgetClass,	/* class    */
				 top_level,	/* parent   */
				 NULL);	/* args     */

    /* Create the Quit Button, and tell it where to position itself */
    quit_widget = XtVaCreateManagedWidget(
				   "quit",	/* name     */
				   xmPushButtonWidgetClass,	/* class    */
				   bb_widget,	/* parent   */
				   XmNx, 20,	/* Some resources */
				   XmNy, 20,
				   NULL);
    /* Tell it what to do. */
    XtAddCallback(quit_widget,		/* widget              */
		  XmNactivateCallback,	/* callback_name       */
		  Quit,		/* callback            */
		  0		/* user info             */
	);

    /*  Create the label string in Motif compound string format.  If
        you specify the label when creating the widget, then you must
        use a Motif compound string, not a simple Ascii string.  The
        widget name is still simple ASCII. */

    pressme_label = XmStringCreateSimple("Press me Again, NOW!");

    /* Create the PressMe button. */
    pressme_widget = XtVaCreateManagedWidget(
				    "pressme",	/* name     */
				    xmPushButtonWidgetClass,	/* class    */
				    bb_widget,	/* parent   */
				    XmNlabelString, pressme_label,
				    XmNx, 20,
				    XmNy, 50,
				    NULL);

    /* Tell the Pressme button what to do */
    XtAddCallback(pressme_widget,	/* widget               */
		  XmNactivateCallback,	/* callback_name        */
		  PopupDialog,	/* callback             */
		  (XtPointer) top_level	/* user info for callback */
	);
    XtRealizeWidget(top_level);
    XtAppMainLoop(app_context);
}
