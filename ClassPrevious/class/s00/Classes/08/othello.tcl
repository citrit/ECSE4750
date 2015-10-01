#! /bin/sh
# The following line restarts wish \
exec wish4.0 "$0" "$@" -name "Othello"

#-------------------------------------------------------------
# Sarah Jordan
# Senior Design Project
# Spring 1998
#
# The purpose of this program is to demonstrate a Tcl
# version of Othello (this game is NOT fully functional).
# Concepts included here are global variables, menu bars
# and buttons, switch statements, if statements, and loops.
#-------------------------------------------------------------

# Set global variables
global boardwidth
global boardsize
global player1
global player2
global color

set player1 1
set player2 2
set color $player1

# Create container for menubar
# Put menubar at top & stretch horizontally
frame .menubar -bg white
pack .menubar -side top -fill x

# Create first button
menubutton .menubar.play -relief raised \
   -fg black -activeforeground blue \
   -bg white -activebackground white \
   -text Play -menu .menubar.play.m

# Define menu widget for first button
menu .menubar.play.m \
   -fg black -activeforeground blue \
   -bg white -activebackground white

# Add cascade menu to first button
.menubar.play.m add command -label "3 x 3 board" -command { createboard 3 }
.menubar.play.m add command -label "4 x 4 board" -command { createboard 4 }
.menubar.play.m add command -label "5 x 5 board" -command { createboard 5 }
.menubar.play.m add command -label "6 x 6 board" -command { createboard 6 }
.menubar.play.m add command -label "7 x 7 board" -command { createboard 7 }
.menubar.play.m add command -label "8 x 8 board" -command { createboard 8 }
.menubar.play.m add separator
.menubar.play.m add command -label "Exit Game"   -command { exit }
pack .menubar.play -side left

# Put welcome message at top of screen
label .menubar.welcome -text "Welcome to Othello!" -padx 10 -bg white
pack .menubar.welcome -side left

#-------------------
# Create game board
#-------------------

proc createboard { size } {

   global boardwidth

   # Set up frame for board
   switch $size {
      3 { set boardwidth 190 }
      4 { set boardwidth 210 }
      5 { set boardwidth 250 }
      6 { set boardwidth 290 }
      7 { set boardwidth 330 }
      8 { set boardwidth 370 }
   }

   frame .frame -width $boardwidth -height $boardwidth -bg white
   pack .frame -anchor nw

   # Create board
   global boardsize
   set boardsize $size
   set color 0
   for { set i 0 } { $i < $boardsize } { incr i } {
      for { set j 0 } { $j < $boardsize } { incr j } {
         button .b$i$j -bg white -activebackground white \
            -highlightbackground black -command [ list board $i $j ]
      }
   }

   # Initialize position values
   set yspace 40
   set xspace 40
   set xmin   30
   set xpos   30
   set ypos   50

   # Draw board
   for { set i 0 } { $i < $boardsize } { incr i } {
      for { set j 0 } { $j < $boardsize } { incr j } {
         place .b$i$j -x $xpos -y $ypos
         set xpos [ expr $xpos+$xspace ]
      }
      set xpos $xmin
      set ypos [ expr $ypos+$yspace ]
   }

   # Is board even or odd size?
   set N [ expr { $size % 2 } ]
   if { $N == 1 } {
      set LR [ expr { ($size-1)/2 } ]
   } else {
      set LR [ expr { $size/2 } ]
   }

   # Set starting positions
   .b[ expr ($LR-1) ][ expr ($LR-1) ] configure -bg red \
      -activebackground red -state disabled
   .b[ expr ($LR) ][ expr ($LR) ] configure -bg red \
      -activebackground red -state disabled
   .b[ expr ($LR-1) ][ expr ($LR) ] configure -bg black \
      -activebackground black -state disabled
   .b[ expr ($LR) ][ expr ($LR-1) ] configure -bg black \
      -activebackground black -state disabled
}

#------------------------------------
# Color board positions when clicked
#------------------------------------

proc board { row col } {

   global color
   global player1
   global player2
   global boardwidth

   set oppcolor [ changecolor $color ]

   if { $color == $player1 } {
      .b$row$col configure -bg red -activebackground red -state disabled
   } elseif { $color == $player2 } {
      .b$row$col configure -bg black -activebackground black -state disabled
   }

   set color $oppcolor
   set oppcolor [ changecolor $color ]

   set adj [ adjacent $row $col ]

   if { $adj == 0 } {
      puts "ERROR - Your move must be adjacent to a piece of the\
         opposite color."
      .b$row$col configure -bg white -activebackground white -state active
      set color $oppcolor
      set oppcolor [ changecolor $color ]
   }

   # Now that we have checked adjacency, we must determine
   # if player is able to flip a piece of the opposite color.
   # Functionality should be inserted here.
}

#---------------
# Change colors
#---------------

proc changecolor { player } {
   global color
   global player1
   global player2

   if { $color == $player1 } {
      set oppcolor $player2
   } elseif { $color == $player2 } {
      set oppcolor $player1
   }

   return $oppcolor
}

#---------------------------------
# Check adjacency to other pieces
#---------------------------------

proc adjacent { row col } {

   global boardsize
   set empty white
   set adj 0
   set left 0
   set right 0
   set up 0
   set down 0
   set upleft 0
   set downleft 0
   set upright 0
   set downright 0

   set color [ .b$row$col cget -bg ]

   set firstrow [ expr ($row-1) ]
   set firstcol [ expr ($col-1) ]
   set lastrow [expr ($row+1) ]
   set lastcol [expr ($col+1) ]

   if { $firstrow < 0 } { set firstrow 0 }
   if { $firstcol < 0 } { set firstcol 0 }
   if { $lastrow >= $boardsize } { set lastrow [ expr ($boardsize-1) ] }
   if { $lastcol >= $boardsize } { set lastcol [ expr ($boardsize-1) ] }

   for { set i $firstrow } { $i <= $lastrow } { incr i } {
      for { set j $firstcol } { $j <= $lastcol } { incr j } {
         set boardcolor [ .b$i$j cget -bg ]
         if { ($boardcolor != $empty ) && ($boardcolor != $color) } {
            set adj 1
         }
      }
   }

   return $adj
}
