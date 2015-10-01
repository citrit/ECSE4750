/*
 * @(#) MacHandleAccess.java
 */

package gl4java.jau.awt.macintosh;

import sun.awt.DrawingSurface;  
import sun.awt.DrawingSurfaceInfo;  
import sun.awt.MacDrawingSurface;

import java.awt.*;

/**
 * This class has no user servicable parts inside.  It is
 * used internally by GLFrame and by our package spoofed
 * sun.awt classes that give us internal access to window
 * variables that we need to set up the OpenGL drawing
 * context
 * 
 *
 * @see WinHandleAccess
 * @version 	0.1, 7. JULY 1998
 * @author      Sven Goethel
 * @author      ported to Mac by gerard ziemski
 * 
 */
public class MacHandleAccess implements gl4java.jau.awt.WinHandleAccess
{
	protected DrawingSurface		ds;
	protected DrawingSurfaceInfo	dsi;
	protected MacDrawingSurface		mds;
	protected int					window,
									depth;
	
	
	protected void achieveData(java.awt.Component c, java.awt.Graphics g)
	{
		/* outta java3d */
		dsi			= null;
		mds			= null;
		window		= 0;
		depth		= 0;
		
		ds = ((DrawingSurface)c.getPeer());
		dsi = ds.getDrawingSurfaceInfo();
		
		if (dsi != null)
		{
			dsi.lock();
			mds = (MacDrawingSurface)dsi.getSurface();  
			dsi.unlock();
		}
		
		if (mds != null)
		{
			dsi.lock();
			
			window = mds.getPort();
			depth = c.getColorModel().getPixelSize();
					
			dsi.unlock();
		}
		
		if (mds == null)
		{
			System.out.println("MacHandleAccess:getWinHandle failed");
		}
  	}

/**
 *
 * gets some structure for windows, and drawable on Mac
 */
	public int getWinHandle(java.awt.Component c, java.awt.Graphics g)
	{
		achieveData(c, g);
		return window;
	}

/**
 *
 * gets some structure for windows, and drawable on Mac
 */
	public int getWinDepth(java.awt.Component c, java.awt.Graphics g)
	{
		achieveData(c, g);
		return depth;
	}
}
