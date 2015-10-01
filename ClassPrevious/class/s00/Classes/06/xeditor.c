#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>

#include <Xm/Xm.h>
#include <Xm/Text.h>
#include <Xm/Form.h>
#include <Xm/PushB.h>
#include <Xm/RowColumn.h>
#include <Xm/CascadeB.h>
#include <Xm/FileSB.h>
#include <Xm/MessageB.h>

/* integer values used to distinguish the call to menuCB. */
#define MENU_OPEN     1
#define MENU_CLOSE    2
#define MENU_QUIT     3

#define MENU_CUT      4
#define MENU_CLEAR    5
#define MENU_COPY     6
#define MENU_PASTE    7

/* integer values used to distinguish the call to dialogCBs. */
#define OK            1
#define CANCEL        2

XtAppContext context;
XmStringCharSet char_set=XmSTRING_DEFAULT_CHARSET;

/* all widgets are global to make life easier. */
Widget toplevel, text, form, label, menu_bar;
Widget open_option, close_option, quit_option;
Widget cut_option, clear_option, copy_option, paste_option;
Widget open_dialog, save_dialog;

char *filename=NULL;
Boolean text_changed=False;

void change_sensitivity(open_state)
Boolean open_state;
/* changes the menu sensitivities as needed for opened
and closed states. */
{ 
	XtSetSensitive(open_option,open_state);
	XtSetSensitive(quit_option,open_state);
	XtSetSensitive(close_option,!open_state);
	XtSetSensitive(cut_option,!open_state);
	XtSetSensitive(copy_option,!open_state);
	XtSetSensitive(paste_option,!open_state);
	XtSetSensitive(clear_option,!open_state);
}

void changedCB(w,client_data,call_data)
Widget w;
XtPointer client_data;
XmAnyCallbackStruct *call_data;
/* triggered every time a character is inserted or deleted in
the text widget. text_changed is used to decide if file needs
saving or not. */
{
	text_changed=True;
}

void openCB(w,client_data,call_data)
Widget w;
int client_data;
XmAnyCallbackStruct *call_data;
/* handles the file selection box callbacks. */
{
	XmFileSelectionBoxCallbackStruct *s =
	(XmFileSelectionBoxCallbackStruct *) call_data;
	FILE *f;
	char *file_contents;
	int file_length;
	struct stat stat_val;

	if (client_data==CANCEL) /* do nothing if cancel is selected. */
	{
		XtUnmanageChild(open_dialog);
		return;
	}

	if (filename != NULL) /* free up filename if it exists. */
	{
		XtFree(filename);
		filename = NULL;
	}

	/* get the filename from the file selection box */
	XmStringGetLtoR(s-> value, char_set, &filename);

	/* open and read the file. */
	if (stat(filename, &stat_val) == 0)
	{
		file_length = stat_val.st_size;
		if ((f=fopen(filename,"r"))!=NULL)
		{
			/* malloc a place for the string to be read to. */
			file_contents = (char *) XtMalloc((unsigned)file_length);
			*file_contents = '\0';

			/* read the file string */
			fread(file_contents, sizeof(char), file_length, f);
			fclose(f);
			file_contents[file_length]='\0';

			/* give the string to the text widget. */
			XmTextSetString(text, file_contents);
			XtFree(file_contents);

			/* set up all resources as needed to make menus and
			text widget sensitive. */
			change_sensitivity(False);
			XtSetSensitive(text,True);
			XmTextSetEditable(text,True);
			XmTextSetCursorPosition(text,0);
			text_changed=False;
		}
	}
	XtUnmanageChild(open_dialog);
}

void handle_save()
/* saves the edit widget's string to a file. */
{
	FILE *f;
	char *s=NULL; 

	if ((f=fopen(filename,"w"))!=NULL)
	{
		/* get the string from the text widget */
		s = (char *)XmTextGetString(text);

		if (s!=NULL)
		{
			/* write the file. */
			fwrite(s, sizeof(char), strlen(s), f);

			/* make sure the last line is terminated by '\\n'
			so that vi, compilers, etc. like it. */
			if (s[strlen(s)-1]!='\n')
			fprintf(f,"\n");
			XtFree(s);
		}
		fflush(f);
		fclose(f);
	}
}

void save_dialogCB(w,client_data,call_data)
Widget w;
int client_data;
XmAnyCallbackStruct *call_data;
/* handles save_dialog buttons. */
{
	switch (client_data)
	{
		case OK:
		handle_save();
		break;
		case CANCEL:
		break;
	}
	/* get rid of the text in the text widget and set it so it
	can't be used. */
	XtSetSensitive(text,False);
	XmTextSetEditable(text,False);
	XmTextSetString(text,"");

	/* change menu sensitivites and make the dialog invisible. */
	change_sensitivity(True);
	XtUnmanageChild(save_dialog);
}

void menuCB(w,client_data,call_data)
Widget w;
int client_data;
XmAnyCallbackStruct *call_data;
/* handles menu options. */
{
	Time time;

	switch (client_data)
	{
		case MENU_OPEN:
			/* make the file selection box appear. */
			XtManageChild(open_dialog);
			break;
		case MENU_CLOSE:
			/* if the text was changed, ask the user about saving it.
			If not, lose the text and set the widget insensitve. */
			if (text_changed)
				XtManageChild(save_dialog);
			else
			{
				XtSetSensitive(text,False);
				XmTextSetEditable(text,False);
				XmTextSetString(text,"");
				change_sensitivity(True);
			}
		break;
		case MENU_QUIT:
			exit(0);
		case MENU_CUT:
			time=call_data-> event-> xbutton.time;
			XmTextCut(text,time);
			break;
		case MENU_CLEAR:
			XmTextRemove(text);
			break;
		case MENU_PASTE:
			XmTextPaste(text);
			break;
		case MENU_COPY:
			time=call_data-> event-> xbutton.time;
			XmTextCopy(text,time);
			break;
	}
}

