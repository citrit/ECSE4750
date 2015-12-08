//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a college course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Computer Graphics 
//           Rensselaer Polytechnic Institute
//  Date:    Sept 2015
//
//////////////////////////////////////////////////////////////////////////

var OGLRen = OGLRen || {};

/**
  * PointType class simply holds the x,y,z of a point
  */
OGLRen.PointType = function(inx, iny, inz)
{
    this.x = 0;
    this.y = 0;
    this.z = 0;
    if (inx !== undefined) {
        if ( iny !== undefined) {
            this.x = inx;
            this.y = iny;
            this.z = inz;
        }
        else {
            this.x = inx[0];
            this.y = inx[1];
            this.z = inx[2];
        }
    }
}
OGLRen.PointType.inheritsFrom(OGLRen.ParentObject);
