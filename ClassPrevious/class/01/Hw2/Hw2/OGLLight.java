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

import jogl.*;

/**
  * Light class to handle light positions.
  */
public class OGLLight extends Light
{

	/** Constructor */
	public OGLLight(int num) { super(num); }

	/** Called by the Renderer to initialize the light */
	public void render(Renderer aren)
	{
		JoglCanvas gl = ((OGLRenderer)aren).gl;
		int lightnum = 0;

		float lightPos[] = {from.x, from.y, from.z, 0.0F};
		float lightDir[] = {to.x, to.y, to.z, 0.0F};

		gl.use();
		if (on == true) {
		    switch (number) {
				case 0: lightnum = GL.LIGHT0; break;
				case 1: lightnum = GL.LIGHT1; break;
				case 2: lightnum = GL.LIGHT2; break;
				case 3: lightnum = GL.LIGHT3; break;
				case 4: lightnum = GL.LIGHT4; break;
				case 5: lightnum = GL.LIGHT5; break;
				case 6: lightnum = GL.LIGHT6; break;
				case 7: lightnum = GL.LIGHT7; break;
			}
			gl.enable(GL.LIGHTING);
			gl.enable(lightnum);
			gl.light(lightnum, GL.POSITION, lightPos);
			gl.light(lightnum, GL.SPOT_DIRECTION, lightDir);
			if (color != null) {
				//gl.light(lightnum, GL.AMBIENT, color.ambient);
				gl.light(lightnum, GL.DIFFUSE, color.diffuse);
				gl.light(lightnum, GL.SPECULAR, color.ambient);
			}
		}
		else {
			gl.disable(GL.LIGHTING);
		}
	}
}

