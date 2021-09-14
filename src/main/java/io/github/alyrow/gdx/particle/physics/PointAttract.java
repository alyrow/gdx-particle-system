package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Minecraftian14
 */
public class PointAttract extends PhysicForce {

    Vector2 cache = new Vector2();
    private Vector2 center;
    private float strength;
    private float drs = 0.3f; // destruction radius squared

    public PointAttract() {
    }

    public PointAttract(float x, float y, float strength) {
        this.center = new Vector2(x, y);
        this.strength = strength;
    }

    public PointAttract(float x, float y, float strength, float drs) {
        this.center = new Vector2(x, y);
        this.strength = strength;
        this.drs = drs;
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        cache.set(center).sub(particle.x, particle.y);
        if (cache.len2() < drs) particle.deleteParticle();
        return cache.nor().scl(strength);
    }

    public void setDestructionRadius(float destructionRadius) {
        this.drs = destructionRadius * destructionRadius;
    }
}
