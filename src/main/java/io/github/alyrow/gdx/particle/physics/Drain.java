package io.github.alyrow.gdx.particle.physics;

import io.github.alyrow.gdx.particle.physics.powerups.CompoundForce;

/**
 * @author Minecraftian14
 */
public class Drain extends CompoundForce {
    public Drain() {
    }

    public Drain(float x, float y, float strength, float speed) {
        super(new PointAttract(x, y, strength), new Revolution(x, y, speed));
    }
}
