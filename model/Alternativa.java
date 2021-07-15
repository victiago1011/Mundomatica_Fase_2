package model;

public class Alternativa {
    private int id;
    private boolean certoErrado;
    private int valorAlternativa;

    public Alternativa(boolean certoErrado, int valorAlternativa) {
        this.certoErrado = certoErrado;
        this.valorAlternativa = valorAlternativa;
    }

    public Alternativa() {}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCertoErrado(boolean certoErrado) {
        this.certoErrado = certoErrado;
    }

    public boolean getCertoErrado() {
        return certoErrado;
    }

    public void setValorAlternativa(int valorAlternativa) {
        this.valorAlternativa = valorAlternativa;
    }

    public int getValorAlternativa() {
        return valorAlternativa;
    }

    @Override
    public String toString() {
        return String.format("%d: %b, %d\n", id, certoErrado, valorAlternativa);
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

        Alternativa outraAlternativa = (Alternativa) obj;
        return this.id == outraAlternativa.id;
    }

}