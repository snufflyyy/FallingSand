package com.snuffly.fallingsand;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class FallingSand extends ApplicationAdapter {
	SpriteBatch batch;

	OrthographicCamera camera;

	Color backgroundColor;

	float tileSize = 10;
	Tile[][] tiles;

	boolean snowMode = false;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		backgroundColor = Color.BLACK;

		tiles = new Tile[Gdx.graphics.getWidth() / (int) tileSize][Gdx.graphics.getHeight() / (int) tileSize];

		for (int x = 0; x < Gdx.graphics.getWidth() / tileSize; x++) {
			for (int y = 0; y < Gdx.graphics.getHeight() / tileSize; y++) {
				tiles[x][y] = new Tile(x * tileSize, y * tileSize, tileSize);
			}
		}
	}

	public void input() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(mousePos);

			for (int x = 0; x < Gdx.graphics.getWidth() / tileSize; x++) {
				for (int y = 0; y < Gdx.graphics.getHeight() / tileSize; y++) {
					if (tiles[x][y].hitBox.contains(mousePos.x, mousePos.y)) {
						tiles[x][y].type = 1;
					}
				}
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			for (int x = 0; x < Gdx.graphics.getWidth() / tileSize; x++) {
				for (int y = 0; y < Gdx.graphics.getHeight() / tileSize; y++) {
					tiles[x][y].type = 0;
				}
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.S) && !snowMode) {
			snowMode = true;
		}
	}

	public void moveSand() {
		for (int x = 0; x < Gdx.graphics.getWidth() / tileSize; x++) {
			for (int y = 0; y < Gdx.graphics.getHeight() / tileSize; y++) {
				if (tiles[x][y].type == 1 && y != 0) {
					if (tiles[x][y - 1].type == 0) {
						tiles[x][y].type = 0;
						tiles[x][y - 1].type = 1;
					} else if (tiles[x][y - 1].type == 1 && tiles[x - 1][y - 1].type == 0 && x != 1) {
						tiles[x][y].type = 0;
						tiles[x - 1][y].type = 1;
					} else if (tiles[x][y - 1].type == 1 && tiles[x + 1][y - 1].type == 0 && x != Gdx.graphics.getWidth() / tileSize - 2) { // dont know why this has to be 2
						tiles[x][y].type = 0;
						tiles[x + 1][y].type = 1;
					}
				}
			}
		}
	}

	public void update() {
		if (!snowMode) {
			input();
		} else {
			int random = (int) (Math.random() * (Gdx.graphics.getWidth() / tileSize - 2 - 1)) + 1;
			tiles[random][(int) (Gdx.graphics.getHeight() / tileSize - 1)].type = 1;
			backgroundColor = Color.LIGHT_GRAY;
			input();
		}
		moveSand();
	}

	@Override
	public void render () {
		update();

		ScreenUtils.clear(backgroundColor);

		batch.setProjectionMatrix(camera.combined);
		for (int x = 0; x < Gdx.graphics.getWidth() / tileSize; x++) {
			for (int y = 0; y < Gdx.graphics.getHeight() / tileSize; y++) {
				tiles[x][y].render(batch);
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
