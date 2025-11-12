package uel.br.sistema_avaliacao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuestaoService {
    
    private static final Logger logger = LoggerFactory.getLogger(QuestaoService.class);
    
    @Autowired
    private QuestaoRepository questaoRepository;
    
    public Questao salvarQuestao(Questao questao) {
        if (questao == null) {
            logger.error("Tentativa de salvar questão nula");
            throw new IllegalArgumentException("Questão não pode ser nula");
        }
        logger.info("Salvando questão: {}", questao.getId());
        return questaoRepository.save(questao);
    }
    
    public List<Questao> listarPorDisciplina(Long disciplinaId) {
        if (disciplinaId == null || disciplinaId <= 0) {
            logger.error("ID da disciplina inválido: {}", disciplinaId);
            throw new IllegalArgumentException("ID da disciplina deve ser válido");
        }
        logger.info("Listando questões para disciplina: {}", disciplinaId);
        return questaoRepository.findByDisciplinaId(disciplinaId);
    }
    
    public List<Questao> listarTodas() {
        logger.info("Listando todas as questões");
        return questaoRepository.findAll();
    }
    
    public Optional<Questao> obterPorId(Long id) {
        if (id == null || id <= 0) {
            logger.error("ID inválido: {}", id);
            throw new IllegalArgumentException("ID deve ser válido");
        }
        logger.info("Obtendo questão com ID: {}", id);
        return questaoRepository.findById(id);
    }
    
    public void deletarQuestao(Long id) {
        if (id == null || id <= 0) {
            logger.error("Tentativa de deletar com ID inválido: {}", id);
            throw new IllegalArgumentException("ID deve ser válido");
        }
        logger.info("Deletando questão com ID: {}", id);
        questaoRepository.deleteById(id);
    }
}