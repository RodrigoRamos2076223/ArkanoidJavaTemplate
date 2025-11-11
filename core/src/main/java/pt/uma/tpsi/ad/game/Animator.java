package pt.uma.tpsi.ad.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {

    // Constant rows and columns of the sprite sheet
    private int FRAME_COLS, FRAME_ROWS;
    // Objects used
    private Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
    private Texture walkSheet;
    private SpriteBatch spriteBatch;
    private String path;
    private int width, height;

    // store frames so we can draw a specific one when needed
    private TextureRegion[] frames;

    // A variable for tracking elapsed time for the animation
    float stateTime;

    public Animator(SpriteBatch batch, String path, int columns, int rows) {
        this.spriteBatch = batch;
        this.FRAME_COLS = columns;
        this.FRAME_ROWS = rows;
        this.path = path;
    }

    public void create() {

        // Load the sprite sheet as a Texture
        walkSheet = new Texture(Gdx.files.internal(path));
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        this.width = walkSheet.getWidth() / FRAME_COLS;
        this.height = walkSheet.getHeight() / FRAME_ROWS;

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        // save frames for direct access
        this.frames = walkFrames;

        // Initialize the Animation with the frame interval and array of frames
        walkAnimation = new Animation<TextureRegion>(0.095f, walkFrames);
        // time to 0
        stateTime = 0f;
    }


    public void render(int posX, int posY) {
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        spriteBatch.draw(currentFrame, posX, posY); // Draw current frame at (50, 50)
    }

    // Overloaded render that scales the frame to provided width/height
    public void render(int posX, int posY, int drawWidth, int drawHeight) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        spriteBatch.draw(currentFrame, posX, posY, drawWidth, drawHeight);
    }

    // Render a specific frame index (useful to show damage states instead of animating)
    public void renderFrame(int posX, int posY, int drawWidth, int drawHeight, int frameIndex) {
        if (frames == null || frames.length == 0) {
            // fallback to normal render
            render(posX, posY, drawWidth, drawHeight);
            return;
        }
        int idx = Math.max(0, frameIndex) % frames.length;
        TextureRegion frame = frames[idx];
        spriteBatch.draw(frame, posX, posY, drawWidth, drawHeight);
    }


    public void dispose() { // SpriteBatches and Textures must always be disposed
        walkSheet.dispose();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
