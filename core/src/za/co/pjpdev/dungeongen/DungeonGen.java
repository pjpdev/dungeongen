package za.co.pjpdev.dungeongen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import za.co.pjpdev.dungeongen.civgen.CivGenerator;
import za.co.pjpdev.dungeongen.mazedungeon.MazeGenerator;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import za.co.pjpdev.dungeongen.mazedungeon.RoomGenerator;


public class DungeonGen extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;
	CivGenerator gen;
	
	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();

		//gen = new MazeGenerator(96, 72, 20);
        gen = new CivGenerator(128, 72, 10);
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
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			gen.generate();
		}
	}
}
