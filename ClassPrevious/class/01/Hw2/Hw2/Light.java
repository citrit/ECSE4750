
//
//
// Light
//
//
public abstract class Light extends ParentObject
{
	protected boolean on;
	int number;
	PointType from, to;
	Material color;

	/**
	  * Constructor create an instance of a light, set it to on,
	  * color to null, from.z = 10.0
	  */
	public Light(int num)
	{
		number = num%8;
		on = true;
		from = new PointType(10, 10, 10);
		to = new PointType();
		color = new Material();
	}
	
	/** Convenience function to turn  the light on */
	public void setState(boolean onOrOff) { on = onOrOff; }
	
	/** Convenience function to turn  the light on */
	public void turnOn() { on = true; }

	/**
	  * convenience function to turn the light off
	  */
	public void turnOff() { on = false; }


	/** Set the color of the light */
	public void setMaterial(Material mat)
	{
		color = mat;
	}

	/** Called by the Renderer to initialize the light */
	public abstract void render(Renderer aren);

}

