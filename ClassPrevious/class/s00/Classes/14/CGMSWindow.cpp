/***************************************************************************
													cgwindow.cpp	-  description															
														 -------------------																				 
		begin 							 : Fri Feb 11 2000																					 
		copyright 					 : (C) 2000 by TC 												
		email 							 : citrit@rpi.edu 																		
 ***************************************************************************/

/***************************************************************************
 *																																				 *
 *	 This program is free software; you can redistribute it and/or modify  *
 *	 it under the terms of the GNU General Public License as published by  *
 *	 the Free Software Foundation; either version 2 of the License, or		 *
 *	 (at your option) any later version.																	 * 
 *																																				 *
 ***************************************************************************/


#include "cgmswindow.h"

//
//	FUNCTION: WndProc(HWND, unsigned, WORD, LONG)
//
//	PURPOSE:	Processes messages for the main window.
//
//	WM_COMMAND	- process the application menu
//	WM_PAINT	- Paint the main window
//	WM_DESTROY	- post a quit message and return
//
//
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	int wmId, wmEvent;
	PAINTSTRUCT ps;
	HDC hdc;

	CGWindow *me = (CGWindow *)GetWindowLong(hWnd,GWL_USERDATA);

	switch (message) 
	{
		case WM_COMMAND:
			wmId		= LOWORD(wParam); 
			wmEvent = HIWORD(wParam); 
			// Parse the menu selections:
			switch (wmId)
			{
				/*case IDM_ABOUT:
					 DialogBox(hInst, (LPCTSTR)IDD_ABOUTBOX, hWnd, (DLGPROC)About);
					 break;
				case IDM_EXIT:
					 DestroyWindow(hWnd);
					 break;*/
				default:
					 return DefWindowProc(hWnd, message, wParam, lParam);
			}
			break;
		case WM_PAINT:
			hdc = BeginPaint(hWnd, &ps);
			// TODO: Add any drawing code here...
			if (me) {
				me->Draw();
			}
			EndPaint(hWnd, &ps);
			break;
		case WM_DESTROY:
			PostQuitMessage(0);
			break;
		default:
			return DefWindowProc(hWnd, message, wParam, lParam);
	 }
	 return 0;
}


/** Constructor Destructor */
CGWindow::CGWindow()
: m_hWindow(0),
	m_hInstance(0),
	m_KeyCallback(NULL),
	m_DrawingCallback(NULL),
	m_KeyArg(NULL),
	m_DrawArg(NULL)
{
	
	strcpy(m_szTitle, "CGWindow Library");
	strcpy(m_szWindowClass, "CGWindow");
	// Create a black pen.
	m_hPen = CreatePen(PS_SOLID, 1, RGB(0, 0, 0));
	// Create a red brush.
	m_hBrush = CreateSolidBrush(RGB(255, 0, 0));

}
CGWindow::~CGWindow()
{

	// Do not forget to clean up.
	DeleteObject(m_hPen);
	DeleteObject(m_hBrush);

}

/**
	* Initialize the window system
	*/
void CGWindow::Initialize(int argc, char *argv[])
{

	m_hInstance = (HINSTANCE) argv;
	// Initialize global strings
	//LoadString(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
	//LoadString(hInstance, IDC_CGWINDOW, szWindowClass, MAX_LOADSTRING);
	MyRegisterClass();

	// Perform application initialization:
	if (!InitInstance (argc)) 
	{
		return;
	}
}
	
/** Main loop to capture events */
int CGWindow::MainLoop()
{
	MSG msg;

	// Main message loop:
	while (GetMessage(&msg, NULL, 0, 0)) 
	{
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}

	return msg.wParam;
}

