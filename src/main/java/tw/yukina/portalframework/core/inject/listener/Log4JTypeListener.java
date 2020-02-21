package tw.yukina.portalframework.core.inject.listener;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.apache.logging.log4j.Logger;

import tw.yukina.portalframework.core.annotation.BaseDependency;
import tw.yukina.portalframework.core.inject.annotation.InjectLogger;
import tw.yukina.portalframework.core.inject.injector.Log4JMembersInjector;

import java.lang.reflect.Field;

@BaseDependency
public class Log4JTypeListener implements TypeListener {
    public <T> void hear(TypeLiteral<T> typeLiteral, TypeEncounter<T> typeEncounter) {
        Class<?> clazz = typeLiteral.getRawType();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType() == Logger.class &&
                    field.isAnnotationPresent(InjectLogger.class)) {
                        typeEncounter.register(new Log4JMembersInjector<T>(field));
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
