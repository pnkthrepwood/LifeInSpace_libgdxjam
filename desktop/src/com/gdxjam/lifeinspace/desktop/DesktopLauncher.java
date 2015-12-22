package com.gdxjam.lifeinspace.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdxjam.lifeinspace.Gaem;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 1200;
		config.height = 800;
		config.resizable = false;
		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		config.backgroundFPS = 0;

		config.fullscreen = false;

		new LwjglApplication(new Gaem(), config);
	}
}
