package io.github.AngryBirdsCDAbhi.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.AngryBirdsCDAbhi.GameScreen;
import io.github.AngryBirdsCDAbhi.Levels.Level1f;
import io.github.AngryBirdsCDAbhi.Levels.Level2f;
import io.github.AngryBirdsCDAbhi.Levels.Level3f;
import io.github.AngryBirdsCDAbhi.LoadingScreen;
import io.github.AngryBirdsCDAbhi.Main;


public class GameEndMenu extends Actor {
    private ExtendViewport viewport;
    private int reqHeight = 50;
    private int currHeight = 1080;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Sprite restartButton, levelButton, homeButton,MenuBird,MenuHappy,MenuSad;
    private Texture restartbutton,levelbutton,homebutton,defaultMenuBird;
    private Sprite restartButtonL, levelButtonL, homeButtonL;
    private float alpha = 0.72f;
    private ExtendViewport mViewport;
    private boolean isOverRestart = false;
    private boolean isOverLevel = false;
    Main game;
    private boolean isOverHome = false;
    private int lvl;
    OrthographicCamera camera;
    // Change constructor to accept the game instance
    public GameEndMenu(ExtendViewport viewport, OrthographicCamera camera, int lvl, Main game) {
        this.viewport = viewport;
        this.camera = camera;
        this.lvl = lvl;
        this.game = game; // Initialize the game instance
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.restartButton = new Sprite(new Texture("Buttons/ClockWiseDark.png"));
        this.restartbutton = new Texture("Buttons/ClockWiseDark.png");
        this.levelButton = new Sprite(new Texture("Buttons/LevelsOff.png"));
        this.levelbutton = new Texture("Buttons/LevelsOff.png");
        this.homebutton = new Texture("Buttons/TickDark.png");
        this.homeButton = new Sprite(new Texture("Buttons/TickDark.png"));
        this.restartButtonL = new Sprite(new Texture("Buttons/ClockWiseLight.png"));
        this.levelButtonL = new Sprite(new Texture("Buttons/LevelsOn.png"));
        this.homeButtonL = new Sprite(new Texture("Buttons/TickLight.png"));
        this.MenuBird = new Sprite(new Texture("Stars.png"));
        this.MenuHappy = new Sprite(new Texture("Stars.png"));
        this.MenuSad = new Sprite(new Texture("Stars.png"));
        this.defaultMenuBird = new Texture("Stars.png");
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        update(13);
        draw(13, true);
    }

    @Override
    public void act(float delta) {
        if(Gdx.input.justTouched()){
            Vector3 WorldCoords = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if(restartButton.getBoundingRectangle().contains(WorldCoords.x, WorldCoords.y)){
                if(LoadingScreen.getCurrLevel()== 1){
                    game.setScreen(new Level1f(game) );
                }
                else if(LoadingScreen.getCurrLevel() == 2){
                    game.setScreen(new Level2f(game));
                }
                else if(LoadingScreen.getCurrLevel() == 3){
                    game.setScreen(new Level3f(game));
                }
            }
            if(levelButton.getBoundingRectangle().contains(WorldCoords.x, WorldCoords.y)){
                game.setScreen(new LoadingScreen(game));
            }
            if(homeButton.getBoundingRectangle().contains(WorldCoords.x, WorldCoords.y)){
                game.setScreen(new GameScreen(game));
            }
        }
    }

    public void update(int aniSpeed) {
        if (currHeight != reqHeight) {
            if (currHeight > reqHeight) {
                currHeight -= aniSpeed;
                if (currHeight < reqHeight) {
                    currHeight = reqHeight;
                }
            } else {
                currHeight += aniSpeed;
                if (currHeight > reqHeight) {
                    currHeight = reqHeight;
                }
            }
        }
        restartButton.setSize(250, 200);
        restartButton.setPosition(800, currHeight + 20);
        levelButton.setSize(200, 200);
        levelButton.setPosition(550, currHeight + 20);
        homeButton.setSize(200, 200);
        homeButton.setPosition(1100, currHeight + 20);
        MenuBird.setSize(600, 500);
        MenuBird.setPosition(700, currHeight + 400);
    }

    public void draw(int aniSpeed, boolean isOn) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        update(aniSpeed);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, alpha);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.rect(500, currHeight, viewport.getWorldWidth() - 1000, viewport.getWorldHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        if (isOn) {
            restartButton.draw(batch);
            levelButton.draw(batch);
            homeButton.draw(batch);
            MenuBird.draw(batch);
        }
        batch.end();
    }

    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
