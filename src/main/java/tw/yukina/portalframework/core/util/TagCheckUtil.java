package tw.yukina.portalframework.core.util;

import java.util.List;

public class TagCheckUtil {

    public static boolean checkTag(List<String> source, List<String> check){
        boolean returnFlag = true;

        for(String checkTag : check){
            boolean flag = false;
            for(String sourceTag : source){
                if(checkTag.equals(sourceTag)){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                returnFlag = false;
                break;
            }
        }
        return returnFlag;
    }

}
