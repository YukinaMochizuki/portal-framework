package tw.yukina.portalframework.core.module;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Singleton;

import org.apache.logging.log4j.Logger;

import tw.yukina.portalframework.core.annotation.PreInitialization;
import tw.yukina.portalframework.core.inject.annotation.InjectLogger;
import tw.yukina.portalframework.core.service.annotation.Service;
import tw.yukina.portalframework.core.service.event.*;

@Service
@Singleton
@PreInitialization
public class ModuleManager {

    @InjectLogger
    private Logger logger;

    @Subscribe
    public void onPreInit(PreInitializationEvent preInitializationEvent){
        //logger.info("Module on preInit !!!");
    }
}
