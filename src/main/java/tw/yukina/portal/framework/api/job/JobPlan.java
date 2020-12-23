package tw.yukina.portal.framework.api.job;

import tw.yukina.portal.framework.api.input.InputListener;
import tw.yukina.portal.framework.api.util.Plan;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JobPlan extends Plan {

	public boolean isAbstract();

	public Map<String,String> getConstantMap();

	public List<WorkDefine> getWorkDefineList();

	public Set<InputListener<?>> getInputListeners();

}