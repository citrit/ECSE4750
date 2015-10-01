#include <iostream.h>
#include <fstream.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

void yankNextMessage(ifstream &istr, int mcnt);
void getNextShellArchive(ifstream &istr, ofstream &ostr);

int main (int argc, char *argv[]) {

  int mcnt;
  
  if (argc < 2) {
    cerr << "usage: " << argv[0] << " MailFileName dircount" << endl;
    return 99;
  }

  ifstream istr(argv[1]);
  if (! istr) {
    cerr << "error: unable to open <" << argv[1] << "> for read." << endl;
    return 101;
  }
  if (argc == 3) 
	mcnt = atoi(argv[2]);
  else
	mcnt = 1;
 
  do {
    yankNextMessage(istr, mcnt);
    mcnt++;
  } while (! istr.eof());

  return 0;
}

void yankNextMessage(ifstream &istr, int mcnt) {

  char line[128];
  ofstream ostr;

  sprintf(line, "%d.shar", mcnt);
  ostr.open(line);
  cout << "Creating: " << line << endl;
  while (strncmp(line, "From:", 5) != 0 && ! istr.eof())
    istr.getline(line, 128);
  getNextShellArchive(istr, ostr);
  ostr << "# " << line << endl;
  ostr.close();
}

void getNextShellArchive(ifstream &istr, ofstream &ostr) {

  char line[128];

  while (strncmp(line, "#!/bin/sh", 9) != 0 &&
	 strncmp(line, "#! /bin/sh", 10) != 0 && ! istr.eof())
    istr.getline(line,128);

  do {
    if ( strncmp(line, "From ", 5) == 0) {
      do {
	istr.getline(line, 128);
      }while (strncmp(line, "X-UIDL:", 7) != 0  && ! istr.eof());
      istr.getline(line, 128);
      istr.getline(line, 128);
    }
    ostr << line << endl;
    istr.getline(line, 128);
  } while (strncmp(line, "exit 0", 6) != 0 && ! istr.eof());
  ostr << line << endl;

}
