package tw.yukina.portalframework.core.inject.factory;

import java.util.Map;
import java.util.HashMap;

import tw.yukina.portalframework.core.annotation.BaseDependency;
import tw.yukina.portalframework.core.inject.module.ContainerModule;

@BaseDependency
public class ContainerModuleFactory {
    private Map<Class<?>, Class<?>> dependencyMap = new HashMap<>();

    public ContainerModuleFactory setDependency(Class<?> dependency){
        dependencyMap.put(dependency, dependency);
        return this;
    }

    public ContainerModuleFactory setDependency(Class<?> type, Class<?> dependency){
        dependencyMap.put(type, dependency);
        return this;
    }

    public ContainerModule buildAndClean(){
        ContainerModule containerModule = new ContainerModule(dependencyMap);
        dependencyMap.clear();
        return containerModule;
    }
}
