package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * @author alyrow
 */
public class ColorPoint extends PhysicForce {

    Vector2 center;
    float radius;
    Color color;
    private final Vector2 cache = new Vector2();

    public ColorPoint() {
    }

    public ColorPoint(Color color, float x, float y, float radius) {
        this.color = color;
        this.center = new Vector2(x, y);
        this.radius = radius;
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        if (Math.sqrt((center.x - particle.x) * (center.x - particle.x) + (center.y - particle.y) * (center.y - particle.y)) <= radius) {
            particle.setColor(new Color((particle.getColor().r + color.r) / 2f, (particle.getColor().g + color.g) / 2f, (particle.getColor().b + color.b) / 2f, (particle.getColor().a + color.a) / 2f)); //particle.getLightColor().add(color).mul(1/2f)
        }

        return cache.set(0, 0);
    }
}
