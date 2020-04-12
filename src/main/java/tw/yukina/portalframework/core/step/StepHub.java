package tw.yukina.portalframework.core.step;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.apache.logging.log4j.Logger;
import tw.yukina.portalframework.api.exception.StepVerifyException;
import tw.yukina.portalframework.api.exception.TypeNotMatchException;
import tw.yukina.portalframework.api.step.StepPlan;
import tw.yukina.portalframework.api.step.StepRunnable;
import tw.yukina.portalframework.core.annotation.PreInitialization;
import tw.yukina.portalframework.core.inject.annotation.InjectLogger;
import tw.yukina.portalframework.core.inject.annotation.NeedClasses;
import tw.yukina.portalframework.core.inject.dependency.NeedClassesSet;
import tw.yukina.portalframework.core.launch.PortalApplication;
import tw.yukina.portalframework.core.module.injector.ModuleBasePackagesInjector;
import tw.yukina.portalframework.core.service.annotation.Service;
import tw.yukina.portalframework.core.service.event.PreInitializationEvent;
import tw.yukina.portalframework.core.service.event.StepValidateEvent;
import tw.yukina.portalframework.core.step.filter.StepFilter;
import tw.yukina.portalframework.core.step.filter.ValidatorFilter;
import tw.yukina.portalframework.core.step.validator.StepValidator;

import java.util.HashSet;
import java.util.Set;

@Service
@Singleton
@PreInitialization
public class StepHub {

    @Inject
    private Injector injector;

    @InjectLogger
    private Logger logger;

    @NeedClasses(basePackageInjector = ModuleBasePackagesInjector.class, filters = {StepFilter.class})
    private NeedClassesSet stepClassesSet;

    @NeedClasses(basePackage = "tw.yukina.portalframework.core.step.validator", filters = {ValidatorFilter.class})
    private NeedClassesSet stepValidatorClassesSet;

    private Set<StepPlan> stepPlanSet = new HashSet<>();

    @Subscribe
    @SuppressWarnings("unchecked")
    private void onPreInit(PreInitializationEvent preInitializationEvent){

//        for(Class<?> stepClass : stepClassesSet.getClassSet()) {
//            try {
//                stepPlanSet.add(new AnnotationRunnableStepPlan((Class<? extends StepRunnable>) stepClass));
//            } catch (TypeNotMatchException e) {
//                e.printStackTrace();
//            }
//        }
        logger.info(stepPlanSet.size());
    }

    @Subscribe
    private void onStepValidateEvent(StepValidateEvent stepValidateEvent){
        logger.info(stepValidatorClassesSet.getClassSet().size());

        try {
            for(StepPlan stepPlan : stepPlanSet){
                for(Class<?> stepValidatorClass :  stepValidatorClassesSet.getClassSet()){
                    StepValidator stepValidator = (StepValidator) injector.getInstance(stepValidatorClass);
                    stepValidator.check(stepPlan);
                }
            }
        } catch (StepVerifyException e) {
            e.printStackTrace();
            PortalApplication.ProgramAborted();
        }
    }
}
