//
//
// oglrtest
//
//
import java.awt.*;
import java.awt.event.*;
import jogl.*;

class Hw2 extends Frame implements ActionListener
{
	OGLRenderer renderer;
	Actor    act;

	public Hw2() {
		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		file.add( new MenuItem( "Run" ));
		mb.add( file );
		file.addActionListener(this);
		this.setMenuBar( mb );
	}
		
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
		rgba[0] = rgba[1] = 0.0F;rgba[2] = 1.0F;
		matSet.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
		tmp[0] = 0.0F;tmp[1] = 0.0F;tmp[2] = 0.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 1.0F;tmp[1] = 0.0F;tmp[2] = 0.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 1.0F;tmp[1] = 1.0F;tmp[2] = 0.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 0.0F;tmp[1] = 1.0F;tmp[2] = 0.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 0.0F;tmp[1] = 0.0F;tmp[2] = 1.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 1.0F;tmp[1] = 0.0F;tmp[2] = 1.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 1.0F;tmp[1] = 1.0F;tmp[2] = 1.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 0.0F;tmp[1] = 1.0F;tmp[2] = 1.0F; myPts.addPoint(new PointType(tmp));
		tmp[0] = 0.5F;tmp[1] = 0.5F;tmp[2] = 0.5F; myPts.addPoint(new PointType(tmp));

		// Draw a Triangle with lines
		lCell.addVal(0); lCell.addVal(1);lCell.addVal(1); lCell.addVal(2);
		lCell.addVal(2); lCell.addVal(0);
		lCell.setPoints(myPts);
		lCell.setMaterials(matSet);
		// Draw a Solid Triangle
		pCell.addVal(0); pCell.addVal(1); pCell.addVal(8);// pCell.addVal(0);
		pCell.setPoints(myPts);
		pCell.setMaterials(matSet);
		// Draw a Square
		sCell.addVal(4); sCell.addVal(5); sCell.addVal(5); sCell.addVal(6);
		sCell.addVal(6); sCell.addVal(7); sCell.addVal(7); sCell.addVal(4);
		sCell.setMaterials(matSet);
		sCell.setPoints(myPts);
		// Add Cells to the DataSet
		actor.addCell(lCell);
		actor.addCell(pCell);
		actor.addCell(sCell);
		// Add this  DataSet to the Renderers collection.
		aren.addActor(actor);
		aren.addCamera(new OGLCamera());
		aren.addLight(new OGLLight(0));


		Hw2 hw2 = new Hw2();
		hw2.renderer = aren;
		hw2.act = actor;
		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(300, 300);
		aren.getCamera().setFrom(0.0F, 0.0F, 5.0F);
		System.out.println("Here we go");

		// Add the canvas to the frame and make it show
  		hw2.add("Center", aren);
		hw2.pack();
		hw2.show();
	}

	public void actionPerformed(ActionEvent evt)
	{
		//if (evt.target instanceof MenuItem) {
			// Since we didn't save references to each of the menu objects,
			// we check which one was pressed by comparing labels.
			if (evt.getActionCommand().equals("Run")) {
				for (int i = 0;i<360;i++) {
					act.rotateX(1);
					renderer.render();
				}
			}
		//}
		System.out.println("Event: " + evt );
	}
}