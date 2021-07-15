package view;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class QuestaoMultipla extends MostraQuestao{
    private JTextField caixaOpcao2;
    private JTextField caixaOpcao3;
    private JTextField caixaOpcao4;

    public QuestaoMultipla() {
        super("Questão Múltipla Escolha");
    }

    @Override
    public void criarResposta(){
        JLabel label1;

        label1 = new JLabel("Resposta");
        label1.setBounds(300, 380, 70, 30);
        add(label1);
        respostaTxt = new JTextField();
        respostaTxt.setBounds(380, 380, 100, 30);
        add(respostaTxt);

        label1 = new JLabel("Opção 2");
        label1.setBounds(520,380,50,30);
        add(label1);
        caixaOpcao2 = new JTextField();
        caixaOpcao2.setBounds(580, 380, 100, 30);
        add(caixaOpcao2);

        label1 = new JLabel("Opção 3");
        label1.setBounds(300, 430, 70, 30);
        add(label1);
        caixaOpcao3 = new JTextField();
        caixaOpcao3.setBounds(380, 430, 100, 30);
        add(caixaOpcao3);

        label1 = new JLabel("Opção 4");
        label1.setBounds(520,430,50,30);
        add(label1);
        caixaOpcao4 = new JTextField();
        caixaOpcao4.setBounds(580, 430, 100, 30);
        add(caixaOpcao4);
        
        criarBotoes("Múltipla", caixaOpcao2, caixaOpcao3, caixaOpcao4);
    }
}// fim da classe QuestaoDiscursiva