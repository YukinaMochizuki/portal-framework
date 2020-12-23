package tw.yukina.portal.framework.test;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import tw.yukina.portal.framework.api.input.InputListener;
import tw.yukina.portal.framework.api.job.JobManager;
import tw.yukina.portal.framework.api.job.JobPlan;
import tw.yukina.portal.framework.api.job.JobPlanBuilder;
import tw.yukina.portal.framework.api.job.JobRuntimeController;
import tw.yukina.portal.framework.api.job.util.WorkDefineBuilderImpl;
import tw.yukina.portal.framework.api.module.annotation.Module;
import tw.yukina.portal.framework.core.input.InputManagerImpl;
import tw.yukina.portal.framework.core.input.event.ExampleEvent;
import tw.yukina.portal.framework.core.job.container.ReturnUnit;
import tw.yukina.portal.framework.core.step.StepManagerImpl;

import java.util.Collections;
import java.util.HashSet;

import static tw.yukina.portal.framework.test.TestMain.buildTestStepPlan;

@Service
public class TestSpringMain {

    private final JobManager jobManager;

    private final StepManagerImpl stepManager;

    private final InputManagerImpl inputManager;

    public TestSpringMain(JobManager jobManager, StepManagerImpl stepManager, InputManagerImpl inputManager) {
        this.jobManager = jobManager;
        this.stepManager = stepManager;
        this.inputManager = inputManager;
    }

    @EventListener
    public void onApplicationStart(ApplicationReadyEvent applicationReadyEvent){
        stepManager.addStepPlan(buildTestStepPlan(TestStep.class, "TestModule"));
        stepManager.addStepPlan(buildTestStepPlan(TestStep2.class, "TestModule"));
        stepManager.build();
        stepManager.registerJob();

        WorkDefineBuilderImpl workDefineBuilder = new WorkDefineBuilderImpl("TestModule");
        workDefineBuilder
                .startByStep("this.TestStep")
                .endByStep("this.TestStep2");

        JobPlanBuilder jobPlanBuilder = JobPlanBuilder.builder()
                .id("TestSubJob")
                .module(TestModule.class.getAnnotation(Module.class))
                .workDefineList(workDefineBuilder.build())
                .build();

        WorkDefineBuilderImpl workDefineBuilder1 = new WorkDefineBuilderImpl("TestModule");
        workDefineBuilder1
                .startByJob("this.TestSubJob")
                .endByStep("this.TestStep2");

        JobPlanBuilder jobPlanBuilder1 = JobPlanBuilder.builder()
                .id("TestJob")
                .module(TestModule.class.getAnnotation(Module.class))
                .workDefineList(workDefineBuilder1.build())
                .inputListeners(new HashSet<InputListener<?>>(Collections.singletonList(new InputListener<ExampleEvent>(ExampleEvent.class))))
                .build();

        JobPlan jobPlan = jobPlanBuilder1.build();
        JobPlan subJobPlan = jobPlanBuilder.build();

        jobManager.addJobPlan(jobPlan);
        jobManager.addJobPlan(subJobPlan);

        HashSet<ReturnUnit> returnUnitSet = new HashSet<>();
        returnUnitSet.add(new ReturnUnit("testInject", String.class, "The testInject Value"));
        JobRuntimeController jobRuntimeController = new JobRuntimeController(returnUnitSet);

        jobManager.findJobRuntimeContainerByFullId("TestModule.TestJob").run(jobManager, jobRuntimeController);
    }
}
