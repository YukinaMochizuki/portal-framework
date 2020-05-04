package tw.yukina.portalframework.core.step.plan;

import tw.yukina.portalframework.api.exception.TypeNotMatchException;
import tw.yukina.portalframework.api.step.StepContainer;
import tw.yukina.portalframework.api.step.StepReturnDefine;
import tw.yukina.portalframework.api.step.StepRunnable;
import tw.yukina.portalframework.core.inject.util.InjectUtil;
import tw.yukina.portalframework.core.step.container.AnnotationRunnableStepContainer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationRunnableStepPlan extends AnnotationStepPlan {

    private Map<String, Class<?>> requireParametersDefine;
    private Map<String, Class<?>> returnDefine;

    private Class<? extends StepRunnable> stepRunnableClass;
    private Class<? extends StepContainer> stepContainerClass;
    private boolean isClose = false;

    public AnnotationRunnableStepPlan(Class<? extends StepRunnable> stepRunnableClass) throws TypeNotMatchException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        super(stepRunnableClass);

        this.stepRunnableClass = stepRunnableClass;
        this.stepContainerClass = AnnotationRunnableStepContainer.class;

        this.requireParametersDefine = new HashMap<>();
        this.returnDefine = new HashMap<>();

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

    private void mapReturnDefine() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object stepRunnableObject = stepRunnableClass.getDeclaredConstructor().newInstance();
        Method getReturnMapMethod = stepRunnableClass.getDeclaredMethod("getReturnMap");
        StepReturnDefine stepReturnDefine = (StepReturnDefine) getReturnMapMethod.invoke(stepRunnableObject);
        returnDefine.putAll(stepReturnDefine.getReturnDefine());
    }

    private void makeDefineReadonly(){
        requireParametersDefine = Collections.unmodifiableMap(requireParametersDefine);
        returnDefine = Collections.unmodifiableMap(returnDefine);
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
        if(!isClose) {
            makeDefineReadonly();
            isClose = true;
        }
    }

    @Override
    public boolean getIsAbstract() {
        return false;
    }
}
