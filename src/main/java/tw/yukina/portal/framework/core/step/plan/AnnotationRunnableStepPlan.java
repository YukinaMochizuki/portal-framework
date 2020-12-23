package tw.yukina.portal.framework.core.step.plan;

import lombok.Getter;
import tw.yukina.portal.framework.api.exception.TypeNotMatchException;
import tw.yukina.portal.framework.api.step.StepContainer;
import tw.yukina.portal.framework.api.step.StepRunnable;
import tw.yukina.portal.framework.core.inject.util.InjectUtil;
import tw.yukina.portal.framework.core.step.container.AnnotationRunnableStepContainer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public class AnnotationRunnableStepPlan extends AnnotationStepPlan {

    private Map<String, Class<?>> requireParametersDefine;
    private Map<String, Class<?>> returnDefine;

    private final Class<? extends StepRunnable> stepRunnableClass;
    private final String fullID;
    private boolean isReady = false;

    public AnnotationRunnableStepPlan(Class<? extends StepRunnable> stepRunnableClass, String moduleName) throws TypeNotMatchException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(stepRunnableClass);

        if(Modifier.isAbstract(stepRunnableClass.getModifiers()) || stepRunnableClass.isInterface())
            throw new TypeNotMatchException();

        this.stepRunnableClass = stepRunnableClass;
        this.fullID = moduleName + "." + getId();

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
        StepRunnable stepRunnable = stepRunnableClass.getDeclaredConstructor().newInstance();
        returnDefine.putAll(stepRunnable.getReturnDefine());
    }

    private void makeDefineReadonly(){
        requireParametersDefine = Collections.unmodifiableMap(requireParametersDefine);
        returnDefine = Collections.unmodifiableMap(returnDefine);
    }

    public Class<? extends StepRunnable> getStepRunnableClass() {
        return stepRunnableClass;
    }

    @Override
    public String getFullId() {
        return fullID;
    }

    @Override
    public boolean isReady() {
        return isReady;
    }

    @Override
    public void markIsReady() {
        if(!isReady) {
            makeDefineReadonly();
            isReady = true;
        }
    }

    @Override
    public StepContainer build() {
        if(!isReady)markIsReady();
        return new AnnotationRunnableStepContainer(this);
    }
}
