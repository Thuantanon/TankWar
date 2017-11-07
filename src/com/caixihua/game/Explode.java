package com.caixihua.game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Explode {
	int x,y;
	// 位置信息
	private GamePanel gamePanel;
	private int step = 0;
	@SuppressWarnings("unused")
	private GamePanel ganmePanel;
	private int eSize;
	
	public static Image[] imgs = {
		new ImageIcon("images/other/blast1.gif").getImage(),
		new ImageIcon("images/other/blast2.gif").getImage(),
		new ImageIcon("images/other/blast3.gif").getImage(),
		new ImageIcon("images/other/blast4.gif").getImage(),
		new ImageIcon("images/other/blast5.gif").getImage(),
		new ImageIcon("images/other/blast6.gif").getImage(),
		new ImageIcon("images/other/blast7.gif").getImage(),
		new ImageIcon("images/other/blast5.gif").getImage(),
		new ImageIcon("images/other/blast2.gif").getImage(),
		new ImageIcon("images/other/blast6.gif").getImage()
	};
	
	public Explode(int x,int y,int size,GamePanel gp){
		// 
		this.x = x;
		this.y = y;
		this.eSize = size;
		this.gamePanel = gp;
		//MediaPlayer.play(MediaPlayer.BLAST);
	}
	
	public void draw(Graphics g){
		// 按照图片大小顺序画出爆炸效果
		if(this.step < Explode.imgs.length){
			g.drawImage(Explode.imgs[step], x, y, this.eSize, this.eSize, null);
		}else{
			gamePanel.explodes.remove(this);
		}
		
		step ++;
	}
	
	
}
