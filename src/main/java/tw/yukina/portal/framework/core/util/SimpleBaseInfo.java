package tw.yukina.portal.framework.core.util;

import lombok.Data;
import tw.yukina.portal.framework.api.util.BaseInfo;

import javax.inject.Inject;
import java.util.Set;

@Data
public class SimpleBaseInfo implements BaseInfo {
    private final String ID;
    private final String fullID;

    private String name;
    private String shortDepiction;
    private String depiction;
    private Set<String> tags;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getFullId() {
        return fullID;
    }
}
