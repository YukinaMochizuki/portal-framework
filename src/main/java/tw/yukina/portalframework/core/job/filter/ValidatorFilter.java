package tw.yukina.portalframework.core.job.filter;

import tw.yukina.portalframework.core.inject.dependency.ClassFilter;
import tw.yukina.portalframework.core.job.validator.JobValidator;

import java.lang.reflect.Modifier;

public class ValidatorFilter implements ClassFilter {
    @Override
    public boolean check(Class<?> classCheck) {
        return JobValidator.class.isAssignableFrom(classCheck) && !Modifier.isAbstract(classCheck.getModifiers())
                && !classCheck.isInterface();
    }
}
