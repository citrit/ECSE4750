/* GL.java - empty interface holding GL constants */

package jogl;

public interface GLU
{


/*************************************************************/

/* Boolean */
  int FALSE =                           0;
  int TRUE =                            1;

  /* Version */
  int VERSION_1_1    =                  1;
  int VERSION_1_2   =                   1;

/* StringName */
  int VERSION       =                   100800;
  int EXTENSIONS   =                    100801;

/* ErrorCode */
  int INVALID_ENUM  =                   100900;
  int INVALID_VALUE =                   100901;
  int OUT_OF_MEMORY =                   100902;
  int INCOMPATIBLE_GL_VERSION =         100903;

/* NurbsDisplay */
/*      FILL */
  int OUTLINE_POLYGON   =               100240;
  int OUTLINE_PATCH     =               100241;

/* NurbsCallback */
  int ERROR             =               100103;

  /* NurbsError */
  int NURBS_ERROR1     =                100251;
  int NURBS_ERROR2      =               100252;
  int NURBS_ERROR3     =                100253;
  int NURBS_ERROR4     =                100254;
  int NURBS_ERROR5     =                100255;
  int NURBS_ERROR6     =                100256;
  int NURBS_ERROR7     =                100257;
  int NURBS_ERROR8     =                100258;
  int NURBS_ERROR9     =                100259;
  int NURBS_ERROR10    =                100260;
  int NURBS_ERROR11    =                100261;
  int NURBS_ERROR12    =                100262;
  int NURBS_ERROR13    =                100263;
  int NURBS_ERROR14    =                100264;
  int NURBS_ERROR15    =                100265;
  int NURBS_ERROR16    =                100266;
  int NURBS_ERROR17    =                100267;
  int NURBS_ERROR18    =                100268;
  int NURBS_ERROR19    =                100269;
  int NURBS_ERROR20    =                100270;
  int NURBS_ERROR21    =                100271;
  int NURBS_ERROR22    =                100272;
  int NURBS_ERROR23    =                100273;
  int NURBS_ERROR24    =                100274;
  int NURBS_ERROR25    =                100275;
  int NURBS_ERROR26    =                100276;
  int NURBS_ERROR27    =                100277;
  int NURBS_ERROR28    =                100278;
  int NURBS_ERROR29    =                100279;
  int NURBS_ERROR30    =                100280;
  int NURBS_ERROR31    =                100281;
  int NURBS_ERROR32    =                100282;
  int NURBS_ERROR33    =                100283;
  int NURBS_ERROR34    =                100284;
  int NURBS_ERROR35    =                100285;
  int NURBS_ERROR36     =               100286;
  int NURBS_ERROR37    =                100287;

  /* NurbsProperty */
  int AUTO_LOAD_MATRIX  =               100200;
  int CULLING            =              100201;
  int SAMPLING_TOLERANCE  =             100203;
  int DISPLAY_MODE          =           100204;
  int PARAMETRIC_TOLERANCE =            100202;
  int SAMPLING_METHOD  =                100205;
  int U_STEP          =                 100206;
  int V_STEP           =                100207;

/* NurbsSampling */
  int PATH_LENGTH      =                100215;
  int PARAMETRIC_ERROR  =               100216;
  int DOMAIN_DISTANCE   =               100217;

  /* NurbsTrim */
  int MAP1_TRIM_2   =                   100210;
  int MAP1_TRIM_3   =                   100211;

/* QuadricDrawStyle */
  int POINT       =                     100010;
  int LINE        =                     100011;
  int FILL        =                     100012;
  int SILHOUETTE  =                     100013;

/* QuadricCallback */
/*      ERROR */

/* QuadricNormal */
  int SMOOTH   =                        100000;
  int FLAT     =                        100001;
  int NONE     =                        100002;

  /* QuadricOrientation */
  int OUTSIDE   =                       100020;
  int INSIDE   =                        100021;

/* TessCallback */
  int TESS_BEGIN   =                    100100;
  int BEGIN        =                    100100;
  int TESS_VERTEX  =                    100101;
  int VERTEX       =                    100101;
  int TESS_END      =                   100102;
  int END           =                   100102;
  int TESS_ERROR    =                   100103;
  int TESS_EDGE_FLAG =                  100104;
  int EDGE_FLAG       =                 100104;
  int TESS_COMBINE    =                 100105;
  int TESS_BEGIN_DATA  =                100106;
  int TESS_VERTEX_DATA  =               100107;
  int TESS_END_DATA     =               100108;
  int TESS_ERROR_DATA   =               100109;
  int TESS_EDGE_FLAG_DATA =             100110;
  int TESS_COMBINE_DATA   =             100111;

/* TessContour */
  int CW              =                 100120;
  int CCW              =                100121;
  int INTERIOR         =                100122;
  int EXTERIOR         =                100123;
  int UNKNOWN          =                100124;

  /* TessProperty */
  int TESS_WINDING_RULE =               100140;
  int TESS_BOUNDARY_ONLY  =             100141;
  int TESS_TOLERANCE      =             100142;

  /* TessError */
  int TESS_ERROR1     =                 100151;
  int TESS_ERROR2       =               100152;
  int TESS_ERROR3       =               100153;
  int TESS_ERROR4       =               100154;
  int TESS_ERROR5       =               100155;
  int TESS_ERROR6       =               100156;
  int TESS_ERROR7       =               100157;
  int TESS_ERROR8        =              100158;
  int TESS_MISSING_BEGIN_POLYGON =      100151;
  int TESS_MISSING_BEGIN_CONTOUR  =     100152;
  int TESS_MISSING_END_POLYGON   =      100153;
  int TESS_MISSING_END_CONTOUR   =      100154;
  int TESS_COORD_TOO_LARGE       =      100155;
  int TESS_NEED_COMBINE_CALLBACK =      100156;

/* TessWinding */
  int TESS_WINDING_ODD        =         100130;
  int TESS_WINDING_NONZERO    =         100131;
  int TESS_WINDING_POSITIVE    =        100132;
  int TESS_WINDING_NEGATIVE    =        100133;
  int TESS_WINDING_ABS_GEQ_TWO =        100134;

  /*************************************************************/

  double TESS_MAX_COORD = 1.0e150;

}
