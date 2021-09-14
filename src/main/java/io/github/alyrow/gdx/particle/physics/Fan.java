package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Minecraftian14
 */
public class Fan extends PhysicForce {

    float speed;
    private Vector2 center;
    private final Vector2 cache = new Vector2();

    public Fan() {
    }

    public Fan(float x, float y, float speed) {
        center = new Vector2(x, y);
        this.speed = speed / 100;
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        return cache.set(particle.y - center.y, center.x - particle.x).scl(speed);
    }

    @Override
    public String toString() {
        return "Fan{ x: " + center.x + "  y: " + center.y + "  speed: " + speed + " }";
    }
}