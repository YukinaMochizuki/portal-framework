package tw.yukina.portalframework.core.service.filter;

import tw.yukina.portalframework.core.service.annotation.*;
import tw.yukina.portalframework.core.inject.dependency.ClassFilter;

public class ServiceClassFilter implements ClassFilter{
    public boolean check(Class<?> checkClass){
        return (checkClass.isAnnotationPresent(Service.class) || checkClass.isAnnotationPresent(Component.class)) && !checkClass.isInterface();
    }
}
