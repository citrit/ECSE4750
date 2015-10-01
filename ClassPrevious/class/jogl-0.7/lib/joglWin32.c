/*
 * Original Author: Leo Chan -- 1995
 *
 * Adam King 1997
 *		Ported to Win32 from X
 *
 * This file takes care of the C implementation of finding the correct
 * Win32 window, assigning an OpenGL graphics context to it, storing that
 * graphics context in the java class data structure.
 *
 * also contains the use() and swap() functions for double buffering
 *
 * September 12, 1997	- Adam King
 *		- Added support for rendering directly into a Canvas ( BIG rewrite )
 */
#include <stdio.h>
#include "windows.h"
#include "jogl_lib.h"
#include "jni.h"

/*--------------------------------------------------------------------------
 * here on in is just regular apple pie C
 */

#include <GL\gl.h>
#include <gl\glu.h>
#include <wingdi.h>

#include "joglWin32.h"
#define HDC_INDEX 3

// Color Palette handle
HPALETTE hPalette = NULL;
// Set Pixel Format function - forward declaration
void SetDCPixelFormat(HDC hDC);
HPALETTE GetOpenGLPalette(HDC hDC);
HGLRC tempRC;
static HGLRC get_GC( int window, HDC hDC );
int setpData( int pdata, JNIEnv* env, jobject this );


JC_BOOL_LIB_FUNC(use)
ARGS()
{
	//int pdata;
	HDC hDC;
	HGLRC gc;
	THIS_CLASS();

	//printf( "DOING USE\n" );
	gc = (HGLRC)GET_INT_FIELD("renderContext");
	//pdata = (int) GET_INT_FIELD("pData");
	if( gc == 0 )
	{
		printf( "gc = 0\n" );
		return 0;
	}

	hDC = (HDC)GET_INT_FIELD("display");
	//printf( "USE - hDC: %d\n", hDC);

	if( !hDC )
	{
		printf( "use - hdc 0" );
		return 0;
	}

	if( wglGetCurrentContext() == NULL )
	{
		printf( "ERROR WITH CONTEXT" );

		wglMakeCurrent( NULL, NULL );
		wglDeleteContext( (HGLRC)GET_INT_FIELD("renderContext") );

		SetDCPixelFormat(hDC);          
		// Create palette if needed
		GetOpenGLPalette(hDC);
		gc = wglCreateContext( hDC );
		wglMakeCurrent( hDC, gc );
		SET_INT_FIELD("renderContext", (long)gc );

		return 0;
	}
	
	if( !gc )
		return 0;
	
	if( !wglMakeCurrent( hDC, gc ) ) 
	{
		wglMakeCurrent( NULL, NULL );
		printf( "Error in wglMakeCurrent: %d", GetLastError() );
		return 0;

	}
	return 1;
}


JC_BOOL_LIB_FUNC(initGL)
ARGS()
{
	int window;
	HDC hDC;
	HGLRC gc;
	THIS_CLASS();

	//if (GET_INT_FIELD("renderContext"))
	//	return 1;

	//pdata = (int)GET_INT_FIELD("pData");
	window = GET_INT_FIELD("window");
	//printf("Got window id: %d\n", window);
	hDC = GetDC((HWND)window);
	if( (gc = get_GC( window, hDC )) == 0 )	
	{
		printf( "getGC error" );
		return FALSE;
	}
	//printf( "got gc: %ld\n", gc );

	SET_INT_FIELD("display", (long)hDC );

	//printf( "setint field display: %ld\n", hDC );

	SET_INT_FIELD("renderContext", (long)gc );

	//printf( "setint field renderContext: %ld\n", gc );

//	setpData( pdata, env, this );

	return 1;
}

JC_VOID_LIB_FUNC(swap)
ARGS()
{
	HDC hDC;
	THIS_CLASS();

//	printf( "DOING SWAP\n" );
	hDC = (HDC)GET_INT_FIELD("display");

	if( GET_BOOL_FIELD("doubleBuffer") )
	{
		if( SwapBuffers( hDC ) == FALSE )
			printf( "Error in swap buffer! hDC: %ld\n", hDC );
	} 
	else 
	{
		/* don't double buffer */
		printf( "No doublebuffer!\n" );
	}
	
}


#if 0
int setpData( int pdata, JNIEnv* env, jobject this )
{
	HDC hDC;
	int *data;
	HGLRC gc;
	THIS_CLASS();

	//printf( "IN setpData\n" );
	/* get the graphics context for this widget */
	if( (gc = get_GC( pdata )) == 0 )	
	{
		printf( "getGC error" );
		return FALSE;
	}

//	printf( "gc : %ld\n", gc );
	SET_INT_FIELD("renderContext", (long)gc );

	data = (int *)pdata;
	//hDC = (HDC)data[HDC_INDEX];
	hDC = GetDC((HWND)pdata);
	//printf( "setpData - hDC: %d, use pdata : %d\n", hDC, pdata );

	SET_INT_FIELD("display", (long)hDC );
}
#endif

