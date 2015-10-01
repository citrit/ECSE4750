/***************************************************************************
                          cgrenderer.js  -  description                              
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
CGLib.Renderer = function(elemStr)
{
    /**
     * Public functions listed at the top to act like a "header".
     */
    this.AddShape = function(shp) { m_Shapes.push(shp); };
    this.Render = function(cgw) {
        var _this = this;
        m_Shapes.forEach(function(entry) {
          entry.Draw(_this);
        });
    };
    this.DrawPoint = function(pt) {
        m_Ctx.fillRect(pt.x(),pt.y(),2,2); // fill in the pixel at pt
    };
    this.DrawLine = function(pts) {
        m_Ctx.beginPath();
        var pt = pts.getPoint(0);
        m_Ctx.moveTo(pt.x(),pt.y());
        for (var id = 1; id < pts.Size(); id++) {
            var pt = pts.getPoint(id);
            m_Ctx.lineTo(pt.x(),pt.y());
        }
        m_Ctx.stroke();
    }
    
    /**
     * Private variables next.
     */
    var m_Shapes = [];
    var m_Canvas = document.getElementById(elemStr);
    var m_Ctx=m_Canvas.getContext("2d");
}