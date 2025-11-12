package uel.br.sistema_avaliacao;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
public class LoginController {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/")
    public String loginPage() {
        logger.info("Acessando página de login");
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String senha, 
                       HttpSession session, 
                       Model model) {
        
        if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            logger.warn("Tentativa de login com email ou senha vazio");
            model.addAttribute("erro", "Email e senha são obrigatórios");
            return "login";
        }
        
        try {
            Optional<Usuario> usuarioOpt = usuarioService.autenticar(email, senha);
            
            if (usuarioOpt.isPresent()) {
                session.setAttribute("usuario", usuarioOpt.get());
                logger.info("Usuário {} logou com sucesso", email);
                return "redirect:/dashboard";
            } else {
                logger.warn("Falha no login para email: {}", email);
                model.addAttribute("erro", "Credenciais inválidas");
                return "login";
            }
        } catch (Exception e) {
            logger.error("Erro ao autenticar usuário: {}", e.getMessage(), e);
            model.addAttribute("erro", "Erro ao processar login");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            logger.info("Usuário desconectado");
            session.invalidate();
        }
        return "redirect:/";
    }
}