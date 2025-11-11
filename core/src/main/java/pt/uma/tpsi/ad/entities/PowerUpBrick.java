package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

public class PowerUpBrick extends Brick {

    private static final Random RNG = new Random();

    public PowerUpBrick(SpriteBatch batch, int x, int y) {
        // Usar green.png como spritesheet (assumido 4 colunas x 1 linha)
        super(batch, "green.png", 2, 1, x, y);
    }

    @Override
    public void onCollision() {
        collided = true;



    }
}

