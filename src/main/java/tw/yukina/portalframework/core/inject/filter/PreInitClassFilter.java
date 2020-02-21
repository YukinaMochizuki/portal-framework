package tw.yukina.portalframework.core.inject.filter;

import tw.yukina.portalframework.core.annotation.*;
import tw.yukina.portalframework.core.inject.dependency.ClassFilter;

public class PreInitClassFilter implements ClassFilter{
    public boolean check(Class<?> checkClass){
        if(checkClass.isAnnotationPresent(PreInitialization.class) && !checkClass.isInterface())return true;
        else return false;
    }
}
