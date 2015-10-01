
#ifdef WIN32
#include "OGLWin32Ren.h"
#else
#include "OGLXRen.h"
#endif
#include "PlyGCell.h"
#include "PlyLCell.h"

#include <iostream.h>
#include <math.h>

#include "mfal.h"

TABLEID  id;

void midPoint(double minx, double miny, double maxx, double maxy,
				double& resx, double &resy) {

	resx = minx + (abs(maxx) - abs(minx));
	resy = miny + (abs(maxy) - abs(miny));
}

int
main(int argc, char *argv[]) {

	Camera *acam = new Camera;
	OGLRenderer *aren = new OGLRenderer;
	Actor *mFalActor = new Actor;
	Material *mat;
	MaterialSet *mtSet;
	PointSet *ptSet;
	PolygonCell *pgCell;
	PolylineCell *plCell;

	TABLEID		id;
	char		filename[256], AttrData[MAX_RECORDLENGTH];
	long		rCnt;
	int			i, j;
	MI_OBJ		Obj;
	double		*bbox, mx, my;

	if (argc < 2) {
		cerr<< "usage: " << argv[0] << " Filename.tab" << endl;
		exit(99);
	}
	
	MIInit(CURRENT_API_VERSION);


    id = MIOpenTable(argv[1], MODE_RDWR);
	if (id) {
		cout << "\nOPEN:\ttable ID = " << id << endl;
	} else {
	    cerr << "\nOPEN:\tcan not open table " << argv[1] << "\n\tError #" << MIGetErrorStatus() << endl;
		return(99);
	}

	MIGetNumRows(id, &rCnt);

	cout << "Number of rows: " << rCnt << endl;

	for (i=1;i<=rCnt;i++) {
		if (!MIFetchRow(id, i,  AttrData, &Obj)) {
			cerr << "\nFETCH:\tcan not fetch row #" << i << "\n\tError #" << MIGetErrorStatus()	<< endl;
			return 10;
		}
		switch(Obj.type) {
		case MFAL_POINT:
			cout << "POINT" << endl;
			break;
		case MFAL_LINE:
			cout << "LINE" << endl;
			break;
		case MFAL_PLINE:
	        cout << "PLINE" << endl;
			break;
		case MFAL_MULTIPLINE:
	        cout << "MULTIPLINE" << endl;
			break;
		case MFAL_ARC:
	        cout << "ARC" << endl;
			break;
		case MFAL_REGION:
			pgCell = new PolygonCell;
			//plCell = new PolylineCell;
			ptSet = new PointSet;
			mtSet = new MaterialSet;
			*mtSet += new Material;
			// REad in points
			ptSet->Reserve(Obj.obj.region.data->points);
			for (j = 0;j < Obj.obj.region.data->points; j++) {
				*ptSet += new PointType(Obj.obj.region.data->pPnts[j].x, Obj.obj.region.data->pPnts[j].y, 0);
				*pgCell += j;
			}
			pgCell->SetPoints(ptSet);
			bbox = ptSet->GetBBox();
			pgCell->SetMaterials(mtSet);
			*mFalActor += pgCell;
	        //cout << "REGION" << endl;
		    break;
		case MFAL_TEXT:
	        cout << "TEXT" << endl;
			break;
		case MFAL_RECT:
	        cout << "RECT" << endl;
			break;
		case MFAL_ROUNDRECT:
	        cout << "ROUNDRECT" << endl;
			break;
		case MFAL_ELLIPSE:
	        cout << "ELLIPSE" << endl;
			break;
		}
	}

	// Add this  DataSet to the Renderers collection.
	midPoint(bbox[0],bbox[1],bbox[3],bbox[4],mx, my);
	mFalActor->Translate(0-mx, 0-my, 0);
	aren->AddActor(mFalActor);

	// Lights...
	aren->AddLight(new Light(0));

	// Camera... glsetvalue
	acam->SetPosition(0, 0, 200,
					  0, 0, 0, 
					  0, 1, 0);
	aren->AddCamera(acam);


	// Initialize the Renderer, creates window and attaches OpenGL Visual
	aren->Initialize(argc, argv);
	// Starts the Event loop. (Action...)
	for ( i=0;i<360;i+=10) {
		mFalActor->RotateY(i);
		aren->Render();
	}
	aren->MainLoop();

	return 0;
}
