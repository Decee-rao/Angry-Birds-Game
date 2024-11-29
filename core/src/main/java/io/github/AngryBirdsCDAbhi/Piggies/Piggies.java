package io.github.AngryBirdsCDAbhi.Piggies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Piggies extends Actor implements Serializable {
    private static final long serialVersionUID = 15L;

    // Health
    int health = 150;

    // Sprite and Properties
    transient Sprite sprite;  // Mark sprite as transient
    private float height;
    private float width;

    // Body and BodyDef
    private transient BodyDef bodyDef;
    private transient Body body;

    private Vector2 savedPosition;
    private float savedAngle;
    private String texturePath;  // Store the texture path for later use

    // Added fields for BodyDef serialization
    private float bodyDefPosX;
    private float bodyDefPosY;
    private float bodyDefAngle;
    private String bodyDefType;

    private boolean isDead = false;
    private boolean shouldDeactivate = false;
    private Vector2 velocity;

    public Piggies(World world, Sprite s, float x, float y, float w, float h, String texturePath) {
        this.sprite = s;
        this.width = w;
        this.height = h;
        this.sprite.setSize(w, h);
        this.texturePath = texturePath;  // Store texture path
        bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        body.setUserData(s);
        CircleShape shape = new CircleShape();
        shape.setRadius(w / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.00001f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.6f;
        body.createFixture(fixtureDef);
        body.setAngularDamping(0.7f);
        shape.dispose();
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

    public Piggies(World world, Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
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

    public Sprite getSprite() {
        if (sprite == null && texturePath != null) {
            sprite = new Sprite(new Texture(Gdx.files.internal(texturePath)));  // Recreate sprite from stored texture path
        }
        sprite.setSize(width, height);
        return sprite;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(isDead){
            return;
        }
        if (sprite != null) {
            getSprite().setPosition(body.getPosition().x - width / 2, body.getPosition().y - height / 2);
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

    public void die() {
        this.body.setActive(false);
    }

    public void scheduleDeactivation() {
        shouldDeactivate = true;
    }
    public void SaveState(){
        this.savedPosition = this.getBody().getPosition();
        this.savedAngle = this.getBody().getAngle();
        this.velocity = this.getBody().getLinearVelocity();
    }
    public void LoadState(Piggies pig){
        this.getBody().setTransform(pig.savedPosition, pig.savedAngle);
        this.getBody().setLinearVelocity(pig.velocity);
        this.isDead = pig.isDead;
        if(pig.isDead){
            this.deactivate();
        }
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
