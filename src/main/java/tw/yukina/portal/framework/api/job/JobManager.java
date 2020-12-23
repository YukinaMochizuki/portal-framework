package tw.yukina.portal.framework.api.job;

import tw.yukina.portal.framework.core.job.container.AbstractJobRuntimeContainer;

import java.util.Set;

public interface JobManager {
    public void addJobPlan(JobPlan jobPlan);

    Set<JobPlan> findJobPlansByListener(Class<?> eventType);

    public AbstractJobRuntimeContainer findJobRuntimeContainerByFullId(String fullId);
}
