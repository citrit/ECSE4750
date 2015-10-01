#!/bin/sh
# the next line restarts using wish \
exec wish "$0" "$@"
# From http://sunscript.sun.com/plugin/stroker.html
# Time-stamp: </home/wrf/cgl-f99/Classes/11/stroker.tcl, Thu, 23 Sep 1999, 10:15:44 EDT, wrf@benvolio.ecse.rpi.edu>

proc StrokeBegin {w x y} {
    global stroke
    catch {unset stroke}
    set stroke(N) 0
    set stroke(0) [list $x $y]
}
proc Stroke {w x y} {
    global stroke
    set last $stroke($stroke(N))
    incr stroke(N)
    set stroke($stroke(N)) [list $x $y]
    eval {$w create line} $last {$x $y -tag segments -fill blue}
}
proc StrokeEnd {w x y} {
    global stroke
    set points {}
    for {set i 0} {$i <= $stroke(N)} {incr i} {
        append points $stroke($i) " "
    }
    $w delete segments
    eval {$w create line} $points \
        {-smooth true -tag line -fill red -width 5}
}

canvas .c -width 400 -height 400  -background bisque
bind .c <Button-1> {StrokeBegin %W %x %y}
bind .c <B1-Motion> {Stroke %W %x %y}
bind .c <ButtonRelease-1> {StrokeEnd %W %x %y}
bind .c <Delete> {%W delete [%W find closest %x %y]}
.c bind line <Enter> {%W itemconfigure [%W find closest %x %y] -fill blue}
.c bind line <Leave> {%W itemconfigure [%W find closest %x %y] -fill red}
pack .c
focus .c
