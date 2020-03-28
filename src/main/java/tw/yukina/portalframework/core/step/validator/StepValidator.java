package tw.yukina.portalframework.core.step.validator;

import tw.yukina.portalframework.api.exception.StepVerifyException;
import tw.yukina.portalframework.api.step.StepContainer;

public interface StepValidator {
    public void check(StepContainer stepContainer) throws StepVerifyException;
}
