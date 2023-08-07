# bdd-galaxy

Here is described library for implementation bdd in java project

## How to implement library in your project

1. add this dependency to your project
2. implement abstract class [AbstractObjectFactory](src/main/java/lv/katise/bdd_galaxy/di/AbstractObjectFactory.java) in
   your project
3. implement abstract class [AbstractBddTest](src/main/java/lv/katise/bdd_galaxy/AbstractBddTest.java) in your project
4. create package ```src/test/resources/META-INF/services```
5. create file with name ```io.cucumber.core.backend.ObjectFactory``` inside package from previous step
6. edit file ```io.cucumber.core.backend.ObjectFactory``` and add path to class which
   implement ```lv.katise.bdd_galaxy.di.AbstractObjectFactory``` from step 2

## Properties structure

- Properties for application have to be provided in [bdd-galaxy.properties](bdd-galaxy.properties) file
- ```galaxy.host``` - rest api host to your GA-GALAXY service
- ```galaxy.token``` - Uniq token for access to your GA-GALAXY service
- ```galaxy.project``` - test project added to your GA-GALAXY service
