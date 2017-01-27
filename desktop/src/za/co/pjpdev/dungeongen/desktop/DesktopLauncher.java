package za.co.pjpdev.dungeongen.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import za.co.pjpdev.dungeongen.DungeonGen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 360;
		config.fullscreen = false;
		config.resizable = false;
		new LwjglApplication(new DungeonGen(), config);
	}
}
