package tw.yukina.portalframework.core.step.container;

import com.google.inject.Injector;
import tw.yukina.portalframework.api.step.StepPlan;
import tw.yukina.portalframework.api.step.StepRuntimeController;

public class AnnotationRunnableStepContainer extends AbstractStepContainer {

    public AnnotationRunnableStepContainer(String fullId, StepPlan stepPlan) {
        super(fullId, stepPlan);
    }

    @Override
    public void runStep(StepRuntimeController stepRuntimeController, Injector injector) {
// TODO
    }
}