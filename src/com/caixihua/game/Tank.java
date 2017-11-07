package com.caixihua.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

public class Tank {
	// 设置基本常量
	public static final int SIZE = 36;
	public static final int STOP = 0;
	public static final int L = 1;
	public static final int R = 2;
	public static final int U = 3;
	public static final int D = 4;
	private static final int speed_x = 5;
	private static final int speed_y = 5;
	public static int MAX_LIFE = 100;

	private int dir;
	// true为自己坦克
	private boolean who;
	private int position_x;
	private int position_y;
	// 无敌状态
	private boolean god = true;
	private GamePanel gamePanel;
	public static Random random = new Random();
	// 电脑的步数
	private int step = random.nextInt(20) + 3;
	private BloodBar blood = new BloodBar();

	// 标记
	private int drawStep = 0;
	private int oldX;
	private int oldY;
	public boolean isLive = true;
	public boolean isMove = false;
	public int life = MAX_LIFE;

	public static Map<String, Image> tankImg = new HashMap<String, Image>();
	public Map<String, Image> enemyImage = new HashMap<String, Image>();
	static {
		tankImg.put("mytankD",
				new ImageIcon("images/self/tankD.gif").getImage());
		tankImg.put("mytankU",
				new ImageIcon("images/self/tankU.gif").getImage());
		tankImg.put("mytankL",
				new ImageIcon("images/self/tankL.gif").getImage());
		tankImg.put("mytankR",
				new ImageIcon("images/self/tankR.gif").getImage());
		// 无敌效果
		tankImg.put("god0", new ImageIcon("images/other/god0.gif").getImage());
		tankImg.put("god1", new ImageIcon("images/other/god1.gif").getImage());
	}

	public Tank(int x, int y, int dir, GamePanel gp) {
		// 创建一个敌军坦克时的构造方法
		this.position_x = x;
		this.position_y = y;
		this.dir = dir;
		this.who = false;
		this.gamePanel = gp;
		this.oldX = this.position_x;
		this.oldY = this.position_y;

		// 敌军坦克
		if (random.nextInt(10) > 8) {
			enemyImage.put("enemyD",
					new ImageIcon("images/enemy/down.gif").getImage());
			enemyImage.put("enemyU",
					new ImageIcon("images/enemy/up.gif").getImage());
			enemyImage.put("enemyL",
					new ImageIcon("images/enemy/left.gif").getImage());
			enemyImage.put("enemyR",
					new ImageIcon("images/enemy/right.gif").getImage());
		} else {
			enemyImage.put("enemyD",
					new ImageIcon("images/enemy/enemyD.gif").getImage());
			enemyImage.put("enemyU",
					new ImageIcon("images/enemy/enemyU.gif").getImage());
			enemyImage.put("enemyL",
					new ImageIcon("images/enemy/enemyL.gif").getImage());
			enemyImage.put("enemyR",
					new ImageIcon("images/enemy/enemyR.gif").getImage());
		}
	}

	public Tank(int x, int y, int dir, GamePanel gp, boolean who) {
		// 创建自己的坦克时的构造方法
		this(x, y, dir, gp);
		this.who = true;
	}

