package tw.yukina.portal.framework.api.step;

public interface StepManager{
    public void addStepPlan(StepPlan stepPlan);
    public StepContainer findStepContainer(String fullId);
}