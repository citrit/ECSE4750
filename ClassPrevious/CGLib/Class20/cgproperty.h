/***************************************************************************
                          cgproperty.h  -  description                              
                             -------------------                                         
    begin                : Sun Mar 26 2000                                           
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


#ifndef CGPROPERTY_H
#define CGPROPERTY_H

#include <cgobject.h>

/**Property container for graphics to hold color, linestyle, font, etc.
  *@author Tom Citriniti
  */

class CGColor : public CGObject  {
public: 
	CGColor();
	CGColor(double r, double g, double b);
	CGColor(double *rgb);
	~CGColor();
public: // Public attributes
  /** Color Components, 0 to 1 */
  double Red, Green, Blue;
};

#endif


