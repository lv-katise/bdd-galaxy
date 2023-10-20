package lv.katise.bdd_galaxy.integration.bdd;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@BddGalaxyStep
public @interface Group {
    String value();
}
