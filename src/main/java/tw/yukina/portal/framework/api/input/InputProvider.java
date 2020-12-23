package tw.yukina.portal.framework.api.input;

import lombok.Getter;

import java.util.Map;

public abstract class InputProvider {

    @Getter
    private final InputPlan inputPlan;

    public InputProvider(InputPlan inputPlan){
        this.inputPlan = inputPlan;
    }

    public abstract Object postInputRequest(InputEvent<?> inputEvent);
}
