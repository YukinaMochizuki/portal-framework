package tw.yukina.portal.framework.core.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.yukina.portal.framework.api.input.InputListener;
import tw.yukina.portal.framework.api.job.JobManager;
import tw.yukina.portal.framework.api.job.JobPlan;
import tw.yukina.portal.framework.api.job.enums.WorkTypeEnum;
import tw.yukina.portal.framework.api.step.StepContainer;
import tw.yukina.portal.framework.api.step.StepManager;
import tw.yukina.portal.framework.core.job.container.AbstractJobRuntimeContainer;
import tw.yukina.portal.framework.core.job.container.JobRuntimeContainer;
import tw.yukina.portal.framework.core.job.container.StepJobRuntimeContainer;

import java.util.HashSet;
import java.util.Set;

@Service
public class JobManagerImpl implements JobManager {

    @Autowired
    public StepManager stepManager;

    public Set<JobPlan> jobPlanSet = new HashSet<>();

    @Override
    public void addJobPlan(JobPlan jobPlan) {
        jobPlanSet.add(jobPlan);
    }

    @Override
    public Set<JobPlan> findJobPlansByListener(Class<?> eventType){
        Set<JobPlan> jobPlanSetWithTargetListener = new HashSet<>();

        for(JobPlan jobPlan: jobPlanSet){
            for(InputListener<?> inputListener: jobPlan.getInputListeners()){
                if(inputListener.getType().equals(eventType)) jobPlanSetWithTargetListener.add(jobPlan);
            }
        }
        return jobPlanSetWithTargetListener;
    }

    @Override
    public AbstractJobRuntimeContainer findJobRuntimeContainerByFullId(String fullId) {
        for(JobPlan jobPlan: jobPlanSet){
            if(jobPlan.getFullId().compareTo(fullId) == 0){
                return wrapperJobToContainer(jobPlan);
            }
        }
        return null;
    }

    private AbstractJobRuntimeContainer wrapperJobToContainer(JobPlan jobPlan){
        if(jobPlan.getWorkDefineList().size() == 1 && jobPlan.getWorkDefineList().get(0).getWorkTypeEnum().equals(WorkTypeEnum.STEP)){
            StepContainer stepContainer = stepManager.findStepContainer(jobPlan.getWorkDefineList().get(0).getId().replace("STEP.", ""));
            return new StepJobRuntimeContainer(this, stepContainer, jobPlan);
        } else return new JobRuntimeContainer(this, jobPlan.getWorkDefineList(), jobPlan);
    }
}
