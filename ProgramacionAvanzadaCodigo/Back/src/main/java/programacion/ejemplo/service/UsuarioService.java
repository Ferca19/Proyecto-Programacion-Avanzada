package programacion.ejemplo.service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import programacion.ejemplo.DTO.DetallePedidoDTO;
import programacion.ejemplo.DTO.PedidoDTO;
import programacion.ejemplo.DTO.RegisterDTO;
import programacion.ejemplo.DTO.UsuarioDTO;
import programacion.ejemplo.Mapper.PedidoMapper;
import programacion.ejemplo.Mapper.UsuarioMapper;
import programacion.ejemplo.model.Pedido;
import programacion.ejemplo.model.Usuario;
import programacion.ejemplo.repository.UsuarioRepository;


import java.util.List;
import java.util.Optional;

@Service

public class UsuarioService implements IUsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    @Lazy
    private IPedidoService pedidoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public PedidoDTO crearPedido(Integer usuarioId, List<DetallePedidoDTO> detallesPedidoDTO) {
        Pedido pedido = pedidoService.crearPedido(usuarioId, detallesPedidoDTO);
        return PedidoMapper.toDTO(pedido);
    }

    // Método para obtener un usuario por su ID
    @Override
    public UsuarioDTO obtenerPorId(Integer usuarioId) {
        // Buscar el usuario por ID y asegurarse de que no esté eliminado
        Optional<Usuario> usuarioOpt = usuarioRepository.findByIdAndEliminadoFalse(usuarioId);

        if (usuarioOpt.isPresent()) {
            // Si se encuentra, convertirlo en DTO y devolverlo
            return UsuarioMapper.toDTO(usuarioOpt.get()); // Llamar directamente a la clase
        } else {
            // Manejar el caso donde no se encuentra el usuario
            throw new EntityNotFoundException("Usuario no encontrado con ID (metodo obtener por id): " + usuarioId);
        }
    }

    public UsuarioDTO crearUsuario(RegisterDTO registerDTO) {
        // Verificar si el email ya está registrado
        if (usuarioRepository.existsByMail(registerDTO.getMail())) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(registerDTO.getNombre());
        usuario.setApellido(registerDTO.getApellido());
        usuario.setMail(registerDTO.getMail());

        // Hashear la contraseña
        String hashedPassword = passwordEncoder.encode(registerDTO.getContrasena());
        usuario.setContrasena(hashedPassword);

        // Asignar rol si es necesario
        // usuario.setRol(getRoleByName(registerDTO.getRol()));

        // Guardar usuario en la base de datos
        usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuario);
    }

    public void eliminarPedido(Integer pedidoId) {
        pedidoService.eliminarPedido(pedidoId);
    }

    public void recuperarPedido(Integer pedidoId) {
        pedidoService.recuperarPedido(pedidoId);
    }
}
