//******************************************************************************
//
// Author : Jason Allen
// Email : jra101@home.com
// Website : delphigl.cfxweb.net
// Description: This is a barebones OpenGL window. No forms. The rendering
//              context is all set up and drawing commands need only to be
//              put into the glDraw function.
//
//******************************************************************************

program RendTex;

uses
  Windows,
  Messages,
  glo,
  OpenGL;

const
  WND_TITLE   = 'OpenGL Window';

var
  h_Wnd  : HWND;                     // Global window handle
  h_DC   : HDC;                      // Global device context
  h_RC   : HGLRC;                    // OpenGL rendering context
  h_Width : Integer;
  h_Height : Integer;
  keyBuf : Array[0..255] of Boolean; // Holds keystrokes
  rot : Single;                     // rotation amount of the cube

  texRot : Single;
  texture : GLuint;

  rotating : Boolean = True;
  
  procedure glCopyTexSubImage2D(target : GLenum; level, xoffset, yoffset, x, y : GLint; width, height: GLsizei); stdcall; external opengl32;  
  procedure glCopyTexImage2D(target : GLenum; level : GLint; internalFormat : GLenum; x, y : GLint; width, height: GLsizei; border : GLint); stdcall; external opengl32;
  procedure glGenTextures(n: GLsizei; var textures: GLuint); stdcall; external opengl32;
  procedure glBindTexture(target: GLenum; texture: GLuint); stdcall; external opengl32;
  procedure glDeleteTextures(n: GLsizei; var textures: GLuint); stdcall; external opengl32;

{$R *.RES}

// Name       : glInit
// Purpose    : Sets up the initial OpenGL rendering settings
// Parameters : none
procedure glInit();
begin
  glClearColor(0.0, 0.0, 0.0, 0.0);
  glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
  glEnable(GL_DEPTH_TEST);
  glEnable(GL_CULL_FACE);
end;

procedure DrawTexture();
begin
  glPushMatrix();
    glLoadIdentity();
    glTranslatef(0.0, 0.0, -4.0);
    
    //glRotatef(texRot, 1.0, 0.0, 0.0);
    glRotatef(texRot, 0.0, 1.0, 0.0);
    //glRotatef(texRot, 0.0, 0.0, 1.0); 

    glColor3f(0.0, 1.0, 1.0);
    gloSolidTorus(0.4, 1.0, 30, 30);
    glColor3f(0.0, 0.0, 0.0);
    glLineWidth(2.0);
    gloWireTorus(0.4, 1.0, 30, 30);
  glPopMatrix();
end;

procedure DrawCube();
begin    
  glBegin(GL_QUADS);
    glColor3f(1.0, 1.0, 1.0);
    glTexCoord2f(0.0, 0.0); glVertex3f( 1.0, 1.0,-1.0);
    glTexCoord2f(1.0, 0.0); glVertex3f(-1.0, 1.0,-1.0);
    glTexCoord2f(1.0, 1.0); glVertex3f(-1.0, 1.0, 1.0);
    glTexCoord2f(0.0, 1.0); glVertex3f( 1.0, 1.0, 1.0);

    glTexCoord2f(0.0, 0.0); glVertex3f( 1.0,-1.0, 1.0);
    glTexCoord2f(1.0, 0.0); glVertex3f(-1.0,-1.0, 1.0);
    glTexCoord2f(1.0, 1.0); glVertex3f(-1.0,-1.0,-1.0);
    glTexCoord2f(0.0, 1.0); glVertex3f( 1.0,-1.0,-1.0);

    glTexCoord2f(0.0, 0.0); glVertex3f( 1.0, 1.0, 1.0);
    glTexCoord2f(1.0, 0.0); glVertex3f(-1.0, 1.0, 1.0);
    glTexCoord2f(1.0, 1.0); glVertex3f(-1.0,-1.0, 1.0);
    glTexCoord2f(0.0, 1.0); glVertex3f( 1.0,-1.0, 1.0);

    glTexCoord2f(0.0, 0.0); glVertex3f( 1.0,-1.0,-1.0);
    glTexCoord2f(1.0, 0.0); glVertex3f(-1.0,-1.0,-1.0);
    glTexCoord2f(1.0, 1.0); glVertex3f(-1.0, 1.0,-1.0);
    glTexCoord2f(0.0, 1.0); glVertex3f( 1.0, 1.0,-1.0);

    glTexCoord2f(0.0, 0.0); glVertex3f(-1.0, 1.0, 1.0);
    glTexCoord2f(1.0, 0.0); glVertex3f(-1.0, 1.0,-1.0);
    glTexCoord2f(1.0, 1.0); glVertex3f(-1.0,-1.0,-1.0);
    glTexCoord2f(0.0, 1.0); glVertex3f(-1.0,-1.0, 1.0);

    glTexCoord2f(0.0, 0.0); glVertex3f( 1.0, 1.0,-1.0);
    glTexCoord2f(1.0, 0.0); glVertex3f( 1.0, 1.0, 1.0);
    glTexCoord2f(1.0, 1.0); glVertex3f( 1.0,-1.0, 1.0);
    glTexCoord2f(0.0, 1.0); glVertex3f( 1.0,-1.0,-1.0); 
  glEnd();
