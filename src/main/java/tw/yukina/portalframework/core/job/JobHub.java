package tw.yukina.portalframework.core.job;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Singleton;
import tw.yukina.portalframework.api.job.JobBuilder;
import tw.yukina.portalframework.api.job.JobPlan;
import tw.yukina.portalframework.core.annotation.PreInitialization;
import tw.yukina.portalframework.core.inject.annotation.NeedClasses;
import tw.yukina.portalframework.core.inject.dependency.NeedClassesSet;
import tw.yukina.portalframework.core.job.filter.ValidatorFilter;
import tw.yukina.portalframework.core.service.annotation.Service;
import tw.yukina.portalframework.core.service.event.JobValidateEvent;

import java.util.Set;

@Service
@Singleton
@PreInitialization
public class JobHub {

    private Set<JobBuilder> jobBuilderSet;

    private Set<JobPlan> jobPlanSet;

    @NeedClasses(basePackage = "tw.yukina.portalframework.core.job.filter", filters = {ValidatorFilter.class})
    private NeedClassesSet jobValidatorClassesSet;

    @Subscribe
    private void onJobValidateEvent(JobValidateEvent jobValidateEvent){

    }
}
