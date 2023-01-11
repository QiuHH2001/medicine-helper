package cn.edu.zucc.g4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class MedicineHelperBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicineHelperBackendApplication.class, args);
	}

}
