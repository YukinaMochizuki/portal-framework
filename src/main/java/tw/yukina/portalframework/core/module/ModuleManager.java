package tw.yukina.portalframework.core.module;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import org.apache.logging.log4j.Logger;

import tw.yukina.portalframework.api.exception.TypeNotMatchException;
import tw.yukina.portalframework.api.module.ModuleContainer;
import tw.yukina.portalframework.core.annotation.PreInitialization;
import tw.yukina.portalframework.core.inject.annotation.InjectLogger;
import tw.yukina.portalframework.core.inject.annotation.NeedClasses;
import tw.yukina.portalframework.core.inject.dependency.NeedClassesSet;
import tw.yukina.portalframework.core.module.filter.ModuleFilter;
import tw.yukina.portalframework.core.module.injector.ModuleBasePackagesInjector;
import tw.yukina.portalframework.core.service.annotation.Service;
import tw.yukina.portalframework.core.service.event.*;

import java.util.ArrayList;
import java.util.List;

@Service
@Singleton
@PreInitialization
public class ModuleManager implements tw.yukina.portalframework.api.module.ModuleManager {

    @InjectLogger
    private Logger logger;

    @Inject
    private Injector injector;

    @NeedClasses(basePackageInjector = ModuleBasePackagesInjector.class, filters = ModuleFilter.class)
    private NeedClassesSet moduleClassesSet;

    private List<ModuleContainer> moduleContainerList;

    @Subscribe
    public void onPreInit(PreInitializationEvent preInitializationEvent){
        logger.info("Module on preInit !!!");

        moduleContainerList = new ArrayList<>();
        for(Class<?> moduleClass : moduleClassesSet.getClassSet()){
            try {
                moduleContainerList.add(new ModuleContainerImpl(moduleClass, injector));
            } catch (TypeNotMatchException e) {
                e.printStackTrace();
            }
        }
    }
}
