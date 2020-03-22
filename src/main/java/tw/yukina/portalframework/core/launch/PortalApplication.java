package tw.yukina.portalframework.core.launch;

import tw.yukina.portalframework.api.launch.LaunchArgs;
import tw.yukina.portalframework.core.annotation.BaseDependency;
import tw.yukina.portalframework.core.inject.factory.ContainerModuleFactory;
import tw.yukina.portalframework.core.inject.module.*;
import tw.yukina.portalframework.core.util.*;
import tw.yukina.portalframework.core.service.*;

import com.google.common.reflect.ClassPath;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.AbstractModule;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Method;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class PortalApplication {
    private static final Logger logger = LogManager.getLogger(PortalApplication.class);
    private static final String MODULE_FOLDER_DEFINE_PROPERTIES = "application.properties";

    public static URLClassLoader jarUrlClassLoader;

    public static void run(LaunchArgs configure) {
        List<URL> jarUrlList = new ArrayList<>();

        try {
            logger.info("Module Folder Define in " + MODULE_FOLDER_DEFINE_PROPERTIES);
            Path path = FilesUtility.getOrCreateFile(MODULE_FOLDER_DEFINE_PROPERTIES);
            
            Properties appProps = new Properties();
            appProps.load(new FileInputStream(path.toString()));

            String moduleFolderPathString = appProps.getProperty("module.folder", "module");
            logger.info("Loading Module Folder " + moduleFolderPathString + " ...");

            Path moduleFolderPath = FilesUtility.getOrCreateDirectory(moduleFolderPathString);
            
            ModuleFileVisitor moduleFileVisitor = new ModuleFileVisitor();
            Files.walkFileTree(moduleFolderPath, moduleFileVisitor);

            List<Path> jarVisitorPathArrayList = moduleFileVisitor.getPathArrayList();
            for(Path jarPath : jarVisitorPathArrayList) jarUrlList.add(jarPath.toUri().toURL());

            logger.info("Find " + jarUrlList.size() + " module file(s)");

            PortalApplication.jarUrlClassLoader = new URLClassLoader((URL[]) jarUrlList.toArray(new URL[0]));
            ClassPath classPath = ClassPath.from(jarUrlClassLoader);

            for (ClassPath.ClassInfo classInfo : classPath.getAllClasses()) {
                if (classInfo.getName().compareTo("tw.yukina.portalframework.core.launch.PortalApplication") == 0) {
                    Class<?> mainClass = classInfo.load();
                    Object mainObject = mainClass.getDeclaredConstructor().newInstance();
                    Method startMethod = mainClass.getDeclaredMethod("start", LaunchArgs.class, List.class, URLClassLoader.class);
                    startMethod.setAccessible(true);
                    startMethod.invoke(mainObject, configure, jarVisitorPathArrayList, jarUrlClassLoader);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void start(LaunchArgs launchArgs, List<Path> moduleJarPath, URLClassLoader urlClassLoader){
        AbstractModule abstractModule = new AbstractModule(){
            @Override
            protected void configure(){
                bind(URLClassLoader.class).toInstance(urlClassLoader);
            }
        };

        Injector injector = Guice.createInjector(new AutoScanBaseDependModule(), abstractModule);
        ServiceManager serviceManager = injector.getInstance(ServiceManager.class);
        serviceManager.startInit(injector);
    }
}
