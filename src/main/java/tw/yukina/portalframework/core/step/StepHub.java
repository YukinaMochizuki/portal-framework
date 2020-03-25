package tw.yukina.portalframework.core.step;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Singleton;
import org.apache.logging.log4j.Logger;
import tw.yukina.portalframework.api.exception.TypeNotMatchException;
import tw.yukina.portalframework.api.step.StepContainer;
import tw.yukina.portalframework.api.step.StepRunnable;
import tw.yukina.portalframework.core.annotation.PreInitialization;
import tw.yukina.portalframework.core.inject.annotation.InjectLogger;
import tw.yukina.portalframework.core.inject.annotation.NeedClasses;
import tw.yukina.portalframework.core.inject.dependency.NeedClassesSet;
import tw.yukina.portalframework.core.module.injector.ModuleBasePackagesInjector;
import tw.yukina.portalframework.core.service.annotation.Service;
import tw.yukina.portalframework.core.service.event.PreInitializationEvent;
import tw.yukina.portalframework.core.step.filter.StepFilter;

import java.util.HashSet;
import java.util.Set;

@Service
@Singleton
@PreInitialization
public class StepHub {

    @InjectLogger
    private Logger logger;

    @NeedClasses(basePackageInjector = ModuleBasePackagesInjector.class, filters = {StepFilter.class})
    private NeedClassesSet stepClassesSet;

    @Subscribe
    @SuppressWarnings("unchecked")
    private void onPreInit(PreInitializationEvent preInitializationEvent){
        Set<StepContainer> allStepRunnableClass = new HashSet<>();

        for(Class<?> stepClass : stepClassesSet.getClassSet()) {
            try {
                allStepRunnableClass.add(new StepContainerImpl((Class<? extends StepRunnable>) stepClass));
            } catch (TypeNotMatchException e) {
                e.printStackTrace();
            }
        }
        logger.info(allStepRunnableClass.size());
    }
}
