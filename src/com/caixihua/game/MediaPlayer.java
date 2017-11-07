package com.caixihua.game;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class MediaPlayer {
	// 声音文件的路径
	public static final String START = "sounds/start.wav";
	public static final String BLAST = "sounds/blast.wav";
	public static final String HIT = "sounds/hit.wav";
	public static final String FIRE = "sounds/fire.wav";
	public static final String ADD = "sounds/add.wav";
	
	private static SourceDataLine sdl = null;

	public static void play(String path) {
		// 播放音乐
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
			AudioFormat af = ais.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
			sdl = (SourceDataLine) AudioSystem.getLine(info);
			sdl.open(af);
			sdl.start();
			int nByte = 0;
			byte[] buffer = new byte[128];
			while (nByte != -1) {
				nByte = ais.read(buffer, 0, 128);
				if (nByte >= 0) {
					sdl.write(buffer, 0, nByte);
				}
			}
			ais.close();
		} catch (Exception e) {
			return;
		}

	}
	

}
