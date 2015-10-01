#include <Xm/Xm.h>
#include <Xm/DrawingA.h>

XtAppContext context;

typedef struct DialogInfo_struct {
        GC gc;
        Widget toplevel;
        Widget drawing_area;
} DialogInfo, *pDialogInfo;

void setup_gc(pInfo)
        pDialogInfo pInfo;
/* set up the graphics context. */
{
        int foreground,background;
        XGCValues vals;
        Arg al[10];
        int ac;

        /* get the current fg and bg colors. */
        ac=0;
        XtSetArg(al[ac],XmNforeground,&foreground); ac++;
        XtSetArg(al[ac],XmNbackground,&background); ac++;
        XtGetValues(pInfo->drawing_area,al,ac);

        /* create the gc. */
        vals.foreground = foreground;
        vals.background = background;
        pInfo->gc=XtGetGC(pInfo->drawing_area,GCForeground | GCBackground,&vals)
;
}

void exposureCB(w,client_data,call_data)
        Widget w;
        XtPointer client_data;
        XtPointer call_data;
/* called whenever drawing area is exposed. */
{
        pDialogInfo pInfo = (pDialogInfo)client_data;
        XmDrawingAreaCallbackStruct *pCallData = (XmDrawingAreaCallbackStruct *)call_data;

        printf("callback event generated for reason: %d\n", pCallData->reason);

        printf("callback event generated for reason: %d\n", pCallData->reason);
        switch (pCallData->reason) {
        case 38: // Expose event
                XDrawLine(XtDisplay(pInfo->drawing_area),XtWindow(pInfo->drawing_area),
                                        pInfo->gc, 0, 0, 300, 300);
                break;
        case 40: // Input event
                break;
        }
}

int main(argc,argv)
int argc;
char *argv[];
{
        Arg al[10];
        int ac;
	DialogInfo di;

        /* create the toplevel shell */
        di.toplevel = XtAppInitialize(&context,"",NULL,0,&argc,argv,
                                                                NULL,NULL,0);

        /* set window size. */
        ac=0;
        XtSetArg(al[ac],XmNheight,300); ac++;
        XtSetArg(al[ac],XmNwidth,300); ac++;
        XtSetValues(di.toplevel,al,ac);

        /* create a drawing area widget. */
        ac=0;
        di.drawing_area=XmCreateDrawingArea(di.toplevel,"drawing_area",al,ac);
        XtManageChild(di.drawing_area);
        XtAddCallback(di.drawing_area,XmNexposeCallback,exposureCB,(XtPointer)&di);
        XtAddCallback(di.drawing_area,XmNinputCallback,exposureCB,(XtPointer)&di);
        XtAddCallback(di.drawing_area,XmNresizeCallback,exposureCB,(XtPointer)&di);
        XtAddCallback(di.drawing_area,XmNdestroyCallback,exposureCB,(XtPointer)&di);          

        setup_gc(&di);

        XtRealizeWidget(di.toplevel);
        XtAppMainLoop(context);

        return 0;
}
            