#! /bin/sh
# the next line restarts using wish \
exec wish "$0" "$@"

# Time-stamp: </home/wrf/cg-f99/Classes/17/popupdemo3.tcl, Thu,  7 Oct 1999, 15:37:00 EDT, wrf@benvolio.ecse.rpi.edu>

# Even fancier way to demo two buttons, each of which pops up a dialog
# asking you to select a department, then printing the chair.

# How to run:
# 1. Ensure that this file is executable.
# 2: Say:
#           popupdemo3.tcl

# The advantage of this method is that you can add a new dept and
# chair together in one line.

# It demonstrates a hash table (associative array) and the concat,
# array, eval, and lindex commands.

set chair(ecse) jennings
set chair(meaem) smith
set chair(biomed) jones
set chair(hanggliding) kirk
set chair(alchemy) bacon
set chair(thaumaturgy)  mephistopheles
set chair(astronomy) ptolomy

set depts [array names chair]

proc engpopup {} {
    global depts
    global chair
    set i [eval [concat tk_dialog .dial deptpicker {{Pick a dept}} {{}} 0 $depts]]
    puts "Number $i chosen"
    set d [lindex $depts $i]
    puts "Chair of $d is $chair($d)"
}

proc scipopup {} { puts "Not implemented" }

button .a -text {Click for Science dialog} -command scipopup
button .b -text { Click for Eng dialog } -command engpopup
button .c -text Quit -command {destroy .}

pack .a
pack .b
pack .c
