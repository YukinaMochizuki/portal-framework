package tw.yukina.portalframework.core.util.annotation.testannotation.subpackage;

import tw.yukina.portalframework.core.util.annotation.testannotation.*;

@ClassAnnotation
@AnotherClassAnnotation
public class SubSourceClass {

    @FieldAnnotation
    private String aPrivateField = "PrivateField 1 with annotation";

    @FieldAnnotation
    private String anotherPrivateField = "PrivateField 2 with annotation";
    
    @FieldAnnotation
    public String aPublicField = "PublicField 1 with annotation";
    
    @FieldAnnotation
    public String anotherPublicField = "PublicField 2 with annotation";

    public String noAnntationPublicField = "PublicField 3";
    private String noAnnotationPrivateField = "PrivateField 3";


    @MethodAnnotation
    public String aPublicMethod(){
        return "PublicMethod 1 with annotation";
    }

    @MethodAnnotation
    public String anotherPublicMethod(){
        return "PublicMethod 2 with annotation";
    }

    @MethodAnnotation
    private String aPrivateMethod(){
        return "PrivateMethod 1 with annotation";
    }

    @MethodAnnotation
    private String anotherPrivateMethod(){
        return "PrivateMethod 2 with annotation";
    }

    public String noAnnotationPublicMethod(){
        return "PublicMethod 3";
    }

    private String noAnnotationPrivateMethod(){
        return "PrivateMethod 3";
    }
}
