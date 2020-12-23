package tw.yukina.portal.framework.api.step;

import java.util.Map;

public abstract class StepRunnable {

    public abstract void exec(StepRuntimeController stepRuntimeController);

    public abstract Map<String, Class<?>> getReturnDefine();

}
