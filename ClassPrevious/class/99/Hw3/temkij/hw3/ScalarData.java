/* Joshua M. Temkin
*  ScalarData object for colormapping and holding scalar values and manipulating them
*
*/

import java.util.Vector;

public class ScalarData extends ParentObject
{
    Vector data;
    Vector contourDat;
    double max = -Double.MAX_VALUE;;
    double min = Double.MAX_VALUE;
    //Lookup table of colors
    int NumEntries = 9;
    float lookupcases[][];

    public ScalarData()
    {
       data = new Vector();
       //contourDat = new Vector();
       lookupcases = new float[NumEntries][4];
       //Color Lookuptable

       lookupcases[0][0] = 0.0F;lookupcases[0][1]=0.0F;lookupcases[0][2]=1.0F;lookupcases[0][3]=1.0F;
       lookupcases[1][0] = 0.0F;lookupcases[1][1]=0.25F;lookupcases[1][2]=0.75F;lookupcases[1][3]=1.0F;
       lookupcases[2][0] = 0.0F;lookupcases[2][1]=0.5F;lookupcases[2][2]=0.50F;lookupcases[2][3]=1.0F;
       lookupcases[3][0] = 0.0F;lookupcases[3][1]=0.75F;lookupcases[3][2]=0.25F;lookupcases[3][3]=1.0F;
       lookupcases[4][0] = 0.0F;lookupcases[4][1]=1.0F;lookupcases[4][2]=0.0F;lookupcases[4][3]=1.0F;
       lookupcases[5][0] = 0.25F;lookupcases[5][1]=0.75F;lookupcases[5][2]=0.0F;lookupcases[0][3]=1.0F;
       lookupcases[6][0] = 0.50F;lookupcases[6][1]=0.50F;lookupcases[6][2]=0.0F;lookupcases[0][3]=1.0F;
       lookupcases[7][0] = 0.75F;lookupcases[7][1]=0.25F;lookupcases[7][2]=0.0F;lookupcases[0][3]=1.0F;
       lookupcases[8][0] = 1.0F;lookupcases[8][1]=0.0F;lookupcases[8][2]=0.0F;lookupcases[0][3]=1.0F;

    }


    public void addValue(float t)
    {

        updateTime();
        Float f = new Float(t);
        data.addElement(f);
        if ( max < t)
        {
            max = t;
        }
        else if (t < min)
        {
            min = t;
        }
    }

    public Vector getContourSet(double num)
    {
        double t = 1/num;
        double diff = max - min;
        //System.out.println("max =" + max + " min =" + min);
        //System.out.println("t = " + t);
        double step = diff * t;
        //System.out.println("step = " + step);
        contourDat = new Vector();
        for (int x = 1; x <= num; x++)
        {
            //float tmp = ( new Double(diff * (t * x))).floatValue();
            float tmp = ( new Double(min + (step * x))).floatValue();
            //System.out.print("tmp =" + tmp);
            Float f = new Float(tmp);
            contourDat.addElement(f);
        }
        return (Vector)contourDat.clone();

    }


    public float getValue(int index)
    {
        Float f = (Float)data.elementAt(index);
        return f.floatValue();
    }

    public int getNumberOfPoints()
    {
        return data.size();
    }

    public Material getColor(float value)
    {
        float t[]= new float[4];
        float s;
        int index;

        if (value <= min)
        {
             index = 0;
        }
        else if (value >= max)
        {
            index = NumEntries - 1;
        }
        else
        {
            index =(new Double(NumEntries * ( (value - min)/(max - min) ))).intValue();
        }
        //System.out.println("lookup index = " + index);
        t = lookupcases[index];

        Material mat = new Material(t);
        return mat;
    }
}
