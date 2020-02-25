package tw.yukina.portalframework.core.inject.module;

import tw.yukina.portalframework.core.annotation.*;
import tw.yukina.portalframework.core.inject.listener.*;

import java.util.Map;
import java.util.HashMap;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ContainerModule extends AbstractModule {
    
    private Map<Class<?>, Class<?>> dependencyContainer;

    public ContainerModule(Map<Class<?>, Class<?>> dependencyContainer){
        this.dependencyContainer = dependencyContainer;
    }

    @Override 
    protected void configure() {
        for(Class<?> type : dependencyContainer.keySet()){
            if(type != dependencyContainer.get(type))bind((Class)type).to(dependencyContainer.get(type));
            else bind(type);
        }
    }
}
