package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.ad.game.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BrickGrid {

    private final List<Brick> bricks;
    private final List<Explosion> explosions;
    private final List<PowerUp> powerUps;
    private final SpriteBatch batch;
    private final Game game;
    private int score = 0;

    private static final int rows = 4;
    private static final int cols = 20;

    // layout constants (simple)
    private static final int brickW = 32;
    private static final int brickH = 16;
    private static final int spacingX = 20;
    private static final int spacingY = 13;
    private static final int topOffset = 100;

    // probability for a brick to contain a powerup (~2 per 10 bricks)
    private static final float powerupProb = 0.2f;

    public BrickGrid(SpriteBatch batch, Game game) {
        this.batch = batch;
        this.game = game;
        this.bricks = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        createBricks();
    }

    private void createBricks() {
        Random rng = new Random();

        int totalWidth = cols * (brickW + spacingX) - spacingX;
        int startX = (Gdx.graphics.getWidth() - totalWidth) / 2;
        int startY = Gdx.graphics.getHeight() - topOffset;

        bricks.clear();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Brick b;

                if (rng.nextFloat() < powerupProb) {
                    b = new PowerUpBrick(batch, 0, 0);
                } else {
                    float p = rng.nextFloat();
                    if (p < 0.5f) b = new NormalBrick(batch, 0, 0);
                    else if (p < 0.8f) b = new StrongBrick(batch, 0, 0);
                    else b = new IndestructibleBrick(batch, 0, 0);
                }

                int x = startX + col * (brickW + spacingX);
                int y = startY - row * (brickH + spacingY);

                b.setPosition(x, y);
                bricks.add(b);
            }
        }
    }

    public void update(float delta, Ball ball, Player player) {
        Iterator<Brick> iterator = bricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            if (brick.getBoundingBox().overlaps(ball.getBoundingBox())) {
                brick.onCollision();
                ball.reverseYDirection();

                if (brick.isCollided()) {
                    score += brick.getPoints();

                    int explosionX = brick.getPosX();
                    int explosionY = brick.getPosY();
                    int w = (int) brick.getBoundingBox().width;
                    int h = (int) brick.getBoundingBox().height;
                    explosions.add(new Explosion(batch, explosionX, explosionY, w, h));

                    if (brick instanceof PowerUpBrick) {
                        PowerUp.Type[] vals = PowerUp.Type.values();
                        PowerUp.Type t = vals[new Random().nextInt(vals.length)];
                        PowerUp pu = new PowerUp(batch, brick.getPosX(), brick.getPosY(), t);
                        powerUps.add(pu);
                    }

                    iterator.remove();
                }

                break;
            }
        }

        // limpar explosões simples
        Iterator<Explosion> expIt = explosions.iterator();
        while (expIt.hasNext()) {
            Explosion e = expIt.next();
            if (e.shouldRemove()) {
                expIt.remove();
            }
        }

        // atualizar powerUps
        Iterator<PowerUp> pIt = powerUps.iterator();
        while (pIt.hasNext()) {
            PowerUp pu = pIt.next();
            pu.update(delta);

            if (pu.getBoundingBox().overlaps(player.getBoundingBox())) {
                pu.activate(ball, player, 5000L); // 5 segundos
                if (game != null) {
                    System.out.println("PowerUp collected: " + pu.getType());
                    game.onPowerUpCollected(pu.getType());
                }
                pIt.remove();
            } else if (pu.shouldRemove()) {
                pIt.remove();
            }
        }
    }

    public void render() {
        for (Brick brick : bricks) {
            if (!brick.isCollided()) {
                brick.render();
            }
        }

        for (Explosion e : explosions) {
            e.render();
        }

        for (PowerUp pu : powerUps) {
            pu.render();
        }
    }

    public int getScore() { return score; }
    public boolean isCleared() { return bricks.isEmpty(); }
}
