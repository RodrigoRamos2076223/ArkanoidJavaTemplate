package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IndestructibleBrick extends Brick {

    public IndestructibleBrick(SpriteBatch batch, int x, int y) {
        super(batch, "sp_block.png", 6, 1, x, y);
    }

    @Override
    public void onCollision() {
        // Não faz nada, bloco não pode ser destruído
        System.out.println("IndestructibleBrick colidido! Não destruído.");
    }
}
