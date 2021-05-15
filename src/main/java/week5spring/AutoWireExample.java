package week5spring;
import org.springframework.stereotype.Component;

@Component
public class AutoWireExample {
    public AutoWireExample() {
        System.out.println("Construct Example");
    }

    public void info() {
        System.out.println("Auto wiring example");
    }
}






