/*
 * Leo Chan -- 1995
 *
 * Header file for OpenGL_open.c
 */

#ifndef _OGL_OPEN_H		/* {{ */
#define _OGL_OPEN_H

#include "windows.h"

#ifndef TRUE
#define TRUE 1
#endif
#ifndef FALSE
#define FALSE 0
#endif

#define MAX_WIN_NAME_SZ 80

LRESULT CALLBACK WndProc(       HWND    hWnd,
							UINT    message,
							WPARAM  wParam,
							LPARAM  lParam);

/*
 * this hides all the nitty gritty X windows information from the JAVA
 * widget class and keeps it tucked in an internal table in the C 
 * function.
 */
struct _widget_table_entry {
	HWND			widgetHandle;/* unique identifier assigned by XCreate.. */
//	Display 		*display;	/* pointer to the display */
//	int				screen;		/* screen number */
	HGLRC		gc;			/* graphics context */
};
typedef struct _widget_table_entry widgetTableEntry;
struct _widget_table {
	int size;
	widgetTableEntry *list_head;
};
typedef struct _widget_table widgetTable;

typedef int Boolean;

#endif /* _OGL_OPEN_H }} */
