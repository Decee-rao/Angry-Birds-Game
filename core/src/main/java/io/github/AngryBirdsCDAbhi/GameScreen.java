package io.github.AngryBirdsCDAbhi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import games.rednblack.editor.renderer.SceneConfiguration;
import games.rednblack.editor.renderer.SceneLoader;
import io.github.AngryBirdsCDAbhi.InputAdapter.GameInputProcessor;
//import io.github.AngryBirdsCDAbhi.InputAdapter.GameScreenInputAdapter;
import io.github.AngryBirdsCDAbhi.Lib.ButtonComp;
import io.github.AngryBirdsCDAbhi.Menus.Settings;

public class GameScreen extends ScreenAdapter {
    Main game;
    ExtendViewport mViewport;
    OrthographicCamera mCamera;
    SpriteBatch batch;
    AssetManager mAssetManager;
    SceneLoader mSceneLoader;
    private Texture PlayButtonDark, PlayButtonLight, SettingsDark, SettingsLight, ExitT, SoundT, not, backGround;
    private Sprite notS, BackGround;
    private ButtonComp PlayButton, SettingsButton, SoundButton, ExitButton;
    private boolean isSoundOff;
    private boolean isSettingsOn;
    private Settings settings;
    private static GameInputProcessor gameInputProcessor;

    public GameScreen(Main game) {
        this.game = game;

    }

    @Override
    public void show() {
        // Initialize camera and batch
        mCamera = new OrthographicCamera();
        batch = new SpriteBatch();
        backGround = new Texture("Background/Loading.png");
        mViewport = new ExtendViewport(1920, 1080, mCamera);

        // Initialize SceneLoader configuration
        SceneConfiguration configuration = new SceneConfiguration();
        mSceneLoader = new SceneLoader(configuration);

        // Initialize AssetManager
        mAssetManager = new AssetManager();

        // Load textures
        PlayButtonDark = new Texture("Buttons/PlayDark.png");
        PlayButtonLight = new Texture("Buttons/PlayLight.png");
        SettingsDark = new Texture("Buttons/Settings_Dark.png");
        SettingsLight = new Texture("Buttons/Settings_Light.png");
        SoundT = new Texture("Buttons/SoundOn.png");
        ExitT = new Texture("Buttons/Exit.png");
        not = new Texture("Buttons/NoImage.png");

        // Initialize sprites and buttons
        notS = new Sprite(not);
        settings = new Settings(mViewport);
        BackGround = new Sprite(backGround);

        PlayButton = new ButtonComp(batch, PlayButtonDark, PlayButtonLight, 0, 0);
        SettingsButton = new ButtonComp(batch, SettingsDark, SettingsLight, 0, 0);
        SoundButton = new ButtonComp(batch, SoundT, not, 0, 0);
        ExitButton = new ButtonComp(batch, ExitT, not, 0, 0);

        isSoundOff = false;
        isSettingsOn = false;
        if(gameInputProcessor == null) {
            gameInputProcessor = new GameInputProcessor(game, batch, PlayButton, SettingsButton, SoundButton, ExitButton, settings, mViewport);
        }
        Gdx.input.setInputProcessor(gameInputProcessor);
        System.out.println("Current Input Processor: " + Gdx.input.getInputProcessor());

//        // Set input processor for button interactions

    }
    private void updateButtons() {
        PlayButton.setScale(0.7f);
        SettingsButton.setScale(0.6f);
        SoundButton.setScale(2.5f);
        ExitButton.setScale(2.5f);

        BackGround.setSize(mViewport.getWorldWidth(), mViewport.getWorldHeight());

        PlayButton.setPos(mViewport.getWorldWidth() - 600, -50);
        SettingsButton.setPos(0, -30);
        SoundButton.setPos(50, mViewport.getWorldHeight() - 90);
        ExitButton.setPos(mViewport.getWorldWidth() - 120, mViewport.getWorldHeight() - 90);
    }

    private void drawButtons() {
        batch.begin();
        BackGround.draw(batch);
        batch.end();

        PlayButton.draw();
        SettingsButton.draw();
        ExitButton.draw();
        SoundButton.draw();

        if (isSoundOff) {
            notS.setPosition(50, mViewport.getWorldHeight() - 90);
            batch.begin();
            notS.draw(batch);
            batch.end();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0); // Clear with transparent black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        Gdx.input.setInputProcessor(gameInputProcessor);
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                System.out.println("Touch Down");
                Vector3 WorldCoords = mViewport.unproject(new Vector3(screenX, screenY, 0));
                if (PlayButton.isClicked(WorldCoords)) {
                    game.setScreen(new LoadingScreen(game));
                }
                if (SoundButton.isClicked(WorldCoords)) {
                    isSoundOff = !isSoundOff;
                }
                if (SettingsButton.isClicked(WorldCoords)) {
                    isSettingsOn = true;
                } else if (isSettingsOn && !settings.getBoundingRectangle().contains(WorldCoords.x, WorldCoords.y)) {
                    isSettingsOn = false;
                }
                if (ExitButton.isClicked(WorldCoords)) {
                    Gdx.app.exit();
                } else {
                    ExitButton.setDark();
                }
                if(isSettingsOn && settings.isRightButtonTouched(WorldCoords.x, WorldCoords.y)){
                    settings.increaseSoundBarLevel();
                }
                if(isSettingsOn && settings.isLeftButtonTouched(WorldCoords.x, WorldCoords.y)){
                    settings.decreaseSoundBarLevel();
                }
                return super.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                Vector3 WorldCoords = mViewport.unproject(new Vector3(screenX, screenY, 0));
                System.out.println("Hovered");
                if (PlayButton.isHovered(WorldCoords)) {

                    PlayButton.setLight();
                } else {
                    PlayButton.setDark();
                }
                if (SettingsButton.isHovered(WorldCoords)) {
                    SettingsButton.setLight();
                } else {
                    SettingsButton.setDark();
                }
                return super.mouseMoved(screenX, screenY);
            }
        });
        System.out.println("Current Input Processor: " + Gdx.input.getInputProcessor());
        mViewport.apply();
        mCamera.update();
        batch.setProjectionMatrix(mViewport.getCamera().combined);

        updateButtons();
        drawButtons();

        settings.setReqHeight(isSettingsOn ? 100 : (int)mViewport.getWorldHeight());
        settings.draw(10, true);  // Draw settings with updated height
    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        mAssetManager.dispose();
        backGround.dispose();
        PlayButtonDark.dispose();
        PlayButtonLight.dispose();
        SettingsDark.dispose();
        SettingsLight.dispose();
        SoundT.dispose();
        ExitT.dispose();
        not.dispose();
        settings.dispose();

    }
}
