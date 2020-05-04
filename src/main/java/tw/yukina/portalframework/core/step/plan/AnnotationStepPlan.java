package tw.yukina.portalframework.core.step.plan;

import com.google.common.collect.Sets;
import tw.yukina.portalframework.api.exception.TypeNotMatchException;
import tw.yukina.portalframework.api.step.StepPlan;
import tw.yukina.portalframework.api.step.StepRunnable;
import tw.yukina.portalframework.api.step.annotation.Step;

import java.lang.reflect.Modifier;
import java.util.Set;

public abstract class AnnotationStepPlan implements StepPlan {

    private String id;
    private String name;
    private String shortDepiction;
    private String depiction;
    private Set<String> tags;
    private boolean isDisable;

    public AnnotationStepPlan(Class<?> stepRunnableClass) throws TypeNotMatchException {
        if(!(StepRunnable.class.isAssignableFrom(stepRunnableClass) && stepRunnableClass.isAnnotationPresent(Step.class) &&
                !Modifier.isAbstract(stepRunnableClass.getModifiers()) && !stepRunnableClass.isInterface()))
            throw new TypeNotMatchException();

        Step stepAnnotation = stepRunnableClass.getAnnotation(Step.class);

        this.id = stepAnnotation.id();
        this.name = stepAnnotation.name();
        this.shortDepiction = stepAnnotation.shortDepiction();
        this.depiction = stepAnnotation.depiction();
        this.tags = Sets.newHashSet(stepAnnotation.tags());

        this.isDisable = stepAnnotation.isDisable();
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
    public boolean isDisable() {
        return isDisable;
    }

    @Override
    public void setDisable(boolean isDisable) {
        this.isDisable = isDisable;
    }
}