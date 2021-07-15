package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.*;

public class MostraLista extends Gerenciamento {
    private JButton btnDiscursiva;
    private JButton btnMultipla;
    private JButton btnEditar;
    private JButton btnRemover;
    private JTable tabela;
    private PerguntaTableModel tableModel;

    public MostraLista(){
        super("Lista de Perguntas");

        criarBotoes();
        criarTabela();
        criarFundo();
    }

    private void criarBotoes(){
        MostraQuestao[] mostraQuestao = new MostraQuestao[2];
        mostraQuestao[0] = new QuestaoDiscursiva();
        mostraQuestao[1] = new QuestaoMultipla();

        Font fonteLabel = new Font("Comic Sans MS",Font.BOLD,22);
        Font fonteBtn = new Font("Comic Sans MS",Font.BOLD,16);
    
        JLabel label = new JLabel("Lista de Questões");
        label.setBounds(390, 60, 300, 60);
        label.setForeground(Color.WHITE);
        label.setFont(fonteLabel);
        add(label);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(300, 370, 180, 40);
		btnEditar.setForeground(Color.WHITE);
        btnEditar.setBackground(new Color(0,255,0));
        btnEditar.setFont(fonteBtn);
        btnEditar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Pergunta pergunta = tableModel.getPergunta(tabela.getSelectedRow());

                if (pergunta.getTipo().equals("Discursiva")) {
                    mostraQuestao[0].setPergunta(pergunta);
                    mostraQuestao[0].criarResposta();
                    mostraQuestao[0].criarFundo();
                    mostraQuestao[0].setVisible(true);
                    dispose();
                } else {
                    mostraQuestao[1].setPergunta(pergunta);
                    mostraQuestao[1].criarResposta();
                    mostraQuestao[1].criarFundo();
                    mostraQuestao[1].setVisible(true);
                    dispose();
                }
                
            }
        });
        add(btnEditar);

        btnRemover= new JButton("Excluir");
        btnRemover.setBounds(500, 370, 180, 40);
        btnRemover.setForeground(Color.WHITE);
        btnRemover.setBackground(new Color(139,0,139));
        btnRemover.setFont(fonteBtn);
        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Pergunta pergunta = tableModel.getPergunta(tabela.getSelectedRow());
                int mensagem = JOptionPane.showConfirmDialog(MostraLista.this, "Deseja realmente excluir?", "Excluir", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (mensagem == JOptionPane.YES_OPTION) {
                    if(PerguntaBD.remover(pergunta)) {
                        tableModel.remover(pergunta);
                    } else {
                        JOptionPane.showMessageDialog(MostraLista.this, "Ops!! temos um problema contate o administrador do programa", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        add(btnRemover);

        btnDiscursiva = new JButton("Adicionar Questão Discursiva");
		btnDiscursiva.setBounds(300, 430, 380, 35);
		btnDiscursiva.setForeground(Color.WHITE);
		btnDiscursiva.setBackground(new Color(30,144,255));
        btnDiscursiva.setFont(fonteBtn);
        btnDiscursiva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostraQuestao[0].criarResposta();
                mostraQuestao[0].criarFundo();
                mostraQuestao[0].setVisible(true);
                dispose();
            }
        });
        add(btnDiscursiva);

        btnMultipla = new JButton("Adicionar Questão Múltipla Escolha");
		btnMultipla.setBounds(300, 485, 380, 35);
		btnMultipla.setForeground(Color.WHITE);
		btnMultipla.setBackground(new Color(30,144,255));
        btnMultipla.setFont(fonteBtn);
        btnMultipla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostraQuestao[1].criarResposta();
                mostraQuestao[1].criarFundo();
                mostraQuestao[1].setVisible(true);
                dispose();
            }
        });
        add(btnMultipla);

        desabilitarBotoes();
    }

    private void criarTabela() {

        tableModel = new PerguntaTableModel(PerguntaMultiplaBD.listarQuestoes(), PerguntaDiscursivaBD.listar());

        tabela = new JTable(tableModel);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tabela.getSelectedRow() >= 0 ) {
                        habilitarBotoes();
                    }else {
                        desabilitarBotoes();
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(300, 150, 380, 200);
        add(scrollPane);
    }

    private void desabilitarBotoes() {
        btnEditar.setEnabled(false);
        btnRemover.setEnabled(false);
        btnEditar.setToolTipText("Nenhuma questão foi selecionada.");
        btnRemover.setToolTipText("Nenhuma questão foi selecionada.");
    }
    
    private void habilitarBotoes() {
        btnEditar.setEnabled(true);
        btnRemover.setEnabled(true);
        btnEditar.setToolTipText("");
        btnRemover.setToolTipText("");
    }
}// fim da classe mostraLista