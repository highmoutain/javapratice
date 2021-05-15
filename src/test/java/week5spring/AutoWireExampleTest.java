package week5spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AutoWireExample.class)
public class AutoWireExampleTest {
    @Autowired
    private AutoWireExample example;

    @Test
    public void test() {
        example.info();
    }
}








