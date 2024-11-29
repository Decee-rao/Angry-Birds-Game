package io.github.AngryBirdsCDAbhi.Lib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ButtonComp extends Actor {

    Sprite main;
    SpriteBatch batch;
    Texture dark;
    Texture light;
    float x, y, width, height;

    public ButtonComp(SpriteBatch batch, Texture dark, Texture light, float x, float y) {
        this.batch = batch;
        this.dark = dark;
        this.light = light;
        this.x = x;
        this.y = y;
        this.main = new Sprite(dark);
        this.width = light.getWidth();
        this.height = light.getHeight();
        main.setBounds(x, y, light.getWidth(), light.getHeight());
    }

    public ButtonComp(SpriteBatch batch, Texture dark, Texture light, float x, float y, float width, float height) {
        this.batch = batch;
        this.dark = dark;
        this.light = light;
        this.x = x;
        this.y = y;
        this.main = new Sprite(dark);
        this.width = width;
        this.height = height;
        main.setBounds(x, y, width, height);
    }

    public Sprite getMain() {
        return main;
    }

    public Texture getDark() {
        return dark;
    }

    public Texture getLight() {
        return light;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    @Override
    public void act(float delta) {
        if(main.getTexture() == null) {
            main.setTexture(dark);
        }
        if (main.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
            main.setTexture(light);
        } else {
            main.setTexture(dark);
        }
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        draw();
    }

    public void draw() {


        batch.begin();
        main.draw(batch);
        batch.end();
    }

    public boolean isClicked(Vector3 touchPos) {
        return main.getBoundingRectangle().contains(touchPos.x, touchPos.y);
    }

    public void setLight() {
        main.setTexture(light);
    }

    public void setDark() {
        main.setTexture(dark);
    }

    public void setPos(float x, float y) {
        main.setPosition(x, y);
    }

    public void setBounds(float x, float y, float width, float height) {
        main.setBounds(x, y, width, height);
    }

    public void setScale(float scale) {
        main.setScale(scale);
    }

    public Boolean isHovered(Vector3 touchPos) {
        return main.getBoundingRectangle().contains(touchPos.x, touchPos.y);
    }

    public void flipX() {
        main.flip(true, false);
    }

    public void flipY() {
        main.flip(false, true);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
