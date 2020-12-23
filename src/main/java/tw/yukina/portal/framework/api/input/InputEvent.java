package tw.yukina.portal.framework.api.input;

import java.util.Map;

public abstract class InputEvent <T> {

    private final Class<T> type;
    private final T event;

    public InputEvent(Class<T> type, T event) {
        this.type = type;
        this.event = event;
    }

    public abstract boolean matches(Map<String, Object> listenerProperty);

    public T getEvent() {
        return event;
    }

    public Class<T> getType() {
        return this.type;
    }
}