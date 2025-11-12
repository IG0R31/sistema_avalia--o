package uel.br.sistema_avaliacao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestaoRepository extends JpaRepository<Questao, Long> {
    List<Questao> findByDisciplina(Disciplina disciplina);
    List<Questao> findByDisciplinaId(Long disciplinaId);
}