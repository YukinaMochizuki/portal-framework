package tw.yukina.portal.framework.api.input;

import lombok.Data;
import tw.yukina.portal.framework.api.util.Plan;

import java.util.Set;

@Data
public class InputPlan implements Plan {
    private final String id;
    private final String fullId;
    private boolean isDisable;
    private boolean isReady;
    private String name;

    private String shortDepiction;
    private String depiction;
    private Set<String> tags;

    private final String preJobFullId;
    private final String postJobFullId;

    @Override
    public void markIsReady() {
        isReady = true;
    }
}
