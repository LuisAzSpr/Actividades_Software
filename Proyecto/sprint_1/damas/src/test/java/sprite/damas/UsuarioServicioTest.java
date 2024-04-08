package sprite.damas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sprite.damas.repositorio.UsuarioRepositorio;
import sprite.damas.servicio.UsuarioServicio;
import sprite.damas.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsuarioServicioTest {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @ParameterizedTest
    @ValueSource(strings = {"luis123","hugo123","joel123"})
    public void buscarUsuarioRegistradoPorNombreRetornaUsuario(String nombre_usuario){
        Usuario usuario = usuarioServicio.buscarUsuarioPorNombre(nombre_usuario);
        assertNotNull(usuario,"El usuario no debe ser nulo");
        assertThat(usuario.getUsername()).isEqualTo(nombre_usuario);
    }


    @ParameterizedTest
    @ValueSource(strings = {"luisito","huguito","joelito"})
    public void buscarUsuarioNoRegistradoPorNombreRetornaNull(String nombre_usuario){
        Usuario usuario = usuarioServicio.buscarUsuarioPorNombre(nombre_usuario);
        assertNull(usuario,"El usuario es nulo");
    }

    @ParameterizedTest
    @ValueSource(ints={1,2,3})
    public void buscarUsuarioRegistradoPorIdRetornaUsuario(int id_usuario){
        Usuario usuario = usuarioServicio.buscarUsuarioPorId(id_usuario);
        assertNotNull(usuario,"El usuario no debe ser nulo");
        assertThat(usuario.getIdUsuario()).isEqualTo(id_usuario);
    }


    @ParameterizedTest
    @ValueSource(ints={10,20,30})
    public void buscarUsuarioNoRegistradoPorIdRetornaNull(int id_usuario){
        Usuario usuario = usuarioServicio.buscarUsuarioPorId(id_usuario);
        assertNull(usuario,"El usuario es nulo");
    }



    @Test
    public void listarTodosLosUsuariosRetornaTrue(){
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        Usuario usuario;
        for(int id=1;id<10;id++){
            usuario = usuarioServicio.buscarUsuarioPorId(id);
            assertThat(usuario.getUsername()).isEqualTo(usuarios.get(id-1).getUsername());
            assertThat(usuario.getEmail()).isEqualTo(usuarios.get(id-1).getEmail());
        }
    }

    @Test
    public void guardarUsuarioNoRegistrado(){
        Usuario nuevo_usuario = new Usuario(10,"nuevo_usuario","nuevo_usuario@gmail.com","password123");
        usuarioServicio.guardarUsuario(nuevo_usuario);
        Usuario usuario_encontrado = usuarioServicio.buscarUsuarioPorId(10);
        assertThat(nuevo_usuario.getUsername()).isEqualTo(usuario_encontrado.getUsername());
        assertThat(nuevo_usuario.getEmail()).isEqualTo(usuario_encontrado.getEmail());
        assertThat(nuevo_usuario.getPassword()).isEqualTo(usuario_encontrado.getPassword());
    }

    @Test
    public void guardarUsuarioRegistrado(){
        Usuario usuario_registrado = new Usuario(10,"nuevo_usuario","nuevo_usuario@gmail.com","password123");
    }

    @Test
    public void borrarUsuarioRegistrado(){
        Usuario nuevo_usuario = new Usuario(2,"luis123","luis@gmail.com","soyluis");
        usuarioServicio.guardarUsuario(nuevo_usuario);
        Usuario usuario_encontrado = usuarioServicio.buscarUsuarioPorId(10);
        assertThat(nuevo_usuario.getUsername()).isEqualTo(usuario_encontrado.getUsername());
        assertThat(nuevo_usuario.getEmail()).isEqualTo(usuario_encontrado.getEmail());
        assertThat(nuevo_usuario.getPassword()).isEqualTo(usuario_encontrado.getPassword());
    }



}
