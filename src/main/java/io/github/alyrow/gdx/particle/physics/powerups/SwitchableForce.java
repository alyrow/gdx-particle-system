package io.github.alyrow.gdx.particle.physics.powerups;

import com.badlogic.gdx.math.Vector2;
import io.github.alyrow.gdx.particle.physics.PhysicForce;
import io.github.alyrow.gdx.particle.physics.PhysicParticle;
import io.github.alyrow.gdx.particle.utils.switches.Switch;
import io.github.alyrow.gdx.particle.utils.switches.TimedSwitch;

/**
 * @author Minecraftian14
 */
public class SwitchableForce extends PhysicForce {

    private PhysicForce[] forces;
    private Switch swt;

    public SwitchableForce() {
    }

    public SwitchableForce(PhysicForce... forces) {
        this.forces = forces;
        swt = new TimedSwitch(100, forces.length);
    }

    public SwitchableForce(Switch swt, PhysicForce... forces) {
        this.forces = forces;
        this.swt = swt;
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        return forces[swt.getState()].getForce(particle);
    }
}
