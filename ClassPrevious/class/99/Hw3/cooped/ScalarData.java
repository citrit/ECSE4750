//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Dave Cooper     cooped@rpi.edu
//  Class:   Advanced Computer Graphics and Data Visualization
//           Rensselaer Polytechnic Institute
//  Date:    March 1999
//
/////////////////////////////////////////////////////////////////////////

import java.util.Vector;

public class ScalarData{

	Vector scalarVector;
	float BBox[];			//Bounding box created as points are added

	/////////////////////////////////////////
	// function: ScalarData
	// purpose: holds scalar data values as
	//				well as calculates color
	//				values for mapped colors
	/////////////////////////////////////////
	public ScalarData(){
		scalarVector = new Vector();
		BBox = new float[2];
		BBox[0] = Float.MAX_VALUE;
		BBox[1] = -Float.MAX_VALUE;
	}

	/////////////////////////////////////////
	//
	/////////////////////////////////////////
	public void addPoint(float aScalarValue){
		scalarVector.add(new Float(aScalarValue));					//adds scalar to the vector as an object
		BBox[0] = (BBox[0]<aScalarValue?BBox[0]:aScalarValue);  	//seeks min
		BBox[1] = (BBox[1]>aScalarValue?BBox[1]:aScalarValue);	//seeks max
	}

	/////////////////////////////////////////
	// function: getScalar
	// purpose: returns the scalar at the
	//				specified index
	/////////////////////////////////////////
	public float getScalar(int index){
		if(!(index > scalarVector.size()))
			return ((Float)scalarVector.elementAt(index)).floatValue();		//returns float value of scalar
		else
			return 0.0F;
	}

	/////////////////////////////////////////
	// function: getMaterial
	//
	/////////////////////////////////////////
   public Material getMaterial(float scalarValue){
		float r,g,b,a;
		float normScalar;
		Material mt;
		r=g=b=a=1.0F;

		if(scalarValue != 0.0F){
			normScalar = ((scalarValue - BBox[0])/(BBox[1] - BBox[0]));   //normalized scalar value
			r = (float)(Math.sin(Math.PI * normScalar));
			g = (float)(Math.sin((Math.PI * normScalar) + Math.PI/2));
			b = (float)(Math.sin((Math.PI * normScalar) - Math.PI/2));
			if(r < 0) r=0; if(g < 0) g=0; if(b < 0) b=0;
		}
		mt = new Material(r,g,b,a);
      return mt;
	}

	/////////////////////////////////////////
	// function: size
	// purpose: returns the size of the vector
	/////////////////////////////////////////
	public int size(){
		return scalarVector.size();
	}

	/////////////////////////////////////////
	// function: getBBox
	// purpose: returns the bounding box
	/////////////////////////////////////////
	public float[] getBBox() { 
		return BBox; 
	}


}
