package lv.katise.bdd_galaxy.integration.bdd;

import io.cucumber.java.StepDefinitionAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@StepDefinitionAnnotation
public @interface BddGalaxyStep {
}
