
if [ $# -lt 1 ]; then
	echo Usage: $0 Mailfilename DirCount
	exit 99;
fi

../grading/splitmail $1 $2
if [ $? -ne 0 ];then
	exit $?
fi

copymissing() {
    if [ ! -f MRIdata.vtk ]; then
	echo linking MRIdata.vtk from source dir
	ln -s ../MRIdata.vtk .
    fi
}


for hwshar in `ls *.shar`;do
	echo "Grading: $hwshar ..."
	sed -e 's/
//g' $hwshar > $hwshar.u
	mv $hwshar.u $hwshar
	archive=`basename $hwshar .shar`
	mkdir $archive.hw
	chmod 755 $hwshar
	(cd $archive.hw
	 copymissing
	 fgrep "From:" ../$hwshar > hw3.log;echo "" >> hw3.log
	 ../$hwshar >> hw3.log
	)
done
