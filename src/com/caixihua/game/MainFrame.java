package com.caixihua.game;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * ���ߣ�������
 * ʱ�䣺2015/09/12
 *
 */

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	//  ������
	private GamePanel gamePanel;
	
	public static final int GAME_WIDTH = 706;
	public static final int GAME_HEIGHT = 754;
	
	public static void main(String[] args){
		new MainFrame().createFrame();
	}

	public void createFrame() {
		this.setTitle("Java->����Ů��");
		this.setIconImage(Tank.tankImg.get("mytankU"));
		this.setBackground(Color.BLACK);
		this.setLocation(120, 0);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(Color.black);
		//this.getContentPane().setBackground(Color.black);
		this.addWindowListener(new WindowAdapter() {
			// ��ӹرմ��ڵ��¼�
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setLookAndFeel();
		this.setVisible(true);
		// ����Ϸ�����ӵ�����
		gamePanel = new GamePanel();
		this.add(gamePanel);
		// ��ʼˢ�߳�
		this.addKeyListener(new KeyMonitor());
		new UpdateThread().start();
	}
	
	public class UpdateThread extends Thread {
		// ˢ�½���
		public void run() {
			while(true) {
				//  �ػ�
				gamePanel.repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void setLookAndFeel() {

		// ��ǰϵͳ��UI UIManager.getSystemLookAndFeelClassName()
		// "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
		// ��ƽ̨��� UIManager.getCrossPlatformLookAndFeelClassName()
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

	}
	
	class KeyMonitor extends KeyAdapter {

		@Override
		public void keyTyped(KeyEvent e) {
			//System.out.println(e.getKeyChar());
			gamePanel.KeyTyped(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			gamePanel.KeyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			gamePanel.KeyReleased(e);
		}
		
	}

}
