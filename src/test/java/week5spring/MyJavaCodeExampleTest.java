package week5spring;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class MyJavaCodeExampleTest {
    @Test
    public void test() {
        AnnotationConfigApplicationContext configApplicationContext =
                new AnnotationConfigApplicationContext(JavaCodeConfig.class);
        JavaCodeExample example = (JavaCodeExample) configApplicationContext.getBean("javaCodeExample2");
        example.info();
    }
}
