package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PowerUpBrick extends Brick {

    public PowerUpBrick(SpriteBatch batch, int x, int y) {
        super(batch, "green.png", 2, 1, x, y);
    }

    @Override
    public void onCollision() {
        collided = true;
    }

    @Override
    public int getPoints() { return 150; }
}
