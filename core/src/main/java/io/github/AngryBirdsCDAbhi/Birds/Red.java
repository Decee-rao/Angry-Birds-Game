package io.github.AngryBirdsCDAbhi.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Red extends Bird{
    private static final long serialVersionUID = 4L;
    private Vector2 savedPosition;
    private float savedAngle;
    private Vector2 velocity;
    public Red(World world, float x, float y, float w, float h) {
        super(world, new Sprite(new Texture(Gdx.files.internal("red.png"))), x, y, w, h,"red.png");
    }
    public void SaveState(){
        savedPosition = this.getBody().getPosition();
        savedAngle = this.getBody().getAngle();
        velocity = this.getBody().getLinearVelocity();
    }
    public void LoadState(Red bird){
        this.getBody().setTransform(bird.savedPosition, bird.savedAngle);
        this.getBody().setLinearVelocity(bird.velocity);
        this.isLaunced = bird.isLaunced;
    }
}
