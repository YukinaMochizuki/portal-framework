package tw.yukina.portal.framework.api.module.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Module {

  public String id();

  public String name() default "";

  public String[] tags() default {};

  public String depiction() default "";

  public String shortDepiction() default "";

}
