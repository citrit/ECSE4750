/** xtext.c
  *
  * Time-stamp: </dept/ecse/graphics/h04/xtext.c, Mon, 25 Jan 1999, 23:58:08 IST, wrf@speed.ecse.rpi.edu>
 *
 * For Computer Graphics class.  Modified by Wm. Randolph Franklin and
 * Shivkumar (Shiva) Ramabadran from a program in O'Reilly.
 *
*/

#include <stdio.h>

#include <Xm/Xm.h>		/* Needed by all Motif programs. */

#include <Xm/CascadeB.h>
#include <Xm/Form.h>
#include <Xm/MainW.h>
#include <Xm/PanedW.h>
#include <Xm/PushB.h>
#include <Xm/RowColumn.h>
#include <Xm/Text.h>
#include <Xm/MessageB.h>


/*
 * Use by Menu Callbacks and PushButton Callbacks.  */
#define MENU_HELP		200
#define MENU_EXIT		201
#define XTEXT_STRING   "This is a test of the Emergency Widget System.\n\
Had this been a real emergency, you wouldn't be\n\
sitting here reading this."


void
help_done(Widget w)
{
   XtDestroyWidget(w);
}


/*
 * MenuCallBack: Process callback from PushButtons in PulldownMenus.
 */
void
MenuCallBack(Widget w,		/* widget id              */
   XtPointer client_data,	/* data from application   */
   XtPointer call_data)
{
   Widget  help_widget;

   switch ((int) client_data)
     {
     case MENU_EXIT:
	printf("xtext: exiting...\n");
	exit(0);

     case MENU_HELP:
	/* Popup an information convenience widget with some useful help.
	 */
	help_widget = XmCreateInformationDialog(w, "help", NULL, 0);

	/* Don't show cancel or help buttons. */
	XtUnmanageChild(XmMessageBoxGetChild(help_widget, 
					     XmDIALOG_CANCEL_BUTTON));
	XtUnmanageChild(XmMessageBoxGetChild(help_widget, 
					     XmDIALOG_HELP_BUTTON));
	/* Press the ok button when finished reading. */
	XtAddCallback(help_widget, XmNokCallback, help_done, NULL);
	XtManageChild(help_widget);
	break;

     default:		     /* This should never occur; it's good practice to
				check anyway. */
	printf("xtext: unexpected tag in menu callback\n");
	break;
     }
}


/*
 * CreateMenuBar:  Create the MenuBar in MainWindow
 */

Widget
CreateMenuBar(Widget parent)
{
   Widget  menu_bar_widget;	/* RowColumn             */
   Widget  cascade;		/* CascadeButton         */
   Widget  pulldown_widget;	/* RowColumn             */
   Widget  exit_widget;		/* PushButton            */

   /* Use a Motif convenience widget creation routine to create the
      menubar widget, which is a specialized version of a rowcolumn
      widget. */

   menu_bar_widget = XmCreateMenuBar(parent, "menubar", NULL, 0);

   /* Since the convenience widget creation routines don't manage
      their children, we must. */

   XtManageChild(menu_bar_widget);

   /*  Create a pulldown menu, child of the menubar widget. */

   pulldown_widget = XmCreatePulldownMenu(menu_bar_widget, "pulldown", NULL, 0);

   /* Create a button on the pulldown menu, and its callback. */

   exit_widget = XtVaCreateManagedWidget(
		    "Exit", xmPushButtonWidgetClass, pulldown_widget, NULL);
   XtAddCallback(exit_widget, XmNactivateCallback, MenuCallBack,
      (XtPointer) MENU_EXIT);

   /* We've created the menu bar and a pulldown menu.  Now we must
      create a cascade button on the menu bar to invoke the pulldown
      menu.  It's not enough the pulldown_widget is a child of
      menu_bar_widget.  This must be done after the pulldown menu is
      created since a resource of this cascade button must be the
      address of the pulldown menu. */
   
   XtVaCreateManagedWidget("actions", xmCascadeButtonWidgetClass,
      menu_bar_widget, XmNsubMenuId, pulldown_widget,
      NULL);

   /*  Create the "Help" button on the menu bar as a separate popup
       window, not as a pulldown menu. */

   cascade = XtVaCreateManagedWidget("help", xmCascadeButtonWidgetClass,
      menu_bar_widget,
      NULL);

   /* Identify this as the help button for the menu bar.  Therefore it
      will be placed at the right. */

   XtVaSetValues(menu_bar_widget, XmNmenuHelpWidget, cascade, NULL);

   /* Use the same callback routine as exit, but with different client
      data so the routine knows which action to perform. */

   XtAddCallback( cascade, XmNactivateCallback, MenuCallBack,
      (XtPointer) MENU_HELP );

   return (menu_bar_widget);
}



