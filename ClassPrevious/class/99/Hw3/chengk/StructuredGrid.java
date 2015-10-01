//Kent Cheng
import java.util.Vector;
import jogl.*;
import jogl.glu.*;

public class StructuredGrid extends Actor
{

	Vector contourI;  //holds contour interval
        
	public StructuredGrid() {  
		contourI = null;
			
	}

	public void SetData(ScalarData data) {
		int i, j;
		float point[] = new float[3];
		PolygonCell pgoncell=null;
		PolylineCell plinecell=null;

		datamap = data;
		if (gcells.size() == 0){  //if the grid is not set, set it
			   for (i=0;i<dimX*dimY;i++)  //get colors
				gmat.addMaterial(datamap.lookup(datamap.getScalar(i)));
		           for (j=0;j<dimY;j++) 
				for (i=0;i<dimX;i++) {
					point[0] = begin.x+i*deltaX;
					point[1] = begin.y+j*deltaY;
					setpt.addPoint(new PointType(point));
				}
  		           makegrid(plinecell,pgoncell);	
		}
		updateTime();
	}

	public void SetStartPoint(PointType pt) {
		this.begin = pt;
		updateTime();
	}	

	public void SetPoints(PointSet pts) {
		setpt = pts;
		updateTime();
	}

	public void SetSize(int w, int h) {
		dimX = w;
		dimY = h;
		updateTime();
	} 


	public void SetDeltas(float deltax, float deltay) {
		deltaX = deltax;
		deltaY = deltay;
		updateTime();
	}


	public void DisplayWireframe() {
		draw = 'w';
	}

	public void DisplayPlane() {
		draw = 's';
	}

	public void DisplayContours() {
		Contour();
	}

	public void SetContours(Vector vec) {
		contourI = vec;
		updateTime();
	}

	
	public void Contour() 
	{       
  		/*i don't fully understand the marching square algorithm, so i don't 
		  really know how to implement it.  I tried to follow the vtk version
		  and implement it but I got stuck quickly and don't know what to do*/
     		int check,y;
		int pt[]=new int[4];
		int indexmask[]={1,2,4,8};
		int i,num,index;
		MaterialSet mtset;
		float cval;			
		Material mat;
		int total = (this.dimX-1)*(this.dimY-1);
		int lineCases[][] = { 
		{-1, -1, -1, -1, -1},
		{0, 3, -1, -1, -1},
		{1, 0, -1, -1, -1},
		{1, 3, -1, -1, -1},
		{2, 1, -1, -1, -1},
		{0, 3, 2, 1, -1},
		{2, 0, -1, -1, -1},
		{2, 3, -1, -1, -1},
		{3, 2, -1, -1, -1},
		{0, 2, -1, -1, -1},
		{1, 0, 3, 2, -1},
		{1, 2, -1, -1, -1},
		{3, 1, -1, -1, -1},
		{0, 1, -1, -1, -1},
		{3, 0, -1, -1, -1},
		{-1, -1, -1, -1, -1}
		};
		int edges[][]={{0,1},{1,2},{2,3},{3,0}};
		
        	for (i=0;i<contourI.size();i++) {  
			cval = ((Float)contourI.elementAt(i)).floatValue();
			for(y=0,check=0;y<total;y++,check++){
				if(check==dimX-1){y++;check=0;}		    
				pt[0]=y;pt[1]=pt[0]+1;pt[2]=pt[1]+dimX;pt[3]=pt[0]+dimX;
				index=0;
				for(num=0;num<4;num++) {
					if(datamap.getScalar(pt[num])>=cval)
						index|=indexmask[num];
				}
				
			}
		}
		System.out.println("Not working!");
	}

	
	

}









