import jogl.*;
import jogl.glu.*;

public class Sphere extends ThreeDObject
{
  Quadric glu;
  int radius, slices, stacks;

  Sphere(int inradius, int inslices, int instacks) {
    radius = inradius;
    slices = inslices;
    stacks = instacks;
    glu = new Quadric();
  }

  public void drawGeometry(JoglCanvas gl) {
    glu.sphere(radius, slices, stacks);
  }
}

