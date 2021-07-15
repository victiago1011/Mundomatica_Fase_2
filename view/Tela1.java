package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Tela1 extends PrincipalFrame {

	public Tela1() {

		criarBotoes();
		criarFundo();
	}
	
	private void criarBotoes() {

		Font fonte = new Font("Comic Sans MS",Font.BOLD,20);

		JButton botao = new JButton("Professor");
		botao.setBounds(200, 350, 150, 50);
		botao.setForeground(Color.WHITE);
		botao.setBackground(Color.BLUE);
		botao.setFont(fonte);
		botao.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
				Login login = new Login();
				login.setVisible(true);
				dispose();
            }
		});

		add(botao);
		
		JButton botao2 = new JButton("Aluno");
		botao2.setBounds(400, 350, 150, 50);
		botao2.setForeground(Color.WHITE);
		botao2.setBackground(Color.GREEN);
		botao2.setFont(fonte);
        add(botao2);
		
	}
	
	private void criarFundo(){
        
        ImageIcon imagem = new ImageIcon(getClass().getResource("fundo1.png"));
        JLabel fundo = new JLabel(imagem);
        fundo.setBounds(0,0,800,600);
        add(fundo);
    } 
    
}// fim da classe Tela 1