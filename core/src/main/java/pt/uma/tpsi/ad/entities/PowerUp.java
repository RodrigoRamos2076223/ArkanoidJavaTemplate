package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.ad.game.Animator;

import java.util.Timer;
import java.util.TimerTask;

public class PowerUp {
    public enum Type { FAST_BALL, FAST_PADDLE }

    private final Animator animator;
    private final int x;
    private int y;
    private final Rectangle boundingBox;
    private final Type type;
    private boolean remove = false;
    private boolean active = false;
    private Timer timer;

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
        if (!remove && !active) {
            y -= 3;
            boundingBox.setPosition(x, y);
        }
    }

    public void render() {
        if (!remove) {
            animator.render(x, y);
        }
    }

    public void activate(Ball ball, Player player, long durationMs) {
        if (active || remove) return;
        active = true;
        if (type == Type.FAST_BALL) {
            ball.increaseSpeedY();
        } else if (type == Type.FAST_PADDLE) {
            player.increaseSpeedPlayer();
        }
        TimerTask task = new TimerTask() {
            public void run() {
                if (type == Type.FAST_BALL) {
                    ball.resetSpeedY();
                } else if (type == Type.FAST_PADDLE) {
                    player.decreaseSpeedPlayer();
                }
                active = false;
                remove = true;
            }
        };
        timer = new Timer("PowerUpTimer", true);
        timer.schedule(task, durationMs);
    }

    public Rectangle getBoundingBox() { return boundingBox; }
    public Type getType() { return type; }
    public boolean shouldRemove() { return remove || y + boundingBox.height < 0; }
}
