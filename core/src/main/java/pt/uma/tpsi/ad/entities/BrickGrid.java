package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrickGrid {

    private final List<Brick> bricks;
    private final List<Explosion> explosions;
    private final List<PowerUp> powerUps;
    private final SpriteBatch batch;
    private int score = 0; // contador simples de pontos

    private static final int ROWS = 4;
    private static final int COLS = 20;

    public BrickGrid(SpriteBatch batch) {
        this.batch = batch;
        this.bricks = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        createBricks();
    }

    private void createBricks() {
        Random rng = new Random();

        int brickW = 32;
        int brickH = 16;
        int spacingX = 0;
        int spacingY = 0;

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

    // Nota: recebe Player para processar colisões com powerups
    public void update(float delta, Ball ball, Player player) {
        // Checa colisões entre bola e bricks usando um ciclo indexado (reverse) para permitir remoção
        for (int i = bricks.size() - 1; i >= 0; i--) {
            Brick brick = bricks.get(i);
            if (brick.getBoundingBox().overlaps(ball.getBoundingBox())) {
                // processa colisão
                brick.onCollision();

                // inverte a direção da bola uma vez por colisão
                ball.reverseYDirection();
                // empurra a bola para fora do brick para evitar colisões repetidas

                // se o brick foi destruído com este impacto, cria explosão e remove o brick
                if (brick.isCollided()) {
                    // incrementa pontuação conforme o tipo de brick
                    score += brick.getPoints();

                    int explosionX = brick.getPosX();
                    int explosionY = brick.getPosY();
                    int w = (int) brick.getBoundingBox().width;
                    int h = (int) brick.getBoundingBox().height;
                    explosions.add(new Explosion(batch, explosionX, explosionY, w, h));

                    // se for PowerUpBrick, cria um PowerUp a cair
                    if (brick instanceof PowerUpBrick) {
                        PowerUp.Type[] vals = PowerUp.Type.values();
                        PowerUp.Type t = vals[(int) (Math.random() * vals.length)];
                        PowerUp pu = new PowerUp(batch, brick.getPosX(), brick.getPosY(), t);
                        powerUps.add(pu);
                    }

                    bricks.remove(i);
                }


                break;
            }
        }


        for (Explosion e : explosions) {
            e.update(delta);
        }


        explosions.removeIf(Explosion::shouldRemove);


        for (PowerUp pu : powerUps) {
            pu.update(delta);
            if (pu.getBoundingBox().overlaps(player.getBoundingBox())) {
                  if (pu.getType() == PowerUp.Type.FAST_BALL) {
                    ball.increaseSpeedY();
                }
                pu.markForRemove();
            }
        }

        // Remove powerups que caíram ou foram apanhados
        powerUps.removeIf(PowerUp::shouldRemove);
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

        // Renderiza powerups
        for (PowerUp pu : powerUps) {
            pu.render();
        }
    }

    // Retorna a pontuação atual
    public int getScore() { return score; }
}