/*
 * PrintButtonCallback.  This demos how to read the text widget contents.
 */

void
PrintButtonCallBack(Widget w, XtPointer client_data, XtPointer call_data)
{
   char   *tstring;		/* text string */
   Widget  textw;		/* text widget */

   textw = (Widget) client_data;

   /* Get the contents of the text widget. XmTextGetString mallocs
     space and returns a pointer to a C char array (string).  */

   tstring = XmTextGetString(textw);

   printf("\n\nText Widget Contains:\n------\n%s\n------\n", tstring);
   XtFree(tstring);		/* Motif allocs, but you free. */
}


/*
 * ClearButtonCallback.  Demos setting the text widget contents.
 */

void
ClearButtonCallBack(Widget w, XtPointer client_data, XtPointer call_data)
{
   Widget  textw;		/* text widget */

   textw = (Widget) client_data;
   XmTextSetString(textw, "");	/* Takes a C string, not a Motif 
				   compound string. */
}

/*
 * main
 */

void
main(int argc, char **argv)
{
   XtAppContext app_context;	/* Application Context */
   Widget  top_level;	/* Application Shell   */
   Widget  main_widget;		/* Main window.        */
   Widget  menubar;		/* Menu bar            */
   Widget  textarea;		/* Text                */
   Widget  buttonbox;		/* Form                */
   Widget  print, clear;	/* pushbuttons.        */

   /* 
    * Initialize toolkit
    */

   top_level = XtVaAppInitialize(
      &app_context,		/* app_context  */
      "XText",			/* Class        */
      NULL,			/* options      */
      0,			/* num_options  */
      &argc,			/* num cmd line */
      argv,			/* cmd line     */
      NULL,			/* fallback     */
      NULL			/* args         */
      );


   /*  We are going to use a Motif MainWindow convenience Widget here.
       This provides a standardized appearance for applications, with
       pulldown menus at the top, space for commands near the bottom,
       etc.  */

   main_widget = XtVaCreateManagedWidget("main", xmMainWindowWidgetClass,
      top_level, NULL);


   /* Create the Motif Menubar, in a separate routine since there are
      so many steps. */

   menubar = CreateMenuBar(main_widget);


   /* Create a text widget as a work area.*/

   textarea = XtVaCreateManagedWidget("text", xmTextWidgetClass, main_widget,
      XmNvalue, XTEXT_STRING,	/* Initial text, as an ascii string.  Cannot  
				   be put in a resources  file. */
      XmNeditMode, XmMULTI_LINE_EDIT,	/* Multiple lines of text are allowed. */
      XmNwordWrap, True,	/* If the user enters a wide line, then insert 
				   a newline between words. */
      XmNwidth, 300, XmNheight, 200,	/* Initial size of widget. */
      NULL);

   /* Create a form widget as a command area to hold the clear and
     print buttons.  Form is yet another composite widget.  With a
     form, you can spec the relation of one child to another.  */

   buttonbox = XtVaCreateManagedWidget("buttonbox", xmFormWidgetClass, 
				       main_widget, NULL);

   print = XmCreatePushButton(buttonbox, "print", NULL, 0);
   XtManageChild(print);
   clear = XmCreatePushButton(buttonbox, "clear", NULL, 0);
   XtManageChild(clear);

   /* Tell the buttons how to position themselves in the form. The
      left side of clear is aligned on the left with (the right side
      of) print. */

   XtVaSetValues(clear, XmNleftAttachment, XmATTACH_WIDGET, XmNleftWidget, 
		 print, NULL);

   /* Add the callbacks */

   XtAddCallback(print, XmNactivateCallback, PrintButtonCallBack,
      (XtPointer) textarea);

   XtAddCallback(clear, XmNactivateCallback, ClearButtonCallBack,
      (XtPointer) textarea);

   /* Tell the MainWindow which kids get used for which functions.
     Again, it's not enough that the widgets are kids of the main
     window.  We must also explicitly list which kid has which
     function here.  We're not using most of the MainWindow options. */

   XmMainWindowSetAreas(main_widget,	/* main window widget */
      menubar,			/* menubar */
      buttonbox,		/* command entry area widget */
      NULL,			/* H scrollbar */
      NULL,			/* V scrollbar */
      textarea			/* work area */
      );


   XtRealizeWidget(top_level);

   XtAppMainLoop(app_context);

}
