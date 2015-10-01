//Kent Cheng
import java.util.Vector;

public class ScalarData extends ParentObject
{

	/** Holds the bounds of the scalar values [min, max] */
	float minmax[];
	/** Holds the float scalars */
	Vector nums;

	
	public ScalarData() 
	{
		minmax = new float[2];
		nums = new Vector();
		minmax[0] = Float.MAX_VALUE;
		minmax[1] = -Float.MAX_VALUE;
                
	}

	
        public void addScalar(float val)
	{
                 
                if(minmax[0] > val)  //keeps track of min and max value   
                   minmax[0] = val;  //holds the min value of boundary
                if(minmax[1] < val)
                   minmax[1] = val;  //holds the max value of boundary
                nums.addElement(new Float(val));
		updateTime();
                //System.out.println("minmax[0]="+minmax[0]+" minmax[1]="+minmax[1]);
	}

	
	public float getScalar(int i)
	{
		return ((Float)nums.elementAt(i)).floatValue(); 
                        //looks up the float stored at index i in Vector nums
	}

    
    public float[] getRange() { return minmax; }

    public Material lookup(float val) {
		Material mat;
		float rgba[] = new float[4], normv;
                
                mat = new Material();
		normv = (val - minmax[0]) / (minmax[1] - minmax[0]);
                         //normalize color range                
		if (normv < 0.0)      //if normv is outside of range, then we set it within 
                    normv = 0.0F;     //range
		if (normv > 1.0) 
                    normv = 1.0F;
                                //color scheme: linear scale between red and green      
                if (normv >= 0.0 && normv <= 0.5){  
                    rgba[0] = 1 - 2*normv;
                    rgba[1] = 2*normv;
                    rgba[2] = 0.0F;
                }
                else{           //color scheme: linear scale between green and blue
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

