package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.ad.game.Animator;

public class PowerUp {
    public enum Type {FAST_PADDLE, FAST_BALL }

    private final Animator animator;
    private int x, y;
    private final Rectangle boundingBox;
    private final Type type;
    private boolean remove = false;
    private float fallSpeed = 3; // velocidade inicial a cair

    public PowerUp(SpriteBatch batch, int x, int y, Type type) {
        this.animator = new Animator(batch, "ship.png", 5, 2);
        animator.create();
        this.x = x;
        this.y = y;
        this.type = type;
        int w = animator.getWidth();
        int h = animator.getHeight();
        this.boundingBox = new Rectangle(x, y, w, h);
    }

    public void update(float delta) {
        // simples movimento descendente
        y -= fallSpeed;
        boundingBox.setPosition(x, y);
    }

    public void render() {
        if (remove) return;
        animator.render(x, y);
    }

    public Rectangle getBoundingBox() { return boundingBox; }

    public Type getType() { return type; }

    public void markForRemove() { remove = true; }

    public boolean shouldRemove() { return remove || y + boundingBox.height < 0; }
}
