		Introduction to Programming with Java 3D
			 Application Examples
	--------------------------------------------------------

Applications
	The following .java files are each applications and applets that,
	when executed, bring up a window into which they render 3D shapes
	using Java 3D.

		A3DApplet.java
		Drag.java
		ExAmbientLight.java
		ExAppearance.java
		ExBackgroundColor.java
		ExBackgroundImage.java     (requires -Xmx32m flag)
		ExBluePrint.java
		ExClip.java
		ExDepthCue.java
		ExDirectionalLight.java
		ExExponentialFog.java
		ExHenge.java
		ExLightBounds.java
		ExLightScope.java
		ExLinearFog.java
		ExPointLight.java
		ExRaster.java
		ExSound.java
		ExSpotLight.java
		ExSwitch.java
		ExTexture.java
		ExTransform.java
		GearBox.java
		HelloUniverse.java
		PickWorld.java
		SphereMotion.java

	Most applications provide a menu bar with a options with which
	to enable or disable example features.

	Most applications respond to mouse drags within the window, which
	rotate, translate, or scale the 3D content.  From the "View" menu,
	select "Examine" to manipulate the content as if it is a shape held
	at arm's length.  Select "Walk" to manipulate the content as if you
	are walking through a scene.

	All source code is commented to highlight features of interest and
	explain tricks and techniques.


Execution
	To execute a Java application from the command-line (DOS or UNIX),
	use the Sun JDK execution tool "java".  Type "java", followed by
	the Java application class name (without the ".java").  For example:

		java ExHenge

	runs the "ExHenge.java" application using the JDK "java" tool.
	The application brings up a window with a menubar and Java 3D canvas.

	Most applications accept a "-d" command-line option to print status
	messages as the application starts up.  For example:

		java ExHenge -d

	Some applications require that you extend the default heap size
	in order to make room for loading textures and sounds.  The default
	size is about 16M, which is too small.  To extend the size, add
	the "-Xmx" option to the command-line *before* the class file name.
	This option increases the maximum heap size to whatever size you
	specify immediately after the option, without intervening spaces.
	For example, to extend the heap size to 64MBytes, type:

		java -Xmx64m ExBackgroundImage

	You only need to add the above "-Xmx" option if an application
	reports an "Out of Memory" error when you try to run it without
	the option.


Caveats
	For notes on Java 3D support issues with this release of the
	tutorial examples, please see the file "caveat.htm" in the
	"notes" folder, up one folder from this one.
