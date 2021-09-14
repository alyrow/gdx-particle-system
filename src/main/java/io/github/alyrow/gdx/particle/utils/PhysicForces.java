package io.github.alyrow.gdx.particle.utils;

import com.badlogic.gdx.math.Vector2;
import io.github.alyrow.gdx.particle.physics.PhysicForce;
import io.github.alyrow.gdx.particle.physics.PhysicParticle;

/**
 * @author Minecraftian14
 */
public class PhysicForces {

    private static final Vector2 cache = new Vector2();

    public static Vector2 Lerp(PhysicForce one, PhysicForce two, PhysicParticle particle, float factor) {
        return cache.set(one.getForce(particle)).lerp(two.getForce(particle), factor);
    }

    public static Vector2 Clamp(Vector2 vc, float limit) {
        return Clamp(vc, -limit, limit);
    }

    public static Vector2 Clamp(Vector2 vc, float ll, float ul) {
        float mag = vc.len();
        if (mag < ll) return cache.set(vc).nor().scl(ll);
        if (mag > ul) return cache.set(vc).nor().scl(ul);
        return cache.set(vc);
    }
}
