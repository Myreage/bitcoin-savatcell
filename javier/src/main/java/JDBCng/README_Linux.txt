Here is how to compile JDBCng and test it :

1) Checkout (or update) the project from SVN :
svn checkout ...
OR
cd JDBCng/
svn update

2) Convert all (only after the checkout !!!) the text files from Windows encoding (CP1252) to UTF-8 :
cd JDBCng/
./scripts_linux/conversion.bash

3) Compile the project (be sure to use the correct Makefile for Linux) :
cd JDBCng/
cp -a Makefile.linux Makefile
make clean ; make all

4) Make sure the user has access to /dev/ttyACM0 (on Debian, add the user to the group 'dialout')

5) Run all tests in tests.lst :
make testall DBMSHOST=/dev/ttyACM0

5) Or alternately run a specific test :
make test TEST=test.jdbc.INRIA_0.Test DBMSHOST=/dev/ttyACM0

5) Enjoy ;)
