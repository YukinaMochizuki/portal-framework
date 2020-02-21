package tw.yukina.portalframework.core.service;

import tw.yukina.portalframework.core.inject.annotation.*;
import tw.yukina.portalframework.core.inject.dependency.*;
import tw.yukina.portalframework.core.inject.filter.PreInitClassFilter;
import tw.yukina.portalframework.core.service.filter.ServiceClassFilter;

import org.apache.logging.log4j.Logger;

public class ServiceManager {

    @NeedClasses(basePackage = "tw.yukina.portalframework.core", filters = {ServiceClassFilter.class, PreInitClassFilter.class})
    private NeedClassesSet needClassesSet;

    @InjectLogger
    private Logger logger;

    public void startInit(){
        logger.info("Starting it !!");
        System.out.println(needClassesSet.getClassSet().size() + " " + needClassesSet.getClassSet().toString());
  } 
}
