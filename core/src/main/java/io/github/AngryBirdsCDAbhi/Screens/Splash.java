package io.github.AngryBirdsCDAbhi.Screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.AngryBirdsCDAbhi.Engines.SpriteAccessor;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import io.github.AngryBirdsCDAbhi.GameScreen;
import io.github.AngryBirdsCDAbhi.LoadingScreen;
import io.github.AngryBirdsCDAbhi.Main;

public class Splash implements Screen {
    private Sprite splash;
    private SpriteBatch batch;
    private TweenManager tweenManager;
    private Main game;
    private float elapsedTime = 0;

    public Splash(Main main) {
        this.game = main;
    }

    @Override
    public void show() {
        splash = new Sprite(new Texture("Splash.png"));
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALPHA, 1.5f).target(1).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALPHA, 1.5f).target(0).delay(1.5f).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new GameScreen(game));
            }
        }).start(tweenManager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        tweenManager.update(delta);

        batch.begin();
        splash.draw(batch);
        batch.end();
        elapsedTime += delta;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        splash.getTexture().dispose();

    }
}
