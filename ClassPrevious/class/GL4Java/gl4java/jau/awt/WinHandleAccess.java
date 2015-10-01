/**
 * @(#) WinHandleAccess.java
 */
package gl4java.jau.awt;

import java.awt.Component;
import java.awt.Graphics;

/**
 * The base interface for accessing the native window handle
 *
 * @version 	2.00, 21. April 1999
 * @author      Sven Goethel
 */
public interface WinHandleAccess
{
    public abstract int getWinHandle(Component component, Graphics g);

    public abstract int getWinDepth(Component component, Graphics g);

}
