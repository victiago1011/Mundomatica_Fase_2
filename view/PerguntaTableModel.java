package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Pergunta;

public class PerguntaTableModel extends AbstractTableModel {
    private List<Pergunta> todasPerguntas = new ArrayList<>();
    private List<Pergunta> listPerguntaMultipla = new ArrayList<>();
    private List<Pergunta> listPerguntasDiscursiva = new ArrayList<>();
    private String[] colunas = new String[]{"Id", "Pergunta", "Resposta", "Pontuação", "Tipo"};

    public PerguntaTableModel(List<Pergunta> listPerguntaMultipla, List<Pergunta> listPerguntasDiscursiva) {
        this.listPerguntaMultipla = listPerguntaMultipla;
        this.listPerguntasDiscursiva = listPerguntasDiscursiva;

        this.todasPerguntas.addAll(this.listPerguntasDiscursiva);
        this.todasPerguntas.addAll(this.listPerguntaMultipla);
    }

    @Override
    public int getRowCount() { // numero de linhas
        return todasPerguntas.size();
    }

    @Override
    public int getColumnCount() { // numero de colunas
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        String columnName = null;

        if (column >= 0 && column < colunas.length) {
            columnName = colunas[column];
        }

        return columnName;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) { // valor de uma célula da tabela
        String value = null;

        if (rowIndex >= 0 && rowIndex < todasPerguntas.size()) {
            Pergunta pergunta = todasPerguntas.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    value = Integer.toString(pergunta.getId());
                    break;
                case 1:
                    value = pergunta.getPergunta(); 
                    break;
                case 2:
                    value = Integer.toString(pergunta.getResposta());
                    break;
                case 3:
                    value = Integer.toString(pergunta.getPontuacao());
                    break;
                case 4 :
                    value = pergunta.getTipo();
                    break;
                default:
                    System.err.printf("[ERRO] Indice de coluna Inválido: %d\n", columnIndex);
                    break;
            }
        }
        return value;
    }

    public Pergunta getPergunta(int rowIndex){
        Pergunta pergunta = null;
        
        if (rowIndex >= 0 && rowIndex < todasPerguntas.size()){
            pergunta = todasPerguntas.get(rowIndex);
        }

        return pergunta;
    }

    public void remover(Pergunta pergunta){
        todasPerguntas.remove(pergunta);
        fireTableDataChanged();
    }

}// fim da classe PerguntaTableModel
