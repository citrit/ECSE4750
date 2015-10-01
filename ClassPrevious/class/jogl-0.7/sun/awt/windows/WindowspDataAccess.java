package sun.awt.windows;

import jogl.JoglpDataAccess;
import java.awt.Graphics;

public class WindowspDataAccess implements JoglpDataAccess
{
  public int getInfo( Graphics g )
    {
      return ((WGraphics)g).pData;
    }

	public int getColormapID(java.awt.Graphics g)
	{
		return 0;
	}
}
