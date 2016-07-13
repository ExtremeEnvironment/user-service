# UserService

This application was generated using JHipster, you can find documentation and help at [https://jhipster.github.io](https://jhipster.github.io).

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

	Install Java 8
	Install Git
	Install Node.js
		Install Yeoman: npm install -g yo
		Install Bower: npm install -g bower
		Install Gulp: npm install -g gulp
		Install JHipster: npm install -g generator-jhipster
		
## Building with JHipster Registry
    
    alle Services in https://github.com/ExtremeEnvironment über Git auschecken

    Zusätzlich die Registry auschecken :

    $ git clone https://github.com/jhipster/jhipster-registry.git
    $ cd jhipster-registry
    $ mvn 
    
    jeden Service über $ ./gradlew bootRun starten


## Building for production

To optimize the MessageService client for production, run:

    ./gradlew -Pprod clean bootRepackage

To ensure everything worked, run:

    java -jar build/libs/*.war --spring.profiles.active=prod

## Continuous Integration

To setup this project in Jenkins, use the following configuration:

* Project name: `UserService`
* Source Code Management
    * Git Repository: `git@github.com:xxxx/MessageService.git`
    * Branches to build: `*/master`
    * Additional Behaviours: `Wipe out repository & force clone`
* Build Triggers
    * Poll SCM / Schedule: `H/5 * * * *`
* Build
    * Invoke Gradle script / Use Gradle Wrapper / Tasks: `-Pprod clean test bootRepackage`
* Post-build Actions
    * Publish JUnit test result report / Test Report XMLs: `build/test-results/*.xml`

[JHipster]: https://jhipster.github.io/
