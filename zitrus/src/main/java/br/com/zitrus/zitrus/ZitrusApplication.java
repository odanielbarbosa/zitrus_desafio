package br.com.zitrus.zitrus;

import br.com.zitrus.zitrus.model.Usuario;
import br.com.zitrus.zitrus.repository.UsuarioRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ZitrusApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext configurableApplicationContext =
				SpringApplication.run(ZitrusApplication.class, args);

		UsuarioRepository usuarioRepository =
				configurableApplicationContext.getBean(UsuarioRepository.class);
	}

}