//
//	FUNCTION: MyRegisterClass()
//
//	PURPOSE: Registers the window class.
//
//	COMMENTS:
//
//		This function and its usage is only necessary if you want this code
//		to be compatible with Win32 systems prior to the 'RegisterClassEx'
//		function that was added to Windows 95. It is important to call this function
//		so that the application will get 'well formed' small icons associated
//		with it.
//
ATOM CGWindow::MyRegisterClass()
{
	WNDCLASSEX wcex;

	wcex.cbSize = sizeof(WNDCLASSEX); 

	wcex.style					= CS_HREDRAW | CS_VREDRAW;
	wcex.lpfnWndProc		= (WNDPROC)WndProc;
	wcex.cbClsExtra 		= 0;
	wcex.cbWndExtra 		= 0;
	wcex.hInstance			= m_hInstance;
	wcex.hIcon					= LoadIcon(m_hInstance, (LPCTSTR)IDI_WINLOGO);
	wcex.hCursor				= LoadCursor(NULL, IDC_ARROW);
	wcex.hbrBackground	= (HBRUSH)(COLOR_WINDOW+1);
	wcex.lpszMenuName 	= NULL;
	wcex.lpszClassName	= m_szWindowClass;
	wcex.hIconSm				= NULL;

	return RegisterClassEx(&wcex);
}

//
//	 FUNCTION: InitInstance(HANDLE, int)
//
//	 PURPOSE: Saves instance handle and creates main window
//
//	 COMMENTS:
//
//				In this function, we save the instance handle in a global variable and
//				create and display the main program window.
//
BOOL CGWindow::InitInstance(int nCmdShow)
{
	 m_hWindow = CreateWindow(m_szWindowClass, m_szTitle, WS_OVERLAPPEDWINDOW,
			CW_USEDEFAULT, 0, CW_USEDEFAULT, 0, NULL, NULL, m_hInstance, NULL);

	 if (!m_hWindow)
	 {
			return FALSE;
	 }

	 ShowWindow(m_hWindow, nCmdShow);
	 UpdateWindow(m_hWindow);
	 SetWindowLong(this->m_hWindow,GWL_USERDATA,(LONG)this); 

	 return TRUE;
}

/** Set the window title */
void CGWindow::SetTitle(char *title)
{
	SetWindowText(m_hWindow, title);
}

/** Method to redraw the window */
void CGWindow::Draw()
{
	static unsigned int isCnt(0);
	try {
		if (!m_hWindow) throw 1;
		cout << "Time to draw at time: " << isCnt++ << endl;

		if (m_DrawingCallback) {
			(m_DrawingCallback)(this, m_DrawArg);
		}

	} catch (int iErr) {
		switch (iErr) {
			case 1:
				cerr << "Expose prior to widget initialization" << endl;
				break;
			default:
				break;
		}
	}
}

/** Base function used as an interface to drawing algorithms */
void CGWindow::DrawPoint(int x, int y)
{
	HDC hdc;

	hdc = GetDC(m_hWindow); 
	this->SetInternalPen(hdc, true);
	MoveToEx(hdc, x, y, NULL); 
	LineTo(hdc, x+1, y); 
	this->SetInternalPen(hdc, false);
	ReleaseDC(m_hWindow, hdc); 

}

/** Draw a line from (x1,y1) to (x2,y2) */
void CGWindow::DrawLine(int x1, int y1, int x2, int y2)
{
	HDC hdc;

	hdc = GetDC(m_hWindow); 
	this->SetInternalPen(hdc, true);
	MoveToEx(hdc, x1, y1, NULL); 
	LineTo(hdc, x2, y2); 
	this->SetInternalPen(hdc, false);
	ReleaseDC(m_hWindow, hdc); 

}

/** Set the window size */
void CGWindow::SetSize(int w, int h)
{
	SetWindowPos(m_hWindow, HWND_TOP, 0, 0, w, h, SWP_NOMOVE | SWP_SHOWWINDOW);
}


/** Set the current color of the drawing system */
void CGWindow::SetColor(short r, short g, short b)
{

	// Delete the old Pen.
	DeleteObject(m_hPen);
	// Create a pen with the specified colors.
	m_hPen = CreatePen(PS_SOLID, 1, RGB(r, g, b));


	this->Modified();

}


// Convenience functions.
void CGWindow::SetInternalPen(HDC hdc, bool bSetToInternal)
{
	static HPEN 	hpenOld;
	static HBRUSH hbrushOld;

	if (bSetToInternal) {
		// Select the new pen and brush, and then draw.
		hpenOld = (HPEN)SelectObject(hdc, m_hPen);
		hbrushOld = (HBRUSH)SelectObject(hdc, m_hBrush);
	}
	else {
		// Do not forget to clean up.
		SelectObject(hdc, hpenOld);
		SelectObject(hdc, hbrushOld);
	}
}

