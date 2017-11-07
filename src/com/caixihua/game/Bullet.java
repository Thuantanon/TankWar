package com.caixihua.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.ImageIcon;

public class Bullet {
	public static final int SIZE = 6;
	public static final int SPEED = 10;
	public static Image img = new ImageIcon("images/other/bullet.gif").getImage();
	
	private int x;
	private int y;
	private int dir;
	private GamePanel gamePanel;
	private boolean who;
	
	// 标记
	public boolean isLive = true;
	
	public Bullet(int x,int y,int d,GamePanel g){
		this.x = x;
		this.y = y;
		this.dir = d;
		this.gamePanel = g;
		this.who = false;
	}
	
	public Bullet(int x,int y,int d,GamePanel g,boolean who){
		// 构造
		this(x,y,d,g);
		this.who = true;
	}
	
	public void draw(Graphics g){
		// 移除已经爆炸的炮弹
		if(!isLive){
			gamePanel.bullets.remove(this);
		}
		
		// 画出
		if(isLive){
			g.drawImage(Bullet.img, x, y, Bullet.SIZE, Bullet.SIZE, null);
		}
		
		move();
	}
	
	private void move(){
		// 移动
		switch (dir) {
		case Tank.U:
			this.y -= Bullet.SPEED;
			break;
		case Tank.L:
			this.x -= Bullet.SPEED;
			break;
		case Tank.R:
			this.x += Bullet.SPEED;
			break;
		case Tank.D:
			this.y += Bullet.SPEED;
			break;
		default:
			break;
		}
		
		// 判断是否出界
		if(x <= 0 || x >= 700 || y <= 0 || y >= 725){
			this.isLive = false;
		}
		
	}
	
	public boolean hitTank(Tank t){
		// 判断是否与坦克相撞
		if(this.isLive && t.isLive && this.who != t.isWho() && !t.isGod() && this.getBulletRect().intersects(t.getTankRect())){
			// 添加爆炸
			// 攻击到坦克
			if(!t.isWho()){
				gamePanel.explodes.add(new Explode(t.getX(), t.getY(), Tank.SIZE, gamePanel));
				gamePanel.TANK_NUM --;
				gamePanel.kill++;
				MediaPlayer.play(MediaPlayer.BLAST);
				this.isLive = false;
				t.isLive = false;
				
			}
			// 攻击到自己
			if(t.isWho()){
				gamePanel.die ++;
				t.life -= 10;
				this.isLive = false;
				if(t.life <= 0){
					gamePanel.explodes.add(new Explode(t.getX(), t.getY(), Tank.SIZE, gamePanel));
					t.isLive = false;
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks){
		// 判断是否攻击到坦克群
		for(int i=0;i<tanks.size();i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean hitBullet(Bullet b){
		// 是否撞到子弹
		if(this.isLive && b.isLive && this.getBulletRect().intersects(b.getBulletRect())){
			this.isLive = false;
			b.isLive = false;
			return true;
		}
		return false;
	}
	
	public boolean hitBullets(List<Bullet> bs){
		//  子弹群
		for(int i=0;i<bs.size();i++){
			if(this != bs.get(i) && this.hitBullet(bs.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall w){
		// 子弹击中墙体
		if(w.getWallType() != Wall.SEA && w.getWallType() != Wall.FOREST && w.getWallType() != Wall.ICE && this.isLive && this.getBulletRect().intersects(w.getWallRect())){
			// 子弹可以穿过ice,forest,sea
			if(w.getWallType() == Wall.BRICK){
				gamePanel.map.remove(w);
				gamePanel.explodes.add(new Explode(w.getX(), w.getY(), w.getWidth(), gamePanel));
				this.isLive = false;
				return true;
			}
			// 不能打坏steel
			if(w.getWallType() == Wall.STEEL){
				gamePanel.explodes.add(new Explode(x-9, y-9, SIZE*4, gamePanel));
				this.isLive = false;
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWalls(List<Wall> walls){
		// 击中墙
		for(int i=0;i<walls.size();i++){
			if(this.hitWall(walls.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public Rectangle getBulletRect(){
		return new Rectangle(x, y, Bullet.SIZE, Bullet.SIZE);
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}

}
