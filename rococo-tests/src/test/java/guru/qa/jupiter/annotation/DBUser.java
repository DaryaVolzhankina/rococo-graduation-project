package guru.qa.jupiter.annotation;

import guru.qa.jupiter.extension.DBUserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({DBUserExtension.class})
public @interface DBUser {
    String username() default "";

    String password() default "";
}
