#! /bin/sh
# The following line restarts wish \
exec wish4.0 "$0" "$@" -name "Widgets"

#---------------------------------------------------
# Sarah Jordan
# Senior Design Project
# Spring 1998
#
# The purpose of this program is to learn about
# various widget types and how to use them.
#---------------------------------------------------

# Learn to use frames
label .framelabel -height 3 -text "This is a very boring frame:"
pack .framelabel
frame .frame -height 50 -width 50 -bg white
pack .frame

# Learn to use listboxes
label .listlabel -height 3 -text "This is a listbox:"
pack .listlabel
listbox .list -height 4 -bg black -fg white \
   -selectbackground blue -selectforeground white
.list insert 4 Daddy Mommy Brother Sister
pack .list

# Learn to use checkbuttons
label .checklabel -height 3 -text "These are checkbuttons:"
pack .checklabel
checkbutton .eng -fg purple -bg white \
   -activeforeground black -activebackground white \
   -selectcolor black -text "Engineering"
checkbutton .arch -fg purple -bg white \
   -activeforeground black -activebackground white \
   -selectcolor black -text "Architecture"
checkbutton .sci -fg purple -bg white \
   -activeforeground black -activebackground white \
   -selectcolor black -text "Science"
pack .eng
pack .arch
pack .sci

# Learn to use menubuttons
label .menulabel -height 3 -text "These are menubuttons:"
pack .menulabel
menubutton .button1 -relief raised -fg red -bg white \
   -activeforeground black -activebackground white -text "B1"
menubutton .button2 -relief raised -fg red -bg white \
   -activeforeground black -activebackground white -text "B2"
pack .button1
pack .button2

label .quitlabel -height 3 -text "Quit the program:"
pack .quitlabel
button .quit -text "Quit" -fg white -bg black \
   -activeforeground yellow -activebackground black -command { exit }
pack .quit
