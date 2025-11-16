package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.ad.game.Animator;

public class Ball {
    private Animator animator;
    private int posX, posY;
    private Rectangle boundingBox;
    private int directionX;
    private int directionY;
    private float speedX = 7;
    private float speedY = 5;
    private final float baseSpeedY = 5;
    private boolean boosted = false;

    public Ball(SpriteBatch batch){
        animator = new Animator(batch, "ball.png", 2, 2);
        this.directionX =1;
        this.directionY =1;
    }

    public void create() {
        animator.create();
        posX = (Gdx.graphics.getWidth()/2) - this.animator.getWidth()/2;
        posY = (Gdx.graphics.getHeight()/2);
        boundingBox = new Rectangle(posX, posY, animator.getWidth(), animator.getHeight());
    }

    public void render(){
        posY += (int)(directionY * speedY);
        posX += (int)(speedX * directionX);
        if (posY > Gdx.graphics.getHeight() - animator.getHeight()) {
            directionY = -1;
        }
        if (posX > Gdx.graphics.getWidth() - animator.getWidth()) {
            directionX = -1;
        } else if (posX < 0) {
            directionX = 1;
        }
        boundingBox.setPosition(posX, posY);
        animator.render(posX,posY);
    }
    public int posY() {
        return posY;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    /** Reverse horizontal direction */
    public void reverseXDirection() {
        directionX *= -1;
    }

    /** Set position of the ball and update bounding box */
    public void setPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
        if (this.boundingBox != null) this.boundingBox.setPosition(x, y);
    }

    public void reverseYDirection() {
        directionY *= -1;
    }

    public void increaseSpeedY() {
        if (!boosted) {
            speedY = baseSpeedY + 1.0f;
            boosted = true;
        }
    }

    public void resetSpeedY() {
        speedY = baseSpeedY;
        boosted = false;
    }
}
