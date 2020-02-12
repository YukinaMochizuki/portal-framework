package tw.yukina.portalframework.core.util.annotation.testannotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassAnnotation {

     public String[] tags() default {};

}
