//
//
// oglrtest
//
//
import java.awt.*;
import java.awt.event.*;
import jogl.*;

class FaceObjectWindow extends Frame
{
	OGLRenderer renderer;
	Actor    act;

	public static void main(String argv[])
	{

		FaceObject actor = new FaceObject();
		OGLRenderer aren = new OGLRenderer();
		// Add this  DataSet to the Renderers collection.
		aren.addActor(actor);
		aren.addCamera(new OGLCamera());
		aren.addLight(new OGLLight(0));


		FaceObjectWindow hw2 = new FaceObjectWindow();
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


}