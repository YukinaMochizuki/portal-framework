package tw.yukina.portalframework.core.step;

import com.google.common.collect.Sets;
import tw.yukina.portalframework.api.exception.TypeNotMatchException;
import tw.yukina.portalframework.api.step.StepContainer;
import tw.yukina.portalframework.api.step.StepPlan;
import tw.yukina.portalframework.api.step.StepRunnable;
import tw.yukina.portalframework.api.step.annotation.Step;
import tw.yukina.portalframework.core.inject.util.InjectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class AnnotationRunnableStepPlan implements StepPlan {

    private String id;
    private String name;
    private String shortDepiction;
    private String depiction;
    private Set<String> tags;

    private Map<String, Class<?>> requireParametersDefine;
    private Map<String, Class<?>> returnDefine;

    private Class<? extends StepRunnable> stepRunnableClass;
    private Class<? extends StepContainer> stepContainerClass;
    private boolean isDisable;
    private boolean isClose = false;

    public AnnotationRunnableStepPlan(Class<? extends StepRunnable> stepRunnableClass, Class<? extends StepContainer> stepContainerClass) throws TypeNotMatchException {

        if(!(StepRunnable.class.isAssignableFrom(stepRunnableClass) && stepRunnableClass.isAnnotationPresent(Step.class) &&
                !Modifier.isAbstract(stepRunnableClass.getModifiers()) && !stepRunnableClass.isInterface())) throw new TypeNotMatchException();

        this.stepRunnableClass = stepRunnableClass;
        this.stepContainerClass = stepContainerClass;
        Step stepAnnotation = stepRunnableClass.getAnnotation(Step.class);

        this.id = stepAnnotation.id();
        this.name = stepAnnotation.name();
        this.shortDepiction = stepAnnotation.shortDepiction();
        this.depiction = stepAnnotation.depiction();
        this.tags = Sets.newHashSet(stepAnnotation.tags());

        this.requireParametersDefine = new HashMap<>();
        this.returnDefine = new HashMap<>();

        this.isDisable = stepAnnotation.isDisable();

        mapRequireParaDefine();
        mapReturnDefine();
    }

    private void mapRequireParaDefine(){
        for(Field field : stepRunnableClass.getDeclaredFields()){
            if(InjectUtil.isFieldNeedInjectInStep(field)){
                    requireParametersDefine.put(InjectUtil.getFieldInjectNamed(field), field.getType());
            }
        }
    }

    private void mapReturnDefine(){

    }

    private void makeDefineReadonly(){
        requireParametersDefine = Collections.unmodifiableMap(requireParametersDefine);
        returnDefine = Collections.unmodifiableMap(returnDefine);
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
    public Map<String, Class<?>> getReturnDefine() {
        return returnDefine;
    }

    @Override
    public Map<String, Class<?>> getRequireParametersDefine() {
        return requireParametersDefine;
    }

    public Class<? extends StepRunnable> getStepRunnableClass() {
        return stepRunnableClass;
    }

    @Override
    public Class<? extends StepContainer> getStepContainerClass() {
        return stepContainerClass;
    }

    @Override
    public boolean isClose() {
        return isClose;
    }

    @Override
    public void close() {
        makeDefineReadonly();
        isClose = true;
    }

    @Override
    public boolean getIsAbstract() {
        return false;
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
