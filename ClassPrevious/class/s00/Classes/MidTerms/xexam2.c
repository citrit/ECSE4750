/** xexam.c   Midterm problem
Time-stamp: </mab_home1/wrf/cg/xexam2.c, Tue, 23 Feb 1999, 19:46:33 EST, wrf@mab.ecse.rpi.edu>
*/

#include <stdio.h>
#include <Xm/Xm.h>		/* Needed by all Motif programs. */
#include <Xm/Label.h>
#include <Xm/PushB.h>
#include <Xm/RowColumn.h>


char   *greek[4] = {"plato","socrates","alex","philip"};

Widget  widgets[4],  quit, topLevel;


void
Proc(Widget w, caddr_t client_data, caddr_t call_data)
{
    Position x, y;
    int     i;

    i=(int) client_data;
    printf("Client data= %d\n", i);

    XtVaGetValues(topLevel, XmNx, &x, XmNy, &y, NULL);

    switch (i) {
    case 0:
	x -= 100;
    case 1:
	x += 100;
	break;
    case 2:
	y -= 100;
    case 3:
	y += 100;
	break;
    };
    XtVaSetValues(topLevel, XmNx, x, XmNy, y, NULL);
}


/*
 * quit button callback function
 */
void
Quit(w, client_data, call_data)
    Widget  w;
    caddr_t client_data, call_data;
{

}


main(int argc, char **argv)
{
    XtAppContext app_context;
    Widget  box;
    int     i;

    topLevel = XtVaAppInitialize(&app_context, "XExam",
					 NULL,	/* options      */
					 0,	/* num_options  */
					 &argc,	/* num cmd line */
					 argv,	/* cmd line     */
					 NULL,	/* fallback     */
					 NULL	/* args         */
	);

    box = XtVaCreateManagedWidget("box",	/* name     */
				 xmRowColumnWidgetClass,	/* class    */
				 topLevel,	/* parent   */
				 NULL);	/* args     */

    for (i = 0; i < 4; i++) {
	widgets[i] = XtVaCreateManagedWidget(greek[i], 
					     xmPushButtonWidgetClass, 
					     box, NULL); 
      }

    quit = XtVaCreateManagedWidget(
				 "quit",	/* 12 */
				 xmPushButtonWidgetClass,	/* 13 */
				 box,	/* 14 */
				 NULL);	/* 15 */

    XtAddCallback(quit, XmNactivateCallback, Quit, 0);

    for (i = 0; i < 4; i++)
	XtAddCallback(widgets[i], XmNactivateCallback, (XtPointer) Proc, (XtPointer) i);

    XtRealizeWidget(topLevel);

    XtAppMainLoop(app_context);
}
