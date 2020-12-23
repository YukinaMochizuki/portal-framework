package tw.yukina.portal.framework.api.step;

import com.google.inject.Injector;

public interface StepContainer {

    public String getFullId();

    public StepPlan getStepPlan();

    public void runStep(StepRuntimeController stepRuntimeController);

}
