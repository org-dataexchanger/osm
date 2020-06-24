package org.dataexchanger.osm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @author Syed Mainul Hasan
 */

@Target(ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Id {
    String value() default "id";
}
