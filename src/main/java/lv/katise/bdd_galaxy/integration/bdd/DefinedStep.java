package lv.katise.bdd_galaxy.integration.bdd;

import io.cucumber.java.StepDefinitionAnnotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@StepDefinitionAnnotation
@Documented
@BddGalaxyStep
public @interface DefinedStep {
    String value();
}
