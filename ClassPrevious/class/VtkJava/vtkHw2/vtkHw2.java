import vtk.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class vtkHw2 extends Frame implements KeyListener
{

	vtkActor [] Acts;
	int objMode;
	vtkPanel renPanel;

	public vtkHw2(String title)
	{ 
		super(title);
		objMode = 0;
		Acts = new vtkActor[3];
	}

	public static void main (String[] args)
	{
	  //this constructor must go all the way to 
	  // addNotify must be called on a Frame before you attempt
	  // to add a vtkPanel to it, otherwise the peer information
	  // doesn't get constructed, causing nasty seg-faults in GetWindowID
	  vtkHw2 hw2 = new vtkHw2("vtkHw2");
	  hw2.addNotify();
	
	  String jvmVendor = java.lang.System.getProperty("java.vendor");
      String jvmVersion = java.lang.System.getProperty("java.version");

	  System.out.println("jvm vendor: "+jvmVendor);
	  System.out.println("jvm version: "+jvmVersion);

	  if (jvmVendor.startsWith("Microsoft",0)) {
		hw2.renPanel = new vtkMSPanel();
	  }
	  else {
		hw2.renPanel = new vtkPanel();
	  }
	  hw2.renPanel.setSize(400,400);
	  hw2.renPanel.addKeyListener(hw2);
	  hw2.removeAll();
	  hw2.add(hw2.renPanel);
	  vtkRenderer ren1 = hw2.renPanel.getRenderer();
	
	  if (args.length > 0) {
		  hw2.readFile(args[0], ren1);
	  }

	  ren1.SetBackground(0.5,0.5,0.5); // Background color grey
	  
	  hw2.pack();
	  hw2.show();
	}
	
	public void readFile(String fileName, vtkRenderer aren) 
	{
		objectReader objR;
		vtkPolyData pData = null;
		vtkPoints Pnts = null;
		int type, cnt;
		double []rgb = new double[3];
		double []vals = new double[3];
		int i,j;
		
		try {
			if(fileName.length() == 0) throw new IllegalArgumentException();
			objR = new objectReader(fileName);
			while (!objR.eof()) {
				type = objR.getInt(); // get the type
				cnt = objR.getInt();  // get the number of things comming
				for (i=0;i<3;i++) 
					rgb[i] = objR.getDouble(); // read the rgb
				System.out.println("Object: " +type+", "+cnt+", "+rgb[0]);
				switch (type) {
				case 0: // read in coordinates
					Pnts = new vtkPoints(); // Create a current PointSet
					for (i=0;i<cnt;i++) { // for all points
						for (j=0;j<3;j++) // read in three vals
							vals[j] = objR.getDouble();
						Pnts.InsertNextPoint(vals); // add a point to the set
					}
					break; // Simply ignore the rgb vals
				case 1: // Create a PointCell
				case 2: // Create a LineCell
				case 3: // Create a PolygonCell
				case 4: // Create a TriangleCell
				case 5: // Create a PolylineCell
					vtkActor act = this.parseObjectFromFile(objR, type, cnt, Pnts);
					act.Print();
					act.GetProperty().SetColor(rgb[0], rgb[1], rgb[2]);
					aren.AddActor(act);
					break;
				}
			};
		}catch (IllegalArgumentException iErr) {
			System.out.println(iErr);
		}
	}
	
	/** Parse the object type from the file
	 */
	vtkActor parseObjectFromFile(objectReader objR, int type, int cnt, vtkPoints pnts)
	{
		int i,j;
		vtkCellArray curCell = null;
		vtkActor pAct = new vtkActor();
		vtkPolyData pData = new vtkPolyData();
		pData.SetPoints(pnts);
		vtkPolyDataMapper pMap = new vtkPolyDataMapper();
		pMap.SetInput(pData);
		pAct.SetMapper(pMap);
		switch (type) {
			case 1: // Create a PointCell
				curCell = new vtkCellArray();
				pData.SetVerts(curCell);
				curCell.InsertNextCell(cnt);
				for (i=0;i<cnt;i++) { // for all points
					curCell.InsertCellPoint(objR.getInt());
				}
				break;
			case 2: // Create a LineCell
				curCell = new vtkCellArray();
				pData.SetLines(curCell);
				for (i=0;i<cnt;i++) { // for all points
					curCell.InsertNextCell(2);
					curCell.InsertCellPoint(objR.getInt());
					curCell.InsertCellPoint(objR.getInt());
				}
				break;
			case 3: // Create a PolygonCell
				curCell = new vtkCellArray();
				pData.SetPolys(curCell);
				for (i=0;i<cnt;i++) { // for all polygons
					curCell.InsertNextCell(1);
					int recnt = 0;
					while ((j = objR.getInt()) != -1) { // get subsequent polygons
						curCell.InsertCellPoint(j);
						recnt++;
					}
					curCell.UpdateCellCount(recnt);
				}
				break;
			case 4: // Create a TriangleCell
				curCell = new vtkCellArray();
				pData.SetPolys(curCell);
				for (i=0;i<cnt;i++) { // for all triangles
					curCell.InsertNextCell(3);
					curCell.InsertCellPoint(objR.getInt()); // grab three points 
					curCell.InsertCellPoint(objR.getInt()); // for each triangle
					curCell.InsertCellPoint(objR.getInt()); 
				}
				break;
			case 5: // Create a PolylineCell
				curCell = new vtkCellArray();
				pData.SetLines(curCell);
				for (i=0;i<cnt;i++) { // for all polygons
					curCell.InsertNextCell(1);
					int recnt = 0;
					while ((j = objR.getInt()) != -1) { // get subsequent polygons
						curCell.InsertCellPoint(j);
						recnt++;
					}
					curCell.UpdateCellCount(recnt);
				}
				break;
		}			
		return pAct;
	}
	
	// Handle the keystrokes and modify appropriate object
	public void keyTyped(KeyEvent e) {

		switch (e.getKeyChar()) {
		case '0':
			break;
		}
		renPanel.Render();
		
	}
	public void keyPressed(KeyEvent e) {

	}
	public void keyReleased(KeyEvent e) {

	}

}

