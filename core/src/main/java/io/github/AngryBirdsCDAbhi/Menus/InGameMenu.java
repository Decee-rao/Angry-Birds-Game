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
import io.github.AngryBirdsCDAbhi.Lib.ButtonComp;
import io.github.AngryBirdsCDAbhi.LoadingScreen;
import io.github.AngryBirdsCDAbhi.Main;

import java.io.File;

public class InGameMenu extends Actor {
    private ExtendViewport viewport;
    private int reqHeight = 50;
    private int currHeight = 1150;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Sprite restartButton, levelButton, homeButton,MenuBird,MenuHappy,MenuSad;
    private Texture restartbutton,levelbutton,homebutton,defaultMenuBird;
    private Sprite restartButtonL, levelButtonL, homeButtonL;
    private ButtonComp restartButtonComp, levelButtonComp, homeButtonComp;
    private float alpha = 0.72f;
    private ExtendViewport mViewport;
    private boolean isOverRestart = false;
    private boolean isOverLevel = false;
    Main game;
    private boolean isOverHome = false;
    private int lvl;
    OrthographicCamera camera;
    // Change constructor to accept the game instance
    public InGameMenu(ExtendViewport viewport, OrthographicCamera camera, int lvl, Main game) {
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
        this.homebutton = new Texture("Buttons/HomeDark.png");
        this.homeButton = new Sprite(new Texture("Buttons/HomeDark.png"));
        this.restartButtonL = new Sprite(new Texture("Buttons/ClockWiseLight.png"));
        this.levelButtonL = new Sprite(new Texture("Buttons/LevelsOn.png"));
        this.homeButtonL = new Sprite(new Texture("Buttons/HomeLight.png"));
        this.MenuBird = new Sprite(new Texture("Menu Bird.png"));
        this.MenuHappy = new Sprite(new Texture("Menu Happy.png"));
        this.MenuSad = new Sprite(new Texture("Menu Sad.png"));
        this.defaultMenuBird = new Texture("Menu Bird.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        update(15);
        draw(15, true);
    }

    @Override
    public void act(float delta) {

        if (Gdx.input.isTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);

            if (restartButton.getBoundingRectangle().contains(touch.x, touch.y)) {
                switch (LoadingScreen.getCurrLevel()) {
                    case 1:
                        getStage().dispose();
                        Level1f.bodies.clear();
                        Level1f.getPiggiesofLevel().clear();
                        Level1f.getBirdsofLevel().clear();
                        Level1f.getBlocksofLevel().clear();
                        deleteFilesInDirectory("Levels/Level1");
                        game.setScreen(new Level1f(game));
                        break;
                    case 2:
                        getStage().dispose();
                        Level2f.bodies.clear();
                        Level2f.getPiggiesofLevel().clear();
                        Level2f.getBirdsofLevel().clear();
                        Level2f.getBlocksofLevel().clear();
                        deleteFilesInDirectory("Levels/Level2");
                        game.setScreen(new Level2f(game));
                        break;
                    case 3:
                        getStage().dispose();
                        Level3f.bodies.clear();
                        Level3f.getPiggiesofLevel().clear();
                        Level3f.getBirdsofLevel().clear();
                        Level3f.getBlocksofLevel().clear();
                        deleteFilesInDirectory("Levels/Level3");
                        game.setScreen(new Level3f(game));
                        break;
                }
            }

            if (levelButton.getBoundingRectangle().contains(touch.x, touch.y)) {
                System.out.println("State is saved !!!");
                switch (LoadingScreen.getCurrLevel()) {
                    case 1:
                        deleteFilesInDirectory("Levels/Level1");
                        Level1f.saveState();
                        game.setScreen(new LoadingScreen(game));
                        break;
                    case 2:
                        deleteFilesInDirectory("Levels/Level2");
                        Level2f.saveState();
                        game.setScreen(new LoadingScreen(game));
                        break;
                    case 3:
                        deleteFilesInDirectory("Levels/Level3");
                        Level3f.saveState();
                        game.setScreen(new LoadingScreen(game));
                        break;
                }
                Gdx.input.setInputProcessor(null);
                game.setScreen(new LoadingScreen(game));
            }

            if (homeButton.getBoundingRectangle().contains(touch.x, touch.y)) {
                System.out.println("Home");
                Gdx.input.setInputProcessor(null);
                getStage().dispose();
                game.setScreen(new GameScreen(game));
            }
        }
        if(restartButton.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())){
            restartButton.setTexture(restartButtonL.getTexture());
            setMenuBird(MenuHappy);
        }
        else {
            restartButton.setTexture(restartbutton);
            setMenuBird(MenuBird);
        }
        if (levelButton.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())){
            levelButton.setTexture(levelButtonL.getTexture());
            setMenuBird(MenuBird);
        }
        else {
            levelButton.setTexture(levelbutton);
            setMenuBird(MenuBird);
        }
        if (homeButton.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())){
            homeButton.setTexture(homeButtonL.getTexture());
            setMenuBird(MenuSad);
        }
        else {
            homeButton.setTexture(homebutton);
            setMenuBird(MenuSad);
        }
    }

    private void deleteFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        System.out.println("Failed to delete: " + file.getName());
                    }
                } else if (file.isDirectory()) {
                    deleteFilesInDirectory(file.getAbsolutePath()); // Recursively delete subdirectories
                    if (!file.delete()) {
                        System.out.println("Failed to delete directory: " + file.getName());
                    }
                }
            }
        }
    }
    public Sprite getRestartButton() {
        return restartButton;
    }

    public Sprite getLevelbutton() {
        return levelButton;
    }

    public Sprite getHomebutton() {
        return homeButton;
    }

    public void setReqHeight(int reqHeight) {
        this.reqHeight = reqHeight;
    }

    public void setMenuBird(Sprite menuBird) {
        MenuBird = menuBird;
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
        MenuBird.setSize(300, 400);
        MenuBird.setPosition(800, currHeight + 400);
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
