package tw.yukina.portal.framework.core.step;

import tw.yukina.portal.framework.api.step.StepRuntimeController;
import tw.yukina.portal.framework.core.job.container.ReturnUnit;

import java.util.HashSet;
import java.util.Set;

public class StepRuntimeControllerImpl implements StepRuntimeController {
    private final Set<ReturnUnit> returnUnitSet;

    public StepRuntimeControllerImpl() {
        this.returnUnitSet = new HashSet<>();
    }

    public StepRuntimeControllerImpl(Set<ReturnUnit> returnUnitSet) {
        this.returnUnitSet = returnUnitSet;
    }

    @Override
    public void putReturn(String name, Class<?> returnType, Object object) {
        returnUnitSet.add(new ReturnUnit(name, returnType, object));
    }

    @Override
    public Object getObject(String name, Class<?> returnType){
        for(ReturnUnit returnUnit: returnUnitSet){
            if(returnUnit.getName().compareTo(name) == 0 && returnUnit.getClassType().equals(returnType))return returnUnit.getObject();
        }

        return null;
    }
}
