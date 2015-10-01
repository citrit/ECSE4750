#! /bin/sh
# The following line restarts wish \
exec wish4.0 -f "$0" "$@" -name "Drawing Demonstration"

#---------------------------------------------------------
# Sarah Jordan
# Senior Design Project
# Spring 1998
#
# The purpose of this program is to demonstrate how
# to draw vertical lines, horizontal lines, diagonal
# lines, and ovals.
#---------------------------------------------------------

# Create canvas for drawing
canvas .canvas -bg white -highlightthickness 0 -width 360 -height 360
pack .canvas

# Draw two horizontal lines
#   Top    line: Endpoints at (20,20) and (340,20)
#   Bottom line: Endpoints at (20,340) and (340,340)

# Line format: x1 y1 x2 y2
set HL1 [ .canvas create line 20 20 340 20 -width 3 -fill black ]
set HL2 [ .canvas create line 20 340 340 340 -width 3 -fill black ]

# Draw two vertical lines
#   Left  line: Endpoints at (20,20) and (20,340)
#   Right line: Endpoints at (340,20) and (340,340)

set VL1 [ .canvas create line 20 20 20 340 -width 3 -fill black ]
set VL2 [ .canvas create line 340 20 340 340 -width 3 -fill black ]

# Draw two diagonal lines
#   Upper left to lower right: Endpoints at (20,20) and (340,340)
#   Lower left to upper right: Endpoints at (20,340) and (340,20)

set DL1 [ .canvas create line 20 20 340 340 -width 3 -fill black ]
set DL2 [ .canvas create line 20 340 340 20 -width 3 -fill black ]

# Draw four ovals
#   Northern oval: Sides at (170,70,190,110)
#   Western  oval: Sides at (70,170,110,190)
#   Southern oval: Sides at (170,250,190,290)
#   Eastern  oval: Sides at (250,170,290,190)

# Oval format: left top right bottom
set OVAL1 [ .canvas create oval 170 70 190 110 \
    -width 3 -outline black -fill green ]
set OVAL2 [ .canvas create oval 70 170 110 190 \
    -width 3 -outline black -fill green ]
set OVAL3 [ .canvas create oval 170 250 190 290 \
    -width 3 -outline black -fill green ]
set OVAL4 [ .canvas create oval 250 170 290 190 \
    -width 3 -outline black -fill green ]

