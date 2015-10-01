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
		ambient = new float[4];
		diffuse = new float[4];
		specular = new float[4];
		this.setAmbient(1.0F, 0.0F, 0.0F, 1);
		this.setDiffuse(1.0F, 0.7F, 0.7F, 1);
		this.setSpecular(1.0F, 0.0F, 0.0F, 1);
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
		setAmbient(r,g,b,a);
		setDiffuse(r,g,b,a);
		setSpecular(r,g,b,a);
	}
	public Material(float rgba[])
	{
		this(rgba[0], rgba[1], rgba[2], rgba[3]);
	}

	/** Set the Ambient component of a Material */
	public void setAmbient(float r, float g, float b, float a)
	{
		ambient[0] = r;
		ambient[1] = g;
		ambient[2] = b;
		ambient[3] = a;
	}
	/** Set the Diffuse component of a Material */
	public void setDiffuse(float r, float g, float b, float a)
	{
		diffuse[0] = r;
		diffuse[1] = g;
		diffuse[2] = b;
		diffuse[3] = a;
	}
	/** Set the Specular component of a Material */
	public void setSpecular(float r, float g, float b, float a)
	{
		specular[0] = r;
		specular[1] = g;
		specular[2] = b;
		specular[3] = a;
	}
}

