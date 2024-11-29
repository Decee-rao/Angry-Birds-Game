package io.github.AngryBirdsCDAbhi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.AngryBirdsCDAbhi.Birds.Bomb;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DynamicBodies extends Actor implements Serializable {
    private static final long serialVersionUID = 16L;

    private transient Sprite sprite;
    private transient BodyDef bodyDef;
    private transient Body body;
    private float width;
    private float height;
    int health = 1500;
    private Vector2 savedPosition;
    private float savedAngle;
    private Vector2 velocity;

    // Added for sprite serialization
    private String texturePath; // Save the texture path

    // Added for BodyDef serialization
    private float bodyDefPosX;
    private float bodyDefPosY;
    private float bodyDefAngle;
    private String bodyDefType;
    public static final float PPM = 100f; // Pixels per meter
    private boolean isDead = false;
    private boolean shouldDeactivate = false;

    public DynamicBodies(World world, Sprite s, float x, float y, float w, float h) {
        this.sprite = new Sprite(s);
        this.width = w;
        this.height = h;
        this.sprite.setSize(w, h);
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.body = world.createBody(bodyDef);
        this.body.setUserData(s);
        this.body.setTransform(x, y, 0);
        createFixture();
    }
    public DynamicBodies(World world, Sprite s, float x, float y) {
        this.sprite = new Sprite(s);
        this.width = s.getWidth();
        this.height = s.getHeight();
        this.sprite.setSize(width, height);
        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.body = world.createBody(bodyDef);
        this.body.setUserData(s);
        this.body.setTransform(x, y, 0);
        createFixture();
    }


    public DynamicBodies(World world, Body body) {
        this.body = body;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean shouldDeactivate() {
        return shouldDeactivate;
    }

    public void deactivate() {
        die();
        this.getBody().setActive(false);
        isDead = true;
    }
    public void takeDamage() {
        health -= 50;
        if (getHealth() <= 0) {
            scheduleDeactivation();
        }
        System.out.println("Piggy health: " + health);
    }
    public int getHealth() {
        return health;
    }
    public void scheduleDeactivation() {
        shouldDeactivate = true;
    }
    public void die() {
        this.body.setActive(false);
    }

    private void createFixture() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2f, height/2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.00001f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);
        body.setAngularDamping(0.7f);
        shape.dispose();
    }

    public Sprite getSprite() {
        if (sprite == null && texturePath != null) {
            sprite = new Sprite(new Texture(texturePath)); // Recreate sprite from texture path
        }
        return sprite;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(isDead){
            return;
        }
        if (sprite != null) {
            getSprite().setPosition(body.getPosition().x - (width / 2), body.getPosition().y - (height / 2));
            getSprite().setOrigin(width / 2, height / 2);
            getSprite().setRotation(body.getAngle() * 180 / (float) Math.PI);
            getSprite().draw(batch);
        }
    }


    @Override
    protected void positionChanged() {
        if (sprite != null) {
            sprite.setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
            sprite.setRotation(body.getAngle() * MathUtils.radDeg);
        }

        super.positionChanged();
    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public Body getBody() {
        return body;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setBodyDef(BodyDef bodyDef) {
        this.bodyDef = bodyDef;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setLinearImpulse(Vector2 impulse) {
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
    }

    public void SaveState(){
        savedPosition = this.getBody().getPosition();
        savedAngle = this.getBody().getAngle();
        velocity = this.getBody().getLinearVelocity();
    }
    public void LoadState(DynamicBodies body){
        this.getBody().setTransform(body.savedPosition, body.savedAngle);
        this.getBody().setLinearVelocity(body.velocity);
        this.isDead = body.isDead;
        if(body.isDead){
            this.deactivate();
        }
    }
}
