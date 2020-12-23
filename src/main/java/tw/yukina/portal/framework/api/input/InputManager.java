package tw.yukina.portal.framework.api.input;

public interface InputManager {
    InputProvider getInputProvider(Class<?> module, InputPlan inputPlan);
}
