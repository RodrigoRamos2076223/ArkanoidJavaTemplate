package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IndestructibleBrick extends Brick {

    public IndestructibleBrick(SpriteBatch batch, int x, int y) {
        // Usar purple.png como spritesheet (assumido 4 colunas x 1 linha)
        super(batch, "purple.png", 2, 1, x, y);
    }

    @Override
    public void onCollision() {
        collided=false;

    }
}
