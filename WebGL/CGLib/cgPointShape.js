/***************************************************************************
                          cgpointshape.js  -  description                              
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
CGLib.CGPointShape = function()
{
    /**
     * Public functions listed at the top to act like a "header".
     */
    this.AddPoint = function(pt) { m_Pts.AddPoint(pt); };
    this.AddPoints = function(pts) { m_Pts = pts };
    this.Draw = function(cgw) {
        for (var id = 0;id < m_Pts.Size();id++) {
            cgw.DrawPoint(m_Pts.getPoint(id));
        }
    };
    
    /**
     * Private variables next.
     */
    var m_Pts = new CGLib.CGPointList();
}