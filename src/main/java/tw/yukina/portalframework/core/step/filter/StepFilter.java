package tw.yukina.portalframework.core.step.filter;

import tw.yukina.portalframework.api.module.annotation.Module;
import tw.yukina.portalframework.api.step.StepRunnable;
import tw.yukina.portalframework.api.step.annotation.Step;
import tw.yukina.portalframework.core.inject.dependency.ClassFilter;

import java.lang.reflect.Modifier;

public class StepFilter  implements ClassFilter {
    @Override
    public boolean check(Class<?> classCheck) {
        return StepRunnable.class.isAssignableFrom(classCheck) && classCheck.isAnnotationPresent(Step.class) &&
                !Modifier.isAbstract(classCheck.getModifiers()) && !classCheck.isInterface();
    }
}
