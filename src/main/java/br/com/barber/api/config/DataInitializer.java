package br.com.barber.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.barber.api.model.AdministradorModel;
import br.com.barber.api.repository.AdministradorRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createDefaultAdmin(AdministradorRepository administradorRepository) {
        return args -> {
            if (administradorRepository.findByEmail("admin@barber.com").isEmpty()) {
                AdministradorModel administrador = new AdministradorModel();
                administrador.setNome("Administrador Padrao");
                administrador.setEmail("admin@barber.com");
                administrador.setSenha("admin123");
                administrador.setAtivo(true);
                administradorRepository.save(administrador);
            }
        };
    }
}
