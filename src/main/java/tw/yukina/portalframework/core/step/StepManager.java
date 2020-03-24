package tw.yukina.portalframework.core.step;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Singleton;
import org.apache.logging.log4j.Logger;
import tw.yukina.portalframework.api.step.annotation.Step;
import tw.yukina.portalframework.core.annotation.PreInitialization;
import tw.yukina.portalframework.core.inject.annotation.InjectLogger;
import tw.yukina.portalframework.core.inject.annotation.NeedClasses;
import tw.yukina.portalframework.core.inject.dependency.NeedClassesSet;
import tw.yukina.portalframework.core.module.injector.ModuleBasePackagesInjector;
import tw.yukina.portalframework.core.service.annotation.Service;
import tw.yukina.portalframework.core.service.event.PreInitializationEvent;
import tw.yukina.portalframework.core.step.filter.StepFilter;

@Service
@Singleton
@PreInitialization
public class StepManager implements tw.yukina.portalframework.api.step.StepManager {

    @InjectLogger
    private Logger logger;

    @NeedClasses(basePackageInjector = ModuleBasePackagesInjector.class, filters = StepFilter.class)
    private NeedClassesSet moduleClassesSet;

    @Subscribe
    public void onPreInit(PreInitializationEvent preInitializationEvent){
        logger.info(moduleClassesSet.getClassSet().size());

    }
}
