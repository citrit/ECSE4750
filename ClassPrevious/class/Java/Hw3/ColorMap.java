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
//////////////////////////////////////////////////////////////////////////

//package oglr;

/**
  * Class to hold the colorMap generator */
public class ColorMap extends ParentObject
{

	/** Range to use */
	float bBox[];

	/** Constructor */
	public ColorMap() {
	}

	/** Sets/Gets the range for the ColorMap */
	public void setRange(float range[]) 
	{
		bBox = range;
	}
	public float[] getRange() { return bBox; }

	/** Looks up a color value based on a scalar */
    public Material lookupValue(float val) {


		Material mat;
		float rgba[] = new float[4], normv;

		mat = new Material();
		normv = (float)(val - bBox[0]) / (float)(bBox[1] - bBox[0]);
		if (normv < 0.0) normv = 0.0F;
		if (normv > 1.0) normv = 1.0F;
		rgba[0] = normv*0.8F;
		rgba[1] = ((normv<0.5F)?(2*normv):((1.0F-normv)*2));
		rgba[2] = 0.8F - normv;
		rgba[3] = 1.0F;
		mat.setAmbient(rgba);
		mat.setDiffuse(rgba);
		mat.setSpecular(rgba);
		return mat;
    }

}

