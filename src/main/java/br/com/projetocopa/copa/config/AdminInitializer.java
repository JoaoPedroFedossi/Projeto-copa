package br.com.projetocopa.copa.config;

import br.com.projetocopa.copa.entity.Usuario;
import br.com.projetocopa.copa.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner criarContaAdmin(UsuarioRepository repository) {
        return args -> {
            Usuario admin = repository.findByNome("ADMIN").orElseGet(() -> {
                Usuario novo = new Usuario();
                novo.setNome("ADMIN");
                novo.setSenha("9e10");
                novo.setGemas(1000);
                novo.setPerfil("ADMIN");
                return novo;
            });
            if (!"ADMIN".equals(admin.getPerfil())) {
                admin.setSenha("9e10");
            }
            admin.setPerfil("ADMIN");
            if (admin.getGemas() == null) admin.setGemas(1000);
            repository.save(admin);

            // Contas antigas que ainda não possuem perfil continuam como usuários comuns.
            repository.findAll().stream()
                    .filter(u -> u.getPerfil() == null || u.getPerfil().isBlank())
                    .forEach(u -> { u.setPerfil("USER"); repository.save(u); });
        };
    }
}
