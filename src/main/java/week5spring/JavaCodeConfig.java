package week5spring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaCodeConfig {
    @Bean
    public JavaCodeExample javaCodeExample2() {
        return new JavaCodeExample();
    }
}





