package io.github.AngryBirdsCDAbhi.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

public class Settings {
    private ExtendViewport viewport;
    private int reqHeight = 50;
    private int currHeight = 1080;
    private Texture background;
    private Sprite backgroundSprite;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Sprite p1,p2,p3,p4,p5,p6,Rbutton,Lbutton;
    private ArrayList<Sprite> progressBar = new ArrayList<Sprite>();
    int soundBarLevel = 0;
    public Settings(ExtendViewport viewport) {
        this.viewport = viewport;
        this.background = new Texture("Menu/MenuBox.png");
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.backgroundSprite = new Sprite(background);
        this.p1 = new Sprite(new Texture("Menu/Loading0.png"));
        this.p2 = new Sprite(new Texture("Menu/Loading1.png"));
        this.p3 = new Sprite(new Texture("Menu/Loading2.png"));
        this.p4 = new Sprite(new Texture("Menu/Loading3.png"));
        this.p5 = new Sprite(new Texture("Menu/Loading4.png"));
        this.p6 = new Sprite(new Texture("Menu/LoadingFull.png"));
        this.Rbutton = new Sprite(new Texture("Menu/RArrow.png"));
        this.Lbutton = new Sprite(new Texture("Menu/LArrow.png"));
        progressBar.add(p1);
        progressBar.add(p2);
        progressBar.add(p3);
        progressBar.add(p4);
        progressBar.add(p5);
        progressBar.add(p6);
//        slider = new Slider(0,100,5,false,new Skin();
    }
    public void increaseSoundBarLevel() {
        soundBarLevel = Math.min(soundBarLevel + 1, 5);
    }
    public void decreaseSoundBarLevel() {
        soundBarLevel = Math.max(soundBarLevel - 1, 0);
    }
    public boolean isRightButtonTouched(float x, float y) {
        return Rbutton.getBoundingRectangle().contains(x, y);
    }
    public boolean isLeftButtonTouched(float x, float y) {
        return Lbutton.getBoundingRectangle().contains(x, y);
    }
    public void setReqHeight(int reqHeight) {
        this.reqHeight = reqHeight;
    }

    public void update(int aniSpeed) {
        // Adjust currHeight towards reqHeight based on aniSpeed
        if (currHeight != reqHeight) {
            if (currHeight > reqHeight) {
                currHeight -= aniSpeed;
                // Clamp to reqHeight to avoid overshooting
                if (currHeight < reqHeight) {
                    currHeight = reqHeight;
                }
            } else {
                currHeight += aniSpeed;
                // Clamp to reqHeight to avoid overshooting
                if (currHeight > reqHeight) {
                    currHeight = reqHeight;
                }
            }
        }
    }
    public Rectangle getBoundingRectangle() {
        return new Rectangle(100, currHeight, viewport.getWorldWidth() - 200, viewport.getWorldHeight() - 100);
    }
    public void draw(int aniSpeed, boolean isOn) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        update(aniSpeed);
        batch.begin();
        if (isOn) {
            backgroundSprite.setSize(viewport.getWorldWidth() - 200, viewport.getWorldHeight() - 100);
            backgroundSprite.setPosition(100, currHeight);

            backgroundSprite.setAlpha(1);
            backgroundSprite.draw(batch);
        }
        if (isOn) {
                progressBar.get(soundBarLevel).setSize(800, 100);
                progressBar.get(soundBarLevel).setPosition(600, currHeight + 410);
                progressBar.get(soundBarLevel).draw(batch);
                Rbutton.setSize(150, 150);
                Rbutton.setPosition(1500, currHeight + 400);
                Rbutton.draw(batch);
                Lbutton.setSize(150, 150);
                Lbutton.setPosition(400, currHeight + 400);
                Lbutton.draw(batch);
        }
        batch.end();
    }


    public void dispose() {
        // Properly dispose resources to prevent memory leaks
        background.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }
}
