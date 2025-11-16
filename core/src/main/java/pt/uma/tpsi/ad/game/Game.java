package pt.uma.tpsi.ad.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.ad.entities.Ball;
import pt.uma.tpsi.ad.entities.BrickGrid;
import pt.uma.tpsi.ad.entities.Player;
import pt.uma.tpsi.ad.entities.PowerUp;

public class Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private BackgroundManagement backgroundManagement;
    private BitmapFont font;
    private Player player;
    private Ball ball;
    private BrickGrid brickGrid;

    private boolean gameOver = false;

    private String powerUpMessage = null;
    private float powerUpTimeLeft = 0f;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1280, 720);
        Gdx.graphics.setForegroundFPS(60);
        batch = new SpriteBatch();

        backgroundManagement = new BackgroundManagement(batch);
        player = new Player(batch);
        player.create();

        ball = new Ball(batch);
        ball.create();
        brickGrid = new BrickGrid(batch, this);

        font = new BitmapFont();
    }

    @Override
    public void render() {
        batch.begin();
        backgroundManagement.render();
        player.render();
        ball.render();
        float delta = Gdx.graphics.getDeltaTime();
        brickGrid.update(delta, ball, player);
        brickGrid.render();

        if (ball.getBoundingBox().overlaps(player.getBoundingBox())) {
            ball.reverseYDirection();
        }

        if (!gameOver) {
            if (ball.posY() < 0) {
                gameOver = true;
            }
        }

        if (gameOver) {
            font.getData().setScale(3);
            font.draw(batch, "GAME OVER",
                Gdx.graphics.getWidth() / 2f - 150,
                Gdx.graphics.getHeight() / 2f);
        }

        if (brickGrid.isCleared() && !gameOver) {
            font.getData().setScale(3);
            font.draw(batch, "YOU WIN",
                Gdx.graphics.getWidth() / 2f - 130,
                Gdx.graphics.getHeight() / 2f + 60);
        }

        font.getData().setScale(1.2f);
        font.draw(batch, "Score: " + brickGrid.getScore(), 10, Gdx.graphics.getHeight() - 10);

        if (powerUpTimeLeft > 0f) {
            powerUpTimeLeft -= delta;
            if (powerUpTimeLeft < 0f) powerUpTimeLeft = 0f;
            int secs = (int) Math.ceil(powerUpTimeLeft);
            if (powerUpMessage == null) powerUpMessage = "Power-up";
            font.getData().setScale(2f);
            font.draw(batch,
                powerUpMessage + " - " + secs + "s",
                Gdx.graphics.getWidth() / 2f - 200,
                Gdx.graphics.getHeight() - 50);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public void onPowerUpCollected(PowerUp.Type type) {
        if (type == PowerUp.Type.FAST_BALL) {
            powerUpMessage = "Ball speed up";
        } else {
            powerUpMessage = "Paddle speed up";
        }
        powerUpTimeLeft = 5f;
        System.out.println("Game: onPowerUpCollected -> " + powerUpMessage + " for 5s");
    }
}
