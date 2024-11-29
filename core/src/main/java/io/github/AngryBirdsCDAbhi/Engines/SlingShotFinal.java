package io.github.AngryBirdsCDAbhi.Engines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Queue;
import io.github.AngryBirdsCDAbhi.Birds.Bird;
import io.github.AngryBirdsCDAbhi.Birds.Bomb;
import io.github.AngryBirdsCDAbhi.Birds.Chuck;
import io.github.AngryBirdsCDAbhi.Levels.Level1f;
import io.github.AngryBirdsCDAbhi.Levels.Level2f;
import io.github.AngryBirdsCDAbhi.Levels.Level3f;
import io.github.AngryBirdsCDAbhi.Lib.ButtonComp;
import io.github.AngryBirdsCDAbhi.LoadingScreen;
import io.github.AngryBirdsCDAbhi.Menus.InGameMenu;
import io.github.AngryBirdsCDAbhi.Piggies.Piggies;

import java.beans.Transient;

public class SlingShotFinal extends Actor {
    private boolean isPaused = false;
    private static float SCALE = 100;
    private Vector2 initialTouchPos;
    private Vector2 birdInitialPosition;
    private Vector2 launchVelocity;
    private Vector2 slingshotAnchorLeft;
    private Vector2 slingshotAnchorRight;
    transient Sprite trajectorySprite;
    transient Sprite SlingShotSprite;
    private boolean isDragging;
    private boolean isReady;
    transient ShapeRenderer shapeRenderer;
    private Vector2 slingshotPosition;
    private Queue<Bird> birdQueue;
    private Bird Currbird;
    private Bird prevBird;
    transient World world;
    private transient InGameMenu inGameMenu;
    private transient ButtonComp pauseButton;
    private transient Sprite oneStar, twoStar, threeStar;
    float timeStepFactor = 60f / 24f;
    private static final float IMPULSE_SCALE = 1000000000f;  // This value determines how strong the impulse is
    public SlingShotFinal(World world, Vector2 position, Queue<Bird> queue, InGameMenu inGameMenu, ButtonComp PauseButton) {
        this.slingshotPosition = position;
        this.isDragging = false;
        this.isReady = false;
        this.birdQueue = queue;
        this.world = world;
        this.pauseButton = PauseButton;
        this.SlingShotSprite = new Sprite(new Texture("slingshot.png"));
        this.SlingShotSprite.setSize(2.0f, 2.0f);
        this.trajectorySprite = new Sprite(new Texture("white-balls.png"));
        this.trajectorySprite.setSize(10.0f, 10.0f);
        if(!this.birdQueue.isEmpty())
        {
            this.Currbird = this.birdQueue.first();
        }

        this.slingshotAnchorLeft = new Vector2(510, 390);
        this.slingshotAnchorRight = new Vector2(570, 390);
        this.shapeRenderer = new ShapeRenderer();
        this.prevBird = null;
        this.inGameMenu = inGameMenu;
        this.oneStar = new Sprite(new Texture("starLeft.png"));
        this.twoStar = new Sprite(new Texture("2 Stars.png"));
        this.threeStar = new Sprite(new Texture("3 Stars.png"));
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(SlingShotSprite.getTexture(), slingshotPosition.x * SCALE, slingshotPosition.y * SCALE + 150, 80, 160);
        if (isDragging) {
            batch.end();
            drawRubberband();
            batch.begin();
            drawTrajectory(batch);
        }
    }
    public void drawRubberband() {
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.188f, 0.090f, 0.031f, 1f);
        Vector2 birdPosition = Currbird.getBody().getPosition();
        shapeRenderer.rectLine(slingshotAnchorLeft, birdPosition, 15f);
        shapeRenderer.rectLine(slingshotAnchorRight, birdPosition, 15f);
        shapeRenderer.end();
    }
    private void drawTrajectory(Batch batch) {
        if (launchVelocity == null) {
            return;
        }
        float time = 0f;
        int pointCount = 30;
        float timeStep = 0.1f;
        Vector2 trajectoryVector = new Vector2(launchVelocity);
        trajectoryVector.scl(1/IMPULSE_SCALE);
        trajectoryVector.scl(1/timeStep);
        for (int i = 0; i < pointCount; i++) {
            float x = birdInitialPosition.x + launchVelocity.x * time;
            float y = birdInitialPosition.y + launchVelocity.y * time - (0.5F * 9.8f * time * time);
            if (y < 0) {
                break;
            }
            batch.draw(trajectorySprite, x * 100, y * 100, 10, 10);
            time += timeStep;
        }
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        if (Currbird != null)
        {
            Currbird.setStatic(true);
            birdInitialPosition = new Vector2(Currbird.getBody().getPosition());
            if ((Currbird.getBody().getPosition().epsilonEquals(slingshotAnchorLeft, 11f) || Currbird.getBody().getPosition().epsilonEquals(slingshotAnchorRight, 11f)) && (prevBird == null || prevBird.getBody().getLinearVelocity().epsilonEquals(new Vector2(0, 0), 10f))) {
                isReady = true;
                System.out.println("Ready");
            } else {
                if (birdQueue.size > 0) {
                    Vector2 targetPosition = new Vector2(slingshotAnchorLeft.x + 20.0f, slingshotAnchorLeft.y + 20.0f);
                    Vector2 currentPosition = Currbird.getBody().getPosition();
                    float speed = 1.0f;
                    Vector2 newPosition = currentPosition.lerp(targetPosition, speed * delta);
                    Currbird.getBody().setTransform(newPosition, 0);
                }
            }
        }
        else {
            if(prevBird!= null &&prevBird.getBody().getLinearVelocity().epsilonEquals(new Vector2(0, 0), 8f)){
                if (!birdQueue.isEmpty()) {
                    Currbird = birdQueue.first();
                    isReady = true;
                }
                else{
                    boolean allPiggiesDead = true;
                    if(LoadingScreen.getCurrLevel() == 1){
                        for(Piggies piggy : Level1f.getPiggiesofLevel()){
                            if(!piggy.isDead()){
                                allPiggiesDead = false;
                                break;
                            }
                        }
                    }
                    else if(LoadingScreen.getCurrLevel() == 2){
                        for(Piggies piggy : Level1f.getPiggiesofLevel()){
                            if(!piggy.isDead()){
                                allPiggiesDead = false;
                                break;
                            }
                        }
                    }
                    else if(LoadingScreen.getCurrLevel() == 3){
                        for(Piggies piggy : Level1f.getPiggiesofLevel()){
                            if(!piggy.isDead()){
                                allPiggiesDead = false;
                                break;
                            }
                        }
                    }
                    if (allPiggiesDead) {
                        if (LoadingScreen.getCurrLevel() == 1) {
                            if (Level1f.score <= 60) {
                                inGameMenu.setMenuBird(oneStar);
                            } else if (Level1f.score > 60 && Level1f.score <= 160) {
                                inGameMenu.setMenuBird(twoStar);
                            } else if (Level1f.score > 160) {
                                inGameMenu.setMenuBird(threeStar);
                            }
                        } else if (LoadingScreen.getCurrLevel() == 2) {
                            if (Level2f.score <= 60) {
                                inGameMenu.setMenuBird(oneStar);
                            } else if (Level2f.score > 60 && Level2f.score <= 160) {
                                inGameMenu.setMenuBird(twoStar);
                            } else if (Level2f.score > 160) {
                                inGameMenu.setMenuBird(threeStar);
                            }
                        } else if (LoadingScreen.getCurrLevel() == 3) {
                            if (Level3f.score <= 60) {
                                inGameMenu.setMenuBird(oneStar);
                            } else if (Level3f.score > 60 && Level1f.score <= 100) {
                                inGameMenu.setMenuBird(twoStar);
                            } else if (Level3f.score > 100) {
                                inGameMenu.setMenuBird(threeStar);
                            }

                        }
                        inGameMenu.setReqHeight(1);
                    }
                }
            }
        }

        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(prevBird instanceof Chuck){
                    ((Chuck) prevBird).activateSpecial();
                }
                if(prevBird instanceof Bomb){
                    ((Bomb) prevBird).activateSpecial();
                }
                if (pauseButton.getMain().getBoundingRectangle().contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                    isPaused = !isPaused;
                    if (isPaused) {
                        inGameMenu.setReqHeight(1);
                    } else {
                        inGameMenu.setReqHeight(1150);
                    }
                    return true;
                } else {
                    // Only activate drag if the touch is near the slingshot
                    Vector2 worldTouchPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                    if (worldTouchPos.dst(slingshotAnchorLeft) < 100f || worldTouchPos.dst(slingshotAnchorRight) < 100f) {
                        if(isReady && Currbird != null)
                        {
                            initialTouchPos = worldTouchPos;
                            isDragging = true;
                            Currbird.getBody().setTransform(slingshotAnchorLeft.x, slingshotAnchorLeft.y, 0);
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging && !isPaused) {
                    Vector2 dragPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);  // Flip Y-axis direction

                    Vector2 clampedPos = dragPos.clamp(slingshotAnchorLeft.x, slingshotAnchorRight.x);

                    Currbird.getBody().setTransform(clampedPos, 0);  // Set the new position of the bird

                    initialTouchPos = dragPos;
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging) {
                    Vector2 finalTouchPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                    Vector2 vectorToCursor = finalTouchPos.sub(slingshotAnchorLeft);
                    launchVelocity = vectorToCursor.scl(-1); // Reverse direction

                    // Launch bird with velocity
                    if (Currbird != null) {
                        prevBird = Currbird;
                        Currbird.setDynamic();
                        launchVelocity.scl(timeStepFactor);
                        prevBird.launch(launchVelocity.scl(IMPULSE_SCALE));

                        // Reset dragging state
                        isDragging = false;
                        isReady = false;
                        birdQueue.removeFirst();  // Remove the launched bird

                        // Move to the next bird
                        if (!birdQueue.isEmpty()) {
                            Currbird = birdQueue.first();
                            isReady = true;
                        } else {
                            Currbird = null;
                            isReady = false;
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

}