end;

// Name       : glResizeWnd
// Purpose    : Resizes the OpenGL window when the window size changes
// Parameters :
//   Width - new width for the window
//   Height - new height for the window
procedure glResizeWnd(Width, Height : Integer);
var
  fAspect : GLfloat;
begin
  h_Width := Width;
  h_Height := Height;

  // Make sure that we don't get a divide by zero exception
  if (Height = 0) then
    Height := 1;

  // Set the viewport for the OpenGL window
  glViewport(0, 0, Width, Height);

  // Calculate the aspect ratio of the window
  fAspect := Width/Height;

  // Go to the projection matrix, this gets modified by the perspective
  // calulations
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();

  // Do the perspective calculations
  gluPerspective(45.0, fAspect, 1.0, 400.0);

  // Return to the modelview matrix
  glMatrixMode(GL_MODELVIEW);
end;

procedure UpdateTexture();
var
  curWidth, curHeight : Integer;
begin
  curWidth := h_Width;
  curHeight := h_Height;

  glClearColor(0.0, 0.0, 1.0, 1.0);
  glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT);
  glResizeWnd(256, 256);

  glDisable(GL_TEXTURE_2D);
  
  DrawTexture();

  texRot := texRot + 0.9;

  glEnable(GL_TEXTURE_2D);
  if texture > 0 then begin
    glBindTexture(GL_TEXTURE_2D, texture);
      
    glCopyTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
  end else begin
    glGenTextures(1, texture);
    glBindTexture(GL_TEXTURE_2D, texture);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

    glCopyTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, 0, 0, 256, 256, 0);
  end;

  glResizeWnd(curWidth, curHeight);
  glClearColor(0.0, 0.0, 0.0, 1.0);
  glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT);
end;

// Name       : glDraw
// Purpose    : Performs the OpenGL drawing commands
// Parameters : none
procedure glDraw();
begin
  // Clear the color and depth buffers
  glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT);
  glMatrixMode(GL_MODELVIEW);
  glLoadIdentity();
   
  // Sample drawing code, draw's a rotating cube onscreen

  glTranslatef(0.0, 0.0, -5.0);
  glRotatef(rot, 1.0, 1.0, 0.0);  // Rotates around the x and y axis

  UpdateTexture();

  glEnable(GL_TEXTURE_2D);
  glBindTexture(GL_TEXTURE_2D, texture);
  glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);

  DrawCube();

  if rotating then
    rot := rot + 0.5; // Increment the current cube rotation amount

end;

// Name       : OnClose
// Desription : Quits the program when its told to
function OnClose(hWnd, wParam, lParam : Integer) : Integer;
begin
  PostQuitMessage(0);
  Result := 0;
