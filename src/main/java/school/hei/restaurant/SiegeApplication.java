package school.hei.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SiegeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiegeApplication.class, args);
	}

}
