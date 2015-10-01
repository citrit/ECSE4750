//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    January 1998
//
/////////////////////////////////////////////////////////////////////////

import java.util.Vector;
import java.lang.Float;

public class ScalarData extends ParentObject
{
	Vector Scalars;
	float MinMax[];

	public ScalarData() {
		Scalars = new Vector();
		MinMax = new float[2];
		MinMax[0] = Float.MAX_VALUE;
		MinMax[1] = Float.MIN_VALUE;
    }

    public void addScalar(float scalarvalue) {
		Scalars.addElement(new Float(scalarvalue));
		MinMax[1] = (MinMax[1]>scalarvalue?MinMax[1]:scalarvalue);
		MinMax[0] = (MinMax[0]<scalarvalue?MinMax[0]:scalarvalue);		
		updateTime();
    }

	public float getScalar(int at)
	{
		Float tmp;
		float scalarvalue;

		tmp = (Float)Scalars.elementAt(at);
		scalarvalue = tmp.floatValue();
			
		return scalarvalue; 
	}

	
	public Material getMaterial(float data)
	{
		float rgb[] = new float[3];
		float normData;
		Material mtr = new Material();			

		normData = normalize(data);

		if(normData <= 0.5F)
		{
			rgb[1] = (0.5F-normData)/0.5F;
			rgb[2] = normData/0.5F;
			rgb[0] = 0.0F;
		}
		else
		{
			rgb[1] = 0.0F;
			rgb[2] = (1.0F-normData)/0.5F;
			rgb[0] = (normData-0.5F)/0.5F;
		}	
		mtr.setAmbient(rgb[0],rgb[1],rgb[2],1.0F);			
		mtr.setDiffuse(0.5F,0.5F,0.5F,1.0F);
		mtr.setSpecular(1.0F,1.0F,1.0F,1.0F);

		return mtr;
	}	

	public float normalize(float data)
	{
		float norm;
		float range;
	
		range = MinMax[1] - MinMax[0];
		norm = (data - MinMax[0])/range;
		
		return norm;
	}

}

