package com.subzero.ld34.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.subzero.ld34.LD34;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Overgrow";
		config.width = 900;
		config.height = 600;
		config.resizable = false;
		new LwjglApplication(new LD34(), config);
	}
}
