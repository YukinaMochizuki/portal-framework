package tw.yukina.portalframework.core.service;

import tw.yukina.portalframework.core.inject.annotation.*;
import tw.yukina.portalframework.core.inject.dependency.*;
import tw.yukina.portalframework.core.inject.filter.*;
import tw.yukina.portalframework.core.inject.factory.ContainerModuleFactory;
import tw.yukina.portalframework.core.service.annotation.Component;
import tw.yukina.portalframework.core.service.annotation.Service;
import tw.yukina.portalframework.core.service.event.*;
import tw.yukina.portalframework.core.service.filter.ServiceClassFilter;
import tw.yukina.portalframework.core.util.AnnotationScanner;

import org.apache.logging.log4j.Logger;

import java.util.*;
import java.net.URLClassLoader;

import com.google.common.eventbus.EventBus;
import com.google.common.reflect.ClassPath;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@SuppressWarnings("UnstableApiUsage")
public class ServiceManager {

    @NeedClasses(basePackage = "tw.yukina.portalframework.core", filters = {ServiceClassFilter.class, PreInitClassFilter.class})
    private NeedClassesSet preInitClassesSet;

    @NeedClasses(basePackage = "tw.yukina.portalframework.core", filters = {ServiceClassFilter.class, InitClassFilter.class})
    private NeedClassesSet initClassesSet;

    @NeedClasses(basePackage = "tw.yukina.portalframework.core", filters = {ServiceClassFilter.class, StepValidateClassFilter.class})
    private NeedClassesSet stepValidateClassesSet;

    @NeedClasses(basePackage = "tw.yukina.portalframework.core", filters = {ServiceClassFilter.class, JobValidateClassFilter.class})
    private NeedClassesSet jobValidateClassesSet;

    @NeedClasses(basePackage = "tw.yukina.portalframework.core", filters = {ServiceClassFilter.class, PostInitClassFilter.class})
    private NeedClassesSet postInitClassesSet;

    @InjectLogger
    private Logger logger;

    @Inject
    private ContainerModuleFactory containerModuleFactory;

    @Inject
    private EventBus eventBus;

    @Inject
    private Properties appProperties;

    @Inject
    private URLClassLoader urlClassLoader;

    public static List<String> moduleBasePackages;

    private static Injector injector;

    public void startInit(Injector injector){
        findModuleBasePackages();

        ServiceManager.injector = injector;

        logger.info("Starting to initialization all service");

        logger.trace("preInitClassesSet.size() is " + preInitClassesSet.getClassSet().size());
        logger.trace("initClassesSet.size() is " + initClassesSet.getClassSet().size());
        logger.trace("stepValidateClassesSet.size() is " + stepValidateClassesSet.getClassSet().size());
        logger.trace("jobValidateClassesSet.size() is " + jobValidateClassesSet.getClassSet().size());
        logger.trace("postInitClassesSet.size() is " + postInitClassesSet.getClassSet().size());

        eventBus.register(this);

        configureAndInit(preInitClassesSet.getClassSet(), new PreInitializationEvent());
        configureAndInit(initClassesSet.getClassSet(), new InitializationEvent());
        configureAndInit(stepValidateClassesSet.getClassSet(), new StepValidateEvent());
        configureAndInit(jobValidateClassesSet.getClassSet(), new JobValidateEvent());
        configureAndInit(postInitClassesSet.getClassSet(),  new PostInitializationEvent());

        eventBus.post(new LoadCompleteEvent());
    } 

    @Subscribe
    private void onLoadComplete(LoadCompleteEvent loadCompleteEvent){
        logger.info("Services load complete");
    }

    @Subscribe
    private void onStoppingSignal(FrameworkStopSignal frameworkStopSignal){
        eventBus.post(new FrameworkStopping());
        eventBus.post(new FrameworkStopped());
    }

    private void findModuleBasePackages(){
        moduleBasePackages = new ArrayList<>();
        String value;
        for(int i = 0; (value = appProperties.getProperty("module.basePackages" + "." + i)) != null; i++) {
            moduleBasePackages.add(value);
        }
    }

    private void configureAndInit(Set<Class<?>> initClassSet, Object event){
        Set<Class<?>> singletonDependencySet = new HashSet<>();

        for(Class<?> initClass : initClassSet){
            Class<?> type = null;

            if(initClass.isAnnotationPresent(Component.class))
                type = initClass.getAnnotation(Component.class).bindInterface().equals(void.class) ?
                        initClass : initClass.getAnnotation(Component.class).bindInterface();

            if(initClass.isAnnotationPresent(Service.class))
                type = initClass.getAnnotation(Service.class).bindInterface().equals(void.class) ?
                        initClass : initClass.getAnnotation(Service.class).bindInterface();

            containerModuleFactory.setDependency(type, initClass);
            if(initClass.isAnnotationPresent(Singleton.class))singletonDependencySet.add(type);
        }

        logger.trace("In " + event.getClass().getSimpleName() + ", " + "singletonDependencySet.size() is " + singletonDependencySet.size());

        injector = injector.createChildInjector(containerModuleFactory.buildAndClean());

        for(Class<?> singletonDependency : singletonDependencySet){
            Object object = injector.getInstance(singletonDependency);
            eventBus.register(object);
        }

        eventBus.post(event);
    }

    public List<String> getModuleBasePackages() {
        return moduleBasePackages;
    }

    public static Injector getInjector(){
        return ServiceManager.injector;
    }
}
