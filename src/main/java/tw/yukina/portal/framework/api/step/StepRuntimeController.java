package tw.yukina.portal.framework.api.step;

public interface StepRuntimeController {
    public void putReturn(String name, Class<?> returnType, Object object);
    Object getObject(String name, Class<?> returnType);
}
