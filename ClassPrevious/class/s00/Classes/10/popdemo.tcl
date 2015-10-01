#! /bin/sh
# the next line restarts using wish \
exec wish "$0" "$@"

proc popit {} {
    toplevel .t
    button .t.b -text { press to pop down } -command popdown
    entry .t.e
    pack .t.e
    pack .t.b
}

proc popdown {} {
    puts [ .t.e get ]
    destroy .t
}

button .popup -text { press to pop up } -command popit
pack .popup
