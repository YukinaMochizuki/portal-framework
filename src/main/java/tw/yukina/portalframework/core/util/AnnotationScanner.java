package tw.yukina.portalframework.core.util;

import com.google.common.reflect.ClassPath;

import java.lang.reflect.*;
import java.lang.annotation.Annotation;
import java.util.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class AnnotationScanner {
 
    private static final Logger logger = LogManager.getLogger(AnnotationScanner.class);

    public static Map<Class<? extends Annotation>, Set<Class<?>>> packageAnnotationsScan(Set<Class<? extends Annotation>> annotations, String packageName, ClassPath classPath){
        Map<Class<? extends Annotation>, Set<Class<?>>> hasAnnotationsMap = new HashMap<>();
        for(Class<? extends Annotation> annotation : annotations)hasAnnotationsMap.put(annotation, packageAnnotationScan(annotation, packageName, classPath));
        return hasAnnotationsMap;
    }

    public static Map<Class<? extends Annotation>, Set<Class<?>>> packageAnnotationsScanRecursive(Set<Class<? extends Annotation>> annotations, String packageName, ClassPath classPath){
        Map<Class<? extends Annotation>, Set<Class<?>>> hasAnnotationsMap = new HashMap<>();
        for(Class<? extends Annotation> annotation : annotations)hasAnnotationsMap.put(annotation, packageAnnotationScanRecursive(annotation, packageName, classPath));
        return hasAnnotationsMap;
    }

    public static Set<Class<?>> packageAnnotationScan(Class<? extends Annotation> annotation, String packageName, ClassPath classPath){
        Set<Class<?>> hasAnnotationSet = new HashSet<>();

        for(ClassPath.ClassInfo classInfo : classPath.getTopLevelClasses(packageName)){
            if(isClassHasAnnotation(annotation, classInfo.load())) hasAnnotationSet.add(classInfo.load());
        }
        return hasAnnotationSet;
    }

    public static Set<Class<?>> packageAnnotationScanRecursive(Class<? extends Annotation> annotation, String packageName, ClassPath classPath){
        Set<Class<?>> hasAnnotationSet = new HashSet<>();

        for(ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(packageName)){
            if(isClassHasAnnotation(annotation, classInfo.load())) hasAnnotationSet.add(classInfo.load());
        }
        return hasAnnotationSet;
    }

    private static boolean isClassHasAnnotation(Class<? extends Annotation> annotation, Class<?> source){
        if(source.isAnnotationPresent(annotation)){
            logger.trace("The class " + source.getName() + " has a annotation " + annotation.getName());
            return true;
        } else {
            logger.trace("The class " + source.getName() + " doesn't have a annotation " + annotation.getName());
            return false;
        }
    }

    public static Set<Method> methodAnnotationScan(Class<? extends Annotation> annotation, Class<?> source){
        Set<Method> hasAnnotationSet = new HashSet<>();

        Method[] sourceMethods = source.getDeclaredMethods();
        for(Method method : sourceMethods){
            if(method.isAnnotationPresent(annotation)){
                logger.trace("The method " + method.getName() + " has a annotation " + annotation.getName() + " in class " + method.getDeclaringClass().getName());
                hasAnnotationSet.add(method);
            }
        }
        return hasAnnotationSet;
    }

    public static Set<Field> fieldAnnotationScan(Class<? extends Annotation> annotation, Class<?> source){
        Set<Field> hasAnnotationSet = new HashSet<>();

        Field[] sourceFields = source.getDeclaredFields();
        for(Field field : sourceFields){
            if(field.isAnnotationPresent(annotation)){
                logger.trace("The field " + field.getName() + " has a annotation " + annotation.getName() + " in class " + field.getDeclaringClass().getName());
                hasAnnotationSet.add(field);
            }
        }
        return hasAnnotationSet;
    }
}
