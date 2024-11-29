package io.github.AngryBirdsCDAbhi;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.AngryBirdsCDAbhi.Levels.Level2f;
import io.github.AngryBirdsCDAbhi.Levels.Level3f;
import io.github.AngryBirdsCDAbhi.Screens.Splash;

/** {@link com.badlogic.gdx.Game} implementation shared by all platforms. */
public class Main extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new Splash(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
    }
}
