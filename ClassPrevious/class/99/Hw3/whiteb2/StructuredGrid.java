// Bryan Whitehead Hw3
import java.util.Vector;

public class StructuredGrid extends Actor
{
	int SizeX,SizeY;
	PointType StartPoint;
	float XSpacing,YSpacing;
	PointSet GridPoints;
	ScalarData DataValues;
	Vector ContourValues;
	Vector WireFrameCells;
	Vector PlaneCells;
	Vector LineCells;
	int displaymode;

	public StructuredGrid() {
		SizeX=0;SizeY=0;
		StartPoint=new PointType();
		GridPoints=null;
		DataValues=null;
		ContourValues=null;
		WireFrameCells=new Vector();
		PlaneCells=new Vector();
		LineCells=new Vector();
		XSpacing=1;YSpacing=1;	
		displaymode=1;
		updateTime();	
	}

	public void SetSize(int w,int h) {
		SizeX=w;SizeY=h;
		updateTime();
	}
	
	public void SetStartPoint(PointType pt) {
		StartPoint=pt;
		updateTime();
	}

	public void SetDeltas(float deltax,float deltay) {
		XSpacing=deltax;
		YSpacing=deltay;
		updateTime();
	}

	public void SetPoints(PointSet pts) {
		GridPoints=pts;
		updateTime();
	}

	public void SetData(ScalarData data) {
		DataValues=data;
		updateTime();
	}

	public void DisplayWireFrame(Renderer Rend) {
		int num;
		for(num=0;num<WireFrameCells.size();num++)
			((Cell)WireFrameCells.elementAt(num)).render(Rend);
	}

	public void DisplayPlane(Renderer Rend) {
		int num;
		for(num=0;num<PlaneCells.size();num++)
			((Cell)PlaneCells.elementAt(num)).render(Rend);
	}

	public void DisplayContours(Renderer Rend) {
		int num;
		for(num=0;num<LineCells.size();num++)
			((Cell)LineCells.elementAt(num)).render(Rend);
	}

	public void SetContours(Vector vec) {
		ContourValues=vec;
		updateTime();
	}
	
