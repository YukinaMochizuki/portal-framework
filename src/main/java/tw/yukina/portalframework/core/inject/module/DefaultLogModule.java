package tw.yukina.portalframework.core.inject.module;

import tw.yukina.portalframework.core.annotation.*;
import tw.yukina.portalframework.core.inject.listener.*;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

@BaseDependency
public class DefaultLogModule extends AbstractModule {
    @Override 
    protected void configure() {
        bindListener(Matchers.any(), new Log4JTypeListener());
    }
}
