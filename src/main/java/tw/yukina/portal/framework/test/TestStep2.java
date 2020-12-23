package tw.yukina.portal.framework.test;

import tw.yukina.portal.framework.api.step.StepRunnable;
import tw.yukina.portal.framework.api.step.StepRuntimeController;
import tw.yukina.portal.framework.api.step.annotation.Step;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Step(id = "TestStep2")

public class TestStep2 extends StepRunnable {

    @Inject
    private Integer integer;

    @Inject
    private String testInject;

    @Override
    public void exec(StepRuntimeController stepRuntimeController) {
        System.out.println("TestStep2 Running and integer is " + integer);
        System.out.println("TestStep2 Running, and the inject field is " + testInject);
    }

    @Override
    public Map<String, Class<?>> getReturnDefine() {
        return new HashMap<>();
    }
}
