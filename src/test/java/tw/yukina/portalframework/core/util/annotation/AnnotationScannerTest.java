package tw.yukina.portalframework.core.util.annotation;

import tw.yukina.portalframework.core.util.AnnotationScanner;
import tw.yukina.portalframework.core.util.annotation.testannotation.*;
import tw.yukina.portalframework.core.util.annotation.testannotation.subpackage.*;

import java.util.*;
import java.lang.reflect.*;
import java.lang.annotation.Annotation;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
 
import com.google.common.reflect.ClassPath;

public class AnnotationScannerTest{
    
    private static String PACKAGE_NAME = "tw.yukina.portalframework.core.util.annotation.testannotation";
    private static Class<?> SOURCE = SourceClass.class;
    private static Set<Class<? extends Annotation>> classAnnotationSet;
    
    private static ClassPath classPath;
    
    @BeforeClass
    public static void initClassPath(){
        try {
            classPath = ClassPath.from(AnnotationScannerTest.class.getClassLoader());
        } catch(Exception e){
          e.printStackTrace();
        }

        classAnnotationSet = new HashSet<>();
        classAnnotationSet.add(ClassAnnotation.class);
        classAnnotationSet.add(AnotherClassAnnotation.class);
    }

    @Test
    public void packageAnnotationsScanTest(){
        Set<Class<?>> expectedClassAnnotationScanResults = new HashSet<>();
        expectedClassAnnotationScanResults.add(SourceClass.class);

        Map<Class<? extends Annotation>, Set<Class<?>>> result = AnnotationScanner.packageAnnotationsScan(classAnnotationSet, PACKAGE_NAME, classPath);

        assertEquals("Unexpected annotation scan results", classAnnotationSet, result.keySet());
        assertEquals("Unexpected annotation scan results", expectedClassAnnotationScanResults, result.get(ClassAnnotation.class));
        assertEquals("Unexpected annotation scan results", expectedClassAnnotationScanResults, result.get(AnotherClassAnnotation.class));
    }

    @Test
    public void packageAnnotationsScanRecursiveTest(){
        Set<Class<?>> expectedClassAnnotationScanResults = new HashSet<>();
        expectedClassAnnotationScanResults.add(SubSourceClass.class);
        expectedClassAnnotationScanResults.add(SourceClass.class);
        
        Map<Class<? extends Annotation>, Set<Class<?>>> result = AnnotationScanner.packageAnnotationsScanRecursive(classAnnotationSet, PACKAGE_NAME, classPath);
        
        assertEquals("Unexpected annotation scan results", classAnnotationSet, result.keySet());
        assertEquals("Unexpected annotation scan results", expectedClassAnnotationScanResults, result.get(ClassAnnotation.class));
        assertEquals("Unexpected annotation scan results", expectedClassAnnotationScanResults, result.get(AnotherClassAnnotation.class));
    }

    @Test
    public void packageAnnotationScanTest(){
        Set<Class<?>> expectedClassAnnotationScanResults = new HashSet<>();
        expectedClassAnnotationScanResults.add(SourceClass.class);
        
        Set<Class<?>> result = AnnotationScanner.packageAnnotationScan(ClassAnnotation.class, PACKAGE_NAME, classPath);

        assertEquals("Unexpected annotation scan results", expectedClassAnnotationScanResults, result);
    }

    @Test
    public void packageAnnotationScanRecursiveTest(){
        Set<Class<?>> expectedClassAnnotationScanResults = new HashSet<>();
        expectedClassAnnotationScanResults.add(SubSourceClass.class);
        expectedClassAnnotationScanResults.add(SourceClass.class);
        
        Set<Class<?>> result = AnnotationScanner.packageAnnotationScanRecursive(ClassAnnotation.class, PACKAGE_NAME, classPath);

        assertEquals("Unexpected annotation scan results", expectedClassAnnotationScanResults, result);
    }

    @Test
    public void methodAnnotationScanTest(){
        SourceClass sourceClassObject = new SourceClass();
      
        Set<String> expectedMethodAnnotationScanResults = new HashSet<>();
        expectedMethodAnnotationScanResults.add("PublicMethod 1 with annotation");
        expectedMethodAnnotationScanResults.add("PublicMethod 2 with annotation");
        expectedMethodAnnotationScanResults.add("PrivateMethod 1 with annotation");
        expectedMethodAnnotationScanResults.add("PrivateMethod 2 with annotation");

        Set<String> result = new HashSet<>();
        Set<Method> resultMethod = AnnotationScanner.methodAnnotationScan(MethodAnnotation.class, SOURCE);

        System.out.println("Point 1 : " + resultMethod.size());

        for(Method method : resultMethod){
            try {
                method.setAccessible(true);
                result.add((String)method.invoke(sourceClassObject));
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        assertEquals("Unexpected annotation scan results", expectedMethodAnnotationScanResults, result);
    }

    @Test
    public void fieldAnnotationScanTest(){
        SourceClass sourceClassObject = new SourceClass();
      
        Set<String> expectedMethodAnnotationScanResults = new HashSet<>();
        expectedMethodAnnotationScanResults.add("PublicField 1 with annotation");
        expectedMethodAnnotationScanResults.add("PublicField 2 with annotation");
        expectedMethodAnnotationScanResults.add("PrivateField 1 with annotation");
        expectedMethodAnnotationScanResults.add("PrivateField 2 with annotation");

        Set<String> result = new HashSet<>();
        Set<Field> resultField = AnnotationScanner.fieldAnnotationScan(FieldAnnotation.class, SOURCE);

        for(Field field : resultField){
            try {
                field.setAccessible(true);
                result.add((String)field.get(sourceClassObject));
            } catch(Exception e){
              e.printStackTrace();
            }
        }
        assertEquals("Unexpected annotation scan results", expectedMethodAnnotationScanResults, result);
    }
}
