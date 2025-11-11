package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NormalBrick extends Brick {

    public NormalBrick(SpriteBatch batch, int x, int y) {
        // Passa para a superclasse o batch, o sprite e a posição
        super(batch, "yellow.png", x, y);
    }

    @Override
    public void onCollision() {
        // Marca o bloco como colidido/destroído
        collided = true;
        // Aqui podes adicionar lógica para pontuação, efeitos visuais, etc.
        System.out.println("NormalBrick colidido!");
    }

}
