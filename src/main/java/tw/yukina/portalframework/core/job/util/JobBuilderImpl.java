package tw.yukina.portalframework.core.job.util;

import tw.yukina.portalframework.api.input.InputListener;
import tw.yukina.portalframework.api.job.JobBuilder;
import tw.yukina.portalframework.api.job.JobPlan;
import tw.yukina.portalframework.api.job.enums.WorkTypeEnum;
import tw.yukina.portalframework.api.job.work.WorkDefine;
import tw.yukina.portalframework.api.step.StepPlan;
import tw.yukina.portalframework.core.job.DefaultJobPlan;

import java.util.*;

public class JobBuilderImpl implements JobBuilder {

    private String id;
    private String name = "";
    private String shortDepiction = "";
    private String depiction = "";
    private String implementJob = "";
    private boolean isOption = false;
    private boolean isAbstract = false;

    private Set<String> tags = new HashSet<>();
    private Set<InputListener> inputListeners = new HashSet<>();
    private List<WorkDefine> jobWorkList = new ArrayList<>();

    public JobBuilderImpl(String id){
        this.id = id;
    }

    @Override
    public JobBuilder setID(String id) {
        this.id = id;
        return this;
    }

    @Override
    public JobBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public JobBuilder setShortDepiction(String shortDepiction) {
        this.shortDepiction = shortDepiction;
        return this;
    }

    @Override
    public JobBuilder setDepiction(String depiction) {
        this.depiction = depiction;
        return this;
    }

    @Override
    public JobBuilder setTags(Set<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public JobBuilder setIsOption(boolean isOption) {
        this.isOption = isOption;
        return this;
    }

    @Override
    public JobBuilder addInputListenersDefine(InputListener inputListener) {
        inputListeners.add(inputListener);
        return this;
    }

    @Override
    public JobBuilder setInputListenersDefine(Set<InputListener> inputListeners) {
        this.inputListeners = inputListeners;
        return this;
    }

    @Override
    public JobBuilder setIsAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
        return this;
    }

    @Override
    public JobBuilder setImplementJob(String implementJob) {
        this.implementJob = implementJob;
        return this;
    }

    @Override
    public JobBuilder startByStep(String id) {
        jobWorkList.clear();
        jobWorkList.add(new WorkDefine(WorkTypeEnum.STEP, id));
        return this;
    }

    @Override
    public JobBuilder startByStep(StepPlan stepPlan) {
        jobWorkList.clear();
        jobWorkList.add(new WorkDefine(WorkTypeEnum.STEP, stepPlan.getId()));
        return this;
    }

    @Override
    public JobBuilder nextByStep(String id) {
        jobWorkList.add(new WorkDefine(WorkTypeEnum.STEP, id));
        return this;
    }

    @Override
    public JobBuilder nextByStep(StepPlan stepPlan) {
        jobWorkList.add(new WorkDefine(WorkTypeEnum.STEP, stepPlan.getId()));
        return this;
    }

    @Override
    public JobBuilder endByStep(String id) {
        jobWorkList.add(new WorkDefine(WorkTypeEnum.STEP, id));
        jobWorkList = Collections.unmodifiableList(jobWorkList);
        return this;
    }

    @Override
    public JobBuilder endByStep(StepPlan stepPlan) {
        jobWorkList.add(new WorkDefine(WorkTypeEnum.STEP, stepPlan.getId()));
        jobWorkList = Collections.unmodifiableList(jobWorkList);
        return this;
    }

    @Override
    public JobBuilder startByJob(String id) {
        jobWorkList.clear();
        jobWorkList.add(new WorkDefine(WorkTypeEnum.Job, id));
        return this;
    }

    @Override
    public JobBuilder startByJob(JobPlan jobPlan) {
        jobWorkList.clear();
        jobWorkList.add(new WorkDefine(WorkTypeEnum.Job, jobPlan.getId()));
        return this;
    }

    @Override
    public JobBuilder nextByJob(String id) {
        jobWorkList.add(new WorkDefine(WorkTypeEnum.Job, id));
        return this;
    }

    @Override
    public JobBuilder nextByJob(JobPlan jobPlan) {
        jobWorkList.add(new WorkDefine(WorkTypeEnum.Job, jobPlan.getId()));
        return this;
    }

    @Override
    public JobBuilder endByJob(String id) {
        jobWorkList.add(new WorkDefine(WorkTypeEnum.Job, id));
        jobWorkList = Collections.unmodifiableList(jobWorkList);
        return this;
    }

    @Override
    public JobBuilder endByJob(JobPlan jobPlan) {
        jobWorkList.add(new WorkDefine(WorkTypeEnum.Job, jobPlan.getId()));
        jobWorkList = Collections.unmodifiableList(jobWorkList);
        return this;
    }

    @Override
    public JobPlan build() {
        JobPlan jobPlan = new DefaultJobPlan(id, isAbstract, implementJob);

        jobPlan.setName(name);
        jobPlan.setDepiction(depiction);
        jobPlan.setShortDepiction(shortDepiction);
        jobPlan.setIsOption(isOption);

        jobPlan.getTags().addAll(tags);
        jobPlan.getInputListenersDefine().addAll(inputListeners);
        jobPlan.getJobWorkList().addAll(jobWorkList);
        jobPlan.close();

        return jobPlan;
    }
}
