/***************************************************************************
                          cglineshape.js  -  description                              
                             -------------------                                         
    begin                : Tue Mar 14 2015                                           
    copyright            : (C) 2015 by Tom Citriniti                         
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

var CGLib = CGLib || {};

/**
 * Class description.
 */
CGLib.CGLineShape = function()
{
    /**
     * Public functions listed at the top to act like a "header".
     */
    this.AddLine = function(p1, p2) { m_Pts.AddPoint(p1);m_Pts.AddPoint(p2); };
    this.AddPoints = function(pts) { m_Pts = pts };
    this.Draw = function(cgw) {
        cgw.DrawLine(m_Pts);
    };
    
    /**
     * Private variables next.
     */
    var m_Pts = new CGLib.CGPointList();
}