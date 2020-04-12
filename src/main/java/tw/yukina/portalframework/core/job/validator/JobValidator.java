package tw.yukina.portalframework.core.job.validator;

import tw.yukina.portalframework.api.exception.JobVerifyException;
import tw.yukina.portalframework.api.job.JobPlan;

public interface JobValidator {
    public void check(JobPlan jobPlan) throws JobVerifyException;
}
