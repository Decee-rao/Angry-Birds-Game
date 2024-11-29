package io.github.AngryBirdsCDAbhi;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class StaticBody extends Actor {
    private static final long serialVersionUID = 17L;
    transient World world;
    private float x;
    private float y;
    private float width;
    private float height;
    private transient BodyDef bodyDef;
    private transient Body body;


    public StaticBody(World world, float x, float y, float width, float height, OrthographicCamera camera) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bodyDef = new BodyDef();
        this.bodyDef.position.set(x, y);
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
        this.body = world.createBody(bodyDef);
        createFixture(camera);
    }
    public void createFixture(OrthographicCamera camera) {
        // Create a fixture for the body
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth, 225f);
        body.createFixture(groundBox, 0.0f);
        groundBox.dispose();
    }
    public Body getBody() {
        return body;
    }

}
