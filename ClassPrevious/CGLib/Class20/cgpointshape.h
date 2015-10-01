/***************************************************************************
                          cgpointshape.h  -  description                              
                             -------------------                                         
    begin                : Thu Mar 23 2000                                           
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


#ifndef CGPOINTSHAPE_H
#define CGPOINTSHAPE_H

#include <cgshape.h>

/**A point tolpology container which generates a point at every vertex.
  *@author Tom Citriniti
  */

class CGPointShape : public CGShape  {
public: 
	CGPointShape();
	~CGPointShape();
  /** Method called when update requested */
	void Draw(CGWindow *cgw, CGTransform &cgt);
};

#endif



