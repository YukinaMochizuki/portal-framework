package tw.yukina.portalframework.core.inject.injector;

import com.google.inject.MembersInjector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.yukina.portalframework.core.inject.dependency.*;
import tw.yukina.portalframework.core.inject.annotation.NeedClasses;
import tw.yukina.portalframework.core.launch.PortalApplication;
import tw.yukina.portalframework.core.util.AnnotationScanner;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.HashSet;

public class NeedClassesMembersInjector<T> implements MembersInjector<T> {
    private final Field field;
    private Logger logger = LogManager.getLogger(NeedClassesMembersInjector.class.getName());
    private final NeedClassesSet needClassesSet;

    public NeedClassesMembersInjector(Field field) {
        this.field = field;
        field.setAccessible(true);

        Set<Class<?>> classSet = new HashSet<>();
        Set<ClassFilter> classFilterSet = new HashSet<>();
        this.needClassesSet = new NeedClassesSet(classSet);
        NeedClasses needClasses = field.getAnnotation(NeedClasses.class);

        try {

            for (Class<? extends ClassFilter> classFilterClass : needClasses.filters()) {
                    classFilterSet.add((ClassFilter)classFilterClass.getDeclaredConstructor().newInstance());
            }

            if(needClasses.basePackage().equals("") && needClasses.basePackageInjector().equals(DefaultBasePackageInjector.class)){
                logger.error("To be inject NeedClasses you must given a base package");
                return;
            }else if(!needClasses.basePackage().equals("") && !needClasses.basePackageInjector().equals(DefaultBasePackageInjector.class)){
                logger.error("To be inject NeedClasses you only can given a base package");
                return;
            }
        
            if(!needClasses.basePackage().equals("")){
                Set<Class<?>> checkClassSet = AnnotationScanner.packageClassScanRecursive(needClasses.basePackage(), PortalApplication.jarUrlClassLoader);
                
                for (Class<?> checkClass : checkClassSet){
                    boolean flag = true;
                    for(ClassFilter classFilter : classFilterSet)if(!classFilter.check(checkClass))flag = false;
                    if(flag)classSet.add(checkClass);
                }

            }else if(!needClasses.basePackageInjector().equals(DefaultBasePackageInjector.class)){
                BasePackageInjector basePackageInjector = (BasePackageInjector)needClasses.basePackageInjector().getDeclaredConstructor().newInstance();
                Set<Class<?>> checkClassSet = AnnotationScanner.packageClassScan(basePackageInjector.getBasePackage(), NeedClassesMembersInjector.class.getClassLoader());

                for (Class<?> checkClass : checkClassSet){
                    boolean flag = true;
                    for(ClassFilter classFilter : classFilterSet)if(!classFilter.check(checkClass))flag = false;
                    if(flag)classSet.add(checkClass);
                }
            }
        } catch(Exception e){
          e.printStackTrace();
        }
    }

    public void injectMembers(T t) {
        try {
            field.set(t, needClassesSet);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

