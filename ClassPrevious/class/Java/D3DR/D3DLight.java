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


/**
  * Light class to handle light positions.
  */
public class D3DLight extends Light
{

	/** Constructor */
	public D3DLight(int num) { super(num); }

	/** Called by the Renderer to initialize the light */
	public void render(Renderer aren)
	{
		int lightnum = 0;

		float lightPos[] = {from.x, from.y, from.z, 0.0F};
		float lightDir[] = {to.x, to.y, to.z, 0.0F};

		if (on == true) {

		}
	}
}

