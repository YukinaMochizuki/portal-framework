package tw.yukina.portalframework.core.module;

import com.google.common.collect.Sets;
import com.google.inject.Injector;
import tw.yukina.portalframework.api.exception.TypeNotMatchException;
import tw.yukina.portalframework.api.job.JobContainer;
import tw.yukina.portalframework.api.module.ModuleContainer;
import tw.yukina.portalframework.api.module.annotation.Module;
import tw.yukina.portalframework.api.step.StepContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModuleContainerImpl implements ModuleContainer {

    private String id;
    private String name;
    private String shortDepiction;
    private String depiction;
    private Set<String> tags;
    private List<StepContainer> stepContainerList;
    private List<JobContainer> jobContainerList;

    private Class<?> moduleClass;
    private Object moduleObject;
    private Module moduleAnnotation;

    @Override
    public Class<?> getModuleClass() {
        return moduleClass;
    }

    @Override
    public Object getModuleObject() {
        return moduleObject;
    }

    @Override
    public Module getModuleAnnotation() {
        return moduleAnnotation;
    }

    public ModuleContainerImpl(Class<?> moduleClass, Injector injector) throws TypeNotMatchException {
        if(!moduleClass.isAnnotationPresent(Module.class)) throw new TypeNotMatchException();

        this.moduleClass = moduleClass;
        this.moduleObject = injector.getInstance(moduleClass);
        this.moduleAnnotation = moduleClass.getAnnotation(Module.class);

        this.id = moduleAnnotation.id();
        this.name = moduleAnnotation.name();
        this.shortDepiction = moduleAnnotation.shortDepiction();
        this.depiction = moduleAnnotation.depiction();
        this.tags = Sets.newHashSet(moduleAnnotation.tags());

        this.stepContainerList = new ArrayList<>();
        this.jobContainerList = new ArrayList<>();
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

    @Override
    public List<StepContainer> getStepContainerList() {
        return stepContainerList;
    }

    @Override
    public List<JobContainer> getJobContainerList() {
        return jobContainerList;
    }
}