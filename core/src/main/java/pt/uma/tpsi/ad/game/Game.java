package pt.uma.tpsi.ad.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pt.uma.tpsi.ad.entities.Ball;
import pt.uma.tpsi.ad.entities.BrickGrid;
import pt.uma.tpsi.ad.entities.Player;
import pt.uma.tpsi.ad.entities.Brick;

public class Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private BackgroundManagement backgroundManagement;
    private BitmapFont font;
    private Player player;
    private Ball ball;
    private BrickGrid brickGrid;

    private boolean gameOver = false; // controla o fim do jogo

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1280, 720);
        batch = new SpriteBatch();

        backgroundManagement = new BackgroundManagement(batch);
        player = new Player(batch);
        player.create();

        ball = new Ball(batch);
        ball.create();
        brickGrid = new BrickGrid(batch);

        // 🔹 Usa a fonte padrão do LibGDX — sem precisar de ficheiros externos
        font = new BitmapFont();
    }

    @Override
    public void render() {
        batch.begin();
        backgroundManagement.render();
        player.render();
        ball.render();
        brickGrid.render();
        // Colisões com os blocos
        for (Brick brick : brickGrid.getBricks()) {
            if (!brick.isCollided() && ball.getBoundingBox().overlaps(brick.getBoundingBox())) {
                brick.onCollision();
                ball.reverseYDirection();
            }
        }



        if (ball.getBoundingBox().overlaps(player.getBoundingBox())) {
            // ajusta direção horizontal consoante o ponto de contacto com o paddle
            ball.adjustDirectionOnContact(player.getBoundingBox());
            ball.reverseYDirection(); // faz a bola “saltar” para cima
        }

        // Se o jogo ainda não acabou, atualiza normalmente
        if (!gameOver) {
            if (ball.posY() < 0) { // se a bola sair por baixo do ecrã
                gameOver = true;
            }
        }

        // Se o jogo acabou, mostra o texto no ecrã
        if (gameOver) {
            font.getData().setScale(3); // aumenta o tamanho do texto
            font.draw(batch, "GAME OVER",
                Gdx.graphics.getWidth() / 2f - 150,
                Gdx.graphics.getHeight() / 2f);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
