package uel.br.sistema_avaliacao;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/questoes")
public class QuestaoController {
    
    private static final Logger logger = LoggerFactory.getLogger(QuestaoController.class);
    
    @Autowired
    private QuestaoService questaoService;
    
    @GetMapping("/cadastro")
    public String cadastroPage(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || usuario.getTipo() != TipoUsuario.PROFESSOR) {
            logger.warn("Tentativa de acesso ao cadastro de questão sem permissão");
            return "redirect:/";
        }

        model.addAttribute("questao", new Questao());
        logger.info("Usuário {} acessando página de cadastro de questão", usuario.getId());
        return "cadastro-questao";
    }
    
    @PostMapping("/salvar")
    public String salvarQuestao(@ModelAttribute Questao questao, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || usuario.getTipo() != TipoUsuario.PROFESSOR) {
            logger.warn("Tentativa de salvar questão sem permissão");
            return "redirect:/";
        }
        
        if (questao == null) {
            logger.error("Tentativa de salvar questão nula");
            model.addAttribute("erro", "Questão não pode ser nula");
            return "cadastro-questao";
        }

        try {
            questaoService.salvarQuestao(questao);
            logger.info("Questão salva com sucesso pelo usuário {}", usuario.getId());
            model.addAttribute("sucesso", "Questão cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação ao salvar questão: {}", e.getMessage());
            model.addAttribute("erro", "Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao cadastrar questão: {}", e.getMessage(), e);
            model.addAttribute("erro", "Erro ao cadastrar questão");
        }

        return "cadastro-questao";
    }
    
    @GetMapping("/listar")
    public String listarQuestoes(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            logger.warn("Tentativa de listar questões sem autenticação");
            return "redirect:/";
        }
        
        try {
            List<Questao> questões = questaoService.listarTodas();
            model.addAttribute("questoes", questões);
            logger.info("Usuário {} listou questões", usuario.getId());
        } catch (Exception e) {
            logger.error("Erro ao listar questões: {}", e.getMessage(), e);
            model.addAttribute("erro", "Erro ao listar questões");
        }
        
        return "listar-questoes";
    }
}