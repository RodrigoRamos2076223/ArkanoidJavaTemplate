package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.ad.game.Animator;

public abstract class Brick {
    protected Animator animator;
    protected int posX, posY;
    protected Rectangle boundingBox;
    protected boolean collided = false;

    public Brick(SpriteBatch batch, String spritePath, int cols, int rows, int x, int y){
        this.animator = new Animator(batch, spritePath, cols, rows);
        this.posX = x;
        this.posY = y;
        animator.create();
        int w = Math.max(1, animator.getWidth());
        int h = Math.max(1, animator.getHeight());
        this.boundingBox = new Rectangle(posX, posY, w, h);
    }

    public void render() {
        if (boundingBox != null) {
            animator.render(posX, posY, (int) boundingBox.width, (int) boundingBox.height);
        } else {
            animator.render(posX, posY);
        }
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public int getPosX() { return posX; }
    public int getPosY() { return posY; }

    public boolean isCollided() {
        return collided;
    }


    public void setPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
        if (this.boundingBox != null) {
            this.boundingBox.setPosition(x, y);
        }
    }

    public abstract void onCollision();

    public int getPoints() { return 0; }
}
