#include "vtk.hh"
#include <iostream.h>

int main(int argc, char *argv[]) {

  vtkDataSetReader reader;
  vtkDataSetWriter writer;

  reader.DebugOn();
  reader.SetFilename(argv[1]);
  reader.Update();

  writer.SetFilename(argv[2]);
  writer.SetFileType(VTK_ASCII);

  writer.SetInput(reader.GetOutput());
  
  writer.Write();

  return 0;
}
