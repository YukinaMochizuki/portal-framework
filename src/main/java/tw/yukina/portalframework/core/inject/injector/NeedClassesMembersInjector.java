package tw.yukina.portalframework.core.inject.injector;

import com.google.inject.Injector;
import com.google.inject.MembersInjector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tw.yukina.portalframework.core.inject.dependency.*;
import tw.yukina.portalframework.core.inject.annotation.NeedClasses;
import tw.yukina.portalframework.core.launch.PortalApplication;
import tw.yukina.portalframework.core.service.ServiceManager;
import tw.yukina.portalframework.core.util.AnnotationScanner;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.HashSet;

public class NeedClassesMembersInjector<T> implements MembersInjector<T> {
    private final Field field;
    private final NeedClassesSet needClassesSet;
    private Logger logger = LogManager.getLogger(NeedClassesMembersInjector.class.getName());

    public NeedClassesMembersInjector(Field field) {
        Injector injector = ServiceManager.getInjector();

        this.field = field;
        field.setAccessible(true);

        Set<Class<?>> classSet = new HashSet<>();
        Set<ClassFilter> classFilterSet = new HashSet<>();
        this.needClassesSet = new NeedClassesSet(classSet);
        NeedClasses needClasses = field.getAnnotation(NeedClasses.class);

        try {
            for (Class<? extends ClassFilter> classFilterClass : needClasses.filters()) {
                if(injector == null) classFilterSet.add(classFilterClass.getDeclaredConstructor().newInstance());
                else classFilterSet.add(injector.getInstance(classFilterClass));
            }

            if(needClasses.basePackage().equals("") && needClasses.basePackages().length == 0
                    && needClasses.basePackageInjector().equals(DefaultBasePackageInjector.class)){
                logger.error("To be inject NeedClasses you must given a base package");
                return;

//                TODO Need to be optimized
            }else if((!needClasses.basePackage().equals("") && !needClasses.basePackages()[0].equals("")
                            && !needClasses.basePackageInjector().equals(DefaultBasePackageInjector.class)) ||
                    (needClasses.basePackage().equals("") && !needClasses.basePackages()[0].equals("")
                            && !needClasses.basePackageInjector().equals(DefaultBasePackageInjector.class)) ||
                    (!needClasses.basePackage().equals("") && needClasses.basePackages()[0].equals("")
                            && !needClasses.basePackageInjector().equals(DefaultBasePackageInjector.class)) ||
                    (!needClasses.basePackage().equals("") && !needClasses.basePackages()[0].equals("")
                            && needClasses.basePackageInjector().equals(DefaultBasePackageInjector.class)) ){
                logger.error("To be inject NeedClasses you only can given a base package injector");
                return;
            }
        
            if(!needClasses.basePackage().equals("")){
                Set<Class<?>> checkClassSet = AnnotationScanner.packageClassScanRecursive(needClasses.basePackage(), PortalApplication.jarUrlClassLoader);
                
                for (Class<?> checkClass : checkClassSet)
                    checkClassAndPut(checkClass, classFilterSet, classSet);

            }else if(!needClasses.basePackages()[0].equals("")) {
                for(String basePackage : needClasses.basePackages()){
                    Set<Class<?>> checkClassSet = AnnotationScanner.packageClassScanRecursive(basePackage, PortalApplication.jarUrlClassLoader);

                    for (Class<?> checkClass : checkClassSet)
                        checkClassAndPut(checkClass, classFilterSet, classSet);
                }
            }else if(!needClasses.basePackageInjector().equals(DefaultBasePackageInjector.class)){
                BasePackageInjector basePackageInjector = (BasePackageInjector)needClasses.basePackageInjector().getDeclaredConstructor().newInstance();

                for(String basePackage : basePackageInjector.getBasePackages()) {
                    Set<Class<?>> checkClassSet = AnnotationScanner.packageClassScanRecursive(basePackage, PortalApplication.jarUrlClassLoader);

                    for (Class<?> checkClass : checkClassSet)
                        checkClassAndPut(checkClass, classFilterSet, classSet);
                }
            }
        } catch(Exception e){
          e.printStackTrace();
        }
    }

    private void checkClassAndPut(Class<?> checkClass, Set<ClassFilter> classFilterSet, Set<Class<?>> classSet){
        boolean flag = true;
        for(ClassFilter classFilter : classFilterSet)if(!classFilter.check(checkClass))flag = false;
        if(flag)classSet.add(checkClass);
    }

    public void injectMembers(T t) {
        try {
            field.set(t, needClassesSet);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

