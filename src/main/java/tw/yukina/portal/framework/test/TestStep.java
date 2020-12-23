package tw.yukina.portal.framework.test;

import tw.yukina.portal.framework.api.step.StepRunnable;
import tw.yukina.portal.framework.api.step.StepRuntimeController;
import tw.yukina.portal.framework.api.step.annotation.Step;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Step(id = "TestStep")
public class TestStep extends StepRunnable {

    @Inject
    String testInject;

    @Override
    public void exec(StepRuntimeController stepRuntimeController) {
        stepRuntimeController.putReturn("integer", Integer.class, 52);
        System.out.println("TestStep1 Running, and the inject field is " + testInject);
    }

    @Override
    public Map<String, Class<?>> getReturnDefine() {
        return new HashMap<>();
    }
}