	public void draw(Graphics g) {
		// 画出当前坦克
		drawStep++;
		if (drawStep >= 100) {
			god = false;
			drawStep = 0;
		}
		// 如果死亡，移除
		if (!isLive) {
			if (!this.who) {
				gamePanel.tanks.remove(this);
			}
			return;
		}

		// 画出血条
		if (isLive && who) {
			blood.draw(g);
		}

		// 画出坦克

		switch (this.dir) {
		case Tank.D: {
			if (this.who) {
				g.drawImage(Tank.tankImg.get("mytankD"), this.position_x,
						this.position_y, Tank.SIZE, Tank.SIZE, null);
			} else {
				g.drawImage(enemyImage.get("enemyD"), this.position_x,
						this.position_y, Tank.SIZE, Tank.SIZE, null);
			}
		}
			break;
		case Tank.L: {
			if (this.who) {
				g.drawImage(Tank.tankImg.get("mytankL"), this.position_x,
						this.position_y, Tank.SIZE, Tank.SIZE, null);
			} else {
				g.drawImage(enemyImage.get("enemyL"), this.position_x,
						this.position_y, Tank.SIZE, Tank.SIZE, null);
			}
		}
			break;
		case Tank.R: {
			if (this.who) {
				g.drawImage(Tank.tankImg.get("mytankR"), this.position_x,
						this.position_y, Tank.SIZE, Tank.SIZE, null);
			} else {
				g.drawImage(enemyImage.get("enemyR"), this.position_x,
						this.position_y, Tank.SIZE, Tank.SIZE, null);
			}
		}
			break;
		case Tank.U: {
			if (this.who) {
				g.drawImage(Tank.tankImg.get("mytankU"), this.position_x,
						this.position_y, Tank.SIZE, Tank.SIZE, null);
			} else {
				g.drawImage(enemyImage.get("enemyU"), this.position_x,
						this.position_y, Tank.SIZE, Tank.SIZE, null);
			}
		}
			break;
		default: {
			if (this.who) {
				g.drawImage(Tank.tankImg.get("mytankU"), this.position_x,
						this.position_y, Tank.SIZE, Tank.SIZE, null);
			} else {
				g.drawImage(enemyImage.get("enemyU"), this.position_x,
						this.position_y, Tank.SIZE, Tank.SIZE, null);
			}
		}
			break;
		}
		// 出生后5秒为无敌状态
		if (god) {
			if (drawStep % 2 == 0) {
				g.drawImage(Tank.tankImg.get("god0"), this.position_x,
						this.position_y, Tank.SIZE, Tank.SIZE, null);
			} else {
				g.drawImage(Tank.tankImg.get("god1"), this.position_x,
						this.position_y, Tank.SIZE, Tank.SIZE, null);
			}
		}

		this.move();

	}

	private void move() {
		// 移动
		this.oldX = this.position_x;
		this.oldY = this.position_y;

		// 区分电脑和自己的移动方式
		if (this.who) {
			if (isLive && isMove) {
				switch (this.dir) {
				case Tank.U:
					this.position_y -= Tank.speed_y;
					break;
				case Tank.L:
					this.position_x -= Tank.speed_x;
					break;
				case Tank.R:
					this.position_x += Tank.speed_x;
					break;
				case Tank.D:
					this.position_y += Tank.speed_y;
					break;
				case Tank.STOP:
					break;
				}
			}

		} else {
			switch (this.dir) {
			case Tank.U:
				this.position_y -= Tank.speed_y;
				break;
			case Tank.L:
				this.position_x -= Tank.speed_x;
				break;
			case Tank.R:
				this.position_x += Tank.speed_x;
				break;
			case Tank.D:
				this.position_y += Tank.speed_y;
				break;
			case Tank.STOP:
				break;
			}
		}

		// 判断是否出界
		if (this.position_x < 0)
			this.position_x = 0;
		if (this.position_x > 664)
			this.position_x = 664;
		if (this.position_y < 0)
			this.position_y = 0;
		if (this.position_y > 689)
			this.position_y = 689;

		// 让电脑自动产生方向
		if (!who) {
			if (step <= 0) {
				step = random.nextInt(50) + 10;
				this.dir = random.nextInt(5);
			}
			step--;

			// 设置电脑有一定几率开火
			if (random.nextInt(100) >= 95) {
				this.fire();
			}
		}

	}

	public boolean collideWithWall(Wall w) {
		// 判断是否撞墙
		if (w.getWallType() != Wall.ICE && w.getWallType() != Wall.FOREST
				&& this.getTankRect().intersects(w.getWallRect())) {
			this.stay();
			return true;
		}
		return false;
	}

	public boolean collideWithWalls(List<Wall> walls) {
		// 判断所有的墙
		for (int i = 0; i < walls.size(); i++) {
			if (collideWithWall(walls.get(i))) {
				// System.out.println(i);
				return true;
			}
		}
		return false;

	}

	public boolean collideWithTank(Tank t) {
		// 判断是否撞到坦克
		if (this != t && this.getTankRect().intersects(t.getTankRect())) {
			this.stay();
			t.stay();
			return true;
		}
		return false;
	}

