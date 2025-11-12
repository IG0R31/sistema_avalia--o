package uel.br.sistema_avaliacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    
    // Construtores, Getters e Setters
    public Questao() {}
    
    // Getters e Setters
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
}

enum TipoQuestao {
    MULTIPLA_ESCOLHA, VERDADEIRO_FALSO, TEXTO_LIVRE
}