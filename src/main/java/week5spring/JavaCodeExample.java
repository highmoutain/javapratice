package week5spring;
import org.springframework.stereotype.Component;

@Component
public class JavaCodeExample {
    public JavaCodeExample() {
        System.out.println("Construct Example");
    }

    public void info() {
        System.out.println("Auto wire example");
    }
}







