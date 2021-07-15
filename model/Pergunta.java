package model;

import java.util.List;

public class Pergunta {
    private int id;
    private String pergunta;
    private int resposta;
    private int pontuacao;
    private String tipo;
    private List<Alternativa> alternativas;
    

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }
    public String getPergunta() {
        return pergunta;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setResposta(int resposta) {
        this.resposta = resposta;
    }

    public int getResposta() {
        return resposta;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    @Override
    public String toString() {
        return String.format("%d: %s, %s\n", id, pergunta, pontuacao);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }

        Pergunta outraPergunta = (Pergunta) obj;
        return this.id == outraPergunta.id;
    }

}// fim da classe PerguntaModel