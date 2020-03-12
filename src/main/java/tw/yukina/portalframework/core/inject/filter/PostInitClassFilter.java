package tw.yukina.portalframework.core.inject.filter;

import tw.yukina.portalframework.core.annotation.*;
import tw.yukina.portalframework.core.inject.dependency.ClassFilter;

public class PostInitClassFilter implements ClassFilter{
    public boolean check(Class<?> checkClass){
        return checkClass.isAnnotationPresent(PostInitialization.class) && !checkClass.isInterface();
    }
}
