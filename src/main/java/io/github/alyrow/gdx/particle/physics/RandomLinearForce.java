package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * @author alyrow
 * Random linear force
 */
public class RandomLinearForce extends PhysicForce {

    public float vx_min;
    public float vx_max;
    public float vy_max;
    public float vy_min;
    private final Vector2 cache = new Vector2();

    public RandomLinearForce() {
    }

    /**
     * Random linear force
     *
     * @param vx_min Min value for x axis
     * @param vx_max Max value for x axis
     * @param vy_min Min value for y axis
     * @param vy_max Max value for y axis
     */
    public RandomLinearForce(float vx_min, float vx_max, float vy_min, float vy_max) {
        this.vx_min = vx_min;
        this.vx_max = vx_max;
        this.vy_min = vy_min;
        this.vy_max = vy_max;
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        particle.data_float.computeIfAbsent("rlinearX", k -> MathUtils.random() * (vx_max - vx_min) + vx_min);
        particle.data_float.computeIfAbsent("rlinearY", k -> MathUtils.random() * (vy_max - vy_min) + vy_min);
        return cache.set(particle.data_float.get("rlinearX"), particle.data_float.get("rlinearY"));
    }
}
