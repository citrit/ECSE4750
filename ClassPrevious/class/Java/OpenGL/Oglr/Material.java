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


/**
  * Material class holds the color components as public arrays
  */
public class Material extends ParentObject
{

	/** Ambient component of color */
	public float[] ambient;
	/** Diffuse component of color */
	public float[] diffuse;
	/** Specular component of color */
	public float[] specular;

	/**
	  * Constructor to create the component arrays and set them to
	  * a safe value.
	  */
	public Material()
	{
		this(0.8F, 0.8F, 0.8F, 1.0F);
	}
	public Material(float rgba[])
	{
		this(rgba[0], rgba[1], rgba[2], rgba[3]);
	}
	/**
	  * Constructor to create the component arrays and set them to
	  * the specified values.
	  */
	public Material(float r, float g, float b, float a)
	{
		ambient = new float[4];
		diffuse = new float[4];
		specular = new float[4];
		this.setDiffuse(r,g,b,a);
		this.setAmbient(0.7F, 0.7F, 0.7F, 1.0F);
		this.setSpecular(0.4F, 0.4F, 0.4F, 1.0F);
	}

	/** Set the Ambient component of a Material */
	public void setAmbient(float r, float g, float b, float a)
	{
		ambient[0] = r;
		ambient[1] = g;
		ambient[2] = b;
		ambient[3] = a;
	}
	public void setAmbient(float rgba[])
	{
		setAmbient(rgba[0],rgba[1],rgba[2],rgba[3]);
	}
	/** Set the Diffuse component of a Material */
	public void setDiffuse(float r, float g, float b, float a)
	{
		diffuse[0] = r;
		diffuse[1] = g;
		diffuse[2] = b;
		diffuse[3] = a;
	}
	public void setDiffuse(float rgba[])
	{
		setDiffuse(rgba[0],rgba[1],rgba[2],rgba[3]);
	}
	/** Set the Specular component of a Material */
	public void setSpecular(float r, float g, float b, float a)
	{
		specular[0] = r;
		specular[1] = g;
		specular[2] = b;
		specular[3] = a;
	}
	public void setSpecular(float rgba[])
	{
		setSpecular(rgba[0],rgba[1],rgba[2],rgba[3]);
	}
}
