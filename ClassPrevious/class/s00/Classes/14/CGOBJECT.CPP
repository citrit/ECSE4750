/***************************************************************************
                          cgobject.cpp  -  description                              
                             -------------------                                         
    begin                : Fri Feb 11 2000                                           
    copyright            : (C) 2000 by TC                         
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


#include "cgobject.h"

unsigned long CGObject::s_iCurrentTime = 1;

CGObject::CGObject(){
	this->Modified();
}
CGObject::~CGObject(){
}

void CGObject::Modified()
{
	m_iModifiedTime = s_iCurrentTime++;
}