	// Creates Contours with Line Cells
	public void Contour() {
		int ContourNumber;
		PointSet ContourPointSet;
		MaterialSet matset;
		int x,y,pt[]=new int[4];
		int indexmask[]={1,2,4,8};
		// defines the points of the four edges
		int edges[][]={{0,1},{1,2},{2,3},{3,0}};
		int edgecases[][]=
			{{-1,-1,-1,-1},
			 { 0, 3,-1,-1},
			 { 0, 1,-1,-1},
			 { 1, 3,-1,-1},
			 { 1, 2,-1,-1},
			 { 0, 1, 2, 3},
			 { 0, 2,-1,-1},
			 { 2, 3,-1,-1},
			 { 2, 3,-1,-1},
			 { 0, 2,-1,-1},
			 { 1, 2, 0, 3},
			 { 1, 2,-1,-1},
			 { 1, 3,-1,-1},
			 { 0, 1,-1,-1},
			 { 0, 3,-1,-1},
			 {-1,-1,-1,-1}};
		int index;
		int num,point1,point2;
		float ContourValue;
		float Xcoord,Ycoord;
		float point[]=new float[3];
		int pointcounter=0;
		float scale,value1,value2,cvalue1,cvalue2;
		LineCell Line;
		ContourPointSet=new PointSet();
		if(ContourValues!=null) {
		for(ContourNumber=0;ContourNumber<ContourValues.size();
			ContourNumber++) {
			ContourValue=((Float)ContourValues.elementAt(ContourNumber)).floatValue();
			for (y=0;y<(SizeY-1);y++)
			for (x=0;x<(SizeX-1);x++) {
				pt[0]=(x+0)+(y+0)*SizeX;
				pt[1]=(x+1)+(y+0)*SizeX;
				pt[2]=(x+1)+(y+1)*SizeX;
				pt[3]=(x+0)+(y+1)*SizeX;
				index=0;
				for(num=0;num<4;num++) {
					if(DataValues.GetValue(pt[num])>=ContourValue)
						index|=indexmask[num];
				}
				if(edgecases[index][0]>-1) {
					Line=new LineCell();
					matset=new MaterialSet();
					matset.addMaterial(DataValues.getMaterialColor(ContourValue));
					Line.setMaterials(matset);
					point1=pt[edges[edgecases[index][0]][0]];
					point2=pt[edges[edgecases[index][0]][1]];
					value1=DataValues.GetValue(point1);
					value2=DataValues.GetValue(point2);
					scale=(value1-ContourValue)/(value1-value2);
					cvalue1=(GridPoints.getPoint(point1)).x;
					cvalue2=(GridPoints.getPoint(point2)).x;
					Xcoord=cvalue1+scale*(cvalue2-cvalue1);
					cvalue1=(GridPoints.getPoint(point1)).y;
					cvalue2=(GridPoints.getPoint(point2)).y;
					Ycoord=cvalue1+scale*(cvalue2-cvalue1);
					point[0]=Xcoord;
					point[1]=Ycoord;
					point[2]=ContourValue;
					ContourPointSet.addPoint(new PointType(point));
					Line.addVal(pointcounter);
					pointcounter++;					
					
					point1=pt[edges[edgecases[index][1]][0]];
					point2=pt[edges[edgecases[index][1]][1]];
					value1=DataValues.GetValue(point1);
					value2=DataValues.GetValue(point2);
					scale=(value1-ContourValue)/(value1-value2);
					cvalue1=(GridPoints.getPoint(point1)).x;
					cvalue2=(GridPoints.getPoint(point2)).x;
					Xcoord=cvalue1+scale*(cvalue2-cvalue1);
					cvalue1=(GridPoints.getPoint(point1)).y;
					cvalue2=(GridPoints.getPoint(point2)).y;
					Ycoord=cvalue1+scale*(cvalue2-cvalue1);
					point[0]=Xcoord;
					point[1]=Ycoord;
					point[2]=ContourValue;
					ContourPointSet.addPoint(new PointType(point));
					Line.addVal(pointcounter);
					pointcounter++;
										
					Line.setPoints(ContourPointSet);
					LineCells.addElement(Line);
				}
					
				if(edgecases[index][2]>-1) {
					Line=new LineCell();
					matset=new MaterialSet();
					matset.addMaterial(DataValues.getMaterialColor(ContourValue));
					Line.setMaterials(matset);
					point1=pt[edges[edgecases[index][2]][0]];
					point2=pt[edges[edgecases[index][2]][1]];
					value1=DataValues.GetValue(point1);
					value2=DataValues.GetValue(point2);
					scale=(value1-ContourValue)/(value1-value2);
					cvalue1=(GridPoints.getPoint(point1)).x;
					cvalue2=(GridPoints.getPoint(point2)).x;
					Xcoord=cvalue1+scale*(cvalue2-cvalue1);
					cvalue1=(GridPoints.getPoint(point1)).y;
					cvalue2=(GridPoints.getPoint(point2)).y;
					Ycoord=cvalue1+scale*(cvalue2-cvalue1);
					point[0]=Xcoord;
					point[1]=Ycoord;
					point[2]=ContourValue;
					ContourPointSet.addPoint(new PointType(point));
					Line.addVal(pointcounter);
					pointcounter++;					
					
					point1=pt[edges[edgecases[index][3]][0]];
					point2=pt[edges[edgecases[index][3]][1]];
					value1=DataValues.GetValue(point1);
					value2=DataValues.GetValue(point2);
					scale=(value1-ContourValue)/(value1-value2);
					cvalue1=(GridPoints.getPoint(point1)).x;
					cvalue2=(GridPoints.getPoint(point2)).x;
					Xcoord=cvalue1+scale*(cvalue2-cvalue1);
					cvalue1=(GridPoints.getPoint(point1)).y;
					cvalue2=(GridPoints.getPoint(point2)).y;
					Ycoord=cvalue1+scale*(cvalue2-cvalue1);
					point[0]=Xcoord;
					point[1]=Ycoord;
					point[2]=ContourValue;
					ContourPointSet.addPoint(new PointType(point));
					Line.addVal(pointcounter);
					pointcounter++;
					
					Line.setPoints(ContourPointSet);
					LineCells.addElement(Line);
				}
			}			
		}
		}
	}
	
