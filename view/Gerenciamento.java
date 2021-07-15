package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Gerenciamento extends PrincipalFrame{
	private JButton btnQuestoes;
	private JButton btnAlunos;
	private JButton btnNotas;
	private JButton btnInicio;

    public Gerenciamento(String title) {
		super(title);

		Font fonteBtn = new Font("Comic Sans MS",Font.BOLD,16);
	
		btnAlunos = new JButton("Alunos");
		btnAlunos.setBounds(10, 220, 160, 40);
		btnAlunos.setForeground(Color.WHITE);
		btnAlunos.setBackground(new Color(30,144,255));
		btnAlunos.setFont(fonteBtn);
        add(btnAlunos);
        
        btnQuestoes = new JButton("Questões");
		btnQuestoes.setBounds(10, 280, 160, 40);
		btnQuestoes.setForeground(Color.WHITE);
		btnQuestoes.setBackground(new Color(30,144,255));
		btnQuestoes.setFont(fonteBtn);
		btnQuestoes.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MostraLista mostraLista = new MostraLista();
                mostraLista.setVisible(true);	
                dispose();   
            }
        });
		add(btnQuestoes);
        
        btnNotas = new JButton("Notas");
		btnNotas.setBounds(10, 340, 160, 40);
		btnNotas.setForeground(Color.WHITE);
		btnNotas.setBackground(new Color(30,144,255));
		btnNotas.setFont(fonteBtn);
		add(btnNotas);

		btnInicio = new JButton("Início");
		btnInicio.setBounds(10, 480, 160, 40);
		btnInicio.setForeground(Color.blue);
		btnInicio.setBackground(Color.white);
		btnInicio.setFont(fonteBtn);
		btnInicio.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
				int mensagem = JOptionPane.showConfirmDialog(Gerenciamento.this, "Deseja realmente sair da sessão?", "Home", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(mensagem == JOptionPane.YES_OPTION){
                    Tela1 tela1 = new Tela1();
                	tela1.setVisible(true);	
                	dispose(); 
                }
            }
        });
		add(btnInicio);
	}
	
	public void criarFundo(){
        
        ImageIcon imagem = new ImageIcon(getClass().getResource("outro.png"));
        JLabel fundo = new JLabel(imagem);
        fundo.setBounds(0,0,800,600);
        add(fundo);
    }
}// fim da classse Gerenciamento