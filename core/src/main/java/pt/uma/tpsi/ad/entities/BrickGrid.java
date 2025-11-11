package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrickGrid {

    private final List<Brick> bricks;
    private final List<Explosion> explosions;
    private final SpriteBatch batch;

    private static final int ROWS = 4;
    private static final int COLS = 20;

    public BrickGrid(SpriteBatch batch) {
        this.batch = batch;
        this.bricks = new ArrayList<>();
        this.explosions = new ArrayList<>();
        createBricks();
    }

    private void createBricks() {
        Random rng = new Random();

        int brickW = 32;
        int brickH = 16;
        int spacingX = 20;
        int spacingY = 13;

        int totalWidth = COLS * (brickW + spacingX) - spacingX;
        int startX = (Gdx.graphics.getWidth() - totalWidth) / 2;
        int startY = Gdx.graphics.getHeight() - 100;

        bricks.clear();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                float p = rng.nextFloat();
                Brick b;

                if (p < 0.5f) {
                    b = new NormalBrick(batch, 0, 0);
                } else if (p < 0.8f) {
                    b = new StrongBrick(batch, 0, 0);
                } else if (p < 0.9f) {
                    b = new IndestructibleBrick(batch, 0, 0);
                } else {
                    b = new PowerUpBrick(batch, 0, 0);
                }

                int x = startX + col * (brickW + spacingX);
                int y = startY - row * (brickH + spacingY);

                b.posX = x;
                b.posY = y;
                b.getBoundingBox().setPosition(x, y);

                bricks.add(b);
            }
        }
    }

    /** Atualiza colisões e explosões */
    public void update(float delta, Ball ball) {
        // Checa colisões entre bola e bricks usando um ciclo indexado (reverse) para permitir remoção
        for (int i = bricks.size() - 1; i >= 0; i--) {
            Brick brick = bricks.get(i);
            if (brick.getBoundingBox().overlaps(ball.getBoundingBox())) {
                // processa colisão
                brick.onCollision();

                // inverte a direção da bola uma vez por colisão
                ball.reverseYDirection();
                // empurra a bola para fora do brick para evitar colisões repetidas
                ball.resolveCollisionWith(brick.getBoundingBox());

                // se o brick foi destruído com este impacto, cria explosão e remove o brick
                if (brick.isCollided()) {
                    int explosionX = brick.getPosX();
                    int explosionY = brick.getPosY();
                    int w = (int) brick.getBoundingBox().width;
                    int h = (int) brick.getBoundingBox().height;
                    explosions.add(new Explosion(batch, explosionX, explosionY, w, h));
                    bricks.remove(i);
                }
            }
        }

        // Atualiza explosões
        for (Explosion e : explosions) {
            e.update(delta);
        }

        // Remove explosões concluídas
        explosions.removeIf(Explosion::shouldRemove);
    }

    /** Renderiza bricks e explosões */
    public void render() {
        // Renderiza bricks ainda não destruídos
        for (Brick brick : bricks) {
            if (!brick.isCollided()) {
                brick.render();
            }
        }

        // Renderiza explosões
        for (Explosion e : explosions) {
            e.render();
        }
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public List<Explosion> getExplosions() {
        return explosions;
    }
}
