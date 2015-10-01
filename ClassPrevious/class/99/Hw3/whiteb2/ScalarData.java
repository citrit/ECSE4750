
// Bryan Whitehead HW3

import java.util.Vector;

public class ScalarData extends ParentObject
{
	float minimum,maximum;
	Vector scalar_values;

	public ScalarData() 
	{
		scalar_values = new Vector();
		minimum = Float.MAX_VALUE;
		maximum = Float.MIN_VALUE;
	}

	public float GetMinimum() { return minimum; }
	public float GetMaximum() { return maximum; }

	public void NewValue(float val)
	{
		if (val<minimum) minimum=val;
		if (val>maximum) maximum=val;
		scalar_values.addElement(new Float(val));
		updateTime();
	}

	public float GetValue(int i)
	{
		return ((Float)scalar_values.elementAt(i)).floatValue(); 
	}
	
	public Material getMaterialColor(float value) 
	{
		Material mat;
		float red,green,blue,alpha;
		float scale;
		
		scale=(value-minimum)/(maximum-minimum);
		if (scale < 0) scale = 0;
		if (scale > 1) scale = 1;
		
		if (scale<0.5) {
			red=1-2*scale;
			green=2*scale;
			blue=0;
		}
		else {
			red=0;
			green=2-2*scale;
			blue=-1+2*scale;
		}
		alpha=0;
		mat=new Material();
		mat.setAmbient(red,green,blue,alpha);
		mat.setDiffuse(red,green,blue,alpha);
		mat.setSpecular(red,green,blue,alpha);
		return mat;
	}
}