package io.github.alyrow.gdx.particle.physics.powerups;

import com.badlogic.gdx.math.Vector2;
import io.github.alyrow.gdx.particle.physics.PhysicForce;
import io.github.alyrow.gdx.particle.physics.PhysicParticle;

import java.util.function.Supplier;

/**
 * @author Minecraftian14
 */
public class LinearTransitionForce extends LerpedForce {

    private float speed;
    private Supplier<Boolean> condition = () -> getFactor() > 1 || getFactor() < 0;

    public LinearTransitionForce() {
    }

    public LinearTransitionForce(PhysicForce one, PhysicForce two, float speed) {
        super(one, two);
        this.speed = speed;
    }

    public LinearTransitionForce(PhysicForce one, PhysicForce two, float factor, float speed) {
        super(one, two, factor);
        this.speed = speed;
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        addFactor(speed);
        if (condition.get()) speed = -speed;
        return super.getForce(particle);
    }

    public void setCondition(Supplier<Boolean> condition) {
        this.condition = condition;
    }
}
