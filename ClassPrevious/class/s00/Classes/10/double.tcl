#! /bin/sh
# the next line restarts using wish \
exec wish "$0" "$@"

label .l0 -text {This demos the double program!}

label .l1 -background yellow -text {Type a number:}

entry .e -background green -textvariable a

label .l4 -text {When you leave the above entry field, the number will be
 doubled:}

label .l2 -background yellow -text {Twice that is:}

label .l3 -text label -textvariable b



pack .l0 .l1 .e .l4 .l2 .l3

bind .e <Leave> doubleit

proc doubleit {} {
global a b
set b [expr 2 * $a]
}

