package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import model.*;

public abstract class MostraQuestao extends Gerenciamento{ 
    private JTextField idTxt;
    private JTextField tipoTxt;
    private JTextArea perguntaTxt;
    private JTextField pontuacaoTxt;
    protected JTextField respostaTxt;
    private JTextField caixaOpcao2;
    private JTextField caixaOpcao3;
    private JTextField caixaOpcao4;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private Pergunta pergunta;
    private String tipoQuestao;

    public MostraQuestao(String tittle){
        super(tittle);
        
        setPergunta(pergunta);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                if (pergunta == null) { // criando pergunta
                    idTxt.setText("");
                    tipoTxt.setText("");
                    perguntaTxt.setText("");
                    pontuacaoTxt.setText("");
                    respostaTxt.setText("");

                    if (tipoQuestao.equals("Múltipla")) {
                        caixaOpcao2.setText("");
                        caixaOpcao3.setText("");
                        caixaOpcao4.setText("");
                    }

                } else { // editando pergunta
                    idTxt.setText(Integer.toString(pergunta.getId()));
                    tipoTxt.setText(pergunta.getTipo());
                    perguntaTxt.setText(pergunta.getPergunta());
                    pontuacaoTxt.setText(Integer.toString(pergunta.getPontuacao()));

                    if (tipoQuestao.equals("Múltipla")) {
                        List<Alternativa> listAlternativas = PerguntaMultiplaBD.listarAlternativas(pergunta.getId());

                        respostaTxt.setText(String.valueOf(listAlternativas.get(0).getValorAlternativa()));
                        caixaOpcao2.setText(String.valueOf(listAlternativas.get(1).getValorAlternativa()));
                        caixaOpcao3.setText(String.valueOf(listAlternativas.get(2).getValorAlternativa()));
                        caixaOpcao4.setText(String.valueOf(listAlternativas.get(3).getValorAlternativa()));
                    } else { // discursiva
                        respostaTxt.setText(Integer.toString(pergunta.getResposta()));
                    }

                }
            }
        });

        criarQuestao();
    }

    public void setPergunta(Pergunta pergunta){
        this.pergunta = pergunta;
    }

    public void criarQuestao(){
        JLabel label;

        Font fonteLabel = new Font("Comic Sans MS",Font.BOLD,22);

        label = new JLabel("Gerenciamento de Questão");
        label.setBounds(340, 60, 300, 60);
        label.setForeground(Color.WHITE);
        label.setFont(fonteLabel);
        add(label);

        label = new JLabel("Id");
        label.setBounds(300, 170, 30, 30);
        add(label);
        idTxt = new JTextField();
        idTxt.setEditable(false);
        idTxt.setBounds(380, 170, 100, 30);
        add(idTxt);

        label = new JLabel("Tipo");
        label.setBounds(520, 170, 50, 30);
        add(label);
        tipoTxt = new JTextField();
        tipoTxt.setEditable(false);
        tipoTxt.setBounds(580, 170, 100, 30);
        add(tipoTxt);

        label = new JLabel("Pergunta");
        label.setBounds(300, 210, 70, 30);
        add(label);
        perguntaTxt = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(perguntaTxt);
        scrollPane.setBounds(380, 210, 300, 100);
        add(scrollPane);

        label = new JLabel("Pontuação");
        label.setBounds(300, 330, 70, 30);
        add(label);
        pontuacaoTxt = new JTextField();
        pontuacaoTxt.setDocument(new MaxCharDocument(3));
        pontuacaoTxt.setBounds(380, 330, 300, 30);
        add(pontuacaoTxt);
    }

    public abstract void criarResposta();

    public void criarBotoes(String tipoQuestao, JTextField caixaOpcao2, JTextField caixaOpcao3, JTextField caixaOpcao4) {
        this.tipoQuestao = tipoQuestao;
        this.caixaOpcao2 = caixaOpcao2;
        this.caixaOpcao3 = caixaOpcao3;
        this.caixaOpcao4 = caixaOpcao4;

        Font fonteBtn = new Font("Comic Sans MS",Font.BOLD,12);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(380, 500, 130, 40);
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setBackground(new Color(139,0,139));
        btnSalvar.setFont(fonteBtn);
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Pergunta pergunta = new Pergunta();

                    if (perguntaTxt.getText().isBlank()|| respostaTxt.getText().isBlank()|| pontuacaoTxt.getText().isBlank()) {
                        JOptionPane.showMessageDialog(MostraQuestao.this, "Campo vazio", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        pergunta.setPergunta(perguntaTxt.getText());
                        // pergunta.setResposta(Integer.parseInt(respostaTxt.getText()));
                        pergunta.setPontuacao(Integer.parseInt(pontuacaoTxt.getText()));
                        pergunta.setTipo(tipoQuestao);

                        if (tipoQuestao.equals("Múltipla")) { // questão múltipla escolha
                            List<Alternativa> alternativasList = new ArrayList<>();
                            
                            if (caixaOpcao2.getText().isBlank() || caixaOpcao3.getText().isBlank() || caixaOpcao4.getText().isBlank()) {
                                JOptionPane.showMessageDialog(MostraQuestao.this, "Campo vazio", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {

                                alternativasList.add(new Alternativa(true, Integer.parseInt(respostaTxt.getText())));
                                alternativasList.add(new Alternativa(false, Integer.parseInt(caixaOpcao2.getText())));
                                alternativasList.add(new Alternativa(false, Integer.parseInt(caixaOpcao3.getText())));
                                alternativasList.add(new Alternativa(false, Integer.parseInt(caixaOpcao4.getText())));

                                pergunta.setAlternativas(alternativasList);


                                if (MostraQuestao.this.pergunta == null) { // criar pergunta
                                    
                                    if(PerguntaMultiplaBD.inserir(pergunta)) {
                                        JOptionPane.showMessageDialog(MostraQuestao.this, "Pergunta criada com sucesso!", "Questão",JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(MostraQuestao.this, "Ops!! temos um problema contate o administrador do programa", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                    
                                } else { // editar pergunta
                                    pergunta.setId(Integer.parseInt(idTxt.getText()));
                                    List<Alternativa> listAlternativas = PerguntaMultiplaBD.listarAlternativas(pergunta.getId());

                                    for(int i = 0; i < listAlternativas.size(); i++) {
                                        alternativasList.get(i).setId(listAlternativas.get(i).getId());
                                    }

                                    if (PerguntaMultiplaBD.atualizar(pergunta)) {
                                        JOptionPane.showMessageDialog(MostraQuestao.this, "Pergunta atualizada com sucesso!", "Questão",JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(MostraQuestao.this, "Ops!! temos um problema contate o administrador do programa", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                
                                MostraLista mostraLista = new MostraLista();
                                mostraLista.setVisible(true);
                                
                                dispose();
                            }
                        } else { // questão discursiva
                            pergunta.setResposta(Integer.parseInt(respostaTxt.getText()));

                            if (MostraQuestao.this.pergunta == null) {

                                if(PerguntaDiscursivaBD.inserir(pergunta)) {
                                    JOptionPane.showMessageDialog(MostraQuestao.this, "Pergunta criada com sucesso!", "Questão",JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(MostraQuestao.this, "Ops!! temos um problema contate o adm do programa", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                
                            } else {
                                pergunta.setId(Integer.parseInt(idTxt.getText()));

                                if (PerguntaDiscursivaBD.atualizar(pergunta)) {
                                    JOptionPane.showMessageDialog(MostraQuestao.this, "Pergunta atualizada com sucesso!", "Questão",JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(MostraQuestao.this, "Ops!! temos um problema contate o administrador do programa", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            
                            MostraLista mostraLista = new MostraLista();
                            mostraLista.setVisible(true);
                            
                            dispose();
                        }
                    }
                } catch (NumberFormatException numberFormatException) {
                    if (tipoQuestao.equals("Múltipla")) {
                        JOptionPane.showMessageDialog(MostraQuestao.this, "Erro você digitou um texto onde era para ser número reveja os campos de pontuação, resposta e as alternativas", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(MostraQuestao.this, "Erro você digitou um texto onde era para ser número, reveja os campos de pontuação e/ou resposta", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(550, 500, 130, 40);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(139,0,139));
        btnCancelar.setFont(fonteBtn);
        btnCancelar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MostraLista mostraLista = new MostraLista();
                mostraLista.setVisible(true);	
                dispose();
            }
        });
        add(btnCancelar);
    }
}// fim da classe MostraQuestao