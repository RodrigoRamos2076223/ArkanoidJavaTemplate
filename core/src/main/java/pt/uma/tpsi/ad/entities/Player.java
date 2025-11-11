package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import pt.uma.tpsi.ad.game.Animator;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Player {
    private Animator animator;
    private int posX, posY;
    private Rectangle boundingBox;
    private int speed = 5;      // tornar inteiro para evitar problemas de cast

    private int width, height;  // dimensões fixas do sprite (frames)
    private SpriteBatch batch;
    private BitmapFont font;
    private int score = 0;

    public Player(SpriteBatch batch){
        this.batch = batch;
        animator = new Animator(batch, "full_paddle.png", 6, 1);
    }

    public void create() {
        animator.create();
        // guardar dimensões dos frames imediatamente
        width = animator.getWidth();
        height = animator.getHeight();

        posX = (Gdx.graphics.getWidth() / 2) - width / 2;
        posY = height;
        // cria o Rectangle com tamanho fixo
        boundingBox = new Rectangle(posX, posY, width, height);

        // inicializa a fonte que mostra a pontuação do jogador
        font = new BitmapFont();
        font.getData().setScale(1.5f);
    }

    public void render(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            posX -= speed;
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            posX += speed;

        // limitar dentro do ecrã
        if (posX < 0) posX = 0;
        if (posX > Gdx.graphics.getWidth() - width) posX = Gdx.graphics.getWidth() - width;

        // garante que o bounding box mantém sempre o mesmo tamanho
        boundingBox.setPosition(posX, posY);
        boundingBox.setSize(width, height);

        // desenha a animação, forçando o tamanho para o mesmo que o boundingBox
        animator.render(posX, posY, width, height);

        // desenha a pontuação no canto superior esquerdo
        String scoreText = "Score: " + score;
        // y = screenHeight - 10 para ficar um pouco abaixo do topo
        font.draw(batch, scoreText, 10, Gdx.graphics.getHeight() - 10);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    // métodos para manipular a pontuação
    public void addScore(int amount) {
        score += amount;
    }

    public int getScore() {
        return score;
    }

    // dispose da fonte para evitar leaks (não fecha o SpriteBatch)
    public void dispose() {
        if (font != null) font.dispose();
    }
}
