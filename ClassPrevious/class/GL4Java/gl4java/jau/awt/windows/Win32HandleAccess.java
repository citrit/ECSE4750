/*
 * @(#) Win32HandleAccess.java
 */

package gl4java.jau.awt.windows;

import sun.awt.DrawingSurface;
import sun.awt.Win32DrawingSurface;
import sun.awt.DrawingSurfaceInfo;  

/**
 * The ms-windows implementation for accessing the native window handle
 *
 * This class has no user servicable parts inside.  It is
 * used internally by GLFrame and by our package spoofed
 * sun.awt classes that give us internal access to window
 * variables that we need to set up the OpenGL drawing
 * ontext 
 *
 * @see WinHandleAccess
 * @version 	0.1, 7. JULY 1998
 * @author      Sven Goethel
 * 
 */
public class Win32HandleAccess 
	implements gl4java.jau.awt.WinHandleAccess
{

  protected DrawingSurfaceInfo dsi;
  protected Win32DrawingSurface wds;
  protected int window, depth;

  protected void achieveData(java.awt.Component c, java.awt.Graphics g)
  {
	/* outta java3d */
	dsi=null;
	wds=null;
	window=0; depth=0;

        dsi = ((DrawingSurface)(c.getPeer())).getDrawingSurfaceInfo();
	if(dsi!=null)
	{
		dsi.lock();
		wds = (Win32DrawingSurface)dsi.getSurface();  
		dsi.unlock();
	}
	if(wds!=null)
	{
		dsi.lock();
		window = wds.getHDC();
		depth = wds.getDepth();
		/*
			System.out.println("wds ="+wds);
			System.out.println("wds.Depth ="+wds.getDepth());
			System.out.println("wds.HDC ="+wds.getHDC());
			System.out.println("wds.HWnd ="+wds.getHWnd());
		*/
		dsi.unlock();
	}
	if(wds==null)
		System.out.println("Win32HandleAccess.getWinHandle failed, because the given Component is NOT a Window-Component\n");
  }

/**
 *
 * gets some structure for windows, and drawable on Win32
 */
  public int getWinHandle(java.awt.Component c, java.awt.Graphics g)
  {
      achieveData(c, g);
      return window;
  }

/**
 *
 * gets some structure for windows, and drawable on Win32
 */
  public int getWinDepth(java.awt.Component c, java.awt.Graphics g)
  {
  	achieveData(c, g);
	return depth;
  }
}

