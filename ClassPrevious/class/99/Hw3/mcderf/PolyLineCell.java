/**
  * PolyLineCell class is a concrete implementation of the Cell class
  * It draws lines between point in the internal array, i.e.
  *   [ 1 2 3 4 5 ] will draw a polygon thorugh the points and close 
  *   from 1 to 5.
  */
public class PolyLineCell extends Cell {
    
    /**
     * Nothing to do here (yet)
     */
    public PolyLineCell() { }
    
    /**
     * Sent by the Renderer to start the drawing process, kind of neat
     * in that the LineCell sends commands back to the Renderer
     * on how to draw itself (Remember the Renderer only has a vector
     * of cells).
     */
    public void render(Renderer aren) {
	//	System.out.println("rendering polygon cell" + intVals.size());
	int cnt;
	
	cnt = mtSet.size();
	aren.beginDraw(aren.POLYLINE);
	for (int i=0;i<intVals.size();i++) {
	    aren.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
	    aren.vertex(ptSet.getPoint(getVal(i))); 
	}
	aren.endDraw();
    }
}

