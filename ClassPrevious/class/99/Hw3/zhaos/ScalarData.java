//////////////////////////////////////////////////////////////////////////
//
//  Name: Shuo Zhao
//  Homework 3
//  ScalarData.java
//////////////////////////////////////////////////////////////////////////

//package oglr;
import java.util.Vector;

public class ScalarData extends ParentObject
{
	
	float color_range[];	
	float scalar_range[];
	Vector Val;

	/** Constructor */
	public ScalarData() 
	{
		scalar_range = new float[2];
		color_range = new float[2];
		Val = new Vector();
	}

	public void setColorRange(float range[]) 
	{
		color_range = range;
	}
	public float[] getColorRange() 
	{ 
		return color_range; 
	}
	
	public float getScalar(int i)
	{
		return ((Float)Val.elementAt(i)).floatValue(); 
	}

	/** Return the scalar range */
	public float[] getScalarRange() 
	{ 
		return scalar_range; 
	}

	/** Add a scalar value */
	public void addScalar(float val)
	{
		if(scalar_range[0]>val)
			scalar_range[0] = val;
		if(scalar_range[1]<val)
			scalar_range[1] = val;

		Val.addElement(new Float(val));
		updateTime();
	}


	/** Looks up a color value based on a scalar */
	public Material colorValue(float val)
	{
		Material mat;
		float rgba[] = new float[4], normv;        
		mat = new Material();
		normv = (val - color_range[0]) / (color_range[1] - color_range[0]);
						//normalize color range
		if (normv < 0.0)      //if normv is outside of range, then we set it within 
			normv = 0.0F;     		
		if (normv > 1.0) 
   	       		normv = 1.0F;  //color scheme: linear scale between red and green 
		if (normv >= 0.0 && normv <= 0.5)
		{ 
	  		rgba[0] = 1 - 2*normv;
	  		rgba[1] = 2*normv;
	  		rgba[2] = 0.0F;
		}
		else
		{           //color scheme: linear scale between green and blue
	       		rgba[0] = 0.0F;
	       		rgba[1] = 2 - 2*normv;
               		rgba[2] = -1 + 2*normv;
	   	}
		rgba[3] = 1.0F;
		mat.setAmbient(rgba[0], rgba[1], rgba[2], rgba[3]);
		mat.setDiffuse(rgba[0], rgba[1], rgba[2], rgba[3]);
		mat.setSpecular(rgba[0], rgba[1], rgba[2], rgba[3]);
		return mat;    
	}
	
}

