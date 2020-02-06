package tw.yukina.portalframework.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PreInitialization {

     public String[] tags() default {};

}
