import javax.swing.JFrame;
import view.*;

public class Mundomatica {
    public static void main(String[] args) {
		JFrame frame = null;

        frame = new Tela1();

        // frame = new MostraLista();
        //frame = new QuestaoDiscursiva();
        // frame = new QuestaoMultipla();

        frame.setVisible(true);

    }// fim do método main
}// fim da classe executar