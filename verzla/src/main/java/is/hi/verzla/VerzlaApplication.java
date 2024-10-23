package is.hi.verzla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Verzla Spring Boot application.
 * This class bootstraps and launches the application.
 */
@SpringBootApplication
public class VerzlaApplication {

    /**
     * The main method, which starts the Spring Boot application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(VerzlaApplication.class, args);
    }

}
