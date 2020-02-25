package tw.yukina.portalframework.core.inject.module;

import tw.yukina.portalframework.core.util.*;
import tw.yukina.portalframework.core.annotation.*;

import java.util.Set;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeListener;

@BaseDependency
public class AutoScanBaseDependModule extends AbstractModule {
    @Override 
    protected void configure() {
        try {
                Set<Class<?>> baseDependencyClassSet = AnnotationScanner.packageAnnotationScanRecursive(BaseDependency.class, "tw.yukina.portalframework.core", AutoScanBaseDependModule.class.getClassLoader());
                for (Class<?> baseDependencyClass : baseDependencyClassSet){
                    if(AbstractModule.class.isAssignableFrom(baseDependencyClass))continue;

                    if(TypeListener.class.isAssignableFrom(baseDependencyClass) && !baseDependencyClass.isInterface()){
                        Object baseDependencyObject = baseDependencyClass.getDeclaredConstructor().newInstance();
                        bindListener(Matchers.any(), (TypeListener)baseDependencyObject);
                    } else {
                        bind(baseDependencyClass);
                    }
                }
        } catch(Exception e){
          e.printStackTrace();
        }
    }
}
