package tw.yukina.portal.framework.core.step.container;

import com.google.inject.Injector;
import lombok.Getter;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import tw.yukina.portal.framework.api.step.StepContainer;
import tw.yukina.portal.framework.api.step.StepPlan;
import tw.yukina.portal.framework.api.step.StepRunnable;
import tw.yukina.portal.framework.api.step.StepRuntimeController;
import tw.yukina.portal.framework.core.step.plan.AnnotationRunnableStepPlan;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@Getter
public class AnnotationRunnableStepContainer implements StepContainer {

    private final String fullId;
    private final AnnotationRunnableStepPlan annotationRunnableStepPlan;

    public AnnotationRunnableStepContainer(AnnotationRunnableStepPlan annotationRunnableStepPlan){
        this.fullId = annotationRunnableStepPlan.getFullId();
        this.annotationRunnableStepPlan = annotationRunnableStepPlan;
    }

    @Override
    public String getFullId() {
        return fullId;
    }

    @Override
    public StepPlan getStepPlan() {
        return annotationRunnableStepPlan;
    }

    @Override
    public void runStep(StepRuntimeController stepRuntimeController) {
        StepRunnable stepRunnable = null;
        try {
            stepRunnable = annotationRunnableStepPlan.getStepRunnableClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        assert stepRunnable != null;

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forClass(annotationRunnableStepPlan.getStepRunnableClass()))
                .setScanners(new FieldAnnotationsScanner()));
        Set<Field> needInject = reflections.getFieldsAnnotatedWith(Inject.class);

        for(Field field: needInject) {
            field.setAccessible(true);
            if(!field.getDeclaringClass().equals(stepRunnable.getClass()))continue;
            if(field.isAnnotationPresent(Named.class)){
                Object object = stepRuntimeController.getObject(field.getAnnotation(Named.class).value(), field.getType());
                try {
                    field.set(stepRunnable, object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }else{
                Object object = stepRuntimeController.getObject(field.getName(), field.getType());
                try {
                    field.set(stepRunnable, object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        stepRunnable.exec(stepRuntimeController);
    }
}
