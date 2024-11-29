package io.github.AngryBirdsCDAbhi.Lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class GifAnimation {
    private Array<Texture> frames;
    private float frameDuration;
    private float stateTime;

    public GifAnimation(String baseFilePath, int frameCount, float frameDuration) {
        this.frameDuration = frameDuration;
        frames = new Array<>();

        // Load each frame using its specific naming convention
        for (int i = 0; i < frameCount; i++) {
            String fileName = baseFilePath + String.format("%03d", i) + "_delay-0.15s.gif";
            frames.add(new Texture(Gdx.files.internal(fileName)));
        }
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
    }

    public Texture getCurrentFrame() {
        int frameIndex = (int)(stateTime / frameDuration) % frames.size;
        return frames.get(frameIndex);
    }

    public void dispose() {
        for (Texture frame : frames) {
            frame.dispose();
        }
    }
}
