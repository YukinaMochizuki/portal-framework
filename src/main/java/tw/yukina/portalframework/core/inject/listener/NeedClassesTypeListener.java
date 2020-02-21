package tw.yukina.portalframework.core.inject.listener;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import tw.yukina.portalframework.core.annotation.BaseDependency;
import tw.yukina.portalframework.core.inject.annotation.NeedClasses;
import tw.yukina.portalframework.core.inject.dependency.NeedClassesSet;
import tw.yukina.portalframework.core.inject.injector.NeedClassesMembersInjector;

import java.lang.reflect.Field;

@BaseDependency
public class NeedClassesTypeListener implements TypeListener {
    public <T> void hear(TypeLiteral<T> typeLiteral, TypeEncounter<T> typeEncounter) {
        Class<?> clazz = typeLiteral.getRawType();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType() == NeedClassesSet.class &&
                    field.isAnnotationPresent(NeedClasses.class)) {
                    typeEncounter.register(new NeedClassesMembersInjector<T>(field));
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
