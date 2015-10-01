/***************************************************************************
                          cgobject.h  -  description                              
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


#ifndef CGOBJECT_H
#define CGOBJECT_H

#include <iostream.h>

/**
  *@author TC
  */

class CGObject {
public: 
	CGObject();
	~CGObject();
/**
	* Get/Set thebojects modified time
	*/
	unsigned long GetModifiedTime() { return m_iModifiedTime; }
  /** Turn this objects debugging on/off */
  void DebugOn() { m_Debug = true; }
  void DebugOff() { m_Debug = false; }

  /** Set objects internal modified time */
	void Modified();

protected:
	unsigned long	m_iModifiedTime;
	static unsigned long s_iCurrentTime;
  /** Will this object output debug info. */
  bool m_Debug;
};

#endif










