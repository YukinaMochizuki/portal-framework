package tw.yukina.portal.framework.api.input;

import lombok.Value;
import tw.yukina.portal.framework.core.job.container.ReturnUnit;

import java.util.Set;

@Value
public class PostResult {
    PostStatus postStatus;
    Object result;
    Set<ReturnUnit> rawResult;
}
