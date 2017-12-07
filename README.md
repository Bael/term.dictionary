# Term Dictionary

Client server application for working with dictionary stored in sever memory. 

## Getting Started
In command line run:
'''
Git clone https://github.com/Bael/term.dictionary.git
'''

For linux:
In server and client subfolders run ./gradlew jar
It will give you a runnable jar files for running the client and server.
By default jar located in build\libs folder.

Now run ther server: 
'''
java -jar server-1.0-SNAPSHOT.jar 9005
'''

And run the client, with server ip, port, command, term and definitions specified:
'''
java -jar client-1.0.jar 127.0.0.1 9005 add good notbad pretty well

java -jar client-1.0.jar 127.0.0.1 9005 add good lucky

java -jar client-1.0.jar 127.0.0.1 9005 get good

java -jar client-1.0.jar 127.0.0.1 9005 remove good
'''


### Prerequisites
Java JDK 1.8

## Running the tests
in server and client folders:
./gradlew test

## Built With

* [Java](https://java.com/) - The platform
* [Gradle](https://gradle.org/) - Dependency Management



## Authors

* **Denis Kerner** - *Initial work* (https://github.com/bael)

## License

This project is licensed under the MIT License