	// Creates Wire Frames with Polyline Cells
	public void CreateWireFrame() {
		PolylineCell Polyline;
		MaterialSet matset;
		int x,y,pt1,pt2,pt3,pt4;
		float Average;
		for(y=0;y<(SizeY-1);y++) {
			for(x=0;x<(SizeX-1);x++) {
				// get points for Polyline Cell
				pt1=(x+0)+(y+0)*SizeX;
				pt2=(x+1)+(y+0)*SizeX;
				pt3=(x+1)+(y+1)*SizeX;
				pt4=(x+0)+(y+1)*SizeX;
				// Create Polyline cell
				Polyline=new PolylineCell();
				Polyline.addVal(pt1);
				Polyline.addVal(pt2);
				Polyline.addVal(pt3);
				Polyline.addVal(pt4);
				Polyline.addVal(pt1);
				Average=(DataValues.GetValue(pt1)+
					 DataValues.GetValue(pt2)+
					 DataValues.GetValue(pt3)+
					 DataValues.GetValue(pt4))/4;
				matset=new MaterialSet();
				matset.addMaterial(DataValues.getMaterialColor(Average));
				Polyline.setMaterials(matset);
				Polyline.setPoints(GridPoints);
				WireFrameCells.addElement(Polyline);				  
			}
		}
	}
	
	// Creates Planes with Polygon Cells
	public void CreatePlanes() {
		PolygonCell Polygon;
		MaterialSet matset;
		int x,y,pt1,pt2,pt3,pt4;
		float Average;
		for(y=0;y<(SizeY-1);y++) {
			for(x=0;x<(SizeX-1);x++) {
				// get points for Polygon Cell
				pt1=(x+0)+(y+0)*SizeX;
				pt2=(x+1)+(y+0)*SizeX;
				pt3=(x+1)+(y+1)*SizeX;
				pt4=(x+0)+(y+1)*SizeX;
				// Create Polygon cell
				Polygon=new PolygonCell();
				Polygon.addVal(pt1);
				Polygon.addVal(pt2);
				Polygon.addVal(pt3);
				Polygon.addVal(pt4);
				Average=(DataValues.GetValue(pt1)+
					 DataValues.GetValue(pt2)+
					 DataValues.GetValue(pt3)+
					 DataValues.GetValue(pt4))/4;
				matset=new MaterialSet();
				matset.addMaterial(DataValues.getMaterialColor(Average));
				Polygon.setMaterials(matset);
				Polygon.setPoints(GridPoints);
				PlaneCells.addElement(Polygon);
			}
		}
	}

	public void render(Renderer Rend) {
		if(DataValues!=null) {
			if (PlaneCells.size() == 0)
				CreatePlanes();
			if (WireFrameCells.size() == 0)
				CreateWireFrame();
			if (LineCells.size() == 0)
				Contour();
			switch(displaymode) {
			case 1:
				DisplayWireFrame(Rend);
				break;
			case 2:
				DisplayPlane(Rend);
				break;
			case 3:
				DisplayContours(Rend);
				break;
			}
		}				
	}

	public void SetMode(int m) {
		displaymode=m;
	}

	public void SetGridPoints() {
		int X,Y;
		PointType pt;
		GridPoints=new PointSet();
		for(Y=0;Y<SizeY;Y++)
		for(X=0;X<SizeX;X++) {
			pt=new PointType();
			pt.x=StartPoint.x+X*XSpacing;
			pt.y=StartPoint.y+Y*YSpacing;
			pt.z=DataValues.GetValue(X+Y*SizeX);
			GridPoints.addPoint(pt);
		}
	}
}