package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.math.Vector2;
import io.github.alyrow.gdx.particle.utils.PhysicForces;

/**
 * @author Minecraftian14
 */
public class BlackHole extends PhysicForce {

    private float effect;
    private Vector2 center;

    private float drs = 0.5f; // destruction radius squared
    private final float limit = 500;
    private final Vector2 cache = new Vector2();
    private float rs;

    public BlackHole() {
    }

    public BlackHole(float x, float y, float mass, float G) {
        center = new Vector2(x, y);
        effect = G * mass;
    }
    public BlackHole(float x, float y, float mass) {
        this(x, y, mass, 40000);
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        cache.set(center.x - particle.x, center.y - particle.y);
        rs = cache.len2();
        if (rs < drs) particle.deleteParticle();

        return PhysicForces.Clamp(cache.nor().scl(effect * particle.mass / rs), limit);
    }

    public void setDestructionRadius(float destructionRadius) {
        this.drs = destructionRadius * destructionRadius;
    }

    public void disableDestructionRadius() {
        drs = 0;
    }
}
