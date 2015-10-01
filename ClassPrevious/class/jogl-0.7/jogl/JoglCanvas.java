/* Copyright 1997 Free Software Foundation, Inc.
 *
 * JoglCanvas
 *
 * October 19, 1995, Leo Chan
 * Additional enhancements/changes made by Adam King (adam@opcom.ca)
 * and Tommy Reilly (tom@pajato.com)
 *
 * creates an java.awt.Canvas and allows OpenGL functions to
 * be called on it. 
 *
 * September 12, 1995	- Adam King
 *		Modified to support rendering into a Canvas under Windows.
 *		Tried to keep changes from affecting existing SGI code, except
 *		for addition of a native method called initGL.
 */

package jogl;

import jogl.GL;
import java.awt.event.*;
import java.awt.*;
import sun.awt.*;

/**

Define a class JoglCanvas which depends on native methods to
implement 3D graphics through OpenGL.  We implement ComponentListener
and listen to ourselves because of how we had to do things on SGI's.
On SGI's we aren't actually rendering in to the java.awt.Canvas object.
We have to create another window and make it a child of the canvas
because in order to use SGI's hardware acceleration via X we need
to create the window in a very specific manner. 

 */
public class JoglCanvas extends Canvas implements ComponentListener
{
  /**
   * Link with the native jogl library.  If we cannot link, an exception
   * is thrown.  
   */
  static boolean libLoaded = false;

  JoglRenderer renderer=null;

  /* class instance data */
  /**
   * window data for the widget
   */
  protected int renderContext=0;	// stores the GL Context (used on windows and X11)

  /* X11 specific data */
  int display=0;        // stores X11 Display
  int window=0;	        // stores X11 Drawable

  /* Windows data */
  int pData = 0;	// stores the pointer structure that holds windows info

  Graphics graphics;	// needed to tell when the hDC (win32) has changed

  /* variable to tell is where windows or not (X11) */
  boolean isWindows;	// needed to now which OS were're running u nder
  
  boolean doubleBuffer = true;	

  /**
   * constructor for the JoglCanvas object given the name of the
   * widget.
   * opens the JoglCanvas window and sets up the graphics context
   */
  public JoglCanvas( JoglRenderer rend ) throws JoglNativeException 
  {
    super();

    loadLib();
        
    addComponentListener(this);

    renderer = rend;
    
    rend.gl = this;
    
    // Determine the OS
    String os = System.getProperty( "os.name" );
    //    System.out.println( os );
    if( os.startsWith( "Wind" ) )
      isWindows = true;
    else
      isWindows = false;
  }

  public JoglCanvas() throws JoglNativeException
  {
    super();

    loadLib();
	  
    addComponentListener(this);

    // Determine the OS
    String os = System.getProperty( "os.name" );
    //    System.out.println( os );
    if( os.startsWith( "Wind" ) )
      isWindows = true;
    else
      isWindows = false;
  }

  /* we use this function (instead of a static initializer) because some
     applications can still run without Jogl and we want them to be
     able to recover nicely */
  private void loadLib() throws JoglNativeException
    {
      // Make sure that the library loaded
      if( !libLoaded )
	{
          try 
            {
              System.loadLibrary( "jogl" );
              libLoaded = true;
            }   
          catch( UnsatisfiedLinkError e) 
            {
              throw new JoglNativeException("Couldn't load jogl library.");
            }
        }    
    }

  /** 
      This function does all the dirty work, it loads the class that
      accesses the internals from the sun.awt classes via Class.forName
      so that the code is a little more platform independent 
  */
  private void init(Graphics g) throws JoglNativeException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
      String access_name = "sun.awt.";
      JoglpDataAccess data_access;

      /* There's no reason to do this twice */
      if(pData != 0)
        return;

	  DrawingSurfaceInfo surfaceInfo =
      ((DrawingSurface)this.getPeer()).getDrawingSurfaceInfo();
			surfaceInfo.lock();
      if(isWindows)
        {
          //data_access = (JoglpDataAccess) Class.forName(access_name + "windows.WindowspDataAccess").newInstance();
          //pData = data_access.getInfo( g );
		  Win32DrawingSurface wds =
				(Win32DrawingSurface)surfaceInfo.getSurface();
		  window = wds.getHWnd();
		  //System.out.println(" Java WindowID: " + window);
		  pData = 1;
		}
      else
        {
          //data_access = (JoglpDataAccess) Class.forName(access_name + "motif.X11pDataAccess").newInstance();
          
          //window = data_access.getInfo( g );
		  //X11DrawingSurface wds =
			  //(X11DrawingSurface)surfaceInfo.getSurface();
		  //window = wds.getDrawable();

          pData = 1; /* to prevent entering this block again */
        }
	   surfaceInfo.unlock();
      
      //	      System.out.println("Window: " + window + "\nDisplay: " + display);
      
      if(! initGL())
        throw new JoglNativeException("initGL failed");
      
      
      if(renderer != null)
        renderer.start();
      
      //	      System.out.println( "oglCanvas: " + pData + " hgc: "+renderContext );
      
