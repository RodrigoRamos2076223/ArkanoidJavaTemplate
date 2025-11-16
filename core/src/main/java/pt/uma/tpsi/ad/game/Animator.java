package pt.uma.tpsi.ad.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {

    private int FRAME_COLS, FRAME_ROWS;
    private Animation<TextureRegion> walkAnimation;
    private Texture walkSheet;
    private SpriteBatch spriteBatch;
    private String path;
    private int width, height;

    float stateTime;

    public Animator(SpriteBatch batch, String path, int columns, int rows) {
        this.spriteBatch = batch;
        this.FRAME_COLS = columns;
        this.FRAME_ROWS = rows;
        this.path = path;
    }

    public void create() {
        walkSheet = new Texture(Gdx.files.internal(path));
        this.width = walkSheet.getWidth() / FRAME_COLS;
        this.height = walkSheet.getHeight() / FRAME_ROWS;

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        walkAnimation = new Animation<>(0.095f, walkFrames);
        stateTime = 0f;
    }

    public void render(int posX, int posY) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        spriteBatch.draw(currentFrame, posX, posY);
    }

    public void render(int posX, int posY, int drawWidth, int drawHeight) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        spriteBatch.draw(currentFrame, posX, posY, drawWidth, drawHeight);
    }

    public void dispose() {
        walkSheet.dispose();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
