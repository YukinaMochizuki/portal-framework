package tw.yukina.portal.framework.api.job.util;

import tw.yukina.portal.framework.api.job.JobPlan;
import tw.yukina.portal.framework.api.job.WorkDefine;
import tw.yukina.portal.framework.api.job.enums.WorkTypeEnum;
import tw.yukina.portal.framework.api.step.StepPlan;

import java.util.ArrayList;
import java.util.List;

public class WorkDefineBuilderImpl implements WorkDefineBuilder {

    private final String moduleId;
    private boolean isClose = false;
    private final List<WorkDefine> workDefineList = new ArrayList<>();

    public WorkDefineBuilderImpl(String moduleId) {
        this.moduleId = moduleId;
    }


    @Override
    public WorkDefineBuilder startByStep(String fullId) {
        if(fullId.startsWith("this."))fullId = fullId.replace("this.", moduleId + ".");
        if(!isClose)workDefineList.add(new WorkDefine(WorkTypeEnum.STEP, fullId));
        return this;
    }

    @Override
    public WorkDefineBuilder startByStep(StepPlan stepPlan) {
        startByStep(stepPlan.getFullId());
        return this;
    }

    @Override
    public WorkDefineBuilder nextByStep(String fullId) {
        startByStep(fullId);
        return this;
    }

    @Override
    public WorkDefineBuilder nextByStep(StepPlan stepPlan) {
        startByStep(stepPlan.getFullId());
        return this;
    }

    @Override
    public WorkDefineBuilder endByStep(String fullId) {
        startByStep(fullId);
        isClose = true;

        return this;
    }

    @Override
    public WorkDefineBuilder endByStep(StepPlan stepPlan) {
        startByStep(stepPlan.getFullId());
        isClose = true;

        return this;
    }

    @Override
    public WorkDefineBuilder startByJob(String fullId) {
        if(fullId.startsWith("this."))fullId = fullId.replace("this.", moduleId + ".");
        if(!isClose)workDefineList.add(new WorkDefine(WorkTypeEnum.JOB, fullId));
        return this;
    }

    @Override
    public WorkDefineBuilder startByJob(JobPlan jobPlan) {
        startByJob(jobPlan.getFullId());
        return this;
    }

    @Override
    public WorkDefineBuilder nextByJob(String fullId) {
        startByJob(fullId);
        return this;
    }

    @Override
    public WorkDefineBuilder nextByJob(JobPlan jobPlan) {
        startByJob(jobPlan.getFullId());
        return this;
    }

    @Override
    public WorkDefineBuilder endByJob(String fullId) {
        startByJob(fullId);
        isClose = true;

        return this;
    }

    @Override
    public WorkDefineBuilder endByJob(JobPlan jobPlan) {
        startByJob(jobPlan.getFullId());
        isClose = true;

        return this;
    }

    @Override
    public List<WorkDefine> build() {
        isClose = true;
        return workDefineList;
    }
}
