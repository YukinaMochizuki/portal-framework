package tw.yukina.portal.framework.api.job;

import lombok.Builder;
import lombok.Data;
import tw.yukina.portal.framework.api.input.InputListener;
import tw.yukina.portal.framework.api.job.util.WorkDefineBuilder;
import tw.yukina.portal.framework.api.module.annotation.Module;
import tw.yukina.portal.framework.api.job.util.WorkDefineBuilderImpl;

import java.util.*;

@Data
@Builder
public class JobPlanBuilder {
    private final String id;
    private final Module module;
    @Builder.Default
    private String name = "";
    @Builder.Default
    private boolean isDisable = false;
    @Builder.Default
    private boolean isAbstract = false;

    @Builder.Default
    private String shortDepiction = "";
    @Builder.Default
    private String depiction = "";
    @Builder.Default
    private Set<String> tags = new HashSet<>();

    @Builder.Default
    private Map<String,String> constantMap = new HashMap<>();
    @Builder.Default
    private List<WorkDefine> workDefineList = new ArrayList<>();
    @Builder.Default
    private Set<InputListener<?>> inputListeners = new HashSet<>();

    public WorkDefineBuilder getWorkDefineBuilder(){
        return new WorkDefineBuilderImpl(module.id());
    }

    public SimpleJobPlan build(){
        return new SimpleJobPlan.SimpleJobPlanBuilder()
                .id(id)
                .fullId(module.id() + "." + id)
                .name(name)
                .isDisable(isDisable)
                .isAbstract(isAbstract)
                .depiction(depiction)
                .shortDepiction(shortDepiction)
                .tags(tags)
                .constantMap(constantMap)
                .workDefineList(workDefineList)
                .inputListeners(inputListeners)
                .build();
    }
}