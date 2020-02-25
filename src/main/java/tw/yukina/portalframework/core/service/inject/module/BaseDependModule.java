package tw.yukina.portalframework.core.service.inject.module;

import tw.yukina.portalframework.core.annotation.BaseDependency;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

@BaseDependency
public class BaseDependModule extends AbstractModule{
    
    @Override
    protected void configure(){
        
    }

    @Provides
    @Singleton
    EventBus provideServiceManagerEventBus(){
        return new EventBus("ServiceManagerEventBus");
    }
}
