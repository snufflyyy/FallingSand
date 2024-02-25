package com.snuffly.fallingsand;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Tile {
    short type = 0; // 0 for no sand 1 for sand

    Rectangle hitBox;

    Texture sandTexture;

    public Tile(float x, float y, float tileSize) {
        hitBox = new Rectangle(x, y, tileSize, tileSize);

        sandTexture = new Texture(Gdx.files.internal("white.png"));
    }

    public void render(SpriteBatch batch) {
        batch.begin();
            if (type == 1) {
                batch.draw(sandTexture, hitBox.x, hitBox.y, hitBox.width, hitBox.height);
            }
        batch.end();
    }
}
