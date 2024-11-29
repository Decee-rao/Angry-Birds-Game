package io.github.AngryBirdsCDAbhi.Piggies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class King extends Piggies {
    private static final long serialVersionUID = 12L;

    public King(World world, Sprite s, float x, float y, float w, float h) {
        super(world, s, x, y, w, h, "King.png");  // Pass texture path to the super constructor
    }

    public King(World world, Sprite s, float x, float y) {
        super(world, s, x, y, 200, 200, "King.png");  // Pass texture path to the super constructor
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
