package com.caixihua.game;


import java.awt.Dialog;

import javax.swing.JOptionPane;

public class Help {

	private static String help = 
			"�ϣ�    W  ��  �������\n" +
			"�£�    S   ��  �������\n" +
			"��    A   ��  �������\n" +
			"�ң�    D   ��  �������\n" +
			"��ͨ������R �� Ctrl\n" +
			"���⹥�����ո�\n" +
			"������          F1\n" +
			"��Ծ�ؿ��� F2\n\n" +
			"";
	
	public static void getHelpDialog(GamePanel tc) {
		Dialog h = new JOptionPane(help).createDialog(tc, "����˵��");
		h.setVisible(true);
		
	}
	
}
