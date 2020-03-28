package tw.yukina.portalframework.core.module.filter;

import com.google.inject.Inject;
import tw.yukina.portalframework.core.inject.dependency.ClassFilter;
import tw.yukina.portalframework.core.module.ModuleManager;

public class InModulePackageFilter implements ClassFilter {

    @Inject
    private ModuleManager moduleManager;

    @Override
    public boolean check(Class<?> classCheck) {
        return false;
    }
}
