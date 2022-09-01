# README #

VM Manager application manages VM life-cycle.

### About Application ###
VM Manager is a java based stand-alone application. Here are the major functionalities included in it:
* Create VM pool of given size
* Checkout of a VM
* Clean up of the VM
* Check-in of the VM and put back in the available pool
* If the application restarts then `shutdown hook` will be called to serialize the used pool object
* while booting up, the application will de-serialize the serialized file
* Unit test cases are available to test basic functionalities


### How to set up or run? ###

* Clone the repo
* From the root directory run `mvn clean test -P<profile name>` to run test cases
* Profile 1: unittests --> It will run all unit tests written in the repository
* Profile 2: vmusersim --> These tests cases will simulate users who will do check out and check-in of VMs
* Max Pool Size input the is given from the config.properties file which can be overridden while running the tests from command line. command is `mvn clean test -DmaxPoolSize=<max pool size>`.
* Default max pool size is 100
* All dependencies are included in pox.xml. Java 11 is required to run this application.

### Major external libraries used in the application ###
* TestNG: Test runner
* lombok: is an annotation-based Java library that allows reducing boilerplate code
* gson: To interact with JSON content
* aeonbits-owner: To interact with .properties file
* assertj-core: For fluent assertions

### Reporting ###
*  surefire report generated in /target/index.html 

### Future enhancements and in-progress tasks 
* Unit test coverage can be generated while running tests from IntelliJ editor. Code coverage trigger from command line is pending.
* Logger implementation is pending
* External reporting tool like Extent report is needed  to integrate
* While de-serializing the used pool (serialized file), need to check if the content is not null