	public boolean collideWithTanks(List<Tank> tanks) {
		// 坦克群之间的撞击
		for (int i = 0; i < tanks.size(); i++) {
			if (collideWithTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}

	public void fire() {
		// 开火播放声音
		// MediaPlayer.play(MediaPlayer.FIRE);
		int x = 0, y = 0;
		switch (this.dir) {
		case Tank.D:
			x = this.getX() + (Tank.SIZE - Bullet.SIZE) / 2;
			y = this.getY() + Tank.SIZE;
			break;
		case Tank.R:
			x = this.getX() + Tank.SIZE;
			y = this.getY() + (Tank.SIZE - Bullet.SIZE) / 2;
			break;
		case Tank.L:
			x = this.getX() - Bullet.SIZE;
			y = this.getY() + (Tank.SIZE - Bullet.SIZE) / 2;
			break;
		case Tank.U:
			x = this.getX() + (Tank.SIZE - Bullet.SIZE) / 2;
			y = this.getY() - Bullet.SIZE;
			break;
		default:
			break;
		}

		Bullet b = new Bullet(x, y, this.dir, gamePanel);
		gamePanel.bullets.add(b);
	}

	public void fire(boolean who) {
		// 开火播放声音
		// MediaPlayer.play(MediaPlayer.FIRE);
		int x = 0, y = 0;
		switch (this.dir) {
		case Tank.D:
			x = this.getX() + (Tank.SIZE - Bullet.SIZE) / 2;
			y = this.getY() + Tank.SIZE;
			break;
		case Tank.R:
			x = this.getX() + Tank.SIZE;
			y = this.getY() + (Tank.SIZE - Bullet.SIZE) / 2;
			break;
		case Tank.L:
			x = this.getX() - Bullet.SIZE;
			y = this.getY() + (Tank.SIZE - Bullet.SIZE) / 2;
			break;
		case Tank.U:
			x = this.getX() + (Tank.SIZE - Bullet.SIZE) / 2;
			y = this.getY() - Bullet.SIZE;
			break;
		default:
			break;
		}

		Bullet b = new Bullet(x, y, this.dir, gamePanel, who);
		gamePanel.bullets.add(b);
	}

	public Rectangle getTankRect() {
		return new Rectangle(this.position_x, this.position_y, Tank.SIZE,
				Tank.SIZE);
	}

	public int getX() {
		return this.position_x;
	}

	public int getY() {
		return this.position_y;
	}

	public boolean isGod() {
		return this.god;
	}

	public boolean isWho() {
		return this.who;
	}

	public void setGod(boolean t) {
		this.god = t;
	}

	// 表示不能行走
	public void stay() {
		this.position_x = this.oldX;
		this.position_y = this.oldY;
	}

	public void setX(int x) {
		this.position_x = x;
	}

	public void setY(int y) {
		this.position_y = y;
	}

	public void KeyTyped(KeyEvent e) {
		// unused

	}

	public void KeyPressed(KeyEvent e) {
		// 按下键盘不放时
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F1:
			Help.getHelpDialog(gamePanel);
			break;
		case KeyEvent.VK_F2: {
			// 复活
			if (!isLive && GamePanel.timer > 100) {
				this.god = true;
				this.isLive = true;
				drawStep = 0;
				life = MAX_LIFE;
			}
		}
			break;
		case KeyEvent.VK_CONTROL:
		case KeyEvent.VK_R: {
			if (isLive && GamePanel.timer > 100) {
				this.fire(true);
				MediaPlayer.play(MediaPlayer.FIRE);
			}
		}
			break;
		case KeyEvent.VK_SPACE: {
			if (isLive && GamePanel.timer > 100) {
				for (int i = 0; i < gamePanel.tanks.size(); i++) {
					Tank t = gamePanel.tanks.get(i);
					if (!gamePanel.tanks.get(i).isGod()) {
						gamePanel.explodes.add(new Explode(t.getX(), t.getY(),
								Tank.SIZE, gamePanel));
						gamePanel.tanks.remove(t);
						gamePanel.kill++;
						gamePanel.TANK_NUM--;

					}
				}
			}
		}
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W: {
			this.isMove = true;
			this.dir = Tank.U;
		}
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S: {
			this.isMove = true;
			this.dir = Tank.D;
		}
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A: {
			this.isMove = true;
			this.dir = Tank.L;
		}
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D: {
			this.isMove = true;
			this.dir = Tank.R;
		}
			break;
		}
	}

	public void KeyReleased(KeyEvent e) {
		// 松开键盘时
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D: {
			this.isMove = false;

		}
			break;
		}
	}

	public class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(position_x, position_y - 5, SIZE, 5);
			g.fillRect(position_x, position_y - 5, SIZE * life / MAX_LIFE, 5);
			g.setColor(c);
		}

	}

}
