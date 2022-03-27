package ch.bbw.aal.Procedure_M152;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({ "ch.bbw.aal.Procedure_M152.*" })
public class ProcedureM152Application {

	public static void main(String[] args) {
		SpringApplication.run(ProcedureM152Application.class, args);
	}

}
