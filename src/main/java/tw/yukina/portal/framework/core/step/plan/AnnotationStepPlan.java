package tw.yukina.portal.framework.core.step.plan;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import tw.yukina.portal.framework.api.step.StepPlan;
import tw.yukina.portal.framework.api.step.annotation.Step;

import java.util.Set;

@Getter
@Setter
public abstract class AnnotationStepPlan implements StepPlan {

    private final String id;
    private String name;
    private String shortDepiction;
    private String depiction;
    private Set<String> tags;
    private boolean isDisable;

    private Class<?> annotationStepClass;

    public AnnotationStepPlan(@NonNull Class<?> annotationStepClass) {

        Step stepAnnotation = annotationStepClass.getAnnotation(Step.class);

        this.id = stepAnnotation.id();
        this.name = stepAnnotation.name();
        this.shortDepiction = stepAnnotation.shortDepiction();
        this.depiction = stepAnnotation.depiction();
        this.tags = Sets.newHashSet(stepAnnotation.tags());
        this.isDisable = stepAnnotation.isDisable();
    }
}