package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Start;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 0;
		config.title="name";
		config.vSyncEnabled = true;
		config.fullscreen=true;
		config.height = 1920 ;
		config.width = 1080;

		new LwjglApplication(new Start(), config);
	}
}
