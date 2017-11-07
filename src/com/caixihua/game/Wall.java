package com.caixihua.game;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class Wall {
	// 墙

	private int position_x;
	private int position_y;
	private int width;
	private int height;
	private int type;
	// 定义墙的类型
	public static final int BRICK = 1;
	public static final int STEEL = 2;
	public static final int SEA = 3;
	public static final int ICE = 4;
	public static final int FOREST = 5;

	// tool
	private static Map<Integer, Image> wallImg = new HashMap<Integer, Image>();
	private Image img = null;
	// 初始化图片资源
	static {
		wallImg.put(Wall.BRICK,new ImageIcon("images/other/brick.gif").getImage());
		wallImg.put(Wall.STEEL,new ImageIcon("images/other/steel.gif").getImage());
		wallImg.put(Wall.FOREST,new ImageIcon("images/other/forest.gif").getImage());
		wallImg.put(Wall.ICE,new ImageIcon("images/other/ice.gif").getImage());
		wallImg.put(Wall.SEA,new ImageIcon("images/other/sea.gif").getImage());
	}

	public Wall(int x, int y, int w,int h, int type) {
		this.position_x = x;
		this.position_y = y;
		this.width = 25*w;
		this.height = 25*h;
		this.type = type;
		this.img = wallImg.get(this.type);
	}

	public int getWallType() {
		return this.type;
	}

	public Rectangle getWallRect(){
		return new Rectangle(position_x, position_y, width, height);
	}
	
	public int getX(){
		return this.position_x;
	}
	
	public int getY(){
		return this.position_y;
	}
	
	public Image getImg(){
		return this.img;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}

}
