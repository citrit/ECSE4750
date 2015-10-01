/*
 * @(#) MSWin32HandleAccess.java
 */

package gl4java.jau.awt.windows;

import java.awt.Component;
import com.ms.awt.GraphicsX;

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
 * @version     0.1, 3. JULY 1999
 * @author      Ron Cemer
 * 
 */
public class MSWin32HandleAccess 
	implements gl4java.jau.awt.WinHandleAccess
{
    protected int window, depth;

    /**
     * @dll.import("USER32",auto)
     */
    private static native int WindowFromDC(int hdc);

    protected void achieveData(java.awt.Component c, java.awt.Graphics g)
    {
        window = WindowFromDC(((GraphicsX)g).gdc.pGetDC());
        depth = c.getColorModel().getPixelSize();
    }

    /**
     *
     * get the window handle
     */
    public int getWinHandle(java.awt.Component c, java.awt.Graphics g)
    {
        achieveData(c, g);
        return window;
    }

    /**
     *
     * get the color depth
     */
    public int getWinDepth(java.awt.Component c, java.awt.Graphics g)
    {
        achieveData(c, g);
        return depth;
    }
}

