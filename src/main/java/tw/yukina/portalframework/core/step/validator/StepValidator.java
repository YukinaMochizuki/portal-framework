package tw.yukina.portalframework.core.step.validator;

import tw.yukina.portalframework.api.exception.StepVerifyException;
import tw.yukina.portalframework.api.step.StepPlan;

public interface StepValidator {
    public void check(StepPlan stepPlan) throws StepVerifyException;
}
