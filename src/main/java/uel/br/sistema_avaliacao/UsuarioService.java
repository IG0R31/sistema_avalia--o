package uel.br.sistema_avaliacao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {
    
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Optional<Usuario> autenticar(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            logger.warn("Tentativa de autenticação com email vazio");
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (senha == null || senha.trim().isEmpty()) {
            logger.warn("Tentativa de autenticação com senha vazia para email: {}", email);
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && passwordEncoder.matches(senha, usuario.get().getSenha())) {
            logger.info("Usuário {} autenticado com sucesso", email);
            return usuario;
        }
        logger.warn("Falha na autenticação para email: {}", email);
        return Optional.empty();
    }
    
    public Usuario salvarUsuario(Usuario usuario) {
        if (usuario == null) {
            logger.error("Tentativa de salvar usuário nulo");
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            logger.error("Tentativa de salvar usuário sem email");
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            logger.error("Tentativa de salvar usuário sem senha");
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        logger.info("Salvando novo usuário com email: {}", usuario.getEmail());
        return usuarioRepository.save(usuario);
    }
    
    public boolean emailExiste(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.warn("Verificação de email vazio");
            return false;
        }
        return usuarioRepository.existsByEmail(email);
    }
}