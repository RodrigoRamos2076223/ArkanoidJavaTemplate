package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.ad.game.Animator;

public class Explosion {
    private final int x, y;
    private final int width, height;
    private final Animator animator;
    private boolean remove = false;
    private float elapsed = 0f;
    private final float duration = 1f; // duração em segundos

    // agora recebe largura/altura para desenhar no lugar do brick
    public Explosion(SpriteBatch batch, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.animator = new Animator(batch, "explosion.png", 5, 1);
        animator.create();
    }

    public void update(float delta) {
        elapsed += delta;
        if (elapsed >= duration) {
            remove = true;
        }
    }

    public void render() {
        if (remove) return;
        animator.render(x, y, width, height);
    }

    public boolean shouldRemove() {
        return remove;
    }
}
