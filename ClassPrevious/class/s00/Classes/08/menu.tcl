#! /bin/sh
# The following line restarts wish \
exec wish4.0 "$0" "$@" -name "Menubar Demonstration"

#---------------------------------------------------------
# Sarah Jordan
# Senior Design Project
# Spring 1998
#
# This program is a revised version of the program
# menu.tcl used in Graphics class last semester.
#---------------------------------------------------------

# Create container widget for menubar
# Put menubar at top and stretch horizontally

frame .menubar -bg white
pack .menubar -side top -fill x

# Create first button
menubutton .menubar.file -relief raised -text File \
   -fg black -activeforeground blue -bg white -activebackground white \
   -menu .menubar.file.m

# Define first pulldown menu and add buttons
menu .menubar.file.m \
   -fg black -activeforeground blue \
   -bg white -activebackground white

.menubar.file.m add command -label Load -command { puts {Pretending to load...} }
.menubar.file.m add command -label Save -command { puts { Not really saving...}  }
.menubar.file.m add separator
.menubar.file.m add command -label Quit \
	-command { puts {Really quitting}; "exit"}

# Make first button visible on menubar
pack .menubar.file -side left

# Repeat for second button
menubutton .menubar.edit -relief raised -text Edit \
   -fg black -activeforeground blue -bg white -activebackground white \
   -menu .menubar.edit.m

menu .menubar.edit.m \
   -fg black -activeforeground blue \
   -bg white -activebackground white

.menubar.edit.m add command -label Cut -command { puts {Cutting?...} }
.menubar.edit.m add command -label Paste -command { puts {Pasting!...} }

pack .menubar.edit -side left

# Create third button at right of menubar
menubutton .menubar.help -text Help \
   -bg black -activebackground blue \
   -fg white -activeforeground white \
   -menu .menubar.help.m

menu .menubar.help.m \
   -fg black -activeforeground blue \
   -bg white -activebackground white

.menubar.help.m add command -label "A Little" \
	-command { puts {You're beyond helping.}}
.menubar.help.m add command -label "A Lot"   \
	 -command { puts {Error IEH2034I-qwe  You are beyond helping.}}

pack .menubar.help -side right

# Create a text message below menubar
label .message -bg white -height 3 \
   -text { This demonstrates a menubar and pulldown menus. }
pack .message