end;

// Name       : OnKeyDown
// Desription : Saves keyboard state (currently pressed buttons are set to 
//              true in the keyboard buffer)
function OnKeyDown(hWnd, wParam, lParam : Integer) : Integer;
begin
  keyBuf[wParam] := True;
  Result := 0;
end;

// Name       : OnKeyUp
// Desription : Saves keyboard state (released buttons are set back to false)
function OnKeyUp(hWnd, wParam, lParam : Integer) : Integer;
begin
  keyBuf[wParam] := False;
  Result := 0;
end;

// Name       : OnSize
// Desription : Resizes the OpenGL window according to the new width and 
//              height of the window
function OnSize(hWnd, wParam, lParam : Integer) : Integer;
begin
  // Resize the window with the new width and height
  glResizeWnd(LOWORD(lParam),HIWORD(lParam));
  Result := 0;
end;

// Name       : WndProc
// Purpose    : Determines the application’s response to the messages received
// Parameters :
//   hWnd - handle to the window to which the message is directed
//   Msg - identifies the window event message
//   wParam - additional information on the message inside msg
// Returns    : Result of program execution
function WndProc(hWnd, msg, wParam, lParam : Integer) : Integer; stdcall;
begin
  case (msg) of
    WM_CLOSE       : Result := OnClose(hWnd, wParam, lParam);
    WM_KEYDOWN     : Result := OnKeyDown(hWnd, wParam, lParam);
    WM_KEYUP       : Result := OnKeyUp(hWnd, wParam, lParam);
    WM_SIZE        : Result := OnSize(hWnd, wParam, lParam);
    else             Result := DefWindowProc(hWnd, Msg, wParam, lParam);
  end;
end;

// Name       : glKillWnd
// Purpose    : Properly destroys the window creas in fullscreen mode (if so then undo it)
// Parameters :
//  Fullscreen - Whether window is currently fullscreen
procedure glKillWnd(Fullscreen : Boolean);
begin
  // Change back to non fullscreen if required
  if Fullscreen then begin
    ChangeDisplaySettings(devmode(nil^), 0);
    ShowCursor(True);
  end;

  // Makes current rendering context not current, and releases the device
  // context that is used by the rendering context.
  wglMakeCurrent(h_DC, 0);

  // Attempts to delete the rendering context
  wglDeleteContext(h_RC);

  // Attemps to release the device context
  if (h_DC <> 0) then
    ReleaseDC(h_Wnd, h_DC);

  // Attempts to destroy the window
  if (h_Wnd <> 0) then 
    DestroyWindow(h_Wnd);
    
  // Attempts to unregister the window class
  UnRegisterClass('OpenGL', hInstance);
end;

// Name       : glCreateWnd
// Purpose    : Creates the window and attaches a OpenGL rendering context to it
// Parameters :
//   Width - width of the window
//   Height - height of the window
//   Fullscreen - whether to go fullscreen or not
// Returns    : Success or failure to create the window
function glCreateWnd(Width, Height, ColorDepth : Integer; Fullscreen : Boolean) : Boolean;
var
  wndClass : TWndClass;         // Window class
  dwStyle : DWORD;              // Window styles
  dwExStyle : DWORD;            // Extended window styles
  dmScreenSettings : DEVMODE;   // Screen settings (fullscreen, etc...)
  PixelFormat : GLuint;         // Settings for the OpenGL rendering
  pfd : PIXELFORMATDESCRIPTOR;  // Describes the pixel format
  h_Instance : HINST;           // Current instance
  clientRect : TRect;
