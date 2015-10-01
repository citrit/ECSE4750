/* Interface to clean up JoglCanvas */

package jogl;

/**
   This class has no user servicable parts inside.  It is
   used internally by JoglCanvas and by our package spoofed
   sun.awt classes that give us internal access to window
   variables that we need to set up the OpenGL drawing
   context 
*/
public interface JoglpDataAccess 
{
  /* gets some structure for windows, and drawable on X11 */
  int getInfo(java.awt.Graphics g);

  int getColormapID(java.awt.Graphics g);
}
