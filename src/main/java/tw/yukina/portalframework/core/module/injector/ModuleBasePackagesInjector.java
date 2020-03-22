package tw.yukina.portalframework.core.module.injector;

import tw.yukina.portalframework.core.inject.dependency.BasePackageInjector;
import tw.yukina.portalframework.core.service.ServiceManager;

public class ModuleBasePackagesInjector implements BasePackageInjector {
    @Override
    public String[] getBasePackages() {
        return ServiceManager.moduleBasePackages.toArray(new String[0]);
    }
}
