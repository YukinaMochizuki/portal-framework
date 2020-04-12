package tw.yukina.portalframework.core.job;

import tw.yukina.portalframework.api.input.InputListener;
import tw.yukina.portalframework.api.job.work.WorkDefine;

import java.util.*;

public class DefaultJobPlan extends AbstractBaseInfoJobPlan {

    private boolean isClose = false;
    private boolean isOption = false;
    private boolean isAbstract = false;
    private String implementJob;

    private Set<InputListener> inputListenersDefine = new HashSet<>();
    private List<WorkDefine> jobWorkList = new ArrayList<>();

    public DefaultJobPlan(String id) {
        super(id);
    }

    public DefaultJobPlan(String id, boolean isAbstract) {
        super(id);

        this.isAbstract = isAbstract;
    }

    @Override
    public boolean isClose() {
        return isClose;
    }

    @Override
    public void close() {
        inputListenersDefine = Collections.unmodifiableSet(inputListenersDefine);
        jobWorkList = Collections.unmodifiableList(jobWorkList);

        isClose = true;
    }

    @Override
    public boolean getIsAbstract() {
        return isAbstract;
    }

    @Override
    public boolean isOption() {
        return isOption;
    }

    @Override
    public void setIsOption(boolean isOption) {
        this.isOption = isOption;
    }

    @Override
    public Set<InputListener> getInputListenersDefine() {
        return inputListenersDefine;
    }

    @Override
    public List<WorkDefine> getJobWorkList() {
        return jobWorkList;
    }

    @Override
    public String getImplementJob() {
        return implementJob;
    }

    @Override
    public void setImplementJob(String implementJob) {
        this.implementJob = implementJob;
    }
}