Widget make_menu_option(option_name,client_data,menu)
char *option_name;
int client_data;
Widget menu;
{
	int ac;
	Arg al[10];
	Widget b;

	ac = 0;
	XtSetArg(al[ac], XmNlabelString,
	XmStringCreateLtoR(option_name,char_set)); ac++;
	b=XmCreatePushButton(menu,option_name,al,ac);
	XtManageChild(b);

	XtAddCallback (b, XmNactivateCallback, menuCB, client_data);
	return(b);
}

Widget make_menu(menu_name,menu_bar)
char *menu_name;
Widget menu_bar;
{
	int ac;
	Arg al[10];
	Widget menu, cascade;

	ac = 0;
	menu = XmCreatePulldownMenu (menu_bar, menu_name, al, ac);

	ac = 0;
	XtSetArg (al[ac], XmNsubMenuId, menu);  ac++;
	XtSetArg(al[ac], XmNlabelString,
	XtSetArg (al[ac], XmNsubMenuId, menu);  ac++;
	XtSetArg(al[ac], XmNlabelString,
	XmStringCreateLtoR(menu_name, char_set)); ac++;
	cascade = XmCreateCascadeButton (menu_bar, menu_name, al, ac);
	XtManageChild (cascade);

	return(menu);
}

void create_menus(menu_bar)
Widget menu_bar;
{
	int ac;
	Arg al[10];
	Widget menu;

	menu=make_menu("File",menu_bar);
	open_option = make_menu_option("Open",MENU_OPEN,menu);
	close_option = make_menu_option("Close",MENU_CLOSE,menu);
	XtSetSensitive(close_option,False);
	quit_option = make_menu_option("Quit",MENU_QUIT,menu);

	menu=make_menu("Edit",menu_bar);
	cut_option = make_menu_option("Cut",MENU_CUT,menu);
	copy_option = make_menu_option("Copy",MENU_COPY,menu);
	paste_option = make_menu_option("Paste",MENU_PASTE,menu);
	clear_option = make_menu_option("Clear",MENU_CLEAR,menu);
}

void main(argc,argv)
int argc;
char *argv[];
{
	Arg al[10];
	int ac;

	/* create the toplevel shell */
	toplevel = XtAppInitialize(&context,"",NULL,0,
	&argc,argv,NULL,NULL,0);

	/* default window size. */
	ac=0;
	XtSetArg(al[ac],XmNheight,200); ac++;
	XtSetArg(al[ac],XmNwidth,200); ac++; 
	XtSetValues(toplevel,al,ac);

	/* create a form widget. */
	ac=0;
	form=XmCreateForm(toplevel,"form",al,ac);
	XtManageChild(form);

	/* create a menu bar and attach it to the form. */
	ac=0;
	XtSetArg(al[ac], XmNtopAttachment,    XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftAttachment,   XmATTACH_FORM); ac++;
	menu_bar=XmCreateMenuBar(form,"menu_bar",al,ac);
	XtManageChild(menu_bar);

	/* create a text widget and attach it to the form. */
	ac=0;
	XtSetArg(al[ac], XmNtopAttachment,    XmATTACH_WIDGET); ac++;
	XtSetArg(al[ac], XmNtopWidget, menu_bar); ac++;
	XtSetArg(al[ac], XmNrightAttachment, XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNleftAttachment,   XmATTACH_FORM); ac++;
	XtSetArg(al[ac], XmNbottomAttachment,   XmATTACH_FORM); ac++;
	XtSetArg(al[ac],XmNeditMode,XmMULTI_LINE_EDIT); ac++;
	text=XmCreateScrolledText(form, "text", al, ac);
	XtAddCallback (text, XmNvalueChangedCallback, changedCB, NULL);
	XtManageChild(text);
	XtSetSensitive(text,False);
	XmTextSetEditable(text,False);

	create_menus(menu_bar);

	/* create the file selection box used by open option. */
	ac=0;
	XtSetArg(al[ac],XmNmustMatch,True); ac++;
	XtSetArg(al[ac],XmNautoUnmanage,False); ac++;
	open_dialog=XmCreateFileSelectionDialog(toplevel,
	"open_dialog",al,ac);
	XtAddCallback (open_dialog, XmNokCallback,openCB, OK);
	XtAddCallback (open_dialog, XmNcancelCallback, openCB, CANCEL);
	XtUnmanageChild(XmSelectionBoxGetChild(open_dialog,
	XmDIALOG_HELP_BUTTON));

	/* create the file saving dialog. */
	ac=0;
	XtSetArg(al[ac], XmNmessageString,
	XmStringCreateLtoR("The text was changed. Save it?",
	char_set));  ac++;
	save_dialog=XmCreateMessageDialog(toplevel,"ok_dialog",al,ac);
	XtAddCallback(save_dialog,XmNokCallback,save_dialogCB,OK);
	XtAddCallback(save_dialog,XmNcancelCallback,save_dialogCB,CANCEL);
	XtUnmanageChild(XmMessageBoxGetChild(save_dialog,
	XmDIALOG_HELP_BUTTON));

	XtRealizeWidget(toplevel);
	XtAppMainLoop(context);
}