package tw.yukina.portal.framework.test;

import tw.yukina.portal.framework.api.exception.TypeNotMatchException;
import tw.yukina.portal.framework.api.input.InputListener;
import tw.yukina.portal.framework.api.job.JobPlan;
import tw.yukina.portal.framework.api.job.JobPlanBuilder;
import tw.yukina.portal.framework.api.job.JobRuntimeController;
import tw.yukina.portal.framework.api.job.util.WorkDefineBuilderImpl;
import tw.yukina.portal.framework.api.module.annotation.Module;
import tw.yukina.portal.framework.api.step.StepPlan;
import tw.yukina.portal.framework.api.step.StepRunnable;
import tw.yukina.portal.framework.core.input.event.ExampleEvent;
import tw.yukina.portal.framework.core.input.event.ExampleInputEvent;
import tw.yukina.portal.framework.core.job.JobManagerImpl;
import tw.yukina.portal.framework.core.job.container.ReturnUnit;
import tw.yukina.portal.framework.core.step.StepManagerImpl;
import tw.yukina.portal.framework.core.step.plan.AnnotationRunnableStepPlan;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class TestMain {
    public static void main(String[]args){
//        JobManagerImpl jobManager = new JobManagerImpl();
//        StepManagerImpl stepManager = new StepManagerImpl();
//        stepManager.jobManager = jobManager;
//        jobManager.stepManager = stepManager;
//
//        stepManager.addStepPlan(buildTestStepPlan(TestStep.class, "TestModule"));
//        stepManager.addStepPlan(buildTestStepPlan(TestStep2.class, "TestModule"));
//        stepManager.build();
//        stepManager.registerJob();
//
//        WorkDefineBuilderImpl workDefineBuilder = new WorkDefineBuilderImpl("TestModule");
//        workDefineBuilder
//                .startByStep("this.TestStep")
//                .endByStep("this.TestStep2");
//
//        JobPlanBuilder jobPlanBuilder = JobPlanBuilder.builder()
//                .id("TestSubJob")
//                .module(TestModule.class.getAnnotation(Module.class))
//                .workDefineList(workDefineBuilder.build())
//                .build();
//
//        WorkDefineBuilderImpl workDefineBuilder1 = new WorkDefineBuilderImpl("TestModule");
//        workDefineBuilder1
//                .startByJob("this.TestSubJob")
//                .endByStep("this.TestStep2");
//
//        JobPlanBuilder jobPlanBuilder1 = JobPlanBuilder.builder()
//                .id("TestJob")
//                .module(TestModule.class.getAnnotation(Module.class))
//                .workDefineList(workDefineBuilder1.build())
//                .inputListeners(new HashSet<InputListener<?>>(Collections.singletonList(new InputListener<ExampleEvent>(ExampleEvent.class))))
//                .build();
//
//        JobPlan jobPlan = jobPlanBuilder1.build();
//        JobPlan subJobPlan = jobPlanBuilder.build();
//
//        jobManager.addJobPlan(jobPlan);
//        jobManager.addJobPlan(subJobPlan);
//
//        HashSet<ReturnUnit> returnUnitSet = new HashSet<>();
//        returnUnitSet.add(new ReturnUnit("testInject", String.class, "The testInject Value"));
//        JobRuntimeController jobRuntimeController = new JobRuntimeController(returnUnitSet);
//
//        jobManager.findJobByFullId("TestModule.TestJob").run(jobManager, jobRuntimeController);
//
    }

    public static StepPlan buildTestStepPlan(Class<? extends StepRunnable> stepClass, String moduleName){
        AnnotationRunnableStepPlan annotationRunnableStepPlan = null;
        try {
            annotationRunnableStepPlan = new AnnotationRunnableStepPlan(stepClass, moduleName);
        } catch (TypeNotMatchException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        assert annotationRunnableStepPlan != null;
        annotationRunnableStepPlan.markIsReady();
        return annotationRunnableStepPlan;
    }

    public static void testStep(){
        AnnotationRunnableStepPlan annotationRunnableStepPlan = null;
        try {
            annotationRunnableStepPlan = new AnnotationRunnableStepPlan(TestStep.class, "TestModule");
        } catch (TypeNotMatchException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        assert annotationRunnableStepPlan != null;
        annotationRunnableStepPlan.markIsReady();
        //TODO stepRuntimeController null
        annotationRunnableStepPlan.build().runStep(null);

        System.out.println(annotationRunnableStepPlan.getFullId());
    }
}
