package tw.yukina.portal.framework.core.job.container;

import com.google.inject.Injector;
import lombok.Getter;
import tw.yukina.portal.framework.api.job.JobManager;
import tw.yukina.portal.framework.api.job.JobPlan;
import tw.yukina.portal.framework.api.job.JobRuntimeController;

public abstract class AbstractJobRuntimeContainer {
    protected final JobManager jobManager;
    @Getter
    protected final JobPlan jobPlan;

    public AbstractJobRuntimeContainer(JobManager jobManager, Injector injector, JobPlan jobPlan) {
        this.jobManager = jobManager;
        this.jobPlan = jobPlan;
    }

    public AbstractJobRuntimeContainer(JobManager jobManager, JobPlan jobPlan) {
        this.jobManager = jobManager;
        this.jobPlan = jobPlan;
    }

    public abstract void run(JobManager jobManager, JobRuntimeController jobRuntimeController);
}
