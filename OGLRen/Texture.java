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

import java.awt.*;
import java.awt.image.*;

public class Texture extends ParentObject
{
	Image gTex;
	int[] pixels;
	int gTexW, gTexH;

	public Texture(String fileName, Canvas appWindow) {
		//Load the texture, make sure its size is a factor of 2 (128x128, 256x256, etc.)
		gTex = appWindow.getToolkit().getImage(fileName);
        try {
			MediaTracker tracker = new MediaTracker(appWindow);
            tracker.addImage(gTex, 0);
            tracker.waitForID(0);
        }
        catch ( Exception e ) {System.err.println("Exception in adding image");}
		
		gTexW = gTex.getWidth(appWindow);
		gTexH = gTex.getHeight(appWindow);
		System.out.println("Texture Width: " + gTexW + " Height: " + gTexH);
		pixels = new int[gTexW * gTexH];
		PixelGrabber pg = new PixelGrabber(gTex, 0, 0, gTexW, gTexH, pixels, 0, gTexW);
		try {
			pg.grabPixels();
		}
		catch (InterruptedException e) {
			System.err.println("interrupted waiting for pixels!");
			return;	
		}
	}
		
/*	public void render(Renderer aren) {

		System.out.println("Loading texture... ");
		aren.loadTexture(0, this);	
	}*/
	
}
