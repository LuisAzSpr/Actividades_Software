package sprite.damas.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sprite.damas.modelo.Usuario;
import sprite.damas.servicio.IUsuarioServicio;

import java.util.List;

@RestController
//http://localhost:8080/damas-app/...
@RequestMapping("damas-app")
@CrossOrigin(value = "http://localhost:3000")
public class UsuarioControlador {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioControlador.class);

    @Autowired
    private IUsuarioServicio usuarioServicio;

    //Obtener todos los usuarios de la base de datos
    //http://localhost:8080/damas-app/usuarios
    @GetMapping("/usuarios")
    public List<Usuario> obtenerUsuarios(){
        var usuarios = usuarioServicio.listarUsuarios();
        usuarios.forEach((usuario -> logger.info(usuario.toString())));
        return usuarios;
    }

    @PostMapping("/usuarios")
    public ResponseEntity<?>  agregarUsuario(@RequestBody Usuario usuario){

        logger.info("Usuario a agregar: " + usuario);
        String nuevoUsuario = usuario.getUsername();
        Usuario usuarioExistente = usuarioServicio.buscarUsuarioPorNombre(nuevoUsuario);
        if (usuarioExistente != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El usuario '" + nuevoUsuario + "' ya existe");
        }
        try {
            Usuario usuarioGuardado = usuarioServicio.guardarUsuario(usuario);
            return ResponseEntity.ok().body(usuarioGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
