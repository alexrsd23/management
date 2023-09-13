package com.rosendo.company;

import com.rosendo.company.Controllers.Principal;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
		Application.launch(Principal.class, args);
	}

}
