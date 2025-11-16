package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.ad.game.Animator;
import java.util.Timer;
import java.util.TimerTask;

public class Explosion {
    private final int x, y;
    private final int width, height;
    private final Animator animator;
    private boolean remove = false;

    private static final long durationMs = 1000L;

    public Explosion(SpriteBatch batch, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.animator = new Animator(batch, "explosion.png", 5, 1);
        animator.create();

        TimerTask task = new TimerTask() {
            public void run() {
                remove = true;
            }
        };

        Timer timer = new Timer("ExplosionTimer", true);
        timer.schedule(task, durationMs);
    }

    public void render() {
        if (!remove) animator.render(x, y, width, height);
    }

    public boolean shouldRemove() {
        return remove;
    }
}
