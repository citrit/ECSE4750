//
//
// STLReader
//
//
import java.awt.*;
import java.awt.event.*;

class STLReader extends Actor
{

	String FileName;
	
	public STLReader(String fileName)
	{
		FileName = new String(fileName);
		
	}
	
	public void render(Renderer aren)
	{
		if (cells.isEmpty() == true) {
			this.readFile();
		}
		else {
			super.render(aren);
		}
	}
	
	public void readFile()
	{
		// Topology
		TriangleCell triCell;

		// Geometry
		PointSet ptSet;

		// Color
		MaterialSet matSet;

		// Some helper structures
		int type, cnt, i, j;
		float vals[] = new float[3];
		float rgba[] = new float[4];


		// Open up a reader
		objectReader obr = new objectReader(this.FileName);
		ptSet = new PointSet(); // Create a current PointSet
		matSet = new MaterialSet();
		rgba[0] = 0.8F;rgba[1] = 0.8F;rgba[2] = 0.0F;rgba[3] = 1.0F;
		matSet.addMaterial(new Material(rgba));
		String inStr;
		String vrtxStr = new String("VERTEX");
		String endlStr = new String("ENDLOOP");
		String normStr = new String("NORMAL");

		triCell = null;
		System.out.println("Begin reading: " + this.FileName);
		do {
		    inStr = obr.getString().toUpperCase();
		    if (inStr.compareTo(vrtxStr) == 0) {
				for (j=0;j<3;j++) // read in three vals
					vals[j] = obr.getFloat();
				ptSet.addPoint(new PointType(vals)); // add a point to the set
				//System.out.print("Reading pt: " + ptSet.size() + "\r");
		    }
		    else if (inStr.compareTo(endlStr) == 0) {
				int sz = ptSet.size();
				triCell.addVal(sz-3); // grab three points 
				triCell.addVal(sz-2); // for each triangle
				triCell.addVal(sz-1); 
				//System.out.print("Adding Tri: " + this.cells.size() + "\r");
		    }
		    else if (inStr.compareTo(normStr) == 0) {
				triCell = new TriangleCell();
				triCell.setMaterials(matSet);
				triCell.setPoints(ptSet);
				this.addCell(triCell);
				for (j=0;j<3;j++) // read in three vals
					vals[j] = obr.getFloat();
				triCell.setNormal(new PointType(vals)); // add a normal to the cell
			}
		} while (! obr.eof());
		System.out.println("End reading: " + this.FileName);
	}
}
