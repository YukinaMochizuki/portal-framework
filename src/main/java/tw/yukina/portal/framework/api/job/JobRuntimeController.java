package tw.yukina.portal.framework.api.job;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tw.yukina.portal.framework.api.input.PostResult;
import tw.yukina.portal.framework.api.input.PostStatus;
import tw.yukina.portal.framework.api.step.StepRuntimeController;
import tw.yukina.portal.framework.core.job.container.ReturnUnit;
import tw.yukina.portal.framework.core.step.StepRuntimeControllerImpl;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class JobRuntimeController {
    private int numberOfRecursion = 1;
    @Getter
    private PostResult postResult = null;

    private final Set<ReturnUnit> returnUnitSet = new HashSet<>();

    public JobRuntimeController(Set<ReturnUnit> returnUnitSet){
        this.returnUnitSet.addAll(returnUnitSet);
    }

    public PostResult setPostReturn(PostStatus postStatus, Object postReturn){
        this.postResult = new PostResult(postStatus, postReturn, returnUnitSet);
        return postResult;
    }

    public int getNumberOfRecursion(){
        return numberOfRecursion;
    }

    public Set<ReturnUnit> getReturnUnitSet() {
        return returnUnitSet;
    }

    public StepRuntimeController getStepRuntimeController(){
        return new StepRuntimeControllerImpl(returnUnitSet);
    }
}
