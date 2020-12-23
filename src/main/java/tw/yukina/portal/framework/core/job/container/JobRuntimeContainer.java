package tw.yukina.portal.framework.core.job.container;

import tw.yukina.portal.framework.api.job.JobManager;
import tw.yukina.portal.framework.api.job.JobPlan;
import tw.yukina.portal.framework.api.job.JobRuntimeController;
import tw.yukina.portal.framework.api.job.WorkDefine;
import tw.yukina.portal.framework.api.job.enums.WorkTypeEnum;

import java.util.List;

public class JobRuntimeContainer extends AbstractJobRuntimeContainer {

    private final List<WorkDefine> workDefineList;

    public JobRuntimeContainer(JobManager jobManager, List<WorkDefine> workDefineList, JobPlan jobPlan){
        super(jobManager, jobPlan);
        this.workDefineList = workDefineList;
    }

    @Override
    public void run(JobManager jobManager, JobRuntimeController jobRuntimeController) {
        AbstractJobRuntimeContainer abstractJobRuntimeContainer = null;

        for(WorkDefine workDefine: workDefineList){
            if(workDefine.getWorkTypeEnum().equals(WorkTypeEnum.STEP)) {
                abstractJobRuntimeContainer = jobManager.findJobRuntimeContainerByFullId("STEP." + workDefine.getId());

                assert abstractJobRuntimeContainer != null;
                abstractJobRuntimeContainer.run(jobManager, jobRuntimeController);
            } else if(workDefine.getWorkTypeEnum().equals(WorkTypeEnum.JOB)) {
                abstractJobRuntimeContainer = jobManager.findJobRuntimeContainerByFullId(workDefine.getId());

                assert abstractJobRuntimeContainer != null;
                abstractJobRuntimeContainer.run(jobManager, jobRuntimeController);
            }
        }
    }
}
