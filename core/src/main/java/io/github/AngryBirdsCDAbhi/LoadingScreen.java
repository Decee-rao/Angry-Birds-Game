package io.github.AngryBirdsCDAbhi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.AngryBirdsCDAbhi.Birds.Bird;
import io.github.AngryBirdsCDAbhi.Levels.*;
import io.github.AngryBirdsCDAbhi.Lib.ButtonComp;
import io.github.AngryBirdsCDAbhi.Piggies.Piggies;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class LoadingScreen extends ScreenAdapter {
    private Main game;
    private OrthographicCamera mCamera;
    private SpriteBatch batch;
    private ExtendViewport mViewport;
    private ShapeRenderer shapeRenderer;
    private World world;

    private Texture backGround, RightArrow, RightArrowL, LeftArrow, LeftArrowL, backButton;
    private ArrayList<Sprite> Levels;
    private Sprite backButtonS;

    private ButtonComp RightArrowButton, LeftArrowButton;
    private static int CurrLevel = 1;
    private boolean isLevelHovered = false;
    private Sprite currLevelSprite;
    HashMap<Integer, Level> levelMap = new HashMap<>();
    public LoadingScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        initializeCameraAndViewport();
        initializeBatchAndRenderer();
        setupWorld();
        loadTexturesAndSprites();
        createButtons();
        setupInputProcessor();
    }
    public static int getCurrLevel(){
        return CurrLevel;
    }

    private void initializeCameraAndViewport() {
        mCamera = new OrthographicCamera();
        mViewport = new ExtendViewport(1980, 1080, mCamera);
    }

    private void initializeBatchAndRenderer() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    private void setupWorld() {
        world = new World(new Vector2(0, -10), true);
        createPhysicsBodies();
    }

    private void createPhysicsBodies() {

        BodyDef circleBodyDef = new BodyDef();
        circleBodyDef.type = BodyDef.BodyType.DynamicBody;
        circleBodyDef.position.set(new Vector2(0f, 100f));

        BodyDef rectangleBodyDef = new BodyDef();
        rectangleBodyDef.type = BodyDef.BodyType.StaticBody;
        rectangleBodyDef.position.set(0, 0);

        Body circleBody = world.createBody(circleBodyDef);
        Body rectangleBody = world.createBody(rectangleBodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(10f);

        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(mCamera.viewportWidth, 10f);

        FixtureDef circleFixtureDef = new FixtureDef();
        circleFixtureDef.shape = circle;
        circleFixtureDef.density = 1f;
        circleFixtureDef.friction = 0.3f;
        circleBody.createFixture(circleFixtureDef);
        rectangleBody.createFixture(rectangle, 0.0f);
        circle.dispose();
        rectangle.dispose();
    }

    private void loadTexturesAndSprites() {
        backGround = new Texture("Background/LevelSelector.png");
        RightArrow = new Texture("Buttons/ResumeOn.png");
        LeftArrow = new Texture("Buttons/ResumeOn.png");
        RightArrowL = new Texture("Buttons/ResumeOff.png");
        LeftArrowL = new Texture("Buttons/ResumeOff.png");
        backButton = new Texture("Buttons/backButton.png");

        Levels = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Levels.add(new Sprite(new Texture("Levels/l" + i + ".png")));
        }

        backButtonS = new Sprite(backButton);
        backButtonS.setScale(1.8f);
    }

    private void createButtons() {
        float buttonY = mViewport.getWorldHeight() / 2;

        RightArrowButton = new ButtonComp(batch, RightArrow, RightArrowL, 300, buttonY);
        LeftArrowButton = new ButtonComp(batch, LeftArrow, LeftArrowL, mViewport.getWorldWidth(), buttonY);

        RightArrowButton.setScale(2.2f);
        LeftArrowButton.setScale(2.2f);
        LeftArrowButton.flipX();
    }

    private void setupInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touch = new Vector3(screenX, screenY, 0);
                mViewport.unproject(touch);

                if (RightArrowButton.isHovered(touch)) {
                    CurrLevel = Math.min(CurrLevel + 1, Levels.size());
                } else if (LeftArrowButton.isHovered(touch)) {
                    CurrLevel = Math.max(CurrLevel - 1, 1);
                } else if (backButtonS.getBoundingRectangle().contains(touch.x, touch.y)) {
                    game.setScreen(new GameScreen(game));
                }
                else if(currLevelSprite.getBoundingRectangle().contains(touch.x, touch.y)){
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if(CurrLevel == 1){
                        game.setScreen(new Level1f(game));
                    }
                    else if(CurrLevel == 2){
                        game.setScreen(new Level2f(game));
                    }
                    else if(CurrLevel == 3){
                        game.setScreen(new Level3f(game));
                    }
                }
                return super.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                Vector3 touch = new Vector3(screenX, screenY, 0);
                mViewport.unproject(touch);

                if(RightArrowButton.isHovered(touch)){
                    RightArrowButton.setLight();
                } else {
                    RightArrowButton.setDark();
                }
                if(LeftArrowButton.isHovered(touch)){
                    LeftArrowButton.setLight();
                } else {
                    LeftArrowButton.setDark();
                }

                return super.mouseMoved(screenX, screenY);
            }
        });
    }

    private void drawCurrLevel() {
        currLevelSprite = Levels.get(CurrLevel - 1);
        currLevelSprite.setBounds(mViewport.getWorldWidth() / 2-(currLevelSprite.getWidth()/2), mViewport.getWorldHeight() / 2 - 300, 500, 600);

        float baseScale = 1.2f;
        float targetScale = 1.3f;

        if (currLevelSprite.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.input.getY())) {
            isLevelHovered = true;
        } else {
            isLevelHovered = false;
        }

        float newScale = currLevelSprite.getScaleX();
        newScale = isLevelHovered ? Math.min(newScale + 0.003f, targetScale) : Math.max(newScale - 0.003f, baseScale);
        currLevelSprite.setScale(newScale);

        batch.begin();
        currLevelSprite.draw(batch);
        backButtonS.setPosition(50, mViewport.getWorldHeight() - backButtonS.getHeight() - 50);
        backButtonS.draw(batch);
        batch.end();

        RightArrowButton.draw();
        LeftArrowButton.draw();
    }

    @Override
    public void render(float delta) {
        mViewport.apply();
        batch.setProjectionMatrix(mCamera.combined);
        batch.begin();
        batch.draw(backGround, 0, 0, mViewport.getWorldWidth(), mViewport.getWorldHeight());
        batch.end();
        setupInputProcessor();

        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(mCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.2f);
        shapeRenderer.rect(0, 0, mViewport.getWorldWidth(), mViewport.getWorldHeight());
        shapeRenderer.end();

        drawCurrLevel();

        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height, true);
        float buttonY = mViewport.getWorldHeight() / 2;

        RightArrowButton.setPos((mViewport.getWorldWidth() - RightArrowButton.getWidth()) * 0.75f, buttonY);
        LeftArrowButton.setPos((mViewport.getWorldWidth() - LeftArrowButton.getWidth()) * 0.25f, buttonY);
    }

    @Override
    public void dispose() {
        batch.dispose();
        backGround.dispose();
        RightArrow.dispose();
        LeftArrow.dispose();
        RightArrowL.dispose();
        LeftArrowL.dispose();
        backButton.dispose();
        backButtonS.getTexture().dispose();

        for (Sprite level : Levels) {
            level.getTexture().dispose();
        }
        shapeRenderer.dispose();
        world.dispose();
    }
}
