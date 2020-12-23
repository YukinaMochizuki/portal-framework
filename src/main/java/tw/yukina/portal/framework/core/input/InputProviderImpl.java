package tw.yukina.portal.framework.core.input;

import tw.yukina.portal.framework.api.input.*;

public class InputProviderImpl extends InputProvider {

    private final InputManagerImpl inputManager;

    public InputProviderImpl(InputPlan inputPlan, InputManagerImpl inputManager) {
        super(inputPlan);
        this.inputManager = inputManager;
    }

    @Override
    public Object postInputRequest(InputEvent<?> inputEvent) {
        PostResult postResult = postInputEvent(inputEvent);
        if(postResult.getPostStatus().compareTo(PostStatus.OK_200) == 0)return postResult.getResult();

        return null;
    }

    private PostResult postInputEvent(InputEvent<?> inputEvent){
        return inputManager.post(inputEvent, this);
    }

}
