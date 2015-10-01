unsigned long get_pixel(Widget w, unsigned short r, unsigned short g, unsigned short b)
{
	XColor color;

	color.red = r;
	color.green = g;
	color.blue = b;

	if (XAllocColor(XtDisplay(w), 
			DefaultColormap(XtDisplay(w),DefaultScreen(XtDisplay(w)),
			&color))
		return color.pixel;
	else {
		printf("Warning: Could not allocate requrested color\n");
		return (BlackPixel(XtDisplay(w),DefaultScreen(XtDisplay(w)));
	}
}