      graphics = g;
    }

  /**
   *  This is called once we have the window information.
   */
  private native boolean initGL();


  /**
     This method does initialization.  It was my impression that this stuff
     should go in AddNotify but that didn't seem to work.  This stuff is
     only executed once. 
  */
  public void paint( Graphics g )
    {
      if(pData == 0)
        {
          try
            {
              init(g);  
            }
          catch(Exception e)
            {
              /* The fact that we can't throw the exception and let the user
                 catch it kind of sucks, I guess we just have to make sure our code
                 is solid and never gets here */
              System.out.println(e);
              System.exit(1);
            }
        }
    }


  /* Functions to implement ComponentListener */
  public void componentHidden(ComponentEvent e) { }
  public void componentShown(ComponentEvent e) { }
  public void componentMoved(ComponentEvent e) { }

  public void componentResized(ComponentEvent e) 
  {
    Dimension d = getSize();
    
    if(graphics != null)
      {
	canvasResized(d.width, d.height);
	viewport(0,0, d.width, d.height);
      }
  }

  /** 
      these are convience functions for the native code 
  */
  public int getWidth()
    {
      return getSize().width;
    }
  public int getHeight()
    {
      return getSize().height;
    }

  private native void canvasResized(int width, int height);

  /**
   * use and swap methods are for double buffering
   */
  public native boolean use();
  
  /**
   * use and swap methods are for double buffering
   */
  public native void swap();

  /*
   * public OpenGL routines.
   *
   * calling these methods will implement the corresponding OpenGL
   * function in native C code.  The naming conventions are as in
   * OpenGL.
   */
  
  /**
   * ACCUM, LOAD, ADD, MULT and RETURN are accepted op values
   */
  public native void accum( int op, float value );

  /**
   * NEVER, LESS, EQUAL. LEQUAL, GREATER, NOTEQUAL, ALWAYS are accepted
   * func values
   */
  public native void alphaFunc( int func, float ref );

  /**
   * POINTS, LINES, LINE_STRIP, LINE_LOOP, TRIANGLES, TRIANGLE_STRIP,
   * TRIANGLE_FAN, QUADS, QUAD_STRIP, POLYGON are accepted mode
   * values
   */
  public native void begin( int mode );

  public native void bindTexture(int type, int texture);

  /**
   * ZERO, ONE, COLOR, ONE_MINUS_DST_COLOR, SRC_ALPHA, ONE_MINUUS_SRC_ALPHA,
   * DST_ALPHA, ONE_MINUS_DEST_ALPHA, SRC_ALPHA_SATURATE are accepted
   * sfactor values.
   * ZERO, ONE, SRC_COLOR, ONE_MINUS_SRC_COLOR, SRC_ALPHA, 
   * ONE_MINUS_SRC_ALPHA, DST_ALPHA, ONE_MINUS_DST_ALPHA are accepted
   * dfactor values
   */
  public native void blendFunc( int sfactor, int dfactor );

  /**
   * COLOR_BUFFER_BIT, DEPTH_BUFFER_BIT, ACCUM_BUFFER_BIT, 
   * STENCIL_BUFFER_BIT are accepted values for mask
   */
  public native void clear( int mask );

  public native void clearAccum( float red, float green, float blue, 
				 float alpha );

  public native void clearColor( float red, float green, float blue, 
				 float alpha );
	
  public native void clearDepth( double depth );

  public native void clearIndex( float c );

  public native void clearStencil( int s );

  /**
   * CLIP_PLANEi where is between 0 and MAX_CLIP_PLANES are accepted
   * values for plane
   * A,B,C,D are coefficients of the plane equation.
   */
  public native void clipPlane( int plane, double A, double B, double C, 
				double D );

  public void color( byte red, byte green, byte blue ) {
	  color3b( red, green, blue );
	}
  private native void color3b( byte red, byte green, byte blue );
  private native void color3d( double red, double green, double blue );
  private native void color3f( float red, float green, float blue );
  private native void color3i( int red, int green, int blue );

  public void color( double red, double green, double blue ) {
		color3d( red, green, blue );
	}
  
  public void color( float red, float green, float blue ) {
		color3f( red, green, blue );
	}
  
  public void
  color( int red, int green, int blue ) {
    color3i( red, green, blue );
  }

  

  public void
  color( short red, short green, short blue ) {
    color3s( red, green, blue );
  }
  private native void
  color3s( short red, short green, short blue );

	public void
	color( byte red, byte green, byte blue, byte alpha ) {
		color4b( red, green, blue, alpha );
	}
		private native void
		color4b( byte red, byte green, byte blue, byte alpha );

	public void
	color( double red, double green, double blue, double alpha ) {
		color4d( red, green, blue, alpha );
	}
		private native void
		color4d( double red, double green, double blue, double alpha );

	public void
	color( float red, float green, float blue, float alpha ) {
		color4f( red, green, blue, alpha );
	}
		private native void
		color4f( float red, float green, float blue, float alpha );

	public void
	color( int red, int green, int blue, int alpha ) {
		color4i( red, green, blue, alpha );
	}
		private native void
		color4i( int red, int green, int blue, int alpha );

	public void
	color( short red, short green, short blue, short alpha ) {
		color4s( red, green, blue, alpha );
	}
		private native void
		color4s( short red, short green, short blue, short alpha );

	public native void
	colorMask( boolean red, boolean green, boolean blue, boolean alpha );

    public native int 
	genLists( int range );

    public native boolean 
	isList( int val );

	public native void
	newList( int list, int mode );

	public native void
	deleteLists( int list, int range );

	public native void
	endList(); 

	public native void
	callList( int list ); 

	/**
	 * FRONT, BACK, FRONT_AND_BACK are accepted values for face.
	 * EMISSION, AMBIENT, DIFFUSE, SPECULAR, AMBIENT_AND_DIFFUSE are accepted
	 * values for mode
	 */
	public native void
	colorMaterial( int face, int mode );

	/**
	 * COLOR, DEPTH, STENCIL are accepted values for type
	 */
	public native void
	copyPixels( int x, int y, int width, int height, int type );

	/**
	 * FRONT, BACK are accepted values for mode 
	 */
	public native void
	cullFace( int mode );

	/**
	 * NEVER,  LESS, EQUAL, LEQUAL, GREATER, NOTEQUAL, GEQUAL, ALWAYS are
	 * accepted values for func.  
	 * The default is LESS
	 */
	public native void
	depthFunc( int func );

	public native void
	depthMask( boolean flag );

	/**
	 * near and far should be between zero and one
	 */
	public native void
	depthRange( double near, double far );

	/**
	 * NONE, FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT, FRONT, BACK,
	 * LEFT, RIGHT, FRONT_AND_BACK, AUXi are accepted values for mode.
	 */
	public native void
	drawBuffer( int mode );

	public native void
	edgeFlag( boolean flag );

	/**
	 * ALPHA_TEST, AUTO_NORMAL, BLEND, CLIP_PLANE, COLOR_MATERIAL,
	 * CULL_FACE, DEPTH_TEST, DITHER, FOG, LIGHTS, LIGHTING, LINE_SMOOTH,
	 * LINE_STIPPLE, LOGIC_OP, MAP1_COLOR_4, MAP1_INDEX, MAP1_NORMAL,
	 * MAP1_TEXTURE_COORD, MAP1_TEXTURE_COORD_2, MAP1_TEXTURE_COORD_3,
	 * MAP1_TEXTURE_COORD_4, MAP1_VERTEX_3, MAP1_VERTEX_4, 
	 * MAP2_COLOR_4, MAP2_INDEX, MAP2_TEXTURE_COORD_1, 
	 * MAP2_TEXTURE_COORD_2, MAP2_TEXTURE_COORD_3, MAP2_VERTEX_3,
	 * MAP2_VERTEX_4, NORMALIZE, POINT_SMOOTH, POLYGON_SMOOTH,
	 * POLYGON_STIPPLE, SCISSOR_TEST, STENCIL_TEST, TEXTURE_1D,
	 * TEXTURE_2D, TEXTURE_GEN_Q, TEXTURE_GEN_R, TEXTURE_GEN_S,
	 * TEXTURE_GEN_T are accepted values for capability
	 */
	public native void
	enable( int capability );

	public native void
	disable( int capability );

	public native void
	end();

	public void evalCoord( double u ) {
		evalCoord1d( u );
	}
		private native void
		evalCoord1d( double u );

	public void evalCoord( float u ) {
		evalCoord1f( u );
	}
		private native void
		evalCoord1f( float u );

	public void evalCoord( double u, double v ) {
		evalCoord2d( u,v );
	}
		private native void
		evalCoord2d( double u, double v );

	public void evalCoord( float u, float v ) {
		evalCoord2f( u,v );
	}
		private native void
		evalCoord2f( float u, float v );
	
	/**
	 * POINT and LINE are accepted values for mode
	 */
	public void evalMesh( int mode, int i1, int i2 ) {
		evalMesh1( mode, i1, i2 );
	}
		private native void
		evalMesh1( int mode, int i1, int i2 );

	/**
	 * POINT, LINE and FILL are accepted values for mode
	 */
	public void evalMesh( int mode, int i1, int i2, int j1, int j2 ) {
		evalMesh2( mode, i1, i2, j1, j2 );
	}
		private native void
		evalMesh2( int mode, int i1, int i2, int j1, int j2 );
	
	public void evalPoint( int i ) {
		evalPoint1( i );
	}
		private native void
		evalPoint1( int i );
	
	public void evalPoint( int i, int j ) {
		evalPoint2( i, j );
	}
		private native void
		evalPoint2( int i, int j );

	public native void
	finish();

	public native void
	flush();

	/**
	 * FOG_MODE, FOG_DENSITY, FOG_START, FOG_END, FOG_INDEX are 
	 * accepted values for name
	 */
	public void fog( int name, float param ) {
		fogf( name, param );
	}

		private native void
		fogf( int name, float param );

	/**
	 * FOG_MODE, FOG_DENSITY, FOG_START, FOG_END, FOG_INDEX are 
	 * accepted values for name
	 */
	public void fog( int name, int param ) {
		fogi( name, param );
	}

		private native void
		fogi( int name, int param );

	public void fogColour( float r, float g, float b, float a ) {
		fogfv( GL.FOG_COLOR, r,g,b,a );
	}
		private native void
		fogfv( int name, float r, float g, float b, float a );

	public void fogColour( int r, int g, int b, int a ) {
		fogiv( GL.FOG_COLOR, r,g,b,a );
	}

		private native void
		fogiv( int name, int r, int g, int b, int a );

	/**
	 * CW, CCW are accepted values for mode 
	 */
	public native void
	frontFace( int mode );

	public native void
	frustum( double left, double right, double bottom, 
			 double top, double near, double far );

  public native int genTextures(int size);

	public void get( int pname, boolean params[] ) {
		getb( pname, params );
	}
		private native void
		getb( int pname, boolean params[] );

	public void get( int pname, double params[] ) {
		getd( pname, params );
	}
		private native void
		getd( int pname, double params[] );

	public void get( int pname, float params[] ) {
		getf( pname, params );
	}
		private native void
		getf( int pname, float params[] );

	public void get( int pname, int params[] ) {
		geti( pname, params );
	}
		private native void
		geti( int pname, int params[] );

	/*	
	 * FOG_HINT, LINE_SMOOTH_HINT, PERSPECTIVE_CORRECTION_HINT, 
	 * POINT_SMOOTH_HINT, POLYGON_SMOOTH_HINT are accepted values for target
	 * FASTEST, NICEST, DONT_CARE are accepted values for mode
	 */
	public native void
	hint( int target, int mode );
	
	public void
	index( double c) {
		indexd( c );
	}
		private native void
		indexd( double c );

	public void
	index( float c) {
		indexf( c );
	}
		private native void
		indexf( float c );

	public void
	index( int c) {
		indexi( c );
	}
		private native void
		indexi( int c );

	public void
	index( short c) {
		indexs( c );
	}
		private native void
		indexs( short c );
	
	public native void 
		indexMask( int mask );

	/**
	 * ALPHA_TEST, AUTO_NORMAL, BLEND, CLIP_PLANE, COLOR_MATERIAL,
	 * CULL_FACE, DEPTH_TEST, DITHER, FOG, LIGHTS, LIGHTING, LINE_SMOOTH,
	 * LINE_STIPPLE, LOGIC_OP, MAP1_COLOR_4, MAP1_INDEX, MAP1_NORMAL,
	 * MAP1_TEXTURE_COORD, MAP1_TEXTURE_COORD_2, MAP1_TEXTURE_COORD_3,
	 * MAP1_TEXTURE_COORD_4, MAP1_VERTEX_3, MAP1_VERTEX_4, 
	 * MAP2_COLOR_4, MAP2_INDEX, MAP2_TEXTURE_COORD_1, 
	 * MAP2_TEXTURE_COORD_2, MAP2_TEXTURE_COORD_3, MAP2_VERTEX_3,
	 * MAP2_VERTEX_4, NORMALIZE, POINT_SMOOTH, POLYGON_SMOOTH,
	 * POLYGON_STIPPLE, SCISSOR_TEST, STENCIL_TEST, TEXTURE_1D,
	 * TEXTURE_2D, TEXTURE_GEN_Q, TEXTURE_GEN_R, TEXTURE_GEN_S,
	 * TEXTURE_GEN_T are accepted values for capability
	 */
	public native boolean 
	isEnabled( int capability );

	public void
	light( int light, int pname, float param ) {
		lightf( light, pname, param );
	}

		private native void
		lightf( int light, int pname, float param );
	
	/**
	 * LIGHTi is accepted for light, where i is between 0 and MAX_LIGHTS
	 * SPOT_EXPONENT, SPOT_CUTOFF, CONSTANT_ATTENUATION, LINEAR_ATTENUATION,
	 * QUADRATIC_ATTENUATION are accepted values for pname
	 */
	public void
	light( int light, int pname, int param ) {
		lighti( light, pname, param );
	}
		private native void
		lighti( int light, int pname, int param );

	/**
	 * LIGHTi is accepted for light, where i is between 0 and MAX_LIGHTS
	 * AMBIENT,DIFFUSE, SPECULAR, POSITION,
	 * SPOT_DIRECTION, SPOT_EXPONENT,
	 * SPOT_CUTOFF, CONSTANT_ATTENUATION,
	 * LINEAR_ATTENUATION, QUADRATIC_ATTENUATION are accepted values 
	 * for pname
	 */
	public void
	light( int light, int pname, int params[] ) {
		switch( pname ) {
		case GL.AMBIENT:			// requires four elems
		case GL.DIFFUSE:
		case GL.SPECULAR:
		case GL.POSITION:
			if( params.length >= 4 )
				lightiv( light, pname, params[0], params[1],
							params[2], params[3] );
			else
				return;			// TO DO: throw an exception here
			break;
		case GL.SPOT_DIRECTION:	// requires three elems
		case GL.CONSTANT_ATTENUATION:
		case GL.LINEAR_ATTENUATION:
		case GL.QUADRATIC_ATTENUATION:
			if( params.length >= 3 )
				lightiv( light, pname, params[0], params[1], params[2], 0 );
			else
				return;			// TO DO: throw an exception here
			break;
		case GL.SPOT_EXPONENT:		// requires one elem
		case GL.SPOT_CUTOFF:
			if( params.length >= 1 )
				lightiv( light, pname, params[0], 0,0,0 );
			else
				return;			// TO DO: throw an exception here
			break;
		}
	}
		private native void
		lightiv( int light, int pname, int param1, int param2, int param3,
				 int param4 );

	/**
	 * LIGHTi is accepted for light, where i is between 0 and MAX_LIGHTS
	 * AMBIENT,DIFFUSE, SPECULAR, POSITION,
	 * SPOT_DIRECTION, SPOT_EXPONENT,
	 * SPOT_CUTOFF, CONSTANT_ATTENUATION,
	 * LINEAR_ATTENUATION, QUADRATIC_ATTENUATION are accepted values 
	 * for pname
	 */
	public void
	light( int light, int pname, float params[] ) {
		switch( pname ) {
		case GL.AMBIENT:			// requires four elems
		case GL.DIFFUSE:
		case GL.SPECULAR:
		case GL.POSITION:
			if( params.length >= 4 )
				lightfv( light, pname, params[0], params[1],
							params[2], params[3] );
			else
				return;			// TO DO: throw an exception here
			break;
		case GL.SPOT_DIRECTION:	// requires three elems
		case GL.CONSTANT_ATTENUATION:
		case GL.LINEAR_ATTENUATION:
		case GL.QUADRATIC_ATTENUATION:
			if( params.length >= 3 )
				lightfv( light, pname, params[0], params[1],
							params[2], 0.0f );
			else
				return;			// TO DO: throw an exception here
			break;
		case GL.SPOT_EXPONENT:		// requires one elem
		case GL.SPOT_CUTOFF:
			if( params.length >= 1 )
				lightfv( light, pname, params[0], 0.0f, 0.0f, 0.0f );
			else
				return;			// TO DO: throw an exception here
			break;
		}
	}
		private native void
		lightfv( int light, int pname, float param1, float param2, float param3,
				 float param4 );

	/**
	 * LIGHT_MODEL_LOCAL_VIEWER, LIGHT_MODEL_TWO_SIDE are accepted values
	 * for pname
	 */
	public void 
	lightModel( int pname, int param ) {
		lightModeli( pname, param );
	}
		private native void
		lightModeli( int pname, int param );


	/**
	 * LIGHT_MODEL_LOCAL_VIEWER, LIGHT_MODEL_TWO_SIDE are accepted values
	 * for pname
	 */
	public void 
	lightModel( int pname, float param ) {
		lightModelf( pname, param );
	}
		private native void
		lightModelf( int pname, float param );

	/**
	 * LIGHT_MODEL_AMBIENT, LIGHT_MODEL_LOCAL_VIEWER and
	 * LIGHT_MODEL_TWO_SIDE are accepted values for pname
	 */
	public void 
	lightModel( int pname, int param[] ) {
		switch( pname ) {
		case GL.LIGHT_MODEL_AMBIENT:		// four elems required
			lightModeliv( pname, param );
			break;
		case GL.LIGHT_MODEL_LOCAL_VIEWER:	// single elem required
		case GL.LIGHT_MODEL_TWO_SIDE:
			lightModeliv( pname, param );
		}
	}
		private native void
		lightModeliv( int pname, int param[] );

	/**
	 * LIGHT_MODEL_AMBIENT, LIGHT_MODEL_LOCAL_VIEWER and
	 * LIGHT_MODEL_TWO_SIDE are accepted values for pname
	 */
	public void 
	lightModel( int pname, float param[] ) {
		switch( pname ) {
		case GL.LIGHT_MODEL_AMBIENT:		// four elems required
			lightModelfv( pname, param );
			break;
		case GL.LIGHT_MODEL_LOCAL_VIEWER:	// single elem required
		case GL.LIGHT_MODEL_TWO_SIDE:
			lightModelfv( pname, param );
		}
	}
		private native void
		lightModelfv( int pname, float param[] );

	public native void
	lineStipple( int factor, short pattern );

	public native void
	lineWidth( float width );

	public native void
	loadIdentity();

	public void
	loadMatrix( double darr[] ) {
		if( darr.length == 16 )
			loadMatrixd( darr );
	}
		private native void
		loadMatrixd( double darr[] );

	public void
	loadMatrix( float farr[] ) {
		if( farr.length == 16 )
			loadMatrixf( farr );
	}
		public native void
		loadMatrixf( float farr[] );


	public native void
	loadName( int name );

	/**
	 * CLEAR, SET, COPY, COPY_INVERTED, NOOP, INVERT, AND, NAND,
	 * OR, NOR, XOR, EQUIV, AND_REVERSE, AND_INVERTED, OR_REVERSE,
	 * OR_INVERTED are accepted values for opcode
	 */
	public native void
	logicOp( int opcode );

	public void
	mapGrid1( int un, double u1, double u2 ) {
		mapGrid1d( un, u1, u2 );
	}
		private native void
		mapGrid1d( int un, double u1, double u2 );

	public void
	mapGrid1( int un, float u1, float u2 ) {
		mapGrid1f( un, u1, u2 );
	}
		private native void
		mapGrid1f( int un, float u1, float u2 );

	public void
	mapGrid2( int un, double u1, double u2, int vn, double v1, double v2 ) {
		mapGrid2d( un, u1, u2, vn, v1, v2 );
	}
		private native void
		mapGrid2d( int un, double u1, double u2, int vn, double v1, double v2 );

	public void
	mapGrid2( int un, float u1, float u2, int vn, float v1, float v2 ) {
		mapGrid2f( un, u1, u2, vn, v1, v2 );
	}
		private native void
		mapGrid2f( int un, float u1, float u2, int vn, float v1, float v2 );

	/**
	 * FRONT_AND_BACK, FRONT, BACK are accepted values for face
	 * SHININESS is accepted value for pname
	 */
	public void
	material( int face, int pname, float param ) {
		materialf( face, pname, param );
	}
		private native void
		materialf( int face, int pname, float param );


	/**
	 * FRONT_AND_BACK, FRONT, BACK are accepted values for face
	 * SHININESS is accepted value for pname
	 */
	public void
	material( int face, int pname, int param ) {
		materiali( face, pname, param );
	}
		private native void
		materiali( int face, int pname, int param );


	/**
	 * AMBIENT, DIFFUSE, SPECULAR, EMISSION, SHININESS,
	 * AMBIENT_AND_DIFFUSE, COLOR_INDEXES are valid values for
	 * pname
	 */
	public void
	material( int face, int pname, int params[] ) {
		switch( pname ) {
		case GL.AMBIENT:		// four elems required
		case GL.DIFFUSE:
		case GL.SPECULAR:
		case GL.EMISSION:
		case GL.AMBIENT_AND_DIFFUSE:
			if( params.length >= 4 )
				materialiv( face, pname, params[0], params[1],
							params[2], params[3] );
			else
				return;		// TO DO: throw and exception here
			break;	
		case GL.COLOR_INDEXES:	// three elems required
			if( params.length >= 3 )
				materialiv( face, pname, params[0], params[1],
							params[2], 0 );
			else
				return;		// TO DO: throw an exception here
			break;
		case GL.SHININESS:		// one elem required
			if( params.length >= 1 )
				materialiv( face, pname, params[0], 0,0,0 );
			else
				return;		// TO DO: throw an exception here
			break;
		}
	}

		private native void
		materialiv( int face, int pname, int p1, int p2, int p3, int p4 );

	/**
	 * AMBIENT, DIFFUSE, SPECULAR, EMISSION, SHININESS,
	 * AMBIENT_AND_DIFFUSE, COLOR_INDEXES are valid values for
	 * pname
	 */
	public void
	material( int face, int pname, float params[] ) {
		switch( pname ) {
		case GL.AMBIENT:		// four elems required
		case GL.DIFFUSE:
		case GL.SPECULAR:
		case GL.EMISSION:
		case GL.AMBIENT_AND_DIFFUSE:
			if( params.length >= 4 )
				materialfv( face, pname, params[0], params[1],
							params[2], params[3] );
			else
				return;		// TO DO: throw and exception here
			break;	
		case GL.COLOR_INDEXES:	// three elems required
			if( params.length >= 3 )
				materialfv( face, pname, params[0], params[1],
							params[2], 0.0f );
			else
				return;		// TO DO: throw an exception here
			break;
		case GL.SHININESS:		// one elem required
			if( params.length >= 1 )
				materialfv( face, pname, params[0], 0.0f,0.0f,0.0f );
			else
				return;		// TO DO: throw an exception here
			break;
		}
	}
		private native void
		materialfv( int face, int pname, float p1, float p2, float p3,
					float p4 );

	/**
	 * MODELVIEW, PROJECTION, TEXTURE are accepted values for mode
	 */
	public native void
	matrixMode( int mode );

	public void
	multMatrix( double M[] ) {
		if( M.length >= 16 )
			multMatrixd( M );
	}
		private native void
		multMatrixd( double M[] );

	public void
	multMatrix( float M[] ) {
		if( M.length >= 16 )
			multMatrixf( M );
	}
		private native void
		multMatrixf( float M[] );

	/**
	 * set the current normal vector
	 */
	public void normal ( byte nx, byte ny, byte nz ) {
		normal3b( nx, ny, nz );
	}
		private native void normal3b ( byte nx, byte ny, byte nz );

	public void normal ( double nx, double ny, double nz ) {
		normal3d( nx, ny, nz );
	}
		private native void normal3d ( double nx, double ny, double nz );

	public void normal ( float nx, float ny, float nz ) {
		normal3f( nx, ny, nz );
	}
		private native void normal3f ( float nx, float ny, float nz );

	public void normal ( short nx, short ny, short nz ) {
		normal3s( nx, ny, nz );
	}
		private native void normal3s ( short nx, short ny, short nz );

	public void normal ( int nx, int ny, int nz ) {
		normal3i( nx, ny, nz );
	}
		private native void normal3i ( int nx, int ny, int nz );

	public native void
		ortho( double left, double right, double bottom, double top,
			   double near, double far );
	
	public native void
		passThrough( float token );

  public void pixelStore(int pname, int param)
  {
    pixelStorei(pname, param);
  }

  private native void pixelStorei(int pname, int param);

  public void pixelStore(int pname, float param)
  {
    pixelStoref(pname, param);
  }
  
  private native void pixelStoref(int pname, float param);
	public native void
		pointSize( float size );
	
	/**
	 * FRONT, BACK, FRONT_AND_BACK are accepted values for face
	 * POINT, LINE, FILL are accepted values for mode
	 */
	public native void
		polygonMode( int face, int mode );

	/**
	 * ACCUM_BUFFER_BIT, COLOR_BUFFER_BIT, CURRENT_BIT, DEPTH_BUFFER_BIT,
	 * ENABLE_BIT, EVAL_BIT, FOG_BIT, HINT_BIT, LIGHTING_BIT, LINE_BIT,
	 * LIST_BIT, PIXEL_MODE_BIT, POINT_BIT, POLYGON_BIT, POLYGON_STIPPLE_BIT,
	 * SCISSOR_BIT, STENCIL_BUFFER_BIT, TEXTURE_BIT, TRANSFORM_BIT,
	 * VIEWPORT_BIT are accepted values for mask 
	 */
	public native void
	pushAttrib( int mask );

	public native void
	popAttrib();

	public native void
	pushMatrix();

	public native void
	popMatrix();

	public native void
	pushName( int name );

	public native void
	popName();

	
	public void rasterPos( double x, double y ) {
		rasterPos2d( x, y );
	}
		private native void
			rasterPos2d( double x, double y );

	public void rasterPos( float x, float y ) {
		rasterPos2f( x, y );
	}
		private native void
			rasterPos2f( float x, float y );

	public void rasterPos( int x, int y ) {
		rasterPos2i( x, y );
	}
		private native void
			rasterPos2i( int x, int y );

	public void rasterPos( short x, short y ) {
		rasterPos2s( x, y );
	}
		private native void
			rasterPos2s( short x, short y );

	public void rasterPos( double x, double y, double z ) {
		rasterPos3d( x, y, z );
	}
		private native void
			rasterPos3d( double x, double y, double z );

	public void rasterPos( float x, float y, float z ) {
		rasterPos3f( x, y, z );
	}
		private native void
			rasterPos3f( float x, float y, float z );

	public void rasterPos( int x, int y, int z ) {
		rasterPos3i( x, y, z );
	}
		private native void
			rasterPos3i( int x, int y, int z );
	
	public void rasterPos( short x, short y, short z ) {
		rasterPos3s( x, y, z );
	}
		private native void
			rasterPos3s( short x, short y, short z );
	
	/**
	 * FRONT_LEFT, BACK_RIGHT, BACK_LEFT, BACK_RIGHT, FRONT, BACK, LEFT,
	 * RIGHT, AUXi where i is between 0 and AUX_BUFFERS are accepted 
	 * values for mode
	 */
	public native void 
		readBuffer( int mode );
	
	public void rect( double x1, double y1, double x2, double y2 ) {
		rectd( x1, y1, x2, y2 );
	}
		private native void
			rectd( double x1, double y1, double x2, double y2 );
	
	public void rect( float x1, float y1, float x2, float y2 ) {
		rectf( x1, y1, x2, y2 );
	}
		private native void
			rectf( float x1, float y1, float x2, float y2 );
	
	public void rect( int x1, int y1, int x2, int y2 ) {
		rectf( x1, y1, x2, y2 );
	}
		private native void
			recti( int x1, int y1, int x2, int y2 );
	
	/**
	 * RENDER, SELECT, FEEDBACK are accepted values for mode.  The default
	 * is RENDER
	 */
	public native void renderMode( int mode );

	public void rotate( double angle, double x, double y, double z ) {
		rotated( angle, x, y, z );
	}
		private native void 
			rotated( double angle, double x, double y, double z );
			
	public void rotate( float angle, float x, float y, float z ) {
		rotatef( angle, x, y, z );
	}
		private native void 
			rotatef( float angle, float x, float y, float z );
			
	public void scale( double x, double y, double z ) {
		scaled( x, y, z );
	}
		private native void 
			scaled( double x, double y, double z );

	public void scale( float x, float y, float z ) {
		scalef( x, y, z );
	}
		private native void 
			scalef( float x, float y, float z );

	public native void
		scissor( int x, int y, int width, int height );
	
	/**
	 * FLAT, SMOOTH are accepted values for mode
	 */
	public native void
		shadeModel( int mode );
	
	/**
	 * NEVER, LESS, LEQUAL, GREATER, GEQUAL, EQUAL, NOTEQAUL, ALWAYS
	 * are accepted values for func
	 */
	public native void
		stencilFunc( int func, int ref, int mask );

	public native void
		stencilMask( int mask );
	
	/**
	 * KEEP, ZERO, REPLACE, INCR, DECR, INVERT are accepted values for
	 * fail, zfail and zpass
	 */
	public native void
		stencilOp( int fail, int zfail, int zpass );
	
	public void texCoord( double s ) {
		texCoord1d( s );
	}
		private native void
			texCoord1d( double s );
	
	public void texCoord( float s ) {
		texCoord1f( s );
	}
		private native void
			texCoord1f( float s );
	
	public void texCoord( int s ) {
		texCoord1i( s );
	}
		private native void
			texCoord1i( int s );
	
	public void texCoord( short s ) {
		texCoord1s( s );
	}
		private native void
			texCoord1s( short s );
	
	public void texCoord( double s, double t ) {
		texCoord2d( s,t );
	}
		private native void
			texCoord2d( double s, double t );
	
	public void texCoord( float s, float t ) {
		texCoord2f( s,t );
	}
		private native void
			texCoord2f( float s, float t );
	
	public void texCoord( int s, int t ) {
		texCoord2i( s,t );
	}
		private native void
			texCoord2i( int s, int t );
	
	public void texCoord( short s, short t ) {
		texCoord2s( s,t );
	}
		private native void
			texCoord2s( short s, short t );
	
	public void texCoord( double s, double t, double r ) {
		texCoord3d( s,t,r );
	}
		private native void
			texCoord3d( double s, double t, double r );
	
	public void texCoord( float s, float t, float r ) {
		texCoord3f( s,t,r );
	}
		private native void
			texCoord3f( float s, float t, float r );
	
	public void texCoord( int s, int t, int r ) {
		texCoord3i( s,t,r );
	}
		private native void
			texCoord3i( int s, int t, int r );
	
	public void texCoord( short s, short t, short r ) {
		texCoord3s( s,t,r );
	}
		private native void
			texCoord3s( short s, short t, short r );
	
	public void texCoord( double s, double t, double r, double q ) {
		texCoord4d( s,t,r,q );
	}
		private native void
			texCoord4d( double s, double t, double r, double q );
	
	public void texCoord( float s, float t, float r, float q ) {
		texCoord4f( s,t,r,q );
	}
		private native void
			texCoord4f( float s, float t, float r, float q );
	
	public void texCoord( int s, int t, int r, int q ) {
		texCoord4i( s,t,r,q );
	}
		private native void
			texCoord4i( int s, int t, int r, int q );
	
	public void texCoord( short s, short t, short r, short q ) {
		texCoord4s( s,t,r,q );
	}
		private native void
			texCoord4s( short s, short t, short r, short q );

	public void texEnv( int target, int name, float param ) {
		texEnvf( target, name, param );
	}
		private native void
			texEnvf( int target, int name, float param );

	public void texEnv( int target, int name, int param ) {
		texEnvi( target, name, param );
	}
		private native void
			texEnvi( int target, int name, int param );

	public void texGen( int coord, int pname, double param ) {
		texGend( coord, pname, param );
	}
		private native void
			texGend( int coord, int pname, double param );

	public void texGen( int coord, int pname, float param ) {
		texGenf( coord, pname, param );
	}
		private native void
			texGenf( int coord, int pname, float param );

	public void texGen( int coord, int pname, int param ) {
		texGeni( coord, pname, param );
	}
		private native void
			texGeni( int coord, int pname, int param );

  public void texImage(int target, int level, int components, 
		       int width, int border, int format, 
		       int type, byte[] pixels)
  {
    texImage1D(target, level, components, width, border, format, type,  pixels);
  }

  private native void texImage1D(int target, int level, int components, 
		       int width, int border, int format, 
		       int type, byte[] pixels);

  public void texImage(int target, int level, int components, 
		       int width, int height, int border, int format, 
		       int type, byte[][][] pixels)
  {
    texImage2Db(target, level, components, width, height, border, format, type, pixels);
  }
  public void texImage(int target, int level, int components, 
		       int width, int height, int border, int format, 
		       int type, int[] pixels)
  {
    texImage2Di(target, level, components, width, height, border, format, type, pixels);
  }

  private native void texImage2Db(int target, int level, int components, 
				 int width, int height, int border, int format, 
				 int type, byte[][][] pixels);

  private native void texImage2Di(int target, int level, int components, 
				 int width, int height, int border, int format, 
				 int type, int[] pixels);     

  public void texParameter(int target, int pname, float param)
  {
    texParameterf(target, pname,  param);
  }

  private native void texParameterf(int target, int pname, float param);

  public void texParameter(int target, int pname, int param)
  {
    texParameteri(target, pname,  param);
  }

  private native void texParameteri(int target, int pname, int param);
  
  public native void copyTexImage2D(int target, int level, int internalFormat,
    int x, int y, int width, int height, int border);



	public void translate( double x, double y, double z ) {
		translated( x, y ,z );
	}
		private native void
			translated( double x, double y, double z );

	public void translate( float x, float y, float z ) {
		translatef( x, y ,z );
	}
		private native void
			translatef( float x, float y, float z );

	public void vertex( double x, double y ) {
		vertex2d( x, y );
	}
		private native void
			vertex2d( double x, double y );

	public void vertex( float x, float y ) {
		vertex2f( x, y );
	}
		private native void
			vertex2f( float x, float y );

	public void vertex( int x, int y ) {
		vertex2i( x, y );
	}
		private native void
			vertex2i( int x, int y );

	public void vertex( short x, short y ) {
		vertex2s( x, y );
	}
		private native void
			vertex2s( short x, short y );

	public void vertex( double x, double y, double z ) {
		vertex3d( x, y, z );
	}
		private native void
			vertex3d( double x, double y, double z );

	public void vertex( float x, float y, float z ) {
		vertex3f( x, y, z );
	}
		private native void
			vertex3f( float x, float y, float z );

	public void vertex( int x, int y, int z ) {
		vertex3i( x, y, z );
	}
		private native void
			vertex3i( int x, int y, int z );

	public void vertex( short x, short y, short z ) {
		vertex3s( x, y, z );
	}
		private native void
			vertex3s( short x, short y, short z );

	public void vertex( double x, double y, double z, double w ) {
		vertex4d( x, y, z, w );
	}
		private native void
			vertex4d( double x, double y, double z, double w );

	public void vertex( float x, float y, float z, float w ) {
		vertex4f( x, y, z, w );
	}
		private native void
			vertex4f( float x, float y, float z, float w );

	public void vertex( int x, int y, int z, int w ) {
		vertex4i( x, y, z, w );
	}
		private native void
			vertex4i( int x, int y, int z, int w );

	public void vertex( short x, short y, short z, short w ) {
		vertex4s( x, y, z, w );
	}
		private native void
			vertex4s( short x, short y, short z, short w );
	
	public native void
		viewport( int x, int y, int width, int height );


	/**
	 * GLU functions
	 */

	//  Miscellaneous functions

	public native void lookAt( double eyex, double eyey, double eyez,
							   double centerx, double centery, double centerz,
							   double upx, double upy, double upz );

	public native void ortho2D( double left, double right, double bottom, double top );

	public native void perspective( double fovy, double aspect, double zNear, double zFar );

	public native void pickMatrix( double x, double y, double width, double height, int viewport[] );

	public native int project( double objx, double objy, double objz,
							   double modelMatrix[],
							   double projMatrix[],
							   int viewport[],
							   Double winx, Double winy, Double winz );

	public native int unProject( double winx, double winy, double winz,
								 double modelMatrix[],
								 double projMatrix[],
								 int viewport[],
								 Double objx, Double objy, Double objz );

	public native String errorString( int errorCode );


	// Mipmapping and image scaling

	public native int scaleImage( int format, 
								  int widthin, int heightin, int typein, Object datain, 
								  int widthout, int heightout, int typeout, Object dataout );

	public native int build1DMipmaps( int target, int components, 
									  int width, int format, int type, Object data );

	public native int build2DMipmaps( int target, int components, 
									  int width, int height, int format, int type, Object data );


	// Get GLU string

	public native String getString( int name );

}
