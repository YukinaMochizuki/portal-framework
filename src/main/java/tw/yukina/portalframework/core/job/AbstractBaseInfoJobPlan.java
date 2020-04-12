package tw.yukina.portalframework.core.job;

import tw.yukina.portalframework.api.job.JobPlan;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractBaseInfoJobPlan implements JobPlan {

    private String id;
    private String name = "";
    private String depiction = "";
    private String shortDepiction = "";

    private Set<String> tags = new HashSet<>();

    public AbstractBaseInfoJobPlan(String id){
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setShortDepiction(String shortDepiction) {
        this.shortDepiction = shortDepiction;
    }

    @Override
    public String getShortDepiction() {
        return shortDepiction;
    }

    @Override
    public void setDepiction(String depiction) {
        this.depiction = depiction;
    }

    @Override
    public String getDepiction() {
        return depiction;
    }

    @Override
    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public Set<String> getTags() {
        return tags;
    }
}
