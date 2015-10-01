// *************************************************************************
// 
// Unit Name : glo
// Purpose   : glo header file, include this file in your project to use
//             the OpenGL shape library.
// Author    : Jason Allen
// Email     : jra101@home.com
// Webpage   : DelphiGL (http://delphigl.cfxweb.net)
// 
// *************************************************************************


unit glo;

interface

uses OpenGL;

procedure gloWireSphere(radius : GLdouble; slices, stacks : GLint); stdcall;
procedure gloWireCone(base, height : GLdouble; slices, stacks : GLint); stdcall;
procedure gloWireCylinder(baseRadius, topRadius, height : GLdouble; slices, stacks : GLint); stdcall;
procedure gloWireDisk(innerRadius, outerRadius : GLdouble; slices, stacks : GLint); stdcall;
procedure gloWireCube(size : GLdouble); stdcall;
procedure gloWireTorus(innerRadius, outerRadius : GLfloat; nsides, rings : GLint); stdcall;
procedure gloWireTeapot(scale : GLdouble); stdcall;
procedure gloWireDodecahedron(); stdcall;
procedure gloWireOctahedron(); stdcall;
procedure gloWireIsosahedron(); stdcall;
procedure gloWireTetrahedron(); stdcall;

procedure gloSolidSphere(radius : GLdouble; slices, stacks : GLint); stdcall;
procedure gloSolidCone(base, height : GLdouble; slices, stacks : GLint); stdcall;
procedure gloSolidCylinder(baseRadius, topRadius, height : GLdouble; slices, stacks : GLint); stdcall;
procedure gloSolidDisk(innerRadius, outerRadius : GLdouble; slices, stacks : GLint); stdcall;
procedure gloSolidCube(size : GLdouble); stdcall;
procedure gloSolidTorus(innerRadius, outerRadius : GLfloat; nsides, rings : GLint); stdcall;
procedure gloSolidTeapot(scale : GLdouble); stdcall;
procedure gloSolidDodecahedron(); stdcall;
procedure gloSolidOctahedron(); stdcall;
procedure gloSolidIsosahedron(); stdcall;
procedure gloSolidTetrahedron(); stdcall;

const
  gloDLL = 'glo.dll';

implementation

procedure gloWireSphere; external gloDLL;
procedure gloSolidSphere; external gloDLL;

procedure gloWireCone; external gloDLL;
procedure gloSolidCone; external gloDLL;

procedure gloWireCylinder; external gloDLL;
procedure gloSolidCylinder; external gloDLL;

procedure gloWireDisk; external gloDLL;
procedure gloSolidDisk; external gloDLL;

procedure gloWireCube; external gloDLL;
procedure gloSolidCube; external gloDLL;

procedure gloWireTorus; external gloDLL;
procedure gloSolidTorus; external gloDLL;

procedure gloWireTeapot; external gloDLL;
procedure gloSolidTeapot; external gloDLL;

procedure gloWireDodecahedron; external gloDLL;
procedure gloSolidDodecahedron; external gloDLL;

procedure gloWireOctahedron; external gloDLL;
procedure gloSolidOctahedron; external gloDLL;

procedure gloWireIsosahedron; external gloDLL;
procedure gloSolidIsosahedron; external gloDLL;

procedure gloWireTetrahedron; external gloDLL;
procedure gloSolidTetrahedron; external gloDLL;

end.
 
