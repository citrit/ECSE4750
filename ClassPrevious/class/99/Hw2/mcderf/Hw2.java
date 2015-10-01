//
//
// oglrtest
//
//
import java.awt.*;
import java.awt.event.*;
import jogl.*;
import java.util.*;

public class Hw2 extends Frame implements ActionListener {
    OGLRenderer renderer;
    Actor    act;
    
    public static void main(String argv[]) {
	
	int objType;
	int numCoords;
	
	objectReader ord;
	ord = new objectReader(argv[0]);
	
	Vector myPnts = new Vector();
	PointSet pnts;
	PointCell pnt = new PointCell();
	LineCell line = new LineCell();
	PolygonCell polygon = new PolygonCell();
	TriangleCell triangle = new TriangleCell();
	PolyLineCell polyline = new PolyLineCell();
	
	Material mat;
	MaterialSet matSet = new MaterialSet();
	Actor actor = new Actor();
	
	Hw2 hw2 = new Hw2();
	
	/*  For some strange reason I was unable to determine, javac didn't like this next
	    Line.  Don't know why */
	//	hw2.addActionListener(hw2);
	
	OGLRenderer aren = new OGLRenderer();
	float tmp[] = new float[3];
	float rgba[];
	
	while (!ord.eof()) {
	    objType = ord.getInt();
	    switch (objType) {
	    case 0:
		pnts = new PointSet();
		rgba = hw2.readCoords(ord, pnts);
		myPnts.addElement(pnts);
		//	    matSet.addMaterial(new Material(rgba[0], rgba[1],rgba[2],rgba[3]));
		break;
	    case 1:
		pnt = new PointCell();
		hw2.loadPoint(ord, pnt, matSet);
		pnt.setPoints((PointSet)myPnts.lastElement());
		pnt.setMaterials(matSet);
		actor.addCell(pnt);
		break;
	    case 2:
		line = new LineCell();
		hw2.loadLine(ord, line, matSet);
		line.setPoints((PointSet)myPnts.lastElement());
		line.setMaterials(matSet);
		actor.addCell(line);
		break;
	    case 3:
		polygon = new PolygonCell();
		hw2.loadPolygon(ord,  polygon, matSet);
		polygon.setPoints((PointSet)myPnts.lastElement());
		polygon.setMaterials(matSet);
		actor.addCell(polygon);
		break;
	    case 4:
		triangle = new TriangleCell();
		hw2.loadTriangle(ord, triangle, matSet);
		triangle.setPoints((PointSet)myPnts.lastElement());
		triangle.setMaterials(matSet);
		actor.addCell(triangle);
		break;
	    case 5:
		polyline = new PolyLineCell();
		hw2.loadPolyLine(ord, polyline, matSet);
		polyline.setPoints((PointSet)myPnts.lastElement());
		polyline.setMaterials(matSet);
		actor.addCell(polyline);
		break;
	    case -2: //eof or other error
		break;
	    default: System.out.println("Object of type " + objType + " not supported!");
		break;
	    }
	}
	
	// Add this  DataSet to the Renderers collection.
	//    float blah[] = {0.2F, 0.2F, 0.2F};
	//    actor.scale(blah);
	aren.addActor(actor);
	aren.addCamera(new OGLCamera());
	aren.addLight(new OGLLight(0));
	
	hw2.renderer = aren;
	hw2.act = actor;
	
	// Make it visible and set size
	aren.setVisible(true);
	aren.setSize(500, 500);
	aren.getCamera().setFrom(0.0F, 0.0F, 5.0F);
	//    System.out.println("Here we go");
	
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
    
    public void  actionPerformed(ActionEvent e) {
	if (e.getSource() instanceof MenuItem) {
	    // Since we didn't save references to each of the menu objects,
	    // we check which one was pressed by comparing labels.
	    if (((MenuItem)e.getSource()).getLabel() == "Run") {
		for (int i = 0;i<360;i++) {
		    act.rotateX(1);
		    renderer.render();
		}
	    } else {System.out.println("try again sucka!!");}
	}
	System.out.println("Event: " + e );
    }
    
    private float[] readCoords(objectReader o, PointSet p) {
	int num = o.getInt();
	float rgba[] = new float[4];
	float tmp[] = new float[3];
	
	rgba[0] = o.getFloat();
	rgba[1] = o.getFloat();
	rgba[2] = o.getFloat();
	rgba[3] = 1.0F;
	
	for (int i = 0; i < num; i++) {
	    tmp[0] = o.getFloat();
	    tmp[1] = o.getFloat();
	    tmp[2] = o.getFloat();
	    p.addPoint(new PointType(tmp));
	}
	return rgba;
    }

    private void loadPoint(objectReader o, PointCell pc, MaterialSet ms) {
	int num = o.getInt();
	int val;
	float[] rgba = new float[4];
	rgba[0] = o.getFloat();
	rgba[1] = o.getFloat();
	rgba[2] = o.getFloat();
	rgba[3] = 1.0F;

	ms.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
	for(int i=0;i<num;i++) {
	    val = o.getInt();
	    pc.addVal(val);
	}
    }

    private void loadLine(objectReader o, LineCell lc, MaterialSet ms) {
	int num = o.getInt();
	int pnt1, pnt2;

	float[] rgba = new float[4];
	rgba[0] = o.getFloat();
	rgba[1] = o.getFloat();
	rgba[2] = o.getFloat();
	rgba[3] = 1.0F;

	ms.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
	for(int i=0;i<num;i++) {
	    pnt1 = o.getInt();
	    pnt2 = o.getInt();
	    lc.addVal(pnt1);
	    lc.addVal(pnt2);
	}
    }

    private void loadPolygon(objectReader o, PolygonCell pc, MaterialSet ms) {
	int num = o.getInt();
	int pnt;
	
	float[] rgba = new float[4];
	rgba[0] = o.getFloat();
	rgba[1] = o.getFloat();
	rgba[2] = o.getFloat();
	rgba[3] = 1.0F;

	ms.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
	for (int i = 0; i < num; i++ ) {
	    pnt = o.getInt();
	    while (pnt != -1) {
		pc.addVal(pnt);
		pnt = o.getInt();
	    }
	}
    }

    private void loadTriangle(objectReader o, TriangleCell t, MaterialSet ms) {
	int num = o.getInt();
	int p1, p2, p3;
	
	float[] rgba = new float[4];
	rgba[0] = o.getFloat();
	rgba[1] = o.getFloat();
	rgba[2] = o.getFloat();
	rgba[3] = 1.0F;
	
	ms.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
	for (int i = 0; i < num; i++) {
	    p1 = o.getInt();
	    p2 = o.getInt();
	    p3 = o.getInt();
	    t.addVal(p1);
	    t.addVal(p2);
	    t.addVal(p3);
	}
    }

    private void loadPolyLine(objectReader o, PolyLineCell pl, MaterialSet ms) {
	int num = o.getInt();
	int pnt;
	
	float[] rgba = new float[4];
	rgba[0] = o.getFloat();
	rgba[1] = o.getFloat();
	rgba[2] = o.getFloat();
	rgba[3] = 1.0F;
	ms.addMaterial(new Material(rgba[0],rgba[1],rgba[2],rgba[3]));
	for (int i = 0; i < num; i++ ) {
	    pnt = o.getInt();
	    while (pnt != -1) {
		pl.addVal(pnt);
		pnt = o.getInt();
	    }
	}
    }
}
