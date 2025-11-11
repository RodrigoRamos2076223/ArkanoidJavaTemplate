package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.ad.game.Animator;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private Animator animator;
    private int posX, posY;
    private Rectangle boundingBox;
    private int speed = 5;      // tornar inteiro para evitar problemas de cast

    private int width, height;  // dimensões fixas do sprite (frames)

    public Player(SpriteBatch batch){
        animator = new Animator(batch, "full_paddle.png", 6, 1);
    }

    public void create() {
        animator.create();
        // guardar dimensões dos frames imediatamente
        width = animator.getWidth();
        height = animator.getHeight();

        posX = (Gdx.graphics.getWidth() / 2) - width / 2;
        posY = height;
        // cria o Rectangle com tamanho fixo
        boundingBox = new Rectangle(posX, posY, width, height);
    }

    public void render(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            posX -= speed;
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            posX += speed;

        // limitar dentro do ecrã
        if (posX < 0) posX = 0;
        if (posX > Gdx.graphics.getWidth() - width) posX = Gdx.graphics.getWidth() - width;

        // garante que o bounding box mantém sempre o mesmo tamanho
        boundingBox.setPosition(posX, posY);
        boundingBox.setSize(width, height);

        // desenha a animação, forçando o tamanho para o mesmo que o boundingBox
        animator.render(posX, posY, width, height);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }


    // Power-up: aumenta velocidade do paddle
    public void increaseSpeed() {
        speed = speed + 5;
    }
}
