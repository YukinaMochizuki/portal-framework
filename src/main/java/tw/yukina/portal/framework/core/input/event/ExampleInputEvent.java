package tw.yukina.portal.framework.core.input.event;

import tw.yukina.portal.framework.api.input.InputEvent;

import java.util.Map;

public class ExampleInputEvent extends InputEvent<ExampleEvent> {

    public ExampleInputEvent(ExampleEvent event) {
        super(ExampleEvent.class, event);
    }

    @Override
    public boolean matches(Map<String, Object> listenerProperty) {
        return true;
    }
}
