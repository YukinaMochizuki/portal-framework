package tw.yukina.portal.framework.core.step;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.yukina.portal.framework.api.job.JobManager;
import tw.yukina.portal.framework.api.step.StepContainer;
import tw.yukina.portal.framework.api.step.StepManager;
import tw.yukina.portal.framework.api.step.StepPlan;
import tw.yukina.portal.framework.core.job.StepJobPlan;

import java.util.HashSet;
import java.util.Set;

@Service
public class StepManagerImpl implements StepManager {

    @Autowired
    public JobManager jobManager;

    private Set<StepPlan> stepPlanSet = new HashSet<>();
    private Set<StepContainer> stepContainerSet = new HashSet<>();
    private Set<StepJobPlan> stepJobPlanSet = new HashSet<>();

    @Override
    public void addStepPlan(StepPlan stepPlan) {
        stepPlanSet.add(stepPlan);
    }

    public void build(){
        for(StepPlan stepPlan: stepPlanSet){
//            StepContainer stepContainer = stepPlan.build();
            stepContainerSet.add(stepPlan.build());
            stepJobPlanSet.add(new StepJobPlan(stepPlan.build()));
        }
    }

    public void registerJob(){
        for(StepJobPlan stepJobPlan: stepJobPlanSet){
            jobManager.addJobPlan(stepJobPlan);
        }
    }

    @Override
    public StepContainer findStepContainer(String fullId) {
        for(StepContainer stepContainer: stepContainerSet){
            if(stepContainer.getFullId().compareTo(fullId) == 0)return stepContainer;
        }

        return null;
    }
}
