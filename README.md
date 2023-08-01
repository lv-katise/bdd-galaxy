# bdd-galaxy
Here is described library for implementation bdd in java project

## How to implement library in your project
1. add this dependency to your project
2. implement abstract class ```com.qa_universe.bdd_galaxy.di.AbstractObjectFactory``` in your project
3. implement abstract class ```com.qa_universe.bdd_galaxy.AbstractBddTest``` in your project
4. create package ```src/test/resources/META-INF/services```
5. create file with name ```io.cucumber.core.backend.ObjectFactory``` inside package from previous step
6. edit file ```io.cucumber.core.backend.ObjectFactory``` and add path to class which implement ```com.qa_universe.bdd_galaxy.di.AbstractObjectFactory``` from step 2
