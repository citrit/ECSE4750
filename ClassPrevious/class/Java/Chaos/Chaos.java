//
//
// GL4Jrtest
//
//
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class Chaos extends Frame implements ActionListener
{
	GL4JRenderer renderer;
	Actor    act;

	public static void main(String argv[])
	{

		PointSet myPts = new PointSet();
		PointCell pCell = new PointCell();
		Material mat;
		MaterialSet matSet;
		Actor actor = new Actor();
		GL4JRenderer aren = new GL4JRenderer();
		PointType pt[] = {new PointType(),new PointType(),new PointType()};
		Random rand = new Random();
		float tmp[] = new float[3];
		int ranPt, curPt;

		matSet = new MaterialSet();
		matSet.addMaterial(new Material());
		pCell.setMaterials(matSet);
		pCell.setPoints(myPts);

		if (argv.length < 1) {
			System.out.println("error: usage: java Chaos NumPoints");
			System.exit(1);
		}
		pt[1].x = 1;
		pt[2].x = 0.5F;pt[2].y = 1;
		curPt = Integer.parseInt(new String(argv[0]));
		System.out.print(argv[0] + " " + curPt);
		tmp[0] = tmp[1] = tmp[2] = 0;
		rand.setSeed(1);
		for (int i=0;i<curPt;i++) {
			ranPt = Math.abs(rand.nextInt()%3);
			tmp[0] = tmp[0] + ((pt[ranPt].x - tmp[0])/2.0F);
			tmp[1] = tmp[1] + ((pt[ranPt].y - tmp[1])/2.0F);
			tmp[2] = tmp[2] + ((pt[ranPt].z - tmp[2])/2.0F);
			myPts.addPoint(new PointType(tmp));
			pCell.addVal(i);
		}


		// Add Cells to the DataSet
		actor.addCell(pCell);
		actor.translate(-0.5F, -0.5F, 0.0F);
		// Add this  DataSet to the Renderers collection.
		aren.addActor(actor);
		aren.addCamera(new GL4JCamera());
		aren.addLight(new GL4JLight(0));


		Chaos Chaos = new Chaos();
		Chaos.renderer = aren;
		Chaos.act = actor;
		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(300, 300);
		aren.getCamera().setFrom(0.0F, 0.0F, 5.0F);
		System.out.println("Here we go");

		// Add the canvas to the frame and make it show
  		Chaos.add("Center", aren);
		Chaos.pack();

		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		file.add( new MenuItem( "Run" ));
		mb.add( file );
		Chaos.setMenuBar( mb );

		Chaos.show();
	}

	/** Let capture menu events (and all others...) */
	public void actionPerformed(ActionEvent evt) 
	{
		if (evt.getActionCommand().equals("Run")) {
			for (int i = 0;i<360;i++) {
				act.rotateX(1);
				renderer.render(true);
			}
		}
		System.out.println("Event: " + evt);
	}
}