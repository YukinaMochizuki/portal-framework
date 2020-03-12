package tw.yukina.portalframework.core.inject.filter;

import tw.yukina.portalframework.core.annotation.JobValidate;
import tw.yukina.portalframework.core.annotation.StepValidate;
import tw.yukina.portalframework.core.inject.dependency.ClassFilter;

public class JobValidateClassFilter implements ClassFilter{
    public boolean check(Class<?> checkClass){
        if(checkClass.isAnnotationPresent(JobValidate.class) && !checkClass.isInterface())return true;
        else return false;
    }
}
