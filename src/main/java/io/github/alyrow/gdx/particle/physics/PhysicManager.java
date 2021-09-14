package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.utils.Array;
import io.github.alyrow.gdx.particle.ParticleView;

/**
 * @author alyrow
 * Physic manager
 */
public class PhysicManager/* implements Json.Serializable*/ {
    public Array<PhysicForce> forces = new Array<>();

    public PhysicManager() {
    }

    /**
     * Add force to particles
     *
     * @param force Force that extend {@link PhysicForce}
     */
    public void addForce(PhysicForce force) {
        forces.add(force);
    }

    /**
     * Remove force
     *
     * @param force Existing forc
     */
    public void removeForce(PhysicForce force) {
        forces.removeValue(force, true);
    }

    /**
     * Internal use only!
     */
    public PhysicParticle getParticleForces(float x, float y, ParticleView view) {
        return new PhysicParticle(x, y, view);
    }
}
