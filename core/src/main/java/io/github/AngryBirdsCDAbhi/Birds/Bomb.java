package io.github.AngryBirdsCDAbhi.Birds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;

import java.util.List;

public class Bomb extends Bird {
    private static final long serialVersionUID = 2L;
    private Vector2 savedPosition;
    private float savedAngle;
    private Vector2 velocity;
    private boolean touchedground = false;
    public Bomb(World world, float x, float y, float w, float h) {
        super(world, new Sprite(new Texture("Bomb.png")), x, y, w, h,"Bomb.png");
    }
    public void activateSpecial() {
        System.out.println("Boom's special activated");
        if (!touchedground) {
            applyRadialForce();
            enhanceBirdAppearance();
        }
    }
    private void applyRadialForce() {
        float explosionRadius = 200.0f;
        float explosionForce = 10000000.0f;
        Vector2 explosionCenter = getBody().getPosition();
        float halfSize = explosionRadius;
        final Rectangle aabb = new Rectangle(
            explosionCenter.x - halfSize,
            explosionCenter.y - halfSize,
            halfSize * 2,
            halfSize * 2
        );

        // From Box2D manual: "Query the world for all fixtures that potentially overlap the provided AABB."
        // This will call the reportFixture method for each fixture found in the AABB.
        getWorld().QueryAABB(new QueryCallback() {
                                   @Override
                                   public boolean reportFixture(Fixture fixture) {
                                       Body otherBody = fixture.getBody();
                                       if (otherBody == getBody()) return true;
                                       Vector2 bodyPosition = otherBody.getPosition();
                                       float distance = bodyPosition.dst(explosionCenter);

                                       if (distance <= explosionRadius) {
                                           Vector2 forceDirection = bodyPosition.cpy().sub(explosionCenter).nor();
                                           float forceMagnitude = explosionForce * (1 - (distance / explosionRadius));
                                           Vector2 force = forceDirection.scl(forceMagnitude);
                                           otherBody.applyLinearImpulse(force, bodyPosition, true);
                                       }

                                       return true;
                                   }
                               },
            explosionCenter.x - halfSize,
            explosionCenter.y - halfSize,
            explosionCenter.x + halfSize,
            explosionCenter.y + halfSize);
    }
    private void enhanceBirdAppearance() {
        this.getSprite().setColor(Color.RED);
        getBody().setLinearVelocity(getBody().getLinearVelocity().scl(2.0f));
    }
    public void SaveState(){
        savedPosition = this.getBody().getPosition();
        savedAngle = this.getBody().getAngle();
        velocity = this.getBody().getLinearVelocity();
    }
    public void LoadState(Bomb bird){
        this.getBody().setTransform(bird.savedPosition, bird.savedAngle);
        this.getBody().setLinearVelocity(bird.velocity);
        this.isLaunced = bird.isLaunced;
    }
}
