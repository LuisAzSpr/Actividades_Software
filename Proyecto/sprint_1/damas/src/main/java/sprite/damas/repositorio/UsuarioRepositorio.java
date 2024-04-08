package sprite.damas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import sprite.damas.modelo.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

}
