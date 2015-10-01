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

import gl4java.GLFunc;
import gl4java.awt.GLCanvas;

/**
  * Light class to handle light positions.
  */
public class GL4JLight extends Light
{
	GLFunc gl;

	/** Constructor */
	public GL4JLight(int num) { super(num); }

	/** Called by the Renderer to initialize the light */
	public void render(Renderer aren)
	{
		gl = ((GL4JRenderer)aren).gl;
		int lightnum = 0;

		float lightPos[] = {from.x, from.y, from.z, 0.0F};
		float lightDir[] = {to.x, to.y, to.z, 0.0F};

		if (on == true) {
		    switch (number) {
				case 0: lightnum = gl.GL_LIGHT0; break;
				case 1: lightnum = gl.GL_LIGHT1; break;
				case 2: lightnum = gl.GL_LIGHT2; break;
				case 3: lightnum = gl.GL_LIGHT3; break;
				case 4: lightnum = gl.GL_LIGHT4; break;
				case 5: lightnum = gl.GL_LIGHT5; break;
				case 6: lightnum = gl.GL_LIGHT6; break;
				case 7: lightnum = gl.GL_LIGHT7; break;
			}
			gl.glEnable(gl.GL_LIGHTING);
			gl.glEnable(lightnum);
			gl.glLightfv(lightnum, gl.GL_POSITION, lightPos);
			gl.glLightfv(lightnum, gl.GL_SPOT_DIRECTION, lightDir);
			if (color != null) {
				//gl.glLightfv(lightnum, gl.GL_AMBIENT, color.ambient);
				gl.glLightfv(lightnum, gl.GL_DIFFUSE, color.diffuse);
				gl.glLightfv(lightnum, gl.GL_SPECULAR, color.ambient);
			}
		}
		else {
			gl.glDisable(gl.GL_LIGHTING);
		}
	}
}

