package tw.yukina.portalframework.core.module.filter;

import tw.yukina.portalframework.api.module.annotation.Module;
import tw.yukina.portalframework.core.inject.dependency.ClassFilter;

import java.lang.reflect.Modifier;

public class ModuleFilter implements ClassFilter {
    @Override
    public boolean check(Class<?> classCheck) {
        return (classCheck.isAnnotationPresent(Module.class)) &&
                (!classCheck.isInterface() && !Modifier.isAbstract(classCheck.getModifiers()));
    }
}
