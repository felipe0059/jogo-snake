package Jogo_Snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


import javax.swing.JPanel;

public class PainelDoJogo extends JPanel implements ActionListener {
		
		static final int SCREEN_WIDHT = 600; // Largura da tela
		static final int SCREEN_HEIGHT = 600; // Altura da tela
		static final int UNIT_SIZE = 25; // Tamanho das partes do corpo (maças) na tela
		static final int GAME_UNITS = (SCREEN_WIDHT*SCREEN_HEIGHT)/UNIT_SIZE; // lógica que calcula quantas partes totais cabem no frame do jogo
		static final int DELAY = 75; // delay temporizador
		final int x [] = new int [GAME_UNITS]; // Array com todas as partes do corpo (maça) posição x
		final int y [] = new int [GAME_UNITS]; // Array com todas as partes do corpo (maça) posição y
		int partesDoCorpo = 4; // quantidade inicial de partes do corpo 
		int macasComidas; // contador de maçãs comidas (pontos)
		int macax; // coordenada de maçãs x
		int macay; // coordenada de maçãs y
		char direcao ='R'; // 
		boolean correndo = false; // 
		Timer relogio;
		Random random;
		
		
	public PainelDoJogo() {
		random = new Random ();
		this.setPreferredSize(new Dimension(SCREEN_WIDHT,SCREEN_HEIGHT)); // construtor da tela
		this.setBackground(Color.white); // construtor cor do background
		this.setFocusable(true); 
		this.addKeyListener(new MyKeyAdapter()); // listener que captura os comandos no teclado
		inicioDoGame();
	}   // regras de início do jogo
		public void inicioDoGame() {
			novaMaca();
			correndo = true;
			relogio = new Timer(DELAY,this); // segura a velocidade no inicio do game
			relogio.start();
			
		}
		public void paintComponent(Graphics g) {
			super.paintComponent (g);
			draw(g);
		}
	//método de desenho do personagem e maçã no game
		public void draw(Graphics g ) {
			
			if(correndo) {
	//opcional para adicionar grades na tela		
		/*	for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDHT, i*UNIT_SIZE);
			}*/
			g.setColor(Color.red);
			g.fillOval(macax, macay, UNIT_SIZE, UNIT_SIZE);
			
			for(int i=0; i< partesDoCorpo; i++) {
				if(i ==0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(45,180,0));
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			 }
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD, 30));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Pontuação: "+macasComidas,(SCREEN_WIDHT - metrics.stringWidth("Pontuação: "+macasComidas))/2, g.getFont().getSize());
		}
				else {
					fimDoJogo(g);
			}
		}
        
	//método que gera a maçã em locais aleatórios	
		public void novaMaca() {
			macax = random.nextInt((int)(SCREEN_WIDHT/UNIT_SIZE))*UNIT_SIZE;
			macay = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		}
	//metodo para direção e ajuste de coordenadas
		public void movimento() {
			for(int i= partesDoCorpo;i>0;i--) {
				x[i] = x[i-1];
				y[i] = y[i-1];
			}
			switch(direcao) {
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
			case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
				
			}
		}
	//método que contabiliza quantas maças a cobra engole
		public void checkMaca() {
			if((x[0] == macax) && (y[0] == macay)) {
				partesDoCorpo++;
				macasComidas++;
				novaMaca();
			}
		}
		
	//metodo que verifica se a cabeca bate no corpo
		public void checkColisao() {
			for(int i =partesDoCorpo; i>0;i--) {
				if((x[0] == x[i]) && y[0] == y[i]) {
					correndo = false;
				}
			}
	//checar se a cabeça toca na borda esquerda
			if(x[0]< 00 ) {
				correndo = false;
			}
	//checar se bate na direita
			if(x[0] > SCREEN_WIDHT) {
				correndo = false;
			}
	//checar se bate na parte de cima
			if(y[0] < 0) {
				correndo = false;
			}
	// checar se bate embaixo
			if (y[0] > SCREEN_HEIGHT){
				correndo = false;
			}
			if (!correndo) {
				relogio.stop();
			}	
		}
		
	//tela fim de jogo (GameOver)	
		public void fimDoJogo(Graphics g) {
			
	//tela final score
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD, 30));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Pontuação Final: "+macasComidas,(SCREEN_WIDHT - metrics1.stringWidth("Pontuação Final: "+macasComidas))/2, g.getFont().getSize());
	//texto do fim de jogo
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD, 75));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString("Fim do Jogo ",(SCREEN_WIDHT - metrics2.stringWidth("Fim do Jogo"))/2, SCREEN_HEIGHT/2);
	//texto do fim de jogo
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD, 15));
			FontMetrics metrics3 = getFontMetrics(g.getFont());
			g.drawString("Aperte espaço para reiniciar",(SCREEN_WIDHT - metrics3.stringWidth("Aperte espaço para reiniciar"))/2, SCREEN_HEIGHT/2 + 60);
	}
		
   //método de movimentaçao
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(correndo) {
				movimento();
				checkMaca();
				checkColisao();
			}
			repaint();
		}
		
		//adaptador que traduz os comandos para o direcional
		public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direcao != 'R') {
					direcao = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direcao != 'L') {
					direcao = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direcao != 'D') {
					direcao = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direcao != 'U') {
					direcao = 'D';
				}
				break;
			}
		}
	}
		public void actionPerformed1(ActionEvent e) {
		// TODO Auto-generated method stub	
	     }
		}
			
			
		
			
			