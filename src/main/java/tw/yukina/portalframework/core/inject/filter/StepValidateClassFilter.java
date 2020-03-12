package tw.yukina.portalframework.core.inject.filter;

import tw.yukina.portalframework.core.annotation.Initialization;
import tw.yukina.portalframework.core.annotation.StepValidate;
import tw.yukina.portalframework.core.inject.dependency.ClassFilter;

public class StepValidateClassFilter implements ClassFilter{
    public boolean check(Class<?> checkClass){
        return checkClass.isAnnotationPresent(StepValidate.class) && !checkClass.isInterface();
    }
}
