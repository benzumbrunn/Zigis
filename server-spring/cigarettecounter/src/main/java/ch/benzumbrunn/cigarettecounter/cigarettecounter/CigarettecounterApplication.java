package ch.benzumbrunn.cigarettecounter.cigarettecounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CigarettecounterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CigarettecounterApplication.class, args);
	}
}
