package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IndestructibleBrick extends Brick {

    public IndestructibleBrick(SpriteBatch batch, int x, int y) {
        super(batch, "purple.png", 2, 1, x, y);
    }

    @Override
    public void onCollision() {
        collided=false;
    }
}
