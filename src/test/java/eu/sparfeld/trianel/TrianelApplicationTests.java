package eu.sparfeld.trianel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TrianelApplicationTests {

    @Test
    void contextLoads() {
        // just to see whether the context loads
    }

}
