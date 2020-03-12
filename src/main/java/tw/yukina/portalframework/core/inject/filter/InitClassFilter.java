package tw.yukina.portalframework.core.inject.filter;

import tw.yukina.portalframework.core.annotation.*;
import tw.yukina.portalframework.core.inject.dependency.ClassFilter;

public class InitClassFilter implements ClassFilter{
    public boolean check(Class<?> checkClass){
        return checkClass.isAnnotationPresent(Initialization.class) && !checkClass.isInterface();
    }
}
