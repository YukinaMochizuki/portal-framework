package tw.yukina.portalframework.core.inject.dependency;

import java.util.Set;

public class NeedClassesSet {
    private final Set<Class<?>> classSet;

    public NeedClassesSet(Set<Class<?>> classSet){
        this.classSet = classSet;
    }

    public Set<Class<?>> getClassSet(){
        return classSet;
    }
}
