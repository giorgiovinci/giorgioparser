Pre-requirement
Insure the following is installed:
jdk1.6.x
mvn 2.1.x

To build the code start the db and then run maven builder in 2 separate prompt:
./startDB
mvn assembly:assembly

The mvn command will generate inside the target folder the jar with all the dependencies.
Then stop (Ctrl + C) and start the db:
./startDB.sh

Finally
./run.sh

Issues
 Improve mapping
 - add more info
 - fix multiple prices per unit
 - fix unit description.
 
 