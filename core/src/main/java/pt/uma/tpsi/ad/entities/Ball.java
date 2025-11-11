package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import pt.uma.tpsi.ad.game.Animator;

public class Ball {
    private Animator animator;
    private int posX, posY;
    private Rectangle boundingBox;
    private int directionX;
    private int directionY;
    private double angle;
    private int speedX = 7; // aumenta para a bola ficar mais rápida horizontalmente
    private int speedY = 5;


    public Ball(SpriteBatch batch){

        animator = new Animator(batch, "ball.png", 2, 2);
        this.directionX =1;
        this.directionY =1;
        this.angle =0;

    }

    public void create() {
        animator.create();
        posX = (Gdx.graphics.getWidth()/2) - this.animator.getWidth()/2;
        posY = (Gdx.graphics.getHeight()/2);
        boundingBox = new Rectangle(posX, posY, animator.getWidth(), animator.getHeight());

    }

    public void render(){

        posY+=(directionY* speedY);
        posX+=(speedX * directionX);

        if (posY > Gdx.graphics.getHeight() - animator.getHeight()) {
            directionY = -1;
        }

        if (posX > Gdx.graphics.getWidth() - animator.getWidth()) {
            directionX = -1;
        } else if (posX < 0) {
            directionX = 1;
        }


        boundingBox.setPosition(posX, posY);
        animator.render(posX,posY);
    }
    public int posY() {
        return posY;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void reverseYDirection() {
        directionY *= -1;
    }

    // Empurra a bola para fora do rect fornecido para evitar deteção repetida de colisão
    public void resolveCollisionWith(Rectangle rect) {
        if (animator == null || boundingBox == null) return;
        float ballCenterY = posY + animator.getHeight() / 2f;
        float rectCenterY = rect.y + rect.height / 2f;
        if (ballCenterY < rectCenterY) {
            // bola estava abaixo do rect, posiciona-a abaixo
            posY = (int) (rect.y - animator.getHeight() - 1);
        } else {
            // bola estava acima do rect, posiciona-a acima
            posY = (int) (rect.y + rect.height + 1);
        }
        boundingBox.setPosition(posX, posY);
    }

    // Ajusta a direção horizontal dependendo do ponto de contacto no rect (paddle ou brick)
    // Versão simples: calcula um offset normalizado (-1 = totalmente à esquerda, +1 = totalmente à direita)
    // e define directionX e speedX proporcionalmente. Não usa vetores.
    public void adjustDirectionOnContact(Rectangle rect) {
        // centro da bola em X
        float ballCenterX = posX + animator.getWidth() / 2f;
        // posição relativa dentro do rect (0.0 = esquerda, 1.0 = direita)
        float relative = (ballCenterX - rect.x) / rect.width;
        // normaliza para -1 .. 1 (centro = 0)
        float offset = (relative - 0.5f) * 2f;

        // limiar para considerar como "centro" (sem desvio horizontal)
        float deadZone = 0.20f; // 20% ao centro
        if (Math.abs(offset) <= deadZone) {
            directionX = 0;
            speedX = 0; // movimento vertical
            return;
        }

        // define a direção (sinal do offset)
        directionX = offset > 0 ? 1 : -1;

        // velocidade horizontal proporcional à distância do centro (mínimo 1, máximo 6)
        int max = 6;
        int min = 1;
        int calculated = Math.round((Math.abs(offset) - deadZone) / (1f - deadZone) * max);
        speedX = Math.max(min, Math.min(max, calculated));
    }

}
