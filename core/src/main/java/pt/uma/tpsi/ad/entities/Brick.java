package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.ad.game.Animator;

public abstract class Brick {
    protected Animator animator;
    protected int posX, posY;
    protected Rectangle boundingBox;
    protected boolean collided = false;

    // compatibility constructor (single-frame sprite)
    public Brick(SpriteBatch batch, String spritePath) {
        this(batch, spritePath, 2,1,0,0); // inicializa temporariamente em (0,0)
    }

    // sprite-sheet aware constructor
    public Brick(SpriteBatch batch, String spritePath, int cols, int rows, int x, int y){
        this.animator = new Animator(batch, spritePath, cols, rows);
        this.posX = x;
        this.posY = y;
        // initialize animator and bounding box immediately
        animator.create();
        int w = Math.max(1, animator.getWidth());
        int h = Math.max(1, animator.getHeight());
        this.boundingBox = new Rectangle(posX, posY, w, h);
    }

    // Renderiza o brick no ecrã
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

    // Cada tipo de brick implementa o comportamento da colisão
    public abstract void onCollision();
}
