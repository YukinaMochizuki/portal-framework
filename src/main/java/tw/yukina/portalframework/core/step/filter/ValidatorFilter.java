package tw.yukina.portalframework.core.step.filter;

import tw.yukina.portalframework.api.step.StepRunnable;
import tw.yukina.portalframework.api.step.annotation.Step;
import tw.yukina.portalframework.core.inject.dependency.ClassFilter;
import tw.yukina.portalframework.core.step.validator.StepValidator;

import java.lang.reflect.Modifier;

public class ValidatorFilter implements ClassFilter {
    @Override
    public boolean check(Class<?> classCheck) {
        return StepValidator.class.isAssignableFrom(classCheck) && !Modifier.isAbstract(classCheck.getModifiers())
                && !classCheck.isInterface();
    }
}
