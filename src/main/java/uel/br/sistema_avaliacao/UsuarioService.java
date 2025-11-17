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
    
    if (usuario.isPresent()) {
        Usuario user = usuario.get();
        String senhaBanco = user.getSenha();
        
       
        if (senhaBanco.startsWith("$2a$")) {
     
            if (passwordEncoder.matches(senha, senhaBanco)) {
                logger.info("Usuário {} autenticado com sucesso (BCrypt)", email);
                return usuario;
            }
        } else {
     
            if (senhaBanco.equals(senha)) {
                logger.info("Usuário {} autenticado com sucesso (texto puro)", email);
                return usuario;
            }
        }
    }
    
    logger.warn("Falha na autenticação para email: {}", email);
    return Optional.empty();
    }
}