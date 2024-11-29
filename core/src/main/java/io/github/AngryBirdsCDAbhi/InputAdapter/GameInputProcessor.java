package io.github.AngryBirdsCDAbhi.InputAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.AngryBirdsCDAbhi.Lib.ButtonComp;
import io.github.AngryBirdsCDAbhi.LoadingScreen;
import io.github.AngryBirdsCDAbhi.Main;
import io.github.AngryBirdsCDAbhi.Menus.Settings;

public class GameInputProcessor extends InputAdapter {

    private Main game;
    private SpriteBatch batch;
    private ButtonComp playButton, settingsButton, soundButton, exitButton;
    private Settings settings;
    private boolean isSoundOff = false;
    private boolean isSettingsOn = false;
    private ExtendViewport mViewport;

    public GameInputProcessor(Main game, SpriteBatch batch, ButtonComp playButton, ButtonComp settingsButton,
                              ButtonComp soundButton, ButtonComp exitButton, Settings settings, ExtendViewport viewport) {
        this.game = game;
        this.batch = batch;
        this.playButton = playButton;
        this.settingsButton = settingsButton;
        this.soundButton = soundButton;
        this.exitButton = exitButton;
        this.settings = settings;
        this.mViewport = viewport;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Touch Down");
        Vector3 WorldCoords = mViewport.unproject(new Vector3(screenX, screenY, 0));
        if (playButton.isClicked(WorldCoords)) {
            game.setScreen(new LoadingScreen(game)); // Change to LoadingScreen on PlayButton click
        }
        if (soundButton.isClicked(WorldCoords)) {
            isSoundOff = !isSoundOff;
        }
        if (settingsButton.isClicked(WorldCoords)) {
            isSettingsOn = true;
        } else if (isSettingsOn && !settings.getBoundingRectangle().contains(WorldCoords.x, WorldCoords.y)) {
            isSettingsOn = false;
        }
        if (exitButton.isClicked(WorldCoords)) {
            Gdx.app.exit();
        } else {
            exitButton.setDark();
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
        if (playButton.isHovered(WorldCoords)) {
            playButton.setLight();
        } else {
            playButton.setDark();
        }
        if (settingsButton.isHovered(WorldCoords)) {
            settingsButton.setLight();
        } else {
            settingsButton.setDark();
        }
        return super.mouseMoved(screenX, screenY);
    }
}
