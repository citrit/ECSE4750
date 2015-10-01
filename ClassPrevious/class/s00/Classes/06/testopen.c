/** Demo the open and read routines by printing the file testsystem.c (if it exists).

Time-stamp: </home/wrf/cgl-f99/Classes/11/testopen.c, Thu, 23 Sep 1999, 10:05:37 EDT, wrf@benvolio.ecse.rpi.edu>

*/

#include <fcntl.h>

#define MBUF 10000

main()
{
  int actread;    /* Number of bytes actually read */
  int fd, nbyte;
  char buf[MBUF];

  fd=open("testopen.c",O_RDONLY);
  if (fd<0) {
    perror("Open failed");
    exit(1);
    }
  nbyte=MBUF-1;
  actread=read(fd,buf,nbyte);
  if (actread<0) {
    perror("read failed.");
    exit(2);
  }
  buf[actread]='\0';
  printf("Number of bytes read=%d, contents=\n%s", actread, buf);
}
