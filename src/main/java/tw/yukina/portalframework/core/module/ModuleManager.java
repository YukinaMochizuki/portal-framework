package tw.yukina.portalframework.core.module;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Singleton;

import org.apache.logging.log4j.Logger;

import tw.yukina.portalframework.core.annotation.PreInitialization;
import tw.yukina.portalframework.core.inject.annotation.InjectLogger;
import tw.yukina.portalframework.core.inject.annotation.NeedClasses;
import tw.yukina.portalframework.core.inject.dependency.NeedClassesSet;
import tw.yukina.portalframework.core.module.filter.ModuleFilter;
import tw.yukina.portalframework.core.module.injector.ModuleBasePackagesInjector;
import tw.yukina.portalframework.core.service.annotation.Service;
import tw.yukina.portalframework.core.service.event.*;

@Service
@Singleton
@PreInitialization
public class ModuleManager implements tw.yukina.portalframework.api.module.ModuleManager {

    @InjectLogger
    private Logger logger;

    @NeedClasses(basePackageInjector = ModuleBasePackagesInjector.class, filters = ModuleFilter.class)
    private NeedClassesSet moduleClassesSet;

    @Subscribe
    public void onPreInit(PreInitializationEvent preInitializationEvent){
        logger.info("Module on preInit !!!");
    }
}
