#include <Xm/Xm.h>
#include <Xm/PushB.h>
#include <Xm/Text.h>
#include <Xm/Form.h>
#include <Xm/RowColumn.h>


void print_text(w, client_data, event_data)
Widget w;
XtPointer client_data;
XtPointer event_data;
{
Widget text = (Widget)client_data;
char *string;

    string = XmTextGetString(text);
    printf("%s\n", string);
    free(string);
}

void quit_program(w, client_data, event_data)
Widget w;
XtPointer client_data;
XtPointer event_data;
{
    exit(0);
}


main(argc, argv)
int argc;
char *argv[];
{
    Widget toplevel, form, scrollw, button, text, container;
    XtAppContext  app;
    XmString label;
    Arg al[10];
    int ac;


    toplevel = XtVaAppInitialize(&app, "Editor", NULL, 0,
        &argc, argv, NULL, NULL);

    form = XtVaCreateManagedWidget("form", xmFormWidgetClass, toplevel,
        NULL);

    ac = 0;
    XtSetArg(al[ac], XmNeditMode, XmMULTI_LINE_EDIT); ac++; 
    XtSetArg(al[ac], XmNscrollVertical, TRUE); ac++; 

    text = XmCreateScrolledText(form, "Text", al, ac);
    XtManageChild(text);

    scrollw = XtParent(text);

    container = XtVaCreateManagedWidget("box", xmRowColumnWidgetClass, form,
        XmNrightAttachment, XmATTACH_FORM, 
        XmNleftAttachment, XmATTACH_FORM, 
        XmNbottomAttachment, XmATTACH_FORM, 
        XmNorientation, XmHORIZONTAL,
        NULL);

    XtVaSetValues(scrollw,
        XmNrightAttachment, XmATTACH_FORM, 
        XmNleftAttachment, XmATTACH_FORM, 
        XmNtopAttachment, XmATTACH_FORM, 
        XmNbottomAttachment, XmATTACH_WIDGET, 
        XmNbottomWidget, container,
        NULL);

    label = XmStringCreateSimple("Print"); 
    button = XtVaCreateManagedWidget("pushme", xmPushButtonWidgetClass, container,
        XmNlabelString, label,
        NULL);
    XmStringFree(label);
    XtAddCallback(button, XmNactivateCallback, print_text, (XtPointer)text);

    label = XmStringCreateSimple("Quit"); 
    button = XtVaCreateManagedWidget("pushme", xmPushButtonWidgetClass, container,
        XmNlabelString, label,
        NULL);
    XmStringFree(label);
    XtAddCallback(button, XmNactivateCallback, quit_program, NULL);


    XtRealizeWidget(toplevel);
    XtAppMainLoop(app);
}
