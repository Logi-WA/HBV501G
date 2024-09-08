package is.hi.hbv501g.a1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class A1Application {

	public static void main(String[] args) {
		SpringApplication.run(A1Application.class, args);
	}

	@Bean
	public CommandLineRunner setningar() {
		return args -> {
			System.out.println("Halló heimur, forrit til lífs");
		};
	}

}
