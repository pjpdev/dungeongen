package za.co.pjpdev.dungeongen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DungeonGen extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;
	Generator gen;
	
	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
		gen = new Generator(315, 175, 100);
		gen.generate();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.08f, 0.42f, 0.63f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gen.render(shapeRenderer);
	}
}
