package tw.yukina.portalframework.core.inject.annotation;

import tw.yukina.portalframework.core.inject.dependency.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedClasses {
     public String basePackage() default "";

     public Class<? extends BasePackageInjector> basePackageInjector() default DefaultBasePackageInjector.class;

     public Class<? extends ClassFilter>[] filters();
}
