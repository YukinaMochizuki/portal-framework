package tw.yukina.portalframework.core.step.validator;

import com.google.inject.Inject;
import tw.yukina.portalframework.api.exception.ModuleNotFoundException;
import tw.yukina.portalframework.api.exception.StepVerifyException;
import tw.yukina.portalframework.api.module.ModuleContainer;
import tw.yukina.portalframework.api.step.StepContainer;
import tw.yukina.portalframework.core.module.ModuleManager;

import java.util.List;
import java.util.Optional;

public class PackageValidator implements StepValidator {

    @Inject
    private ModuleManager moduleManager;

    @Override
    public void check(StepContainer stepContainer) throws StepVerifyException {
        List<String> moduleIdList = moduleManager.getModuleIdList();

        boolean flag = false;

        for(String moduleId : moduleIdList){
            String modulePackageName = null;

            Optional<ModuleContainer> moduleContainerOptional = moduleManager.getModuleContainer(moduleId);

            if(moduleContainerOptional.isPresent())modulePackageName = moduleContainerOptional.get().getModuleClass().getPackage().getName();
            else try {
                throw new ModuleNotFoundException(moduleId);
            } catch (ModuleNotFoundException e) {
                e.printStackTrace();
            }

            assert modulePackageName != null;
            if(stepContainer.getStepRunnableClass().getName().startsWith(modulePackageName)){
                flag = true;
                break;
            }
        }

        if(!flag)throw new StepVerifyException("The step " + stepContainer.getStepRunnableClass().getName() + " didn't in any module mainClass's package");
    }
}
