package io.github.AngryBirdsCDAbhi.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import io.github.AngryBirdsCDAbhi.Birds.*;
import io.github.AngryBirdsCDAbhi.DynamicBodies;
import io.github.AngryBirdsCDAbhi.Engines.SlingShotFinal;
//import io.github.AngryBirdsCDAbhi.Engines.Slingshot;
import io.github.AngryBirdsCDAbhi.Lib.ButtonComp;
import io.github.AngryBirdsCDAbhi.LoadingScreen;
import io.github.AngryBirdsCDAbhi.Main;
import io.github.AngryBirdsCDAbhi.Menus.InGameMenu;
import io.github.AngryBirdsCDAbhi.Piggies.King;
import io.github.AngryBirdsCDAbhi.Piggies.Much;
import io.github.AngryBirdsCDAbhi.Piggies.Normal;
import io.github.AngryBirdsCDAbhi.Piggies.Piggies;
import io.github.AngryBirdsCDAbhi.StaticBody;

import java.io.*;
import java.util.ArrayList;
import java.util.Queue;

import static java.lang.Thread.sleep;

public class Level2f extends Level {

    private static final long serialVersionUID = 10L;
    //Texture Paths for the blocks,Pigs and Birds
    String bgTP;
    String KingTP;
    String NormalTP;
    String MuchTP;
    String IceBlockTP;
    String WoodBlockTP;
    String StoneBlockTP;
    String RedBirdTP;
    String BombBirdTP;
    String ChuckBirdTP;
    String PauseDarkTP;
    String PauseLightTP;

    transient World world;
    public transient ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    ExtendViewport viewport;
    Main main;


    // Making an Array List to Store the Birds and Etc.
    static ArrayList<Bird> birds = new ArrayList<Bird>();
    static ArrayList<Piggies> piggies = new ArrayList<Piggies>();
    static ArrayList<DynamicBodies> blocks = new ArrayList<DynamicBodies>();

    //Making the Bodies inside the Level
    King king;
    Normal normal;
    Much much;
    Red red;
    Bomb bomb;
    Chuck chuck;
    DynamicBodies iceBlock01;
    DynamicBodies iceBlock;
    DynamicBodies iceBlock2;
    DynamicBodies iceBlock3;
    DynamicBodies iceBlock4;
    DynamicBodies iceBlock5;
    DynamicBodies iceBlock6;
    DynamicBodies iceBlock7;

    //InGame Menu
    InGameMenu inGameMenu;
    boolean isPaused = false;
    public transient ButtonComp PauseButton;

    //Temporary Sprite to get the Horizontal SPrite
    transient Sprite tempSprite;

    //The World and the Sprite Batch
    transient SpriteBatch batch;
    transient Texture image;
    transient Sprite backgroundSprite;

    //Transient Bodies
    transient BodyDef groundBodyDef;
    transient Body groundBody;

    // Pause Button
    boolean pauseButtonClicked = false;
    boolean isPauseMenuOn = false;

    //Queue Birds
    transient Bird CurrBody = null;
    public static com.badlogic.gdx.utils.Queue<Bird> bodies = new com.badlogic.gdx.utils.Queue<Bird>();

    //Stage for the Game
    transient Stage stage;

    //Image Button
    transient ImageButton pauseButton;

    //Slingshot
    transient SlingShotFinal slingShotFinal;
    public static int score = 0;
    public static File redFile = new File("Levels/Level2/red.ser");
    public static File boomFile = new File("Levels/Level2/boom.ser");
    public static File chuckFile = new File("Levels/Level2/chuck.ser");
    public static File kingFile = new File("Levels/Level2/king.ser");
    public static File normalFile = new File("Levels/Level2/normal.ser");
    public static File muchFile = new File("Levels/Level2/much.ser");
    public static File iceBlockFile = new File("Levels/Level2/iceBlock.ser");
    public static File iceBlock2File = new File("Levels/Level2/iceBlock2.ser");
    public static File iceBlock3File = new File("Levels/Level2/iceBlock3.ser");
    public static File iceBlock4File = new File("Levels/Level2/iceBlock4.ser");
    public static File iceBlock5File = new File("Levels/Level2/iceBlock5.ser");
    public static File iceBlock6File = new File("Levels/Level2/iceBlock6.ser");
    public static File iceBlock7File = new File("Levels/Level2/iceBlock7.ser");
    //Constructor to set the texture paths
    public Level2f(Main M) {

        super(new ArrayList<Bird>(),new ArrayList<Piggies>(),new ArrayList<DynamicBodies>(),0,0,false);
        main = M;
        bodies.clear();
        birds.clear();
        piggies.clear();
        blocks.clear();

    }

