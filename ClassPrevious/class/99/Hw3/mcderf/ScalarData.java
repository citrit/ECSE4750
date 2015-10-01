// Frank McDermott
// ACG - Spring 1999

import java.util.Vector;

public class ScalarData extends ParentObject {


    float min = 0;
    float max = 0;

    Vector data;
    int curr = 0;

    public ScalarData() {
	super();
	data = null;
    }

    public void setData(Vector v) {
	data = v;
	float currVal;
	for(int i = 0; i< data.size();i++) {
	    currVal = ((Float)data.elementAt(i)).floatValue();
	    if (max < currVal) {
		max = currVal;
	    }
	    if (min > currVal) {
		min = currVal;
	    }
	}
	//	System.out.println("max : " + max + "  min " + min);
	updateTime();
    }
    
    protected float getValue(int i) {
	if (data != null) {
	    if ((i>0) && (i<data.size())) {
		return ((Float)data.elementAt(i)).floatValue();
	    }
	}
	return 0;
    }

    protected float getNextValue() {
	float val;
	if (data !=null) {
	    if ((curr>=0)&&(curr<data.size())) {
		val = ((Float)data.elementAt(curr)).floatValue();
		curr++;
	    } else {
		val = Float.NaN;
	    }
	    return val;
	}
	return 0;
    }
    
    public void resetPos() {
	curr = 0;
    }

    protected float[] mapScalarToColor(float s) {
	float rgb[] = new float[3];
	float adjMin = 0, adjMax = max + Math.abs(min);   // set range to be from 0 to (min + max) instead of min to max
	float adjScalar = s + Math.abs(min);

	float ratio = adjScalar / adjMax;

	return rgb;
    }    
}	
