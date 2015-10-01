//
//  CLASS
//    ExTexture	-  illustrate use of textures
//
//  LESSON
//    Use Texture2D and TextureAttributes to apply a texture image
//    to a shape.
//
//  AUTHOR
//    Michael J. Bailey / San Diego Supercomputer Center
//

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.lang.*;
import java.net.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.image.*;

public class ExTexture
	extends Example
{
	//  nodes that can be updated via a menu:

	private Shape3D sh = null;			// overall scene shape
	private TransformGroup geomGroup = null;	// group of geometry
	private GeometryArray geom = null;		// scene geometry
	private Appearance app = null;			// geometry appearance
	private Appearance dummyApp = null;		// temporary appearance
	private Material mat = null;			// geometry surface color
	private PolygonAttributes polyatt = null;	// polygon attributes
	private TextureAttributes texatt = null;	// texture attributes
	private TextureAttributes dummyAtt = null;	// temporary texture attributes
	private Transform3D tt = null;			// texture transform
	private Texture2D tex = null;			// current texture
	private TransformGroup tg = null;		// object transform
	private Transform3D tgt = null;			// object transform


	//  Build the scene:

	public Group buildScene()
	{
		// Turn on the headlight
		setHeadlightEnable( true );

		// Build the scene root
		Group scene = new Group();


		// Create application bounds
		BoundingSphere worldBounds = new BoundingSphere(
			new Point3d( 0.0, 0.0, 0.0 ),  // Center
			1000.0 );                      // Extent


		mat = new Material();
		mat.setAmbientColor( 0.6f, 0.6f, 0.6f );
		mat.setDiffuseColor( 1.0f, 0.0f, 0.0f );
		mat.setSpecularColor( 0.0f, 0.0f, 0.f );

		tt = new Transform3D();
		tt.setIdentity();
		tt.setScale( 1. );

		polyatt = new PolygonAttributes();
		polyatt.setCullFace( PolygonAttributes.CULL_NONE );
		polyatt.setPolygonMode( PolygonAttributes.POLYGON_FILL );

		dummyAtt = new TextureAttributes();

		texatt = new TextureAttributes();
		texatt.setCapability( TextureAttributes.ALLOW_MODE_WRITE );
		texatt.setCapability( TextureAttributes.ALLOW_BLEND_COLOR_WRITE );
		texatt.setCapability( TextureAttributes.ALLOW_TRANSFORM_WRITE );
		texatt.setTextureMode( ((Integer)modes[currentMode].value).intValue() );
		texatt.setPerspectiveCorrectionMode( TextureAttributes.NICEST );
		texatt.setTextureTransform( tt );
		texatt.setTextureBlendColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.5f ) );

		dummyApp = new Appearance();

		app = new Appearance();
		app.setCapability( Appearance.ALLOW_TEXTURE_WRITE );
		app.setCapability( Appearance.ALLOW_TEXTURE_ATTRIBUTES_WRITE );
		app.setMaterial( mat );
		app.setPolygonAttributes( polyatt );
		app.setTextureAttributes( texatt );
		app.setTexture( tex );

		sh = new Shape3D();
		sh.setAppearance( app );
		sh.setCapability( Shape3D.ALLOW_GEOMETRY_WRITE );
		sh.setCapability( Shape3D.ALLOW_APPEARANCE_WRITE );
		buildGeometry();
		sh.setGeometry( geom );

		tgt = new Transform3D();
		tgt.rotX( 0.0 * Math.PI / 6. );
		tg = new TransformGroup( tgt );
		tg.addChild( sh );

		scene.addChild( tg );

		return scene;
	}


	//
	//  Main (if invoked as an application)
	//
	public static void main( String[] args )
	{
		ExTexture ex = new ExTexture();
		ex.initialize( args );
		ex.buildUniverse();
		ex.showFrame();
	}

	//  Image menu choices
	private int currentTexture = 0;
	private int currentMode = 0;
	private int currentBoundary = 0;
	private int currentFilter = 0;
	private CheckboxMenuItem[] textureMenu;
	private CheckboxMenuItem[] modeMenu;
	private CheckboxMenuItem[] boundaryMenu;
	private CheckboxMenuItem[] filterMenu;
	private CheckboxMenuItem[] xformMenu;

	private NameValue[] images =
	{
		new NameValue( "Earth",  	"earth.jpg" ),
	};

	private NameValue[] modes =
	{
		new NameValue( "BLEND",  	new Integer( TextureAttributes.BLEND ) ),
		new NameValue( "DECAL",  	new Integer( TextureAttributes.DECAL ) ),
		new NameValue( "MODULATE",  	new Integer( TextureAttributes.MODULATE ) ),
		new NameValue( "REPLACE",  	new Integer( TextureAttributes.REPLACE ) ),
	};

	private NameValue[] boundaries =
	{
		new NameValue( "CLAMP",  	new Integer( Texture.CLAMP ) ),
		new NameValue( "WRAP",  	new Integer( Texture.WRAP ) ),
	};

	private NameValue[] filters =
	{
		new NameValue( "POINT",  	new Integer( Texture.BASE_LEVEL_POINT ) ),
		new NameValue( "LINEAR",  	new Integer( Texture.BASE_LEVEL_LINEAR ) ),
	};

	private Texture2D[]	textureComponents;
	private TextureLoader[]	texLoader;


	//
	//  Initialize the GUI (application and applet)
	//
	public void initialize( String[] args )
	{
		// Initialize the window, menubar, etc.
		super.initialize( args );
		exampleFrame.setTitle( "Java 3D Texture Mapping Example" );

		// Add a menu to select among texture options

		Menu mt = new Menu( "Texture" );
		textureMenu = new CheckboxMenuItem[ images.length ];
		for( int i = 0; i < images.length; i++ )
		{
			textureMenu[i] = new CheckboxMenuItem( images[i].name );
			textureMenu[i].addItemListener( this );
			textureMenu[i].setState( false );
			mt.add( textureMenu[i] );
		}
		exampleMenuBar.add( mt );

		// Add a menu to select the texture mode:

		Menu mm = new Menu( "Texture Mode" );
		modeMenu = new CheckboxMenuItem[ modes.length ];
		for( int i = 0; i < modes.length; i++ )
		{
			modeMenu[i] = new CheckboxMenuItem( modes[i].name );
			modeMenu[i].addItemListener( this );
			modeMenu[i].setState( false );
			mm.add( modeMenu[i] );
		}
		exampleMenuBar.add( mm );

		// Add a menu to select the texture boundary:

		Menu mb = new Menu( "Texture Boundary" );
		boundaryMenu = new CheckboxMenuItem[ boundaries.length ];
		for( int i = 0; i < boundaries.length; i++ )
		{
			boundaryMenu[i] = new CheckboxMenuItem( boundaries[i].name );
			boundaryMenu[i].addItemListener( this );
			boundaryMenu[i].setState( false );
			mb.add( boundaryMenu[i] );
		}
		exampleMenuBar.add( mb );

		// Add a menu to select the filter mode:

		Menu mf = new Menu( "Filter Mode" );
		filterMenu = new CheckboxMenuItem[ filters.length ];
		for( int i = 0; i < filters.length; i++ )
		{
			filterMenu[i] = new CheckboxMenuItem( filters[i].name );
			filterMenu[i].addItemListener( this );
			filterMenu[i].setState( false );
			mf.add( filterMenu[i] );
		}
		exampleMenuBar.add( mf );

		// Add a menu to select the texture transformation:

		Menu mx = new Menu( "Transformation" );
		xformMenu = new CheckboxMenuItem[ 3 ];

		xformMenu[0] = new CheckboxMenuItem( "Identity" );
		xformMenu[0].addItemListener( this );
		xformMenu[0].setState( true );
		mx.add( xformMenu[0] );

		xformMenu[1] = new CheckboxMenuItem( "Rotate 45" );
		xformMenu[1].addItemListener( this );
		xformMenu[1].setState( false );
		mx.add( xformMenu[1] );

		xformMenu[2] = new CheckboxMenuItem( "Scale 2" );
		xformMenu[2].addItemListener( this );
		xformMenu[2].setState( false );
		mx.add( xformMenu[2] );

		exampleMenuBar.add( mx );


		// set the current values:

		currentTexture = 0;
		textureMenu[currentTexture].setState( true );

		currentMode = 3;
		modeMenu[currentMode].setState( true );

		currentBoundary = 0;
		boundaryMenu[currentBoundary].setState( true );

		currentFilter = 0;
		filterMenu[currentFilter].setState( true );


		// Preload the texture images
		//   Use the texture loading utility to read in the texture
		//   files and process them into an ImageComponent2D
		//   for use in the Background node.

		if( debug )
			System.err.println( "Loading textures..." );
		textureComponents = new Texture2D[images.length];
		texLoader = new TextureLoader[images.length];
		String value = null;
		for( int i = 0; i < images.length; i++ )
		{
			value = (String)images[i].value;
			if( debug )
				System.err.println( "Loading texture '" + value + "'" );
			texLoader[i] = new TextureLoader( value, this);
			ImageComponent2D ic = texLoader[i].getImage();
			if( ic == null )
			{
				System.err.println( "Cannot load texture '" + value + "'" );
				textureComponents[i] = null;
			}
			else
			{
				Texture2D t = (Texture2D) texLoader[i].getTexture();
				t.setBoundaryModeS( ((Integer)boundaries[currentBoundary].value).intValue() );
				t.setBoundaryModeT( ((Integer)boundaries[currentBoundary].value).intValue() );
				t.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
				t.setMagFilter( ((Integer)filters[currentFilter].value).intValue() );
				t.setMinFilter( ((Integer)filters[currentFilter].value).intValue() );
				t.setMipMapMode( Texture.BASE_LEVEL );
				t.setCapability( Texture.ALLOW_BOUNDARY_COLOR_READ );
				t.setCapability( Texture.ALLOW_BOUNDARY_MODE_READ );
				t.setCapability( Texture.ALLOW_ENABLE_READ );
				t.setCapability( Texture.ALLOW_FILTER_READ );
				t.setCapability( Texture.ALLOW_IMAGE_READ );
				t.setCapability( Texture.ALLOW_MIPMAP_MODE_READ );
				t.setCapability( Texture.ALLOW_ENABLE_WRITE );
				t.setEnable( true );
				textureComponents[i] = t;
			}
		}

		tex = textureComponents[ currentTexture ];

	}


	//
	//  Handle checkboxes
	//
	public void itemStateChanged( ItemEvent event )
	{
		Object src = event.getSource();

		// Check if it is an image choice
		for( int i = 0; i < textureMenu.length; i++ )
		{
			if( src == textureMenu[i] )
			{
				// Update the checkboxes
				textureMenu[currentTexture].setState( false );
				currentTexture = i;
				textureMenu[currentTexture].setState( true );

				// Set the texture
				sh.setAppearance( dummyApp );
				tex = textureComponents[currentTexture];
				app.setTexture( tex );
				sh.setAppearance( app );
				return;
			}
		}


		// Check if it is a mode choice
		for( int i = 0; i < modeMenu.length; i++ )
		{
			if( src == modeMenu[i] )
			{
				// Update the checkboxes
				modeMenu[currentMode].setState( false );
				currentMode = i;
				modeMenu[currentMode].setState( true );

				// Set the mode

				app.setTextureAttributes( dummyAtt );
				texatt.setTextureMode( ((Integer)modes[currentMode].value).intValue() );
				app.setTextureAttributes( texatt );
				return;
			}
		}

		// Check if it is a boundary choice
		for( int i = 0; i < boundaryMenu.length; i++ )
		{
			if( src == boundaryMenu[i] )
			{
				// Update the checkboxes
				boundaryMenu[currentBoundary].setState( false );
				currentBoundary = i;
				boundaryMenu[currentBoundary].setState( true );

				// Set the boundary
				String value = (String)images[currentTexture].value;
				if( debug )
					System.err.println( "Re-loading texture '" + value + "'" );
				TextureLoader tl = new TextureLoader( value, this );
				Texture2D t = (Texture2D) tl.getTexture();
				t.setBoundaryModeS( ((Integer)boundaries[currentBoundary].value).intValue() );
				t.setBoundaryModeT( ((Integer)boundaries[currentBoundary].value).intValue() );
				t.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
				t.setMagFilter( ((Integer)filters[currentFilter].value).intValue() );
				t.setMinFilter( ((Integer)filters[currentFilter].value).intValue() );
				t.setMipMapMode( Texture.BASE_LEVEL );
				t.setCapability( Texture.ALLOW_ENABLE_WRITE );
				t.setEnable( true );
				app.setTexture( t );
				tex = textureComponents[currentTexture] = t;
				if( debug )
					System.err.println( "Ready." );
				return;
			}
		}

		// Check if it is a filter choice
		for( int i = 0; i < filterMenu.length; i++ )
		{
			if( src == filterMenu[i] )
			{
				// Update the checkboxes
				filterMenu[currentFilter].setState( false );
				currentFilter = i;
				filterMenu[currentFilter].setState( true );

				// Set the filter
				String value = (String)images[currentTexture].value;
				if( debug )
					System.err.println( "Re-loading texture '" + value + "'" );
				TextureLoader tl = new TextureLoader( value, this );
				Texture2D t = (Texture2D) tl.getTexture();
				t.setBoundaryModeS( ((Integer)boundaries[currentBoundary].value).intValue() );
				t.setBoundaryModeT( ((Integer)boundaries[currentBoundary].value).intValue() );
				t.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
				t.setMagFilter( ((Integer)filters[currentFilter].value).intValue() );
				t.setMinFilter( ((Integer)filters[currentFilter].value).intValue() );
				t.setMipMapMode( Texture.BASE_LEVEL );
				t.setCapability( Texture.ALLOW_ENABLE_WRITE );
				t.setEnable( true );
				app.setTexture( t );
				tex = textureComponents[currentTexture] = t;
				if( debug )
					System.err.println( "Ready." );
				return;
			}
		}

		// Check if it is a texture transform choice
		for( int i = 0; i < 3; i++ )
		{
			if( src == xformMenu[0] )
			{
				tt = new Transform3D( );
				tt.setIdentity();
				texatt.setTextureTransform( tt );	
				xformMenu[0].setState( true );
				xformMenu[1].setState( false );
				xformMenu[2].setState( false );
				if( debug )
					System.err.println( "Ready." );
				return;
			}
			else if( src == xformMenu[1] )
			{
				tt = new Transform3D( );
				tt.setIdentity();
				tt.rotZ( Math.PI/4. );
				texatt.setTextureTransform( tt );	
				xformMenu[0].setState( false );
				xformMenu[1].setState( true );
				xformMenu[2].setState( false );
				if( debug )
					System.err.println( "Ready." );
				return;
			}
			else if( src == xformMenu[2] )
			{
				tt = new Transform3D( );
				tt.setIdentity();
				tt.setScale( 2. );
				texatt.setTextureTransform( tt );	
				xformMenu[0].setState( false );
				xformMenu[1].setState( false );
				xformMenu[2].setState( true );
				if( debug )
					System.err.println( "Ready." );
				return;
			}
		}

		// Handle all other checkboxes
		super.itemStateChanged( event );
	}


	public void buildGeometry()
	{
		QuadArray cube = new QuadArray(
					24,
					GeometryArray.COORDINATES |
					GeometryArray.NORMALS     |
					GeometryArray.TEXTURE_COORDINATE_2 );
		cube.setCapability( GeometryArray.ALLOW_COORDINATE_WRITE );
		cube.setCapability( GeometryArray.ALLOW_TEXCOORD_WRITE );

		VertexList vl = new VertexList( cube );

		float MAX =  1.0f;
		float MIN = -1.0f;

		vl.xyzijkst( -1.0f, -1.0f,  1.0f,	 0.0f,  0.0f,  1.0f,	MIN, MIN );
		vl.xyzijkst(  1.0f, -1.0f,  1.0f,	 0.0f,  0.0f,  1.0f,	MAX, MIN );
		vl.xyzijkst(  1.0f,  1.0f,  1.0f,	 0.0f,  0.0f,  1.0f,	MAX, MAX );
		vl.xyzijkst( -1.0f,  1.0f,  1.0f,	 0.0f,  0.0f,  1.0f,	MIN, MAX );
 
		vl.xyzijkst( -1.0f, -1.0f, -1.0f,	 0.0f,  0.0f, -1.0f,	MIN, MIN );
		vl.xyzijkst(  1.0f, -1.0f, -1.0f,	 0.0f,  0.0f, -1.0f,	MAX, MIN );
		vl.xyzijkst(  1.0f,  1.0f, -1.0f,	 0.0f,  0.0f, -1.0f,	MAX, MAX );
		vl.xyzijkst( -1.0f,  1.0f, -1.0f,	 0.0f,  0.0f, -1.0f,	MIN, MAX );

		vl.xyzijkst(  1.0f, -1.0f, -1.0f,	 1.0f,  0.0f,  0.0f,	MIN, MIN );
		vl.xyzijkst(  1.0f,  1.0f, -1.0f,	 1.0f,  0.0f,  0.0f,	MAX, MIN );
		vl.xyzijkst(  1.0f,  1.0f,  1.0f,	 1.0f,  0.0f,  0.0f,	MAX, MAX );
		vl.xyzijkst(  1.0f, -1.0f,  1.0f,	 1.0f,  0.0f,  0.0f,	MIN, MAX );

		vl.xyzijkst( -1.0f, -1.0f, -1.0f,	-1.0f,  0.0f,  0.0f,	MIN, MIN );
		vl.xyzijkst( -1.0f,  1.0f, -1.0f,	-1.0f,  0.0f,  0.0f,	MAX, MIN );
		vl.xyzijkst( -1.0f,  1.0f,  1.0f,	-1.0f,  0.0f,  0.0f,	MAX, MAX );
		vl.xyzijkst( -1.0f, -1.0f,  1.0f,	-1.0f,  0.0f,  0.0f,	MIN, MAX );

		vl.xyzijkst( -1.0f,  1.0f, -1.0f,	 0.0f,  1.0f,  0.0f,	MIN, MIN );
		vl.xyzijkst(  1.0f,  1.0f, -1.0f,	 0.0f,  1.0f,  0.0f,	MAX, MIN );
		vl.xyzijkst(  1.0f,  1.0f,  1.0f,	 0.0f,  1.0f,  0.0f,	MAX, MAX );
		vl.xyzijkst( -1.0f,  1.0f,  1.0f,	 0.0f,  1.0f,  0.0f,	MIN, MAX );

		vl.xyzijkst( -1.0f, -1.0f, -1.0f,	 0.0f, -1.0f,  0.0f,	MIN, MIN );
		vl.xyzijkst(  1.0f, -1.0f, -1.0f,	 0.0f, -1.0f,  0.0f,	MAX, MIN );
		vl.xyzijkst(  1.0f, -1.0f,  1.0f,	 0.0f, -1.0f,  0.0f,	MAX, MAX );
		vl.xyzijkst( -1.0f, -1.0f,  1.0f,	 0.0f, -1.0f,  0.0f,	MIN, MAX );

		geom = cube;
	}


	public class
	VertexList
	{
		private int index = 0;
		private GeometryArray ga = null;

		public VertexList( GeometryArray _ga )
		{
			index = 0;
			ga = _ga;
		}

		public void xyzst( float x, float y, float z, float s, float t )
		{
			ga.setCoordinate( index, new Point3f( x, y, z ) );
			ga.setTextureCoordinate( index, new Point2f( s, t ) );
			index++;
		}

		public void xyzijkst(	float x, float y, float z,
					float i, float j, float k,
					float s, float t )
		{
			ga.setCoordinate( index, new Point3f( x, y, z ) );
			ga.setNormal( index, new Vector3f( i, j, k ) );
			ga.setTextureCoordinate( index, new Point2f( s, t ) );
			index++;
		}
	}

}
