package io.github.AngryBirdsCDAbhi.Birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Chuck extends Bird {
    private static final long serialVersionUID = 3L;
    private Vector2 savedPosition;
    private float savedAngle;
    private Vector2 velocity;
    private boolean touchedground = false;
    public Chuck(World world, float x, float y, float w, float h) {
        super(world, new Sprite(new Texture("Chuck.png")), x, y, w, h,"Chuck.png");
    }
    public void activateSpecial() {
        System.out.println("Chuck's special activated");
        if (!touchedground) {
            Vector2 currentVelocity = this.getBody().getLinearVelocity();
            float angle = currentVelocity.angleRad();
            float magnitude = currentVelocity.len();
            float doubleImpulseMagnitude = magnitude * 100000000f;
            float newVelocityX = doubleImpulseMagnitude * (float)Math.cos(angle);
            float newVelocityY = doubleImpulseMagnitude * (float)Math.sin(angle);
            getBody().setLinearVelocity(newVelocityX, newVelocityY);
        }
    }

    public void SaveState(){
        savedPosition = this.getBody().getPosition();
        savedAngle = this.getBody().getAngle();
        velocity = this.getBody().getLinearVelocity();
    }
    public void LoadState(Chuck bird){
        this.getBody().setTransform(bird.savedPosition, bird.savedAngle);
        this.getBody().setLinearVelocity(bird.velocity);
        this.isLaunced = bird.isLaunced;
    }

}
