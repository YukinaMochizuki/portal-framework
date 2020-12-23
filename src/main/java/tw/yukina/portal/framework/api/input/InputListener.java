package tw.yukina.portal.framework.api.input;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class InputListener<T> {

    private final Class<T> type;
    private final Map<String, Object> listenerProperty = new HashMap<>();

    @Getter
    @Setter
    private String preJobFullId = "default";

    @Getter
    @Setter
    private String postJobFullId = "default";

    public Map<String, Object> getListenerProperty(){
        return listenerProperty;
    }

    public InputListener(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return this.type;
    }
}