begin
  h_Instance := GetModuleHandle(nil); //Grab An Instance For Our Window

  // Clear the window class structure
  ZeroMemory(@wndClass, SizeOf(wndClass));

  // Set up the window class
  with wndClass do begin
    style         := CS_HREDRAW or  // Redraws entire window if length changes
                     CS_VREDRAW or  // Redraws entire window if height changes
                     CS_OWNDC;      // Unique device context for the window
    lpfnWndProc   := @WndProc;      // Set the window procedure to our func WndProc
    hInstance     := h_Instance;
    hCursor       := LoadCursor(0, IDC_ARROW);
    lpszClassName := 'OpenGL';
  end;

  // Attemp to register the window class
  if (RegisterClass(wndClass) = 0) then begin
    MessageBox(0, 'Failed to register the window class!', 'Error', MB_OK or MB_ICONERROR);
    Result := False;
    Exit
  end;

  // Change to fullscreen if so desired
  if Fullscreen then begin
    // Clear screen settings structure
    ZeroMemory(@dmScreenSettings, SizeOf(dmScreenSettings));

    // Set parameters for the screen setting
    with dmScreenSettings do begin
      dmSize       := SizeOf(dmScreenSettings);
      dmPelsWidth  := Width;                    // Window width
      dmPelsHeight := Height;                   // Window height
      dmBitsPerPel := ColorDepth;               // Window color depth
      dmFields     := DM_PELSWIDTH or DM_PELSHEIGHT or DM_BITSPERPEL;
    end;

    // Try to change screen mode to fullscreen
    if (ChangeDisplaySettings(dmScreenSettings, CDS_FULLSCREEN) = DISP_CHANGE_FAILED) then begin
      // Couldn't switch to fullscreen
      MessageBox(0, 'Unable to switch to fullscreen!', 'Error', MB_OK or MB_ICONERROR);
      // Go to windowed mode instead
      Fullscreen := False;
    end;

  end;

  // If we are still in fullscreen then
  if (Fullscreen) then begin
    // change a few settings
    dwStyle := WS_POPUP or          // Creates a popup window
               WS_CLIPCHILDREN      // Doesn't draw within child windows
               or WS_CLIPSIBLINGS;  // Doesn't draw within sibling windows
    dwExStyle := WS_EX_APPWINDOW;   // Top level window
    ShowCursor(False);              // Turn of the cursor (gets in the way)
  end else begin
    dwStyle := WS_OVERLAPPEDWINDOW or // Creates an overlapping window
               WS_CLIPCHILDREN or     // Doesn't draw within child windows
               WS_CLIPSIBLINGS;       // Doesn't draw within sibling windows
    dwExStyle := WS_EX_APPWINDOW or   // Top level window
                 WS_EX_WINDOWEDGE;    // Border with a raised edge
  end;

  // Attempt to create the actual window
  h_Wnd := CreateWindowEx(dwExStyle,      // Extended window styles
                          'OpenGL',       // Class name
                          WND_TITLE,      // Window title (caption)
                          dwStyle,        // Window styles
                          0, 0,           // Window position
                          Width, Height,  // Size of window
                          0,              // No parent window
                          0,              // No menu
                          h_Instance,     // Instance
                          nil);           // Pass nothing to WM_CREATE
                          
  if h_Wnd = 0 then begin
    glKillWnd(Fullscreen);                // Undo all the settings we've changed
    MessageBox(0, 'Unable to create window!', 'Error', MB_OK or MB_ICONERROR);
    Result := False;
    Exit;
  end;

  // Try to get a device context
  h_DC := GetDC(h_Wnd);
  if (h_DC = 0) then begin
    glKillWnd(Fullscreen);
    MessageBox(0, 'Unable to get a device context!', 'Error', MB_OK or MB_ICONERROR);
    Result := False;
    Exit;
  end;

  // Set all fields in the pixelformatdescriptor to zero
  ZeroMemory(@pfd, SizeOf(pfd));

  // Initialize only the fields we need
  pfd.nSize       := SizeOf(PIXELFORMATDESCRIPTOR); // Size Of This Pixel Format Descriptor
  pfd.nVersion    := 1;                         // The version of this data structure
  pfd.dwFlags     := PFD_DRAW_TO_WINDOW         // Buffer supports drawing to window
                     or PFD_SUPPORT_OPENGL      // Buffer supports OpenGL drawing
                     or PFD_DOUBLEBUFFER;       // Supports double buffering
  pfd.iPixelType  := PFD_TYPE_RGBA;             // RGBA color format
  pfd.cColorBits  := ColorDepth;                // OpenGL color depth
  pfd.cDepthBits  := 16;                        // Specifies the depth of the depth buffer

  // Attempts to find the pixel format supported by a device context that is the
  // best match to a given pixel format specification.
  PixelFormat := ChoosePixelFormat(h_DC, @pfd);
  if (PixelFormat = 0) then begin
    glKillWnd(Fullscreen);
    MessageBox(0, 'Unable to find a suitable pixel format', 'Error', MB_OK or MB_ICONERROR);
    Result := False;
    Exit;
  end;

  // Sets the specified device context's pixel format to the format specified by
  // the PixelFormat.
  if (not SetPixelFormat(h_DC, PixelFormat, @pfd)) then begin
    glKillWnd(Fullscreen);
    MessageBox(0, 'Unable to set the pixel format', 'Error', MB_OK or MB_ICONERROR);
    Result := False;
    Exit;
  end;

  // Create a OpenGL rendering context
  h_RC := wglCreateContext(h_DC);
  if (h_RC = 0) then begin
    glKillWnd(Fullscreen);
    MessageBox(0, 'Unable to create an OpenGL rendering context', 'Error', MB_OK or MB_ICONERROR);
    Result := False;
    Exit;
  end;

  // Makes the specified OpenGL rendering context the calling thread's current
  // rendering context
  if (not wglMakeCurrent(h_DC, h_RC)) then begin
    glKillWnd(Fullscreen);
    MessageBox(0, 'Unable to activate OpenGL rendering context', 'Error', MB_OK or MB_ICONERROR);
    Result := False;
    Exit;
  end;

  // Settings to ensure that the window is the topmost window
  ShowWindow(h_Wnd, SW_SHOW);
  SetForegroundWindow(h_Wnd);
  SetFocus(h_Wnd);

  // Get the windows client rect (drawing area)
  GetClientRect(h_Wnd, clientRect);

  // Ensure the OpenGL window is resized properly
  glResizeWnd(clientRect.Right, clientRect.Bottom);
  glInit();

  Result := True;
