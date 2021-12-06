package fes.aragon.Ventas.repo;

import fes.aragon.Ventas.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
    Usuario findByUsername(String username);
}
