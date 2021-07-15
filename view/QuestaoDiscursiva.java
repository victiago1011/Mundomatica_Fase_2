package view;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class QuestaoDiscursiva extends MostraQuestao{
    private JLabel label;
    
    public QuestaoDiscursiva(){
        super("Questão Discursiva");

        criarBotoes("Discursiva", null, null, null);
    }

    @Override
    public void criarResposta(){
        label = new JLabel("Resposta");
        label.setBounds(300, 380, 70, 30);
        add(label);
        respostaTxt = new JTextField();
        respostaTxt.setBounds(380, 380, 300, 30);
        add(respostaTxt);
    }
}// fim da classe QuestaoDiscursiva