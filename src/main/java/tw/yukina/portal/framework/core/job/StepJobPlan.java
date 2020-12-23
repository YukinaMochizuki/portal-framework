package tw.yukina.portal.framework.core.job;

import lombok.Data;
import tw.yukina.portal.framework.api.input.InputListener;
import tw.yukina.portal.framework.api.job.JobPlan;
import tw.yukina.portal.framework.api.job.WorkDefine;
import tw.yukina.portal.framework.api.job.enums.WorkTypeEnum;
import tw.yukina.portal.framework.api.step.StepContainer;

import java.util.*;

@Data
public class StepJobPlan implements JobPlan {
    private final String id;
    private final String fullId;
    private boolean isDisable;
    private boolean isAbstract;
    private boolean isReady;
    private String name;

    private String shortDepiction;
    private String depiction;
    private Set<String> tags;

    private final Map<String,String> constantMap;
    private final List<WorkDefine> workDefineList;
    private final Set<InputListener<?>> inputListeners = new HashSet<>();

    public StepJobPlan(StepContainer stepContainer){
        id = stepContainer.getStepPlan().getId();
        fullId = "STEP." + stepContainer.getFullId();

        constantMap = new HashMap<>();
        workDefineList = new ArrayList<>();
        workDefineList.add(new WorkDefine(WorkTypeEnum.STEP, fullId));
    }

    @Override
    public void markIsReady() {

    }
}
