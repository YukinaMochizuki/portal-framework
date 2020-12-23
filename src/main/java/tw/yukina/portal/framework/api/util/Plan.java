package tw.yukina.portal.framework.api.util;

public interface Plan extends BaseInfo {

    public boolean isReady();

    public void markIsReady();

    public boolean isDisable();

    public void setDisable(boolean isDisable);

}
