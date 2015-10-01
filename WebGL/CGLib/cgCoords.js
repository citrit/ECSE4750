/***************************************************************************
                          cgcoords.js  -  description                              
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
CGLib.CGPoint = function(sx, sy, sz)
{
    /**
     * Public functions listed at the top to act like a "header".
     */
    this.x = function() { return pts[0] };
    this.y = function() { return pts[1] };
    this.z = function() { return pts[2] };
    this.toString = function() { return "[" + pts[0] + ", " + pts[1] + ", " + pts[2] + "]"; }

    /**
     * Private variables next.
     */
    var pts = [sx, sy, sz];
}

/**
 * Class description.
 */
CGLib.CGPointList = function() {
  /** Add a point to the collection. */
  this.AddPoint = function(pt) { m_Points.push(pt); }
  this.getPoint = function(id) { return m_Points[id]; }
  /** Returns the size of the array */
  this.Size = function() { return m_Points.length; }
  this.toString = function() { 
      var ret = "";
      m_Points.forEach(function(entry) {
          ret = ret + entry.toString() + "<br>";
      });
      return ret; 
  }

  var m_Points = [];
};