package io.github.AngryBirdsCDAbhi.Piggies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Much extends Piggies {
    private static final long serialVersionUID = 13L;
    private boolean isDead = false;
    private boolean shouldDeactivate = false;
    private String texturePath;  // Store the texture path

    public Much(World world, Sprite s, float x, float y, float w, float h) {
        super(world, s, x, y, w, h, "Piggies/Much.png");
        this.texturePath = "Piggies/Much.png";
    }
    public Much(World world, Sprite s, float x, float y) {
        super(world, s, x, y,80,80, "Piggies/Much.png");
        this.texturePath = "Piggies/Much.png";
    }
    public Much(World world, Body body) {
        super(world, body);
    }

    public void setPos(float x, float y) {
        this.getBody().setTransform(x, y, 0);
    }

    public Vector2 getPos() {
        return this.getBody().getPosition();
    }

    public void setSprite(Sprite s) {
        this.sprite = s;
    }

}
