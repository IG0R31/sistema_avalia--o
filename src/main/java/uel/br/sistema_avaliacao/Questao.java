package uel.br.sistema_avaliacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import java.util.List;

@Entity
@Table(name = "questao")
public class Questao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Enunciado é obrigatório")
    @Column(columnDefinition = "TEXT")
    private String enunciado;

    @Enumerated(EnumType.STRING)
    private TipoQuestao tipo;

    private String alternativaA;
    private String alternativaB;
    private String alternativaC;
    private String alternativaD;
    private String respostaCorreta;

    @NotNull(message = "Valor é obrigatório")
    private Double valorPadrao;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public TipoQuestao getTipo() { return tipo; }
    public void setTipo(TipoQuestao tipo) { this.tipo = tipo; }

    public String getAlternativaA() { return alternativaA; }
    public void setAlternativaA(String alternativaA) { this.alternativaA = alternativaA; }

    public String getAlternativaB() { return alternativaB; }
    public void setAlternativaB(String alternativaB) { this.alternativaB = alternativaB; }

    public String getAlternativaC() { return alternativaC; }
    public void setAlternativaC(String alternativaC) { this.alternativaC = alternativaC; }

    public String getAlternativaD() { return alternativaD; }
    public void setAlternativaD(String alternativaD) { this.alternativaD = alternativaD; }

    public String getRespostaCorreta() { return respostaCorreta; }
    public void setRespostaCorreta(String respostaCorreta) { this.respostaCorreta = respostaCorreta; }

    public Double getValorPadrao() { return valorPadrao; }
    public void setValorPadrao(Double valorPadrao) { this.valorPadrao = valorPadrao; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    @AssertTrue(message = "Para questões de múltipla escolha, todas as alternativas e resposta correta são obrigatórias")
    public boolean isValidMultiplaEscolha() {
        if (tipo == TipoQuestao.MULTIPLA_ESCOLHA) {
            return alternativaA != null && !alternativaA.trim().isEmpty() &&
                   alternativaB != null && !alternativaB.trim().isEmpty() &&
                   alternativaC != null && !alternativaC.trim().isEmpty() &&
                   alternativaD != null && !alternativaD.trim().isEmpty() &&
                   respostaCorreta != null && !respostaCorreta.trim().isEmpty() &&
                   List.of("A", "B", "C", "D").contains(respostaCorreta.toUpperCase());
        }
        return true;
    }
}
