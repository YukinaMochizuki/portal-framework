package tw.yukina.portal.framework.api.step;

import tw.yukina.portal.framework.api.util.Plan;

import java.util.Map;

public interface StepPlan extends Plan {

	public StepContainer build();

	public Map<String, Class<?>> getRequireParametersDefine();

	public Map<String, Class<?>> getReturnDefine();

}