static HGLRC get_GC( int window, HDC hDC )
{

	//printf( "window: %d, hDC : %d\n", window, hDC );

	// Select the pixel format
	SetDCPixelFormat(hDC);          

	// Create palette if needed
	hPalette = GetOpenGLPalette(hDC);

    tempRC = wglCreateContext( hDC );

    /* check if the context could be created */
    if( tempRC == NULL ) {
        return( 0 );
    }

    /* associated the context with the X window */
    if( wglMakeCurrent( hDC, tempRC ) == FALSE) {
     wglDeleteContext( tempRC );
        return( 0 );
    }

	//printf( "HGLRC created: %ld\n", tempRC );
    return tempRC;
}

// Select the pixel format for a given device context
void SetDCPixelFormat(HDC hDC)
	{
	int nPixelFormat;

	static PIXELFORMATDESCRIPTOR pfd = {
		sizeof(PIXELFORMATDESCRIPTOR),  // Size of this structure
		1,                                  // Version of this structure    
		PFD_DRAW_TO_WINDOW |                    // Draw to Window (not to bitmap)
		PFD_SUPPORT_OPENGL |					// Support OpenGL calls in window
		PFD_DOUBLEBUFFER,                       // Double buffered
		PFD_TYPE_RGBA,                          // RGBA Color mode
		24,                                     // Want 24bit color 
		0,0,0,0,0,0,                            // Not used to select mode
		0,0,                                    // Not used to select mode
		0,0,0,0,0,                              // Not used to select mode
		32,                                     // Size of depth buffer
		0,                                      // Not used to select mode
		0,                                      // Not used to select mode
		PFD_MAIN_PLANE,                         // Draw in main plane
		0,                                      // Not used to select mode
		0,0,0 };                                // Not used to select mode

	// Choose a pixel format that best matches that described in pfd
	nPixelFormat = ChoosePixelFormat(hDC, &pfd);
	if( nPixelFormat == 0 )
		printf( "Error with PixelFOrmat\n" );

	// Set the pixel format for the device context
	if( SetPixelFormat(hDC, nPixelFormat, &pfd) == FALSE)
		printf( "setpixel failed\n" );
	}


// If necessary, creates a 3-3-2 palette for the device context listed.
HPALETTE GetOpenGLPalette(HDC hDC)
	{
	HPALETTE hRetPal = NULL;	// Handle to palette to be created
	PIXELFORMATDESCRIPTOR pfd;	// Pixel Format Descriptor
	LOGPALETTE *pPal;			// Pointer to memory for logical palette
	int nPixelFormat;			// Pixel format index
	int nColors;				// Number of entries in palette
	int i;						// Counting variable
	BYTE RedRange,GreenRange,BlueRange;
								// Range for each color entry (7,7,and 3)


	// Get the pixel format index and retrieve the pixel format description
	nPixelFormat = GetPixelFormat(hDC);
	DescribePixelFormat(hDC, nPixelFormat, sizeof(PIXELFORMATDESCRIPTOR), &pfd);

	// Does this pixel format require a palette?  If not, do not create a
	// palette and just return NULL
	if(!(pfd.dwFlags & PFD_NEED_PALETTE))
		return NULL;

	// Number of entries in palette.  8 bits yeilds 256 entries
	nColors = 1 << pfd.cColorBits;	

	// Allocate space for a logical palette structure plus all the palette entries
	pPal = (LOGPALETTE*)malloc(sizeof(LOGPALETTE) + nColors*sizeof(PALETTEENTRY));

	// Fill in palette header 
	pPal->palVersion = 0x300;		// Windows 3.0
	pPal->palNumEntries = nColors; // table size

	// Build mask of all 1's.  This creates a number represented by having
	// the low order x bits set, where x = pfd.cRedBits, pfd.cGreenBits, and
	// pfd.cBlueBits.  
	RedRange = (1 << pfd.cRedBits) -1;
	GreenRange = (1 << pfd.cGreenBits) - 1;
	BlueRange = (1 << pfd.cBlueBits) -1;

	// Loop through all the palette entries
	for(i = 0; i < nColors; i++)
		{
		// Fill in the 8-bit equivalents for each component
		pPal->palPalEntry[i].peRed = (i >> pfd.cRedShift) & RedRange;
		pPal->palPalEntry[i].peRed = (unsigned char)(
			(double) pPal->palPalEntry[i].peRed * 255.0 / RedRange);

		pPal->palPalEntry[i].peGreen = (i >> pfd.cGreenShift) & GreenRange;
		pPal->palPalEntry[i].peGreen = (unsigned char)(
			(double)pPal->palPalEntry[i].peGreen * 255.0 / GreenRange);

		pPal->palPalEntry[i].peBlue = (i >> pfd.cBlueShift) & BlueRange;
		pPal->palPalEntry[i].peBlue = (unsigned char)(
			(double)pPal->palPalEntry[i].peBlue * 255.0 / BlueRange);

		pPal->palPalEntry[i].peFlags = (unsigned char) NULL;
		}
		

	// Create the palette
	hRetPal = CreatePalette(pPal);

	// Go ahead and select and realize the palette for this device context
	SelectPalette(hDC,hRetPal,FALSE);
	RealizePalette(hDC);

	// Free the memory used for the logical palette structure
	free(pPal);

	// Return the handle to the new palette
	return hRetPal;
	}


JC_VOID_LIB_FUNC(canvasResized)
ARGS2(jint width, jint height)
{  
}
