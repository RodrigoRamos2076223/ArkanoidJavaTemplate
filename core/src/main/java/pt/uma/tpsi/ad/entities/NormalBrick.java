package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NormalBrick extends Brick {

    public NormalBrick(SpriteBatch batch, int x, int y) {
        // Usar yellow.png como spritesheet (assumido 4 colunas x 1 linha)
        super(batch, "yellow.png", 2, 1, x, y);
    }

    @Override
    public void onCollision() {
        collided= true;
    }

    @Override
    public int getPoints() {
        return 50;
    }

}
