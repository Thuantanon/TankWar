package com.caixihua.game;


import java.awt.Dialog;

import javax.swing.JOptionPane;

public class Help {

	private static String help = 
			"上：    W  或  方向键上\n" +
			"下：    S   或  方向键下\n" +
			"左：    A   或  方向键左\n" +
			"右：    D   或  方向键右\n" +
			"普通攻击：R 或 Ctrl\n" +
			"特殊攻击：空格\n" +
			"帮助：          F1\n" +
			"跳跃关卡： F2\n\n" +
			"";
	
	public static void getHelpDialog(GamePanel tc) {
		Dialog h = new JOptionPane(help).createDialog(tc, "操作说明");
		h.setVisible(true);
		
	}
	
}
