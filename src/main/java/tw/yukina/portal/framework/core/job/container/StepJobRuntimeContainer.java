package tw.yukina.portal.framework.core.job.container;

import tw.yukina.portal.framework.api.job.JobManager;
import tw.yukina.portal.framework.api.job.JobPlan;
import tw.yukina.portal.framework.api.job.JobRuntimeController;
import tw.yukina.portal.framework.api.step.StepContainer;

public class StepJobRuntimeContainer extends AbstractJobRuntimeContainer{

    private final StepContainer stepContainer;

    public StepJobRuntimeContainer(JobManager jobManager, StepContainer stepContainer, JobPlan jobPlan) {
        super(jobManager, jobPlan);

        this.stepContainer = stepContainer;
    }

    @Override
    public void run(JobManager jobManager, JobRuntimeController jobRuntimeController) {
        stepContainer.runStep(jobRuntimeController.getStepRuntimeController());
    }
}
