package io.github.alyrow.gdx.particle.physics.powerups;

import com.badlogic.gdx.math.Vector2;
import io.github.alyrow.gdx.particle.physics.PhysicForce;
import io.github.alyrow.gdx.particle.physics.PhysicParticle;

/**
 * @author Minecraftian14
 */
public class CompoundForce extends PhysicForce {

    PhysicForce[] forces;
    Vector2 cache = new Vector2();

    public CompoundForce() {
    }

    public CompoundForce(PhysicForce... forces) {
        this.forces = forces;
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        cache.set(0, 0);
        for (PhysicForce force : forces) cache.add(force.getForce(particle));
        return cache;
    }

    public PhysicForce[] getPhysicForces() {
        return forces;
    }
}
