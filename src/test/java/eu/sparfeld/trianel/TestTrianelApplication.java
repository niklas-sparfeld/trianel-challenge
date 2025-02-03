package eu.sparfeld.trianel;

import org.springframework.boot.SpringApplication;

public class TestTrianelApplication {

    public static void main(String[] args) {
        SpringApplication.from(TrianelApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
