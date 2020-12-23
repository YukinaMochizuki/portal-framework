package tw.yukina.portal.framework.api.job.builder;

import com.google.inject.Injector;
import tw.yukina.portal.framework.core.job.container.AbstractJobRuntimeContainer;
import tw.yukina.portal.framework.api.job.JobPlan;

public interface JobBuilder {

    public JobPlan getJobPlan();

    public AbstractJobRuntimeContainer build();

    public AbstractJobRuntimeContainer build(Injector injector);
}
