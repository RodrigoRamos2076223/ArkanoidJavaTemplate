package pt.uma.tpsi.ad.entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.ad.game.Animator;

public abstract class Brick {
    protected Animator animator;
    protected int posX, posY;
    protected Rectangle boundingBox;
    protected boolean collided = false;

    // standard size shared between all bricks (set when first brick is created)
    private static int standardWidth = 0;
    private static int standardHeight = 0;

    // Scale factor to render bricks smaller than the source image (pode ser ajustado)
    public static float SCALE = 0.6f;

    // constructor que aceita cols/rows
    public Brick(SpriteBatch batch, String spritePath, int cols, int rows, int x, int y){
        this.animator = new Animator(batch, spritePath, cols, rows);
        this(batch, spritePath, 1, 1, x, y);
    }

    // novo constructor que permite especificar cols/rows do sprite sheet

    // constructor antigo (compatibilidade) assume 1x1
    public Brick(SpriteBatch batch, String spritePath, int x, int y){
        this(batch, spritePath, 1, 1, x, y);
    }

    public Brick(SpriteBatch batch, String spritePath, int cols, int rows, int x, int y){
        this.animator = new Animator(batch, spritePath, cols, rows);
        this.posX = x;
        this.posY = y;
        // bounding box is initialized in create() after animator.create() sets sizes
    }
    public void create() {
        animator.create();
        // define standard size the first time a brick is created
        if (standardWidth == 0 || standardHeight == 0) {
            // aplica escala para reduzir tamanho visual dos bricks
            standardWidth = Math.max(1, Math.round(animator.getWidth() * SCALE));
            standardHeight = Math.max(1, Math.round(animator.getHeight() * SCALE));
        }
        // use the standard size for all bricks' bounding boxes
        this.boundingBox = new Rectangle(posX, posY, standardWidth, standardHeight);
        boundingBox.setPosition(posX, posY);
    }

    public void render() {
        // render scaled to the shared standard size so all bricks appear the same
        animator.render(posX, posY, standardWidth, standardHeight);
    }

    public boolean isCollided() {
        return collided;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public abstract void onCollision();
}
