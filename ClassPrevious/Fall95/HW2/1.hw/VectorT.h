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
//  Date:    October 3, 1995
//
//////////////////////////////////////////////////////////////////////////


// Vector.H

#ifndef Vector_H
#define Vector_H

#include <string.h>
#include <iostream.h>

#define	DEFAULTINCREMENT	100

template <class T> class VectorType
{
public:
   VectorType()
   {
      Allocated=DEFAULTINCREMENT;
      Data=new T[Allocated];
      Used=0;
   }

   ~VectorType(void)
   {
      delete[] Data;
   }

/*   VectorType<T>& operator!(void)
   {
      int i;
      if(Data)
      {
         for(i=0;i<Allocated;i++)
            !Data[i];
      }
      Data=NULL;
      Allocated=0;
      Used=0;
      return *this;
   } */

   void Reserve(int newSize)
   {
      T *temp;
      int i,oldSize;

      if(newSize >= Allocated)
      {
         oldSize=Allocated;
         Allocated=newSize+DEFAULTINCREMENT;
         temp=Data;
         Data=new T[Allocated];
         if(Data==NULL)
         {
            cout << "Vector.Reserve failed.  newSize=" << newSize << endl;
            return;
         }
         memcpy((void*)Data, (void*)temp, oldSize*sizeof(T));
/*         for(i=0;i<oldSize;i++)
            !temp[i]; */
         delete[] temp;
      }
   }

   void Demand(int newSize)
   {
      Reserve(newSize);
      Used=newSize;
   }

   int Count(void)
   {
      return Used;
   }

   T& operator[](int index)
   {
     if (index > Used)
       Demand(index);
     return Data[index];
   }

   operator T*()
   {
      return Data;
   }

   VectorType<T>& operator+=(T datum)
   {
      Reserve(Used+1);
      Data[Used]=datum;
      Used++;
      return *this;
   }

protected:
   T *Data;
   int Allocated;
   int Used;
};

#endif
