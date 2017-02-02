package za.co.pjpdev.dungeongen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
		gen = new Generator(100, 100, 20);
		gen.generate();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.08f, 0.42f, 0.63f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gen.render(shapeRenderer);

		update(Gdx.graphics.getDeltaTime());
	}

	public void update (float delta) {
		float x = 0;
		float y = 0;
		float speed = 50;

		if (Gdx.input.isKeyPressed(Keys.A)) {
			x = speed;
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			x = -speed;
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			y = -speed;
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			y = speed;
		}

		gen.move(x, y, delta);
	}
}
