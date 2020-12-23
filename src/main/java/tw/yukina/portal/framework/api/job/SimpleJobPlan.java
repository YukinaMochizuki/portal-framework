package tw.yukina.portal.framework.api.job;

import lombok.Builder;
import lombok.Data;
import tw.yukina.portal.framework.api.input.InputListener;
import tw.yukina.portal.framework.api.job.JobPlan;
import tw.yukina.portal.framework.api.job.WorkDefine;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class SimpleJobPlan implements JobPlan {
    private final String id;
    private final String fullId;
    private boolean isDisable;
    private boolean isAbstract;
    private boolean isReady;
    private String name;

    private String shortDepiction;
    private String depiction;
    private Set<String> tags;

    private Map<String,String> constantMap;
    private List<WorkDefine> workDefineList;
    private Set<InputListener<?>> inputListeners;

    @Override
    public void markIsReady() {
        constantMap = Collections.unmodifiableMap(constantMap);
        workDefineList = Collections.unmodifiableList(workDefineList);
        isReady = true;
    }
}
