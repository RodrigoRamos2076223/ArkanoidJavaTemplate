package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StrongBrick extends Brick {

    private int lives = 2;

    public StrongBrick(SpriteBatch batch, int x, int y) {
        // sprite animado com 6 frames
        super(batch, "red.png", 6, 1, x, y);
    }

    @Override
    public void onCollision() {
        lives--;
        if (lives <= 0) {
            collided = true;
        }
        System.out.println("StrongBrick colidido! Vidas restantes: " + lives);
    }

    @Override
    public void render() {
        // se metade partido (uma vida a menos) mostrar frame fixo 1, senão animar
        if (lives == 1) {
            animator.renderFrame(posX, posY, standardWidth, standardHeight, 1);
        } else {
            super.render();
        }
    }
}
