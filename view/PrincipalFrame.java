package view;

import javax.swing.JFrame;

public class PrincipalFrame extends JFrame {

    public PrincipalFrame() {
		this("Home");
	}
	
	public PrincipalFrame(String title) {
		super(title);
        
        this.setLayout(null);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

}// fim da classe Principal frame