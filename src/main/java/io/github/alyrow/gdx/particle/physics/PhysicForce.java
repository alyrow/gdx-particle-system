package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * @author alyrow
 * Well base class of all forces.
 * If you want to create a force, extend your class with it and take a look at {@link PhysicParticle}
 */
public class PhysicForce /*implements Json.Serializable*/ {
    public float vx, vy;
    Vector2 force;

    public Vector2 getForce(PhysicParticle particle) {
        return force;
    }
}
