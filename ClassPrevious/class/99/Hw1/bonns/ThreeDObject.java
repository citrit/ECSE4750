import jogl.*;

public abstract class ThreeDObject
{
  public static final int RELATIVE = 0;
  public static final int ABSOLUTE = 1;

  float ambientV[] = {0.0F, 0.0F, 0.0F, 0.0F};
  float diffuseV[] = {0.0F, 0.0F, 0.0F, 0.0F};
  float specularV[] = {0.0F, 0.0F, 0.0F, 0.0F};

  float translateV[] = {0.0F, 0.0F, 0.0F};
  float rotateV[] = {0.0F, 0.0F, 0.0F};
  float scaleV[] = {1.0F, 1.0F, 1.0F};
  
  ThreeDObject() {}

  public void draw(JoglCanvas gl) {
    gl.material(GL.FRONT_AND_BACK, GL.AMBIENT, ambientV);
    gl.material(GL.FRONT_AND_BACK, GL.SPECULAR, specularV);
    gl.material(GL.FRONT_AND_BACK, GL.DIFFUSE, diffuseV);

    gl.matrixMode(GL.MODELVIEW);
    gl.pushMatrix();

    gl.scale(scaleV[0], scaleV[1], scaleV[2]);
    gl.translate(translateV[0], translateV[1], translateV[2]);
    gl.rotate(rotateV[0], 1.0, 0.0, 0.0);
    gl.rotate(rotateV[1], 0.0, 1.0, 0.0);
    gl.rotate(rotateV[2], 0.0, 0.0, 1.0);

    drawGeometry(gl);

    gl.popMatrix();
  }

  public abstract void drawGeometry(JoglCanvas gl);

  // Translate functions
  public void translatev(float t[], int how) {translate(t[0], t[1], t[2], how);}
  public void translate(float x, float y, float z, int how) {
    if (how == ABSOLUTE) {
      translateV[0] = x;
      translateV[1] = y;
      translateV[2] = z;
    } else {
      translateV[0] += x;
      translateV[1] += y;
      translateV[2] += z;
    }
  }

  // Scale functions
  public void scalev(float s[], int how) {scale(s[0], s[1], s[2], how);}
  public void scale(float x, float y, float z, int how) {
    if (how == ABSOLUTE) {
      scaleV[0] = x;
      scaleV[1] = y;
      scaleV[2] = z;
    } else {
      scaleV[0] += x;
      scaleV[1] += y;
      scaleV[2] += z;
    }
  }

  // Rotate functions
  public void rotatev(float r[], int how) {rotate(r[0], r[1], r[2], how);}
  public void rotate(float x, float y, float z, int how) {
    if (how == ABSOLUTE) {
      rotateV[0] = x;
      rotateV[1] = y;
      rotateV[2] = z;
    } else {
      rotateV[0] += x;
      rotateV[1] += y;
      rotateV[2] += z;
    }
  }

  // Get Functions
  public float[] getAmbientV() {return ambientV;}
  public float[] getDiffuseV() {return diffuseV;}
  public float[] getSpecularV() {return specularV;}
  public float[] getTranslateV() {return translateV;}
  public float[] getRotateV() {return rotateV;}
  public float[] getScaleV() {return scaleV;}

  // Set Functions
  public void setAmbientV(float a[]) {ambientV = a;}
  public void setDiffuseV(float d[]) {diffuseV = d;}
  public void setSpecularV(float s[]) {specularV = s;}
  public void setTranslateV(float t[]) {translateV = t;}
  public void setRotateV(float r[]) {rotateV = r;}
  public void setScaleV(float s[]) {scaleV = s;}

  // Vertexv function
  public void vertexv(float[] p, JoglCanvas gl) {gl.vertex(p[0], p[1], p[2]);}

}