    public static ArrayList<Bird> getBirdsofLevel() {
        return birds;
    }

    public static ArrayList<DynamicBodies> getBlocksofLevel() {
        return blocks;
    }


    public void InitializeWorld(){

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        image = new Texture("libgdx.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1980, 1080);
        viewport = new ExtendViewport(1980, 1080, camera);
        // World Creation
        world = new World(new Vector2(0,-10), true);
        // Camera and Viewport

        //VIewPort Sprite
        backgroundSprite = new Sprite(new Texture("background.png"));
        backgroundSprite.setSize(camera.viewportWidth, camera.viewportHeight);
        backgroundSprite.setPosition(0, 0);


    }
    public void loadAndPrepareStage() throws IOException, ClassNotFoundException, InterruptedException {
        bgTP = "background.png";
        KingTP = "King.png";
        NormalTP = "Piggies/normal.png";
        MuchTP = "Piggies/Much.png";
        IceBlockTP = "ice.png";
        WoodBlockTP = "wood.png";
        StoneBlockTP = "stone.png";
        RedBirdTP = "red.png";
        BombBirdTP = "Bomb.png";
        ChuckBirdTP = "Chuck.png";
        PauseDarkTP = "Buttons/PauseDark.png";
        PauseLightTP = "Buttons/PauseLight.png";
        red = new Red(world, 300, 240,100,100);
        bomb = new Bomb(world, 200, 240,100,100);
        chuck = new Chuck(world, 100, 240,100,100);
        king = new King(world,new Sprite(new Texture(KingTP)), 600, 240);
        float IceBlocksHeight = king.getHeight()+30f;
        iceBlock4 = new DynamicBodies(world, new Sprite(new Texture(IceBlockTP)), 530, 240,20,IceBlocksHeight);
        iceBlock = new DynamicBodies(world, new Sprite(new Texture(IceBlockTP)), 680, 240,20,IceBlocksHeight);
        normal = new Normal(world,new Sprite(new Texture(NormalTP)), 750, 240);
        iceBlock2 = new DynamicBodies(world, new Sprite(new Texture(IceBlockTP)), 880, 240,20,IceBlocksHeight);
        much = new Much(world,new Sprite(new Texture(MuchTP)), 950, 240);
        iceBlock01 = new DynamicBodies(world, new Sprite(new Texture(WoodBlockTP)), 780, 260, 20, IceBlocksHeight);
        iceBlock3 = new DynamicBodies(world, new Sprite(new Texture(WoodBlockTP)),605, 260,20,IceBlocksHeight);

        tempSprite = new Sprite(new Texture(IceBlockTP));
        tempSprite.rotate90(true);
        if(tempSprite == null){
            System.out.println("Temp Sprite is Null");
        }
        iceBlock5 = new DynamicBodies(world, tempSprite,0, 0,350,20);
        iceBlock6 = new DynamicBodies(world, tempSprite, 0, 0,300,20);
        iceBlock7 = new DynamicBodies(world, tempSprite, 0, 0,325,20);
        red.getBody().setTransform(400,250,0);
        bomb.getBody().setTransform(200,250,0);
        chuck.getBody().setTransform(100,250,0);
        iceBlock.getBody().setTransform(900,300,0);
        king.getBody().setTransform(1050,300+15,0);
        iceBlock2.getBody().setTransform(1250,300,0);
        normal.getBody().setTransform(1400,300+5,0);
        iceBlock3.getBody().setTransform((iceBlock.getBody().getPosition().x+iceBlock2.getBody().getPosition().x)/2,iceBlock.getBody().getPosition().y+iceBlock.getHeight()+20+40,0);
        much.getBody().setTransform(1225,iceBlock.getBody().getPosition().y+iceBlock.getHeight()+4,0);
        iceBlock4.getBody().setTransform(1550,300,0);

        iceBlock01.getBody().setTransform((iceBlock2.getBody().getPosition().x+iceBlock4.getBody().getPosition().x)/2,iceBlock.getBody().getPosition().y+iceBlock.getHeight()+20+30,0);
        iceBlock5.getBody().setTransform(iceBlock.getBody().getPosition().x+(iceBlock5.getWidth()/2), iceBlock.getBody().getPosition().y+iceBlock.getHeight(),0);
        iceBlock6.getBody().setTransform(iceBlock2.getBody().getPosition().x+(iceBlock6.getWidth()/2), iceBlock2.getBody().getPosition().y+iceBlock2.getHeight(),0);
        iceBlock7.getBody().setTransform(iceBlock3.getBody().getPosition().x+(iceBlock7.getWidth()/2), iceBlock3.getBody().getPosition().y+iceBlock3.getHeight(),0);
        StaticBody ground = new StaticBody(world, 0, 0, 1980, 240, camera);
        piggies.add(king);
        piggies.add(normal);
        piggies.add(much);
        blocks.add(iceBlock);
        blocks.add(iceBlock2);
        blocks.add(iceBlock3);
        blocks.add(iceBlock4);
        blocks.add(iceBlock5);
        blocks.add(iceBlock6);
        blocks.add(iceBlock7);
        birds.add(red);
        birds.add(bomb);
        birds.add(chuck);
        inGameMenu = new InGameMenu(viewport,camera,2,main);
        inGameMenu.setReqHeight(1150);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        LoadState();
        sleep(100);
        stage = new Stage(viewport);
        stage.addActor(king);
        stage.addActor(normal);
        stage.addActor(much);
        stage.addActor(iceBlock01);
        stage.addActor(iceBlock);
        stage.addActor(iceBlock2);
        stage.addActor(iceBlock3);
        stage.addActor(iceBlock4);
        bodies.addLast(red);
        bodies.addLast(bomb);
        bodies.addLast(chuck);
        stage.addActor(red);
        stage.addActor(bomb);
        stage.addActor(chuck);
        stage.addActor(ground);
        stage.addActor(iceBlock5);
        stage.addActor(iceBlock6);
        stage.addActor(iceBlock7);
        stage.addActor(inGameMenu);
        batch = new SpriteBatch();
        PauseButton = new ButtonComp(batch, new Texture("Buttons/PauseDark.png"), new Texture("Buttons/PauseLight.png"), 50, viewport.getWorldHeight()-150, 100, 100);
        slingShotFinal = new SlingShotFinal(world, new Vector2(5f, 1f), bodies,inGameMenu,PauseButton);
        stage.addActor(slingShotFinal);
        stage.addActor(PauseButton);
    }
    @Override
    public void show() {
        super.show();
        Box2D.init();
        InitializeWorld();
        try {
            loadAndPrepareStage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Gdx.input.setInputProcessor(stage);
        world.setContactListener(new ContactListener() {
            private static final float VELOCITY_THRESHOLD = 15f;
            @Override
            public void beginContact(Contact contact) {
                // Get the bodies involved in the collision
                Body bodyA = contact.getFixtureA().getBody();
                Body bodyB = contact.getFixtureB().getBody();

                // Get the velocities of the relevant bodies involved in the collision
                float velocityA = bodyA.getLinearVelocity().len();
                float velocityB = bodyB.getLinearVelocity().len();

                // Add logic for King taking damage
                if ((velocityA > VELOCITY_THRESHOLD || velocityB > VELOCITY_THRESHOLD) &&
                    (bodyA == king.getBody() || bodyB == king.getBody())) {

                    king.takeDamage();
                    System.out.println("King's Health: " + king.getHealth());

                    if (king.isDead()) {
                        System.out.println("The King pig has died!");
                    }
                }

                // Add logic for Normal pig taking damage
                if ((velocityA > VELOCITY_THRESHOLD || velocityB > VELOCITY_THRESHOLD) &&
                    (bodyA == normal.getBody() || bodyB == normal.getBody())) {

                    normal.takeDamage();
                    System.out.println("Normal's Health: " + normal.getHealth());

                    if (normal.isDead()) {
                        System.out.println("The Normal pig has died!");
                    }
                }

//                 Add logic for Much pig taking damage
                if ((velocityA > VELOCITY_THRESHOLD || velocityB > VELOCITY_THRESHOLD) &&
                    (bodyA == much.getBody() || bodyB == much.getBody())) {

                    much.takeDamage();
                    System.out.println("Much's Health: " + much.getHealth());

                    if (much.isDead()) {
                        System.out.println("The Much pig has died!");
                    }
                }
                if ((velocityA > VELOCITY_THRESHOLD || velocityB > VELOCITY_THRESHOLD) &&
                    (bodyA == iceBlock.getBody() || bodyB == iceBlock.getBody())) {
                    iceBlock.takeDamage();
                }
                if ((velocityA > VELOCITY_THRESHOLD || velocityB > VELOCITY_THRESHOLD) &&
                    (bodyA == iceBlock2.getBody() || bodyB == iceBlock2.getBody())) {
                    iceBlock2.takeDamage();
                }
                if ((velocityA > VELOCITY_THRESHOLD || velocityB > VELOCITY_THRESHOLD) &&
                    (bodyA == iceBlock3.getBody() || bodyB == iceBlock3.getBody())) {
                    iceBlock3.takeDamage();
                }
                if ((velocityA > VELOCITY_THRESHOLD || velocityB > VELOCITY_THRESHOLD) &&
                    (bodyA == iceBlock4.getBody() || bodyB == iceBlock4.getBody())) {
                    iceBlock4.takeDamage();
                }
                if ((velocityA > VELOCITY_THRESHOLD || velocityB > VELOCITY_THRESHOLD) &&
                    (bodyA == iceBlock5.getBody() || bodyB == iceBlock5.getBody())) {
                    iceBlock5.takeDamage();
                }
                if ((velocityA > VELOCITY_THRESHOLD || velocityB > VELOCITY_THRESHOLD) &&
                    (bodyA == iceBlock6.getBody() || bodyB == iceBlock6.getBody())) {
                    iceBlock6.takeDamage();
                }
                if ((velocityA > VELOCITY_THRESHOLD || velocityB > VELOCITY_THRESHOLD) &&
                    (bodyA == iceBlock7.getBody() || bodyB == iceBlock7.getBody())) {
                    iceBlock7.takeDamage();
                }

            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.begin();
        backgroundSprite.draw(batch);
        updatePigs();
        batch.end();
        PauseButton.draw();
        stage.act();
        stage.draw();
        world.step(1 / 60f, 8, 3);


    }
    public void updatePigs(){
        for (Piggies pig : piggies) {
            if (pig.getHealth() <=0) {
                pig.deactivate();
                if(pig instanceof King){
                    score += 100;
                }
                else if(pig instanceof Normal){
                    score += 50;
                }
                else if(pig instanceof Much){
                    score += 75;
                }
            }
        }
        for (DynamicBodies block : blocks) {
            if(block.getHealth() <= 0){
                block.deactivate();
            }
        }
    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
    @Override
    public void hide() {
        super.hide();
    }
    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        shapeRenderer.dispose();
        world.dispose();
        stage.dispose();

    }

    public static ArrayList<Piggies> getPiggiesofLevel() {
        return piggies;
    }
    public static void saveState(){
        try {
            saveBirds();
            savePiggies();
            saveBlocks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void saveBirds() throws IOException {
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(redFile));
            Red bird1 = (Red) birds.get(0);
            bird1.SaveState();
            out.writeObject(bird1);
            out.close();
            ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(boomFile));
            Bomb bird2 = (Bomb) birds.get(1);
            bird2.SaveState();
            out2.writeObject(bird2);
            out2.close();
            ObjectOutputStream out3 = new ObjectOutputStream(new FileOutputStream(chuckFile));
            Chuck bird3 = (Chuck) birds.get(2);
            bird3.SaveState();
            out3.writeObject(bird3);
            out3.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void saveBlocks() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(iceBlockFile));
        DynamicBodies block1 = (DynamicBodies) blocks.get(0);
        block1.SaveState();
        out.writeObject(block1);
        out.close();
        ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(iceBlock2File));
        DynamicBodies block2 = (DynamicBodies) blocks.get(1);
        block2.SaveState();
        out2.writeObject(block2);
        out2.close();
        ObjectOutputStream out3 = new ObjectOutputStream(new FileOutputStream(iceBlock3File));
        DynamicBodies block3 = (DynamicBodies) blocks.get(2);
        block3.SaveState();
        out3.writeObject(block3);
        out3.close();
        ObjectOutputStream out4 = new ObjectOutputStream(new FileOutputStream(iceBlock4File));
        DynamicBodies block4 = (DynamicBodies) blocks.get(3);
        block4.SaveState();
        out4.writeObject(block4);
        out4.close();
        ObjectOutputStream out5 = new ObjectOutputStream(new FileOutputStream(iceBlock5File));
        DynamicBodies block5 = (DynamicBodies) blocks.get(4);
        block5.SaveState();
        out5.writeObject(block5);
        out5.close();
        ObjectOutputStream out6 = new ObjectOutputStream(new FileOutputStream(iceBlock6File));
        DynamicBodies block6 = (DynamicBodies) blocks.get(5);
        block6.SaveState();
        out6.writeObject(block6);
        out6.close();
        ObjectOutputStream out7 = new ObjectOutputStream(new FileOutputStream(iceBlock7File));
        DynamicBodies block7 = (DynamicBodies) blocks.get(6);
        block7.SaveState();
        out7.writeObject(block7);
        out7.close();
    }
    public static void savePiggies() throws IOException {
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(kingFile));
            King pig1 = (King) piggies.get(0);
            pig1.SaveState();
            out.writeObject(pig1);
            out.close();
            ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(normalFile));
            Normal pig2 = (Normal) piggies.get(1);
            pig2.SaveState();
            out2.writeObject(pig2);
            out2.close();
            ObjectOutputStream out3 = new ObjectOutputStream(new FileOutputStream(muchFile));
            Much pig3 = (Much) piggies.get(2);
            pig3.SaveState();
            out3.writeObject(pig3);
            out3.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void LoadState() throws IOException, ClassNotFoundException {
        if(redFile.exists()){
            bodies.clear();
            loadBirds();
            loadPiggies();
            loadBlocks();
            if(!red.isLaunced)
            {
                bodies.addLast(red);
            }
            if(!bomb.isLaunced)
            {
                bodies.addLast(bomb);
            }
            if(!chuck.isLaunced)
            {
                bodies.addLast(chuck);
            }

        }
        else{
            bodies.addLast(red);
            bodies.addLast(bomb);
            bodies.addLast(chuck);
        }
    }
    public void loadBirds() throws IOException {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(redFile));
            Red bird1 = (Red) in.readObject();
            red.LoadState(bird1);
            in.close();
            ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(boomFile));
            Bomb bird2 = (Bomb) in2.readObject();
            bomb.LoadState(bird2);
            in2.close();
            ObjectInputStream in3 = new ObjectInputStream(new FileInputStream(chuckFile));
            Chuck bird3 = (Chuck) in3.readObject();
            chuck.LoadState(bird3);
            in3.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadPiggies() throws IOException {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(kingFile));
            King pig1 = (King) in.readObject();
            king.LoadState(pig1);
            in.close();
            ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(normalFile));
            Normal pig2 = (Normal) in2.readObject();
            normal.LoadState(pig2);
            in2.close();
            ObjectInputStream in3 = new ObjectInputStream(new FileInputStream(muchFile));
            Much pig3 = (Much) in3.readObject();
            much.LoadState(pig3);
            in3.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadBlocks() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(iceBlockFile));
        DynamicBodies block1 = (DynamicBodies) in.readObject();
        iceBlock.LoadState(block1);
        in.close();
        ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(iceBlock2File));
        DynamicBodies block2 = (DynamicBodies) in2.readObject();
        iceBlock2.LoadState(block2);
        in2.close();
        ObjectInputStream in3 = new ObjectInputStream(new FileInputStream(iceBlock3File));
        DynamicBodies block3 = (DynamicBodies) in3.readObject();
        iceBlock3.LoadState(block3);
        in3.close();
        ObjectInputStream in4 = new ObjectInputStream(new FileInputStream(iceBlock4File));
        DynamicBodies block4 = (DynamicBodies) in4.readObject();
        iceBlock4.LoadState(block4);
        in4.close();
        ObjectInputStream in5 = new ObjectInputStream(new FileInputStream(iceBlock5File));
        DynamicBodies block5 = (DynamicBodies) in5.readObject();
        iceBlock5.LoadState(block5);
        in5.close();
        ObjectInputStream in6 = new ObjectInputStream(new FileInputStream(iceBlock6File));
        DynamicBodies block6 = (DynamicBodies) in6.readObject();
        iceBlock6.LoadState(block6);
        in6.close();
        ObjectInputStream in7 = new ObjectInputStream(new FileInputStream(iceBlock7File));
        DynamicBodies block7 = (DynamicBodies) in7.readObject();
        iceBlock7.LoadState(block7);
        in7.close();
    }
}
