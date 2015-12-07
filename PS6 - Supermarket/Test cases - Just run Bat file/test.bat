del *.class
del *.txt

set pName=Supermarket
set in=Supermarket
set out=Supermarket
set maxIndex=20

javac %pName%.java

for /L %%i in (1,1,%maxIndex%) do (
	java %pName% < %in%%%i.in > %out%%%i.txt
)

for /L %%i in (1,1,%maxIndex%) do (
	FC %out%%%i.txt %out%%%i.out
)

pause
