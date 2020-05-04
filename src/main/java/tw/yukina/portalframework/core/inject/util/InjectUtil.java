package tw.yukina.portalframework.core.inject.util;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import tw.yukina.portalframework.core.inject.annotation.InjectLogger;

import java.lang.reflect.Field;

public class InjectUtil {
    public static boolean isFieldNeedInjectInStep(Field field){
        return field.isAnnotationPresent(Inject.class) ||
                field.isAnnotationPresent(javax.inject.Inject.class);
    }

    public static String getFieldInjectNamed(Field field){
        String name;

        if(field.isAnnotationPresent(Named.class)){
            name = field.getAnnotation(Named.class).value();
        }else if(field.isAnnotationPresent(javax.inject.Named.class)){
            name = field.getAnnotation(javax.inject.Named.class).value();
        }else name = field.getType().getName();

        return name;
    }
}
