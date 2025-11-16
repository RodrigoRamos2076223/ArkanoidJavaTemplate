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
    private int speed = 10;
    private final int baseSpeed = 10;
    private boolean boosted = false;
    private int width, height;

    public Player(SpriteBatch batch){
        animator = new Animator(batch, "full_paddle.png", 6, 1);
    }

    public void create() {
        animator.create();
        width = animator.getWidth();
        height = animator.getHeight();
        posX = (Gdx.graphics.getWidth() / 2) - width / 2;
        posY = height;
        boundingBox = new Rectangle(posX, posY, width, height);
    }

    public void render(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            posX -= speed;
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            posX += speed;
        if (posX < 0) posX = 0;
        if (posX > Gdx.graphics.getWidth() - width) posX = Gdx.graphics.getWidth() - width;
        boundingBox.setPosition(posX, posY);
        boundingBox.setSize(width, height);
        animator.render(posX, posY, width, height);
    }

    public Rectangle getBoundingBox() { return boundingBox; }

    public void increaseSpeedPlayer() {
        if (!boosted) {
            speed = baseSpeed + 2;
            boosted = true;
        }
    }
    public void decreaseSpeedPlayer() {
        speed = baseSpeed;
        boosted = false;
    }
}
