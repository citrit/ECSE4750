import jogl.*;

public class Box extends ThreeDObject
{
  float boxCors[][] = {{-1.0F, -1.0F, -1.0F},
		       {1.0F, -1.0F, -1.0F},
		       {1.0F, 1.0F, -1.0F},
		       {-1.0F, 1.0F, -1.0F},
		       {-1.0F, -1.0F, 1.0F},
		       {1.0F, -1.0F, 1.0F},
		       {1.0F, 1.0F, 1.0F},
		       {-1.0F, 1.0F, 1.0F}};
  int boxIndex[] = {0, 1, 2, 3,
		    4, 5, 6, 7,
		    0, 1, 5, 4,
		    2, 6, 7, 3,
		    1, 2, 6, 5,
		    0, 3, 7, 4};

  public void drawGeometry(JoglCanvas gl) {
    for (int x = 0; x < 6; x++) {
      gl.begin(GL.POLYGON);
      for (int y = 0; y < 4; y++)
	vertexv(boxCors[boxIndex[(x*4)+y]], gl);
      gl.end();
    }
  }
}