end;

// Name       : WinMain
// Purpose    : Main message loop for the application
function WinMain(hInstance : HINST; hPrevInstance : HINST;  lpCmdLine : PChar; nCmdShow : Integer) : Integer; stdcall;
var
  msg : TMsg;
  finished : Boolean;
begin
  finished := False;

	// Perform application initialization:
  if not glCreateWnd(640, 480, 32, False) then begin
    // Some error was encountered when creating the window, so exit the program
		Result := 0;
    Exit;
  end; 

	// Main message loop:
  while not finished do begin
    // Check if there is a message for this window
    if (PeekMessage(msg, 0, 0, 0, PM_REMOVE)) then begin
      // If WM_QUIT message received then we are done
      if (msg.message = WM_QUIT) then
        finished := True
      else begin // Else translate and dispatch the message to this window
		  	TranslateMessage(msg);
  		  DispatchMessage(msg);
      end;
    end else begin
      glDraw();
      SwapBuffers(h_DC);

      // If ESC key is pressed the user wants to quit
      if (keyBuf[VK_ESCAPE]) then
        finished := True;

      if (keyBuf[VK_SPACE]) then begin
        rotating := not rotating;

        keyBuf[VK_SPACE] := False;
      end;
    end;
  end;

  // Properly dispose of the window, ensures no memory leaks
  glKillWnd(False);

 // Shutdown the window
	Result := msg.wParam;
end;

begin
  // Start execution
  WinMain( hInstance, hPrevInst, CmdLine, CmdShow );
end.
