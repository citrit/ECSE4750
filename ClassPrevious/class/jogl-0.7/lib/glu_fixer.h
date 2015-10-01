/* Makes 1.1 work with the 1.2 code we have */

#ifndef __GLU_FIXER_H__
#define __GLU_FIXER_H__

#ifndef GLU_VERSION_1_2

typedef GLUnurbsObj GLUnurbs;
typedef GLUtriangulatorObj GLUtesselator;
typedef GLUquadricObj GLUquadric;

#define CALLBACK 

#endif /* !GLU_VERION_1_2 */


#endif
