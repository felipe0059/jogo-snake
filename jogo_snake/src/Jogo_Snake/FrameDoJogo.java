package Jogo_Snake;

import javax.swing.JFrame;

public class FrameDoJogo extends JFrame{

	
	
	 FrameDoJogo(){
		
		this.add(new PainelDoJogo());
		this.setTitle("Jogo Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}	
}



