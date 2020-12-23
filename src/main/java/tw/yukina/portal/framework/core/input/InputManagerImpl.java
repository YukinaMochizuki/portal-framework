package tw.yukina.portal.framework.core.input;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.yukina.portal.framework.api.input.*;
import tw.yukina.portal.framework.api.job.JobManager;
import tw.yukina.portal.framework.api.job.JobPlan;
import tw.yukina.portal.framework.api.job.JobRuntimeController;
import tw.yukina.portal.framework.core.job.container.AbstractJobRuntimeContainer;
import tw.yukina.portal.framework.core.job.container.ReturnUnit;

import java.util.HashSet;
import java.util.Set;

@Service
public class InputManagerImpl implements InputManager {

    @Autowired
    private JobManager jobManager;

    @Override
    public InputProvider getInputProvider(Class<?> module, InputPlan inputPlan) {
        return new InputProviderImpl(inputPlan, this);
    }

    public PostResult post(InputEvent<?> inputEvent, InputProvider inputProvider){
        AbstractJobRuntimeContainer preAbstractJobRuntimeContainer = null;
        AbstractJobRuntimeContainer postAbstractJobRuntimeContainer = null;

        Set<JobPlan> jobPlanSetWithTargetListener = jobManager.findJobPlansByListener(inputEvent.getType());
        AbstractJobRuntimeContainer targetAbstractJobRuntimeContainer = null;
        InputListener<?> targetInputListener = null;

        for(JobPlan jobPlan: jobPlanSetWithTargetListener){
            for(InputListener<?> inputListener: jobPlan.getInputListeners()){
                if(inputListener.getType().equals(inputEvent.getType())) {
                    if(inputEvent.matches(inputListener.getListenerProperty())){
                        targetAbstractJobRuntimeContainer = jobManager.findJobRuntimeContainerByFullId(jobPlan.getFullId());
                        targetInputListener = inputListener;
                    }
                };
            }
        }
        if(targetAbstractJobRuntimeContainer == null)return null;

        if(targetInputListener.getPreJobFullId().compareTo("default") == 0)
            preAbstractJobRuntimeContainer = jobManager
                    .findJobRuntimeContainerByFullId(inputProvider.getInputPlan().getPreJobFullId());
        else preAbstractJobRuntimeContainer = jobManager
                .findJobRuntimeContainerByFullId(targetInputListener.getPreJobFullId());

        if(targetInputListener.getPostJobFullId().compareTo("default") == 0)
            postAbstractJobRuntimeContainer = jobManager
                    .findJobRuntimeContainerByFullId(inputProvider.getInputPlan().getPreJobFullId());
        else postAbstractJobRuntimeContainer = jobManager
                .findJobRuntimeContainerByFullId(targetInputListener.getPreJobFullId());

        Set<ReturnUnit> returnUnitSet = new HashSet<>();
        returnUnitSet.add(new ReturnUnit("rawEvent", inputEvent.getType(), inputEvent.getEvent()));
        JobRuntimeController jobRuntimeController = new JobRuntimeController(returnUnitSet);

        if(preAbstractJobRuntimeContainer != null) preAbstractJobRuntimeContainer.run(jobManager, jobRuntimeController);
        targetAbstractJobRuntimeContainer.run(jobManager, jobRuntimeController);
        if(postAbstractJobRuntimeContainer != null) postAbstractJobRuntimeContainer.run(jobManager, jobRuntimeController);

        return jobRuntimeController.getPostResult();
    }

    public Set<ReturnUnit> postAndGetRawReturn(InputEvent<?> inputEvent, HashSet<ReturnUnit> returnUnitSet){
        JobRuntimeController jobRuntimeController = new JobRuntimeController(returnUnitSet);
//        jobManager.findJobByListener(inputEvent.getType()).run(jobManager, jobRuntimeController);
        return jobRuntimeController.getReturnUnitSet();
    }
}
