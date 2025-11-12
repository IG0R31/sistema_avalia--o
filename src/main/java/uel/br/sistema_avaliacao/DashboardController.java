package uel.br.sistema_avaliacao;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session == null) {
            logger.warn("Session is null - redirecting to home");
            return "redirect:/";
        }
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            logger.warn("Usuario not found in session - redirecting to home");
            return "redirect:/";
        }
        
        logger.info("User {} accessed dashboard", usuario.getId());
        model.addAttribute("usuario", usuario);
        return "dashboard";
    }
}