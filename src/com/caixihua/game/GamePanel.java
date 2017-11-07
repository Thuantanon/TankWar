package com.caixihua.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	// 负责游戏画面的绘制
	public static int timer = 0;
	public int TANK_NUM = 0;
	public static int TANK_MAX_NUM = 5;
	public int bo = 0;
	public int kill = 0;
	public int die = 0;

	public Tank myTank = new Tank(240, 689, Tank.U, this, true);
	//public Tank myTank = new Tank(0, 0, Tank.STOP, this, true);
	public List<Wall> map = new CopyOnWriteArrayList<Wall>();
	public List<Tank> tanks = new CopyOnWriteArrayList<Tank>();
	public List<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();
	public List<Explode> explodes = new CopyOnWriteArrayList<Explode>();
	private static Image hello = new ImageIcon("images/other/hello.jpg").getImage();
	private static Image get1 = new ImageIcon("images/other/star.gif").getImage();
	//private static Image get2 = new ImageIcon("images/other/timer.gif").getImage();

	public GamePanel() {
		this.setOpaque(false);
		this.setBackground(Color.BLACK);
		this.setBounds(0, 0, MainFrame.GAME_WIDTH, MainFrame.GAME_HEIGHT);
		this.getMap(Maps.getMaps());
	}

	public void paint(Graphics g) {
		// 游戏进程控制
		timer++;
		g.setColor(Color.black);
		g.fillRect(0, 0, MainFrame.GAME_WIDTH, MainFrame.GAME_HEIGHT);

		// 开始游戏绘制
		if (timer < 30) {
			// 欢迎界面
			g.drawImage(hello, 0, 0, MainFrame.GAME_WIDTH,MainFrame.GAME_HEIGHT, null);
			return;
		}
		
		if(timer >= 30 && timer <60 && bo == 0){
			// 打印品牌
			g.setColor(new Color(0, 200, 0));
			g.setFont(new Font("华文新魏", Font.BOLD, 100));
			g.drawString("Java", MainFrame.GAME_WIDTH/2-100, MainFrame.GAME_HEIGHT/2-50);
			return;
		}

		if (timer >= 60 && timer < 100) {
			//if(timer == 40) MediaPlayer.play(MediaPlayer.START);
			// 显示关卡
			g.setColor(Color.gray);
			g.setColor(new Color(0, 0, 200));
			g.setFont(new Font("华文新魏", Font.BOLD, 40));
			g.drawString("第 " + (bo+1) + " 波", MainFrame.GAME_WIDTH / 2 - 68,
					MainFrame.GAME_HEIGHT / 2 - 45);
			g.drawString("守护女神！", MainFrame.GAME_WIDTH / 2 - 90,
					MainFrame.GAME_HEIGHT / 2 - 105);
			g.drawImage(Tank.tankImg.get("mytankU"),
					MainFrame.GAME_WIDTH / 2 - 60, MainFrame.GAME_HEIGHT / 2,
					Tank.SIZE, Tank.SIZE, null);
			g.drawImage(Tank.tankImg.get("enemyU"),
					MainFrame.GAME_WIDTH / 2 + 20, MainFrame.GAME_HEIGHT / 2,
					Tank.SIZE, Tank.SIZE, null);
			return;
		}
		// 画出女神
		if(timer == 100){
			new Thread(new Runnable(){
				public void run() {
					MediaPlayer.play(MediaPlayer.START);
				}
			}).start();
			getMap(Maps.getMaps());
			myTank.setX(240);
			myTank.setY(689);
		}
		
		/*
		 * 游戏控制
		 * 逻辑分析
		 */
		for (int i = 0; i < map.size(); i++) {
			if(map.get(i).getWallType() != Wall.FOREST){
				g.drawImage(map.get(i).getImg(), map.get(i).getX(), map.get(i).getY(), map.get(i).getWidth(), map.get(i).getHeight(), null);
			}
		}

		if(myTank.isLive){
			myTank.draw(g);
			myTank.collideWithWalls(map);
			myTank.collideWithTanks(tanks);
		}
		
		for(int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.draw(g);
			t.collideWithTank(myTank);
			t.collideWithTanks(tanks);
			t.collideWithWalls(map);
		}
		
		// 先画坦克，后画森林
		for (int i = 0; i < map.size(); i++) {
			if(map.get(i).getWallType() == Wall.FOREST){
				g.drawImage(map.get(i).getImg(), map.get(i).getX(), map.get(i).getY(), map.get(i).getWidth(), map.get(i).getHeight(), null);
			}
		}
		
		// 判断子弹是否撞击到物体并打印子弹
		for(int i=0;i<bullets.size();i++){
			Bullet b = bullets.get(i);
			b.hitTanks(tanks);
			b.hitWalls(map);
			b.hitTank(myTank);
			b.hitBullets(bullets);
			if(b.getBulletRect().intersects(new Rectangle(325, 675, 50,50))){
				b.isLive = false;
				explodes.add(new Explode(325, 675, 50, this));
			}
			b.draw(g);
		}
		//  画出女神
		g.drawImage(hello, 325, 675, 50, 50, null);
		// 画出爆炸
		for(int i=0;i<explodes.size();i++){
			explodes.get(i).draw(g);
			
		}
		//   打印相关信息
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawString("Missiles   Count: " + bullets.size(), 3, 10);
		g.drawString("Explodes Count: " + explodes.size(), 3, 30);
		g.drawString("Tanks       Count: " + tanks.size(), 3, 50);
		g.drawString("Die            Count: " + die, 3, 70);
		g.drawString("Kill     Number: " + kill, 3, 90);
		g.drawString("Die            Count: " + die, 3, 110);
		g.drawString("第"+(bo+1)+"波", MainFrame.GAME_WIDTH/2-20, 30);
		g.drawString("帮    助 F1", 620, 20);
		g.drawString("原地复活 F2", 620, 40);
		g.drawString("精确攻击 Space", 620, 60);
		g.setColor(c);
		// 
		
		g.drawImage(get1, 200, 200, null);
		
		// 保持坦克数量
		if(tanks.size() < GamePanel.TANK_MAX_NUM && timer >= 100 && tanks.size()+kill < 20) addTanks();
		if(kill >= 20){
			tanks.clear();
			explodes.clear();
			bullets.clear();
			bo ++;
			kill = 0;
			timer = 30;
			TANK_NUM = 0;
			if(TANK_MAX_NUM < 10){
				TANK_MAX_NUM ++;
			}
		}

	}
	
	private void addTanks(){
		// 增加坦克数量
		for(;;){
			if(TANK_NUM >= TANK_MAX_NUM) break;
			Tank t = new Tank(Tank.random.nextInt(664), 10, Tank.D, this);
			// 判断出生地有无障碍
			if( !t.collideWithTanks(tanks) && !t.collideWithWalls(map) && !t.collideWithTank(myTank) && this.TANK_NUM <= GamePanel.TANK_MAX_NUM && tanks.size() < GamePanel.TANK_MAX_NUM){
				tanks.add(t);
				TANK_NUM ++;
			}
		}
	}

	private void getMap(Wall[] wall) {
		// 获取地图
		for (int i = 0; i < wall.length; i++) {
			this.map.add(wall[i]);
		}
	}

	// 为游戏添加键盘事件
	public void KeyPressed(KeyEvent e) {
		myTank.KeyPressed(e);
	}

	public void KeyReleased(KeyEvent e) {
		myTank.KeyReleased(e);
	}

	public void KeyTyped(KeyEvent e) {
		myTank.KeyTyped(e);
	}
	

}
