// Bryan Whitehead HW3

import java.awt.*;
import java.awt.event.*;
import jogl.*;
import java.util.Vector;

class Hw3 extends Frame
{
	StructuredGrid SG;
	OGLRenderer ren;
	
	public static void main(String argv[])
	{
		Hw3 hw3 = new Hw3();
		ScalarData DataValues;
		int SizeX,SizeY,X,Y;
		PointType StartPt=new PointType();
		PointType Pt;
		int num;
		float value;
		float min,max;
		Vector ContourValues;

		hw3.ren = new OGLRenderer();
		hw3.SG = new StructuredGrid();
		DataValues= new ScalarData();
		
		objectReader reader=new objectReader(argv[0]);
		SizeX=reader.getInt();
		SizeY=reader.getInt();
		hw3.SG.SetSize(SizeX,SizeY);
		
		StartPt.x=-SizeX/2;
		StartPt.y=-SizeY/2;
		hw3.SG.SetStartPoint(StartPt);
		
		for(num=0;num<(SizeX*SizeY);num++) {
			value=reader.getFloat();
			DataValues.NewValue(value);
		}
		hw3.SG.SetData(DataValues);
		hw3.SG.SetGridPoints();
		hw3.SG.SetMode(3);
		
		min=DataValues.GetMinimum();
		max=DataValues.GetMaximum();
		ContourValues=new Vector();
		for(value = min;value<max;value+=0.25F)
			ContourValues.addElement(new Float(value));
		hw3.SG.SetContours(ContourValues);

		hw3.ren.addActor(hw3.SG);
		hw3.ren.addCamera(new OGLCamera());
		hw3.ren.addLight(new OGLLight(0));

		hw3.ren.setVisible(true);
		hw3.ren.setSize(300,300);
		hw3.ren.getCamera().setFrom(0.0F,0.0F,50.0F);

		hw3.add("Center", hw3.ren);
		hw3.pack();
		
		MenuBar mb = new MenuBar();
		Menu ss = new Menu( "Select Style" );
		ss.add( new MenuItem( "Wireframe" ));
		ss.add( new MenuItem( "Plane" ));
		ss.add( new MenuItem( "Contour" ));
		mb.add( ss );
		hw3.setMenuBar( mb );

		System.out.println("Controls");
		System.out.println("X,x:Rotate around X axis");
		System.out.println("Y,y:Rotate around Y axis");
		System.out.println("Z,z:Rotate around Z axis");
		System.out.println("Escape: To close program");
		System.out.println();
		System.out.println("Use menu to select Style");

		hw3.show();
	}

	public boolean action(Event evt, Object what)
	{
		if (evt.target instanceof MenuItem) 
		{
			if (((String)evt.arg).equals("Wireframe")) {
				SG.SetMode(1);
				ren.render();
			}
			if (((String)evt.arg).equals("Plane")) {
				SG.SetMode(2);
				ren.render();
			}
			if (((String)evt.arg).equals("Contour")) {
				SG.SetMode(3);
				ren.render();
			}
		}
		return super.action(evt, what);
	}

}