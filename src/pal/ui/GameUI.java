package pal.ui;

import java.awt.Graphics;

import javax.swing.JFrame;

import pal.game.Game;

public class GameUI extends JFrame {	

	public GameUI(Game game) {
		super("Pal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH); // ���
		//setUndecorated(true); // ��Ҫ�߿�
		GamePanel panel = new GamePanel(game);		
		setContentPane(panel);
		setBounds(0, 0, 640, 400);
		addKeyListener(panel);
		setVisible(true);
	}
	
	/**
	 * ����update()�������������paint(), ����������Ϊ�˷�ֹˢ��, �Է���˸.
	 * 
	 *  (non-Javadoc)
	 * @see javax.swing.JFrame#update(java.awt.Graphics)
	 */
	@Override
	public void update(Graphics g) {
		super.paint(g);		
	}
}







