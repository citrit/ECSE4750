//
//
// oglrtest
//
//
import java.awt.*;
import java.awt.event.*;
import jogl.*;
import java.io.*;
import java.util.*;

class Hw2 extends Frame
{
	OGLRenderer renderer;
	Actor    act;
	
	public static int getInt( String token )
	{
		return Integer.parseInt( token );
	};

	public static float getFloat( String token )
	{
		return new Float( token ).floatValue();
	};

	public static PointType getCoors( StringTokenizer tokens )
	{
		float point[] = new float[ 3 ];

		point[ 0 ] = getFloat( tokens.nextToken() );
		point[ 1 ] = getFloat( tokens.nextToken() );
		point[ 2 ] = getFloat( tokens.nextToken() );

		return new PointType( point );
	};

	public static void main(String argv[])
	{

		PointSet myPts = new PointSet();
		LineCell lCell = new LineCell();
		LineCell sCell = new LineCell();
		PolygonCell pCell = new PolygonCell();
		Material mat;
		MaterialSet matSet;
		Actor actor = new Actor();
		OGLRenderer aren = new OGLRenderer();
		float tmp[] = new float[3];
		float rgba[] = { 1.0F, 0.0F, 0.0F, 0.1F };

		mat = new Material(rgba[0],rgba[1],rgba[2],rgba[3]);
		matSet = new MaterialSet();
		matSet.addMaterial(mat);
		
		String buffer = new String();

		BufferedReader input = new BufferedReader(
		new FileReader(
		new FileInputStream( "example.dat").getFD() ));

		try
		{
			while( true )
			{
				buffer = input.readLine();
				StringTokenizer tokens = new
				StringTokenizer( buffer );

				int Type = getInt( tokens.nextToken() );
				int Length = getInt( tokens.nextToken() );
				float Red = getFloat( tokens.nextToken() );
				float Green = getFloat( tokens.nextToken() );
				float Blue = getFloat( tokens.nextToken() );

				buffer = input.readLine();
				tokens = new StringTokenizer( buffer );

				switch( Type )
				{
					case 0:
					PointType XYZpoints[] = new PointType[ Length ];

					for( int loop = 0; loop < Length; loop++ )
						XYZpoints[ loop ] = getCoors( tokens );
					break;

					case 1:
					int points[] = new int[Length ];

					for( int loop = 0; loop <Length; loop++ )
					points[ loop ] =getInt( tokens.nextToken() );
					break;

					case 2:
					int xLines[] = new int[Length ];
					int yLines[] = new int[Length ];

					for( int loop = 0; loop < Length; loop++ )
						{
						xLines[ loop ] =getInt( tokens.nextToken() );
						yLines[ loop ] =getInt( tokens.nextToken() );
						}
					break;

					case 3:
					for( int loop = 0; loop <Length; loop++ )
						{
						int point = getInt(tokens.nextToken() );
						//PolygonCell thePoly = new PolygonCell();

						while( point != -1 )
							{
							//thePoly.addVal( point );

							point = getInt( tokens.nextToken() );
							};

							//do something with polygon
							};
					break;

					case 4:
					for( int loop = 0; loop < Length; loop++ )
						{
							int p1 = getInt(tokens.nextToken() );
							int p2 = getInt(tokens.nextToken() );
							int p3 = getInt(tokens.nextToken() );

							Tri myTri = new Tri( p1, p2, p3 );
						};
					break;

					case 5:
					for( int loop = 0; loop < Length; loop++ )
						{
							int point = getInt(tokens.nextToken() );
							PolygonCell thePoly = new PolygonCell();

							while( point != -1 )
							{
							thePoly.addVal( point );

							point = getInt( tokens.nextToken() );
							};

							//do something with polyLine
						};
					break;
				}

				System.out.println( Type + " " + Length + "" + Red + " " + Green + " " + Blue + " " );
				};
			}
			catch( EOFException eof ){};
	};









		Hw2 hw2 = new Hw2();
		//hw2.renderer = aren;
		hw2.act = actor;
		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(300, 300);
		aren.getCamera().setFrom(0.0F, 0.0F, 5.0F);
		System.out.println("Here we go");

		// Add the canvas to the frame and make it show
  		hw2.add("Center", aren);
		hw2.pack();

		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		file.add( new MenuItem( "Run" ));
		mb.add( file );
		hw2.setMenuBar( mb );

		hw2.show();
	}

	public boolean action(Event evt, Object what)
	{
		if (evt.target instanceof MenuItem) {
			// Since we didn't save references to each of the menu objects,
			// we check which one was pressed by comparing labels.
			if (((String)evt.arg).equals("Run")) {
				for (int i = 0;i<360;i++) {
					act.rotateX(1);
					renderer.render();
				}
			}
		}
		System.out.println("Event: " + evt + " Object: " + what);
		return super.action(evt, what);
	}
}