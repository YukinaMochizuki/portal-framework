package tw.yukina.portalframework.core.service.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    public Class<?> bindInterface() default void.class;
    public String[] tags() default {};
}
