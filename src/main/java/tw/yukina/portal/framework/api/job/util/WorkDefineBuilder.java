package tw.yukina.portal.framework.api.job.util;

import tw.yukina.portal.framework.api.job.JobPlan;
import tw.yukina.portal.framework.api.job.WorkDefine;
import tw.yukina.portal.framework.api.step.StepPlan;

import java.util.List;

public interface WorkDefineBuilder {
    public WorkDefineBuilder startByStep(String id);

    public WorkDefineBuilder startByStep(StepPlan stepPlan);

    public WorkDefineBuilder nextByStep(String id);

    public WorkDefineBuilder nextByStep(StepPlan stepPlan);

    public WorkDefineBuilder endByStep(String id);

    public WorkDefineBuilder endByStep(StepPlan stepPlan);

    public WorkDefineBuilder startByJob(String id);

    public WorkDefineBuilder startByJob(JobPlan jobPlan);

    public WorkDefineBuilder nextByJob(String id);

    public WorkDefineBuilder nextByJob(JobPlan jobPlan);

    public WorkDefineBuilder endByJob(String id);

    public WorkDefineBuilder endByJob(JobPlan jobPlan);

    public List<WorkDefine> build();
}
