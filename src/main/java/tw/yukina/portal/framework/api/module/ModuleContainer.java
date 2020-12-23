package tw.yukina.portal.framework.api.module;

import tw.yukina.portal.framework.api.job.JobPlan;
import tw.yukina.portal.framework.api.step.StepPlan;
import tw.yukina.portal.framework.api.util.BaseInfo;

import java.util.List;

public interface ModuleContainer extends BaseInfo {

  public Class<?> getModuleClass();

  public Object getModuleObject();

  public Module getModuleAnnotation();

  public List<StepPlan> getStepPlanList();

  public List<JobPlan> getJobPlanList();

}
