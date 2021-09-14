package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Minecraftian14
 */
public class Revolution extends PhysicForce {

    // Black hole specific
    private float speed;
    private Vector2 center;
    private final Vector2 cache = new Vector2();

    public Revolution() {
    }

    public Revolution(float x, float y, float speed) {
        center = new Vector2(x, y);
        this.speed = speed;
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        return cache.set(particle.y - center.y, center.x - particle.x).nor().scl(speed);
    }
}
