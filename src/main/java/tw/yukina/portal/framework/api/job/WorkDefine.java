package tw.yukina.portal.framework.api.job;

import tw.yukina.portal.framework.api.job.enums.WorkTypeEnum;

import java.util.Optional;

public class WorkDefine {

    private final WorkTypeEnum workTypeEnum;
    private final String id;

    public WorkDefine(WorkTypeEnum workTypeEnum, String id) {
        this.workTypeEnum = workTypeEnum;
        this.id = id;
    }

    public WorkTypeEnum getWorkTypeEnum() {
        return workTypeEnum;
    }

    public String getId() {
        return id;
    }
}
