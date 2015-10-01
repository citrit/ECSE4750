//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    December 1996
//
//////////////////////////////////////////////////////////////////////////


#ifndef _CELL_SET_H_
#define _CELL_SET_H_

#include "PObject.h"
#include "VectorT.h"
#include "Cell.h"
#include "Matrix.h"

class ActorBC : public VectorType<Cell *> {
 public:
	 ActorBC() { dList = 0;
				 orientation[0] = orientation[1] = orientation[2] = 0;
				 scale[0] = scale[1] = scale[2] = 1.0;
				}
	~ActorBC() { }

	virtual void Render(Renderer *aren) = 0;

	char* ObjectType() { return "Actor"; }

	void PrintSelf(ostream& os) {
		os << ObjectType() << " ***** " << endl
			<< "\tNumber of Cells: " << this->Count() << endl;
	}

	virtual void Translate(float x, float y, float z) {
	  position.x += x; 
	  position.y += y; 
	  position.z += z; 
	}
	/** Change orientation */
	void RotateX(float angle)
	{
		orientation[0] += angle;
	}
	/** Change orientation */
	void RotateY(float angle)
	{
		orientation[1] += angle;
	}
	/** Change orientation */
	void RotateZ(float angle)
	{
		orientation[2] += angle;
	}
	void Scale(float *sc)
	{
		scale[0] = sc[0]; scale[1] = sc[1]; scale[2] = sc[2];
	}
 protected:
	/** Orientation as rotations around X,Y, and Z */
	float orientation[3];
	PointType position; // Obvious
	float scale[3]; // Obvious
	unsigned int dList; 
};

#endif
