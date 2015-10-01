import jogl.*;
import jogl.glu.*;

public class Cone extends ThreeDObject
{
  Quadric glu;
  float base, top, height;
  int slices, stacks;

  Cone(float inbase, float intop, float inheight, int inslices, int instacks) {
    base = inbase;
    top = intop;
    height = inheight;
    slices = inslices;
    stacks = instacks;
  }

  public void drawGeometry(JoglCanvas gl) {
    glu = new Quadric();
    glu.cylinder(base, top, height, slices, stacks);
  }
}

