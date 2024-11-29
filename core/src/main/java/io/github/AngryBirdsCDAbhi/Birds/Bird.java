package io.github.AngryBirdsCDAbhi.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Bird extends Actor implements Serializable {

    private static final long serialVersionUID = 1L;

    int health = 100;
    transient Sprite sprite;
    private float height;
    private float width;
    private transient BodyDef bodyDef;
    private transient Body body;

    private String texturePath;
    public boolean isLaunced = false;

    public Bird(World world, Sprite s, float x, float y, float w, float h, String texturePath) {
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
        fixtureDef.restitution = 0.7f;
        body.createFixture(fixtureDef);
        body.setAngularDamping(0.7f);
        shape.dispose();
    }
    public World getWorld(){
        return body.getWorld();
    }
    public Bird(World world, Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public void launch(Vector2 impulse) {
        this.isLaunced = true;
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
        body.setAngularVelocity(0);
        body.setAngularDamping(1f);
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

    public Vector2 getPosition(){
        return new Vector2(body.getPosition().x,body.getPosition().y);
    }
    public void setPosition(Vector2 position){
        body.setTransform(position.x,position.y,body.getAngle());
    }


    public void setDynamic(boolean dynamic) {
        if (dynamic) {
            body.setType(BodyDef.BodyType.DynamicBody);
        }
    }
    public void setStatic(boolean isStatic) {
        if (isStatic) {
            body.setType(BodyDef.BodyType.StaticBody);
        }
    }
    public void setBodyPosition(float x, float y) {
        body.setTransform(x, y, body.getAngle());
    }
    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setDynamic() {
        body.setType(BodyDef.BodyType.DynamicBody);
    }
}
