package tw.yukina.portalframework.core.step.container;

import com.google.inject.Inject;
import tw.yukina.portalframework.api.step.StepContainer;
import tw.yukina.portalframework.api.step.StepPlan;

public abstract class AbstractStepContainer implements StepContainer {

    private String fullId;
    private StepPlan stepPlan;

    public AbstractStepContainer(String fullId, StepPlan stepPlan) {
        this.fullId = fullId;
        this.stepPlan = stepPlan;
    }

    @Override
    public StepPlan getStepPlan() {
        return stepPlan;
    }

    @Override
    public String getFullId() {
        return fullId;
    }
}
