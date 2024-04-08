package sprite.damas.servicio;

import sprite.damas.modelo.Usuario;

import java.util.List;

public interface IUsuarioServicio {

    public List<Usuario> listarUsuarios();

    public Usuario buscarUsuarioPorId(Integer idUsuario);

    public Usuario guardarUsuario(Usuario usuario);

    public void eliminarUsuario(Usuario usuario);

    public Usuario buscarUsuarioPorNombre(String nombre);
}
