/***************************************************************************
                          cgtransform.cpp  -  description                              
                             -------------------                                         
    begin                : Wed Mar 15 2000                                           
    copyright            : (C) 2000 by Tom Citriniti                         
    email                : citrit@rpi.edu                                     
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   * 
 *                                                                         *
 ***************************************************************************/

#include <math.h>

#include "cgtransform.h"
#include "cgcoords.h"

CGMatrix3x3::CGMatrix3x3()
{
	this->SetIdentity();
}

CGMatrix3x3::~CGMatrix3x3()
{
}

/** Set Internal matrix to identity */
void CGMatrix3x3::SetIdentity()
{
	int 	i,j;
	
	for (i=0;i<3;i++) {
		for (j=0;j<3;j++) {
			m_Data[i][j] = (double)i == j;
		}
	}	
}

/** Multiplies the internal matrix by trans stores the value internal */
void CGMatrix3x3::PreMultiply(CGMatrix3x3 &trans)
{
	int r,c;
	double tmp[3][3];
	
	for (r=0;r<3;r++) {
		for (c=0;c<3;c++) {
			tmp[r][c] = trans.m_Data[r][0]*m_Data[0][c] + trans.m_Data[r][1]*m_Data[1][c] +
									trans.m_Data[r][2]*m_Data[2][c];
		}
	}
	for (r=0;r<3;r++) {
		for (c=0;c<3;c++) {
			m_Data[r][c] = tmp[r][c];
		}
	}
}

/** Constructor/Destructor */
CGTransform::CGTransform(){
}
CGTransform::~CGTransform(){
}

/** Translate the matrix by tx and ty */
void CGTransform::Translate(double tx, double ty)
{
	CGMatrix3x3 	m;
	
	m.SetIdentity();
	m.m_Data[0][2] = tx;
	m.m_Data[1][2] = ty;
	m_Matrix.PreMultiply(m);
}

/** Scale the object by sx and sy using refPt */
void CGTransform::Scale(double sx, double sy, CGPoint &refPt)
{
	CGMatrix3x3 	m;
		
	m.SetIdentity();
	m.m_Data[0][0] = sx;
	m.m_Data[0][2] = (1 - sx) * refPt.x;
	m.m_Data[1][1] = sy;
	m.m_Data[1][2] = (1 - sy) * refPt.y;
	m_Matrix.PreMultiply(m);	
}

/** Rotate the object using a around refPt */
void CGTransform::Rotate(double a, CGPoint &refPt)
{
	CGMatrix3x3 	m;

	m.SetIdentity();
	a = DegreesToRadians(a); // convert to radians	
	m.m_Data[0][0] = cos(a);
	m.m_Data[0][1] = -sin(a);
	m.m_Data[0][2] = refPt.x * (1 - cos(a)) + refPt.y * sin(a);
	m.m_Data[1][0] = sin(a);
	m.m_Data[1][1] = cos(a);
	m.m_Data[1][2] = refPt.y * (1 - cos(a)) - refPt.x * sin(a);
	m_Matrix.PreMultiply(m);	
}

/** Translate the points using the internal matrix in place */
void CGTransform::TransformPoints(CGPointList &pts)
{
	unsigned int 		k;
	double	tmp;
	
	for (k=0;k<pts.Size();k++) {
		tmp = m_Matrix.m_Data[0][0] * pts[k].x + m_Matrix.m_Data[0][1] * pts[k].y +
						m_Matrix.m_Data[0][2];
		pts[k].y = m_Matrix.m_Data[1][0] * pts[k].x + m_Matrix.m_Data[1][1] *
									pts[k].y + m_Matrix.m_Data[1][2];
		pts[k].x = tmp;
	}
}


