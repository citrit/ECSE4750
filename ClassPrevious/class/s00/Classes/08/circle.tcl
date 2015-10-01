#! /bin/sh
# the next line restarts using wish \
exec wish4.0 "$0" "$@"

# Time-stamp: </dept/ecse/graphics/Tcl/circle.tcl, Sun,  1 Mar 1998, 16:20:55 PST, wrf@speed.ecse.rpi.edu>
#

# This draws a filled circle.  It changes color when you enter and
# leave it.


canvas .c -width 300 -height 300
pack .c

set item [.c create oval 50 50 210 210 -width 5 -outline black -fill green]
.c addtag point withtag $item

.c bind point <Any-Enter> ".c itemconfig current -fill red"
.c bind point <Any-Leave> ".c itemconfig current -fill green"
