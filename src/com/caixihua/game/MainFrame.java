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
 * 作者：蔡锡华
 * 时间：2015/09/12
 *
 */

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	//  主窗口
	private GamePanel gamePanel;
	
	public static final int GAME_WIDTH = 706;
	public static final int GAME_HEIGHT = 754;
	
	public static void main(String[] args){
		new MainFrame().createFrame();
	}

	public void createFrame() {
		this.setTitle("Java->保卫女神");
		this.setIconImage(Tank.tankImg.get("mytankU"));
		this.setBackground(Color.BLACK);
		this.setLocation(120, 0);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(Color.black);
		//this.getContentPane().setBackground(Color.black);
		this.addWindowListener(new WindowAdapter() {
			// 添加关闭窗口的事件
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setLookAndFeel();
		this.setVisible(true);
		// 将游戏面板添加到窗口
		gamePanel = new GamePanel();
		this.add(gamePanel);
		// 开始刷线程
		this.addKeyListener(new KeyMonitor());
		new UpdateThread().start();
	}
	
	public class UpdateThread extends Thread {
		// 刷新界面
		public void run() {
			while(true) {
				//  重绘
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

		// 当前系统的UI UIManager.getSystemLookAndFeelClassName()
		// "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
		// 跨平台外观 UIManager.getCrossPlatformLookAndFeelClassName()
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
