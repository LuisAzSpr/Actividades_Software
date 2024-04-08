package sprite.damas.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sprite.damas.modelo.Usuario;
import sprite.damas.repositorio.UsuarioRepositorio;

import java.util.List;

@Service
public class UsuarioServicio implements IUsuarioServicio{

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        return usuarioRepositorio.findById(idUsuario).orElse(null);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuarioRepositorio.delete(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorNombre(String nombre) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        for(Usuario usuario:usuarios){
            if(usuario.getUsername().equals(nombre)){
                return usuario;
            }
        }
        return null;
    }
}
