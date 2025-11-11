package pt.uma.tpsi.ad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BrickGrid {

    private List<Brick> bricks;
    private SpriteBatch batch;

    public BrickGrid(SpriteBatch batch) {
        this.batch = batch;
        bricks = new ArrayList<>();
        createBricks();
    }

    private void createBricks() {
        int rows = 5;
        int cols = 16;

        // passo 1: criar instâncias com posições temporárias
        List<Brick> temp = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = 0; // pos temporária
                int y = 0;
                int type = (int)(Math.random() * 4);
                Brick brick;
                switch (type) {
                    case 0: brick = new NormalBrick(batch, x, y); break;
                    case 1: brick = new StrongBrick(batch, x, y); break;
                    case 2: brick = new IndestructibleBrick(batch, x, y); break;
                    default: brick = new NormalBrick(batch, x, y); break;
                }
                temp.add(brick);
            }
        }

        // passo 2: chamar create() em todos para inicializar dimensões compartilhadas
        for (Brick b : temp) {
            b.create();
        }

        // obter largura/altura padrão dos bricks (cast de float para int)
        int brickW = (int) temp.get(0).getBoundingBox().width;
        int brickH = (int) temp.get(0).getBoundingBox().height;

        // calcular espaçamento e posição inicial para centrar a grelha
        int spacingX = Math.round(brickW * 0.2f); // 20% do brick como espaçamento
        int spacingY = Math.round(brickH * 0.4f);

        int totalWidth = cols * brickW + (cols - 1) * spacingX;
        int startX = (int)((com.badlogic.gdx.Gdx.graphics.getWidth() - totalWidth) / 2f);
        int startY = com.badlogic.gdx.Gdx.graphics.getHeight() - 80; // perto do topo, pode ajustar

        // passo 3: posicionar os bricks e adicionar à lista final
        bricks.clear();
        int index = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Brick b = temp.get(index++);
                int x = startX + col * (brickW + spacingX);
                int y = startY - row * (brickH + spacingY);
                // atualizar posição e bounding box
                b.posX = x;
                b.posY = y;
                b.getBoundingBox().setPosition(x, y);
                bricks.add(b);
            }
        }

        // Embaralhar a lista para mais aleatoriedade
        Collections.shuffle(bricks);
    }

    public void render() {
        for (Brick brick : bricks) {
            if (!brick.isCollided()) {
                brick.render();
            }
        }
    }

    public List<Brick> getBricks() {
        return bricks;
    }
}
