package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StrongBrick extends Brick {

    private int lives = 2;


    public StrongBrick(SpriteBatch batch, int x, int y) {
        // usa constructor que permite indicar o spritesheet (assumido 4 colunas x 1 linha)
        super(batch, "red.png", 2, 1, x, y);
    }

    @Override
    public void onCollision() {
        lives--;
        if (lives <= 0) {
            collided = true;
        }

    }

    @Override
    public int getPoints() { return 20; }
}
