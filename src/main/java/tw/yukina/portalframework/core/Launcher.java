package tw.yukina.portalframework.core;

import tw.yukina.portalframework.core.launch.PortalApplication;
import tw.yukina.portalframework.core.launch.configure.DevelopmentArgs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Launcher {
    private static final Logger logger = LogManager.getLogger(Launcher.class);

    public static void main(String[] args){
        logger.info("Starting Portal Framework in Development mode");

        PortalApplication.run(new DevelopmentArgs());
    }
}
