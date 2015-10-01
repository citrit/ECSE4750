// Frank McDermott
// ACG - Spring 1999
// Homework 3

import java.awt.*;
import java.awt.event.*;
import jogl.*;
import java.util.*;

public class Hw3 extends Frame implements ActionListener {
    OGLRenderer renderer;
    StructuredGrid grid;
    ScalarData scalars;
    
    public static void main(String argv[]) {
	int num;

	Hw3 hw3 = new Hw3();

	int w = 0, h = 0;
	
	objectReader reader;
	
	OGLRenderer aren = new OGLRenderer();
	StructuredGrid myGrid = new StructuredGrid();
	ScalarData myScalars = new ScalarData();
	
	String filename = argv[0];
	if (filename == "") {
	    myScalars = null;
	} else {
	    reader = new objectReader(filename);
	    w = reader.getInt();
	    h = reader.getInt();
	    num = 0;

	    Vector data = new Vector();
	    
	    myGrid.setSize(w,h);

	    while(!reader.eof()) {
		data.addElement(new Float(reader.getFloat()));
		num++;
	    }
	    myScalars.setData(data);
	}
	    
	myGrid.setData(myScalars);
       	myGrid.setStartPoint(new PointType(-w/2, -h/2, 0.0F)); 
	aren.addActor(myGrid);
	myGrid.displayWireFrame();
	aren.addCamera(new OGLCamera());
	aren.addLight(new OGLLight(0));
	
	hw3.renderer = aren;
	hw3.grid = myGrid;
	hw3.scalars = myScalars;
	
	// Make it visible and set size
	aren.setVisible(true);
	aren.setSize(500, 500);
	aren.getCamera().setFrom(0.0F, 0.0F, 25.0F);

	// Add the canvas to the frame and make it show
	hw3.add("Center", aren);
	hw3.pack();
	
	// Set up the menu
	MenuBar mb = new MenuBar();
	Menu file = new Menu( "File" );
	file.add( new MenuItem( "Run" ));
	mb.add( file );
	hw3.setMenuBar( mb );
	
	hw3.show();
	aren.getCamera().zoom(-50.0F);
    }
    
    public void  actionPerformed(ActionEvent e) {
	if (e.getSource() instanceof MenuItem) {
	    // Since we didn't save references to each of the menu objects,
	    // we check which one was pressed by comparing labels.
	    if (((MenuItem)e.getSource()).getLabel() == "Run") {
		for (int i = 0;i<360;i++) {
		    grid.rotateX(1);
		    renderer.render();
		}
	    } else {System.out.println("try again sucka!!");}
	}
	System.out.println("Event: " + e );
    }
}
