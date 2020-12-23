package tw.yukina.portal.framework.core.job.container;

import lombok.Data;

@Data
public class ReturnUnit {
    private final String name;
    private final Class<?> classType;
    private final Object object;
}
