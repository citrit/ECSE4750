/* Class that package spoofs sun.awt.motif to get the
   pData out of X11Graphics */

package sun.awt.motif;

import sun.awt.*;
import jogl.JoglpDataAccess;
import java.awt.Graphics;

public class X11pDataAccess implements JoglpDataAccess
{
  public int getInfo( Graphics g )
    {
      MCanvasPeer cp = (MCanvasPeer) ((X11Graphics)g).peer;
      X11DrawingSurface xds = (X11DrawingSurface) cp.getDrawingSurfaceInfo();

      return xds.getDrawable();
    }


  public int getColormapID( Graphics g )
    {
      MCanvasPeer cp = (MCanvasPeer) ((X11Graphics)g).peer;
      X11DrawingSurface xds = (X11DrawingSurface) cp.getDrawingSurfaceInfo();
      
      return xds.getColormapID();
    }
  
}
