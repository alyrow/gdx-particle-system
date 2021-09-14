package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.math.Vector2;
import io.github.alyrow.gdx.particle.utils.Line;
import io.github.alyrow.gdx.particle.utils.PhysicForces;

/**
 * @author Minecraftian14
 */
public class BlackLine extends PhysicForce {

    private float effect;
    private Line line;

    private float drs = 0.1f; // destruction radius squared
    private final float limit = 10000;
    private final Vector2 cache = new Vector2();
    private float rs;

    public BlackLine() {
    }

    public BlackLine(Line line, float mass, float G) {
        this.line = line;
        effect = G * mass;
    }
    public BlackLine(Line line, float mass) {
        this(line, mass, 10000);
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        line.normal(cache, particle.x, particle.y);

        rs = line.distance(particle.x, particle.y);
        rs *= rs;

        if (rs < drs) particle.deleteParticle();

        return PhysicForces.Clamp(cache.scl(-effect * particle.mass / rs), limit);
    }

    public void setDestructionRadius(float destructionRadius) {
        this.drs = destructionRadius * destructionRadius;
    }
}
