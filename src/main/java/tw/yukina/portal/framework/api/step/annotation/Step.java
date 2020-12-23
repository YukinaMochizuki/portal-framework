package tw.yukina.portal.framework.api.step.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Step {
    public String id();

    public String name() default "";

    public String[] tags() default {};

    public String depiction() default "";

    public String shortDepiction() default "";

    public boolean isDisable() default false;
}
