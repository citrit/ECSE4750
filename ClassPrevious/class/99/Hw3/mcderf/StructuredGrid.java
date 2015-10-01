// Frank McDermott
// ACG - Spring 1999


import java.util.Vector;

public class StructuredGrid extends Actor {

    protected int dx = 1;
    protected int dy = 1;
    protected int h = 0;
    protected int w = 0;
    protected PointType origin;
    protected PointSet ps;
    protected Vector contours;

    protected ScalarData scalars;

    protected Vector gridCells = null, planeCells = null, contourCells = null;
    
    public StructuredGrid () {
	super();
	origin = new PointType();
	ps = null;

	contours = new Vector();
    }

    public void setSize(int nw, int nh) {
	h = nh;
	w = nw;
    }

    public void setStartPoint(PointType o) {
	origin = o;
    }

    public void setDeltas(int ndx, int ndy) {
	dx = ndx; dy = ndy;
    }

    public void setPoints(PointSet nps) {
	ps = nps;
    }

    public void setData(ScalarData data) {
	scalars = data;
    }

    public void displayWireFrame() {
	PointSet pnts;
	LineCell line;
	
	MaterialSet matSet = new MaterialSet();
	matSet.addMaterial(new Material(1.0F, 1.0F, 1.0F, 1.0F));
	
	if (ps == null) {
	    pnts = genPnts();
	    ps = pnts;
	} else {
	    pnts = ps;
	}

	if (gridCells == null) {
	    gridCells = new Vector();
	    
	    for (int i = 0; i < h; i++) {
		for (int j = 0; j < w-1; j++) {
		    line = new LineCell();
		    line.addVal(j + (i*h));
		    line.addVal(j+1 + (i*h));
		    line.setMaterials(matSet);
		    line.setPoints(pnts);
		    gridCells.addElement(line);
		    this.addCell(line);
		}
	    }
	    for (int i=0;i<h-1;i++) {
		for(int j=0;j<w;j++) {
		    line = new LineCell();
		    line.addVal(j + (i*w));
		    line.addVal(j + ((i+1) * w));
		    line.setMaterials(matSet);
		    line.setPoints(pnts);
		    gridCells.addElement(line);
		    this.addCell(line);
		}
	    }
	} else {
	    this.clearCells();
	    for(int i=0;i<gridCells.size();i++) {
		this.addCell((LineCell)gridCells.elementAt(i));
	    }
	}
    }
    
    public void displayPlane() {
	PointSet pnts;
	PolygonCell pg;

	int row = 0;
	
	MaterialSet matSet = new MaterialSet();
	matSet.addMaterial(new Material(1.0F, 1.0F, 1.0F, 1.0F));
	
	if (ps == null) {
	    pnts = genPnts();
	    ps = pnts;
	} else {
	    pnts = ps;
	}

	if (planeCells == null) {
	    planeCells = new Vector();
	    for(int i=0;i<h-1;i++) {
		for(int j=0;j<w-1;j++) {
		    pg = new PolygonCell();
		    pg.addVal((row+w)+j);
		    pg.addVal((row+w)+j+1);
		    pg.addVal(row+j+1);
		    pg.addVal(row+j);
		    pg.setMaterials(matSet);
		    pg.setPoints(pnts);
		    planeCells.addElement(pg);
		    this.addCell(pg);
		}
		row+=w;
	    }
	} else {
	    this.clearCells();
	    for(int i=0;i<planeCells.size();i++) {
		this.addCell((PolygonCell)planeCells.elementAt(i));
	    }
	}
    }
    
    public void displayContours() {
	System.out.println("displaying contours");
	
    }

    public void setContours(Vector c) {
	contours = c;
    }

    public void Contour() {
	System.out.println("creating contours");
    }

    public PointSet genPnts() {
	PointSet pnts = new PointSet();
	float tmp[] = new float[3];
	
	for (int i = (int)origin.x;i<origin.x+w;i+=dx) {
	    for (int j = (int)origin.y;j<origin.y+h;j+=dy) {
		tmp[0] = i;
		tmp[1] = j;
		tmp[2] = 0;
		pnts.addPoint(new PointType(tmp));
	    }
	}

	return pnts;
    }

    protected void clearCells() {
	this.cells.removeAllElements();
    }
}
