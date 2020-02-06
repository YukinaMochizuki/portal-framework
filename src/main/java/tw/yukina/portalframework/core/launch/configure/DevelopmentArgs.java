package tw.yukina.portalframework.core.launch.configure;

import tw.yukina.portalframework.api.launch.LaunchArgs;

public class DevelopmentArgs implements LaunchArgs {
    public String[] launchTags(){
        return new String[] {"dev"};
    }
